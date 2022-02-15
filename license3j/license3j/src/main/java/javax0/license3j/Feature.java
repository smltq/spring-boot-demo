package javax0.license3j;

import javax0.license3j.parsers.NumericParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A feature is a single "feature" in a license. It has a
 * <ul>
 *
 * <li>name,</li>
 * <li>a type</li>
 * <li>and a value.</li>
 * </ul>
 *
 * <p>The type can be one of the types that are defined in the enumeration {@link Type}.</p>
 *
 * <p>There is a utility class inside this class called {@link Create} that contains public static methods to
 * create features for each type. Invoking one of those methods is the way to create a new feature instance.</p>
 *
 * <p>A feature can be tested against the type calling one of the {@code boolean} methods {@code isXXX()},
 * where  {@code XXX} is one
 * of the types. Getting the value from a type should be via the methods {@code getXXX}, where, again, {@code XXX} is
 * one of the types. Invoking {@code getXXX} for a feature that has a type that is not {@code XXX} will throw
 * {@link IllegalArgumentException}.</p>
 *
 * <p>Features can be serialized to a byte array invoking the method {@link #serialized()}. The same byte array can be
 * used as an argument to the utility method {@link Create#from(byte[])} to create a {@code Feature} of the same name,
 * and the same value.</p>
 */
public class Feature {
    private static final String[] DATE_FORMAT =
        {"yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH",
            "yyyy-MM-dd"
        };
    private static final int VARIABLE_LENGTH = -1;
    private final String name;
    private final Type type;
    private final byte[] value;

    private Feature(String name, Type type, byte[] value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    private static SimpleDateFormat getUTCDateFormat(String format) {
        final var simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    private static String dateFormat(Object date) {
        return getUTCDateFormat(DATE_FORMAT[0]).format(date);
    }

    /**
     * <p>Parse the string as a date using the different date format listed in the constant {@link #DATE_FORMAT}.
     * Parsing uses the first format in the array, then the second until it finds one that can be used to
     * parse the string as a date.</p>
     *
     * <p>If none of the formats can be used as a date then the method throws {@link IllegalArgumentException}.</p>
     *
     * @param date the date string, presumably formatted
     * @return the {@link Date} object created from the string {@code date}.
     */
    private static Date dateParse(String date) {
        for (var format : DATE_FORMAT) {
            try {
                return getUTCDateFormat(format).parse(date);
            } catch (ParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Cannot parse " + date);
    }

    /**
     * <p>Split up a string representing a feature into three parts</p>
     *
     * <ul>
     *     <li>name</li>
     *     <li>type</li>
     *     <li>value</li>
     * </ul>
     *
     * <p>A feature in string format contains these part in the above order. The name and the type are separated using a
     * {@code :}. The type and the value are separated using a {@code =}. The type is optional. In case it is missing
     * along with the {@code :} before it then the default value {@code STRING} is used as a type.</p>
     *
     * @param feature string represented feature
     * @return a three-element String array containing the name, the type and the value of the feature in this order.
     */
    static String[] splitString(String feature) {
        final int typeEnd = feature.indexOf("=");
        if (typeEnd == -1) {
            throw new IllegalArgumentException("The feature's string representation must have a '=' after the type");
        }
        final var colIndex = feature.substring(0, typeEnd).indexOf(":");
        final var nameEnd = colIndex == -1 ? typeEnd : colIndex;
        final String name = feature.substring(0, nameEnd).trim();
        final String type = nameEnd == typeEnd ? "STRING" : feature.substring(nameEnd + 1, typeEnd).trim();
        final var valueString = feature.substring(typeEnd + 1);
        return new String[]{name, type, valueString};
    }

    /**
     * <p>Create a {@code Feature} object out of the name, type and value represented in the arguments as strings.</p>
     *
     * @param name        the name of the feature to be created
     * @param typeString  the type of the feature represented as a String
     * @param valueString the value of the feature string formatted
     * @return the new {@code Feature} object
     */
    static Feature getFeature(String name, String typeString, String valueString) {
        final var type = Type.valueOf(typeString);
        final var value = type.unstringer.apply(valueString);
        return type.factory.apply(name, value);
    }

    /**
     * @return the name of the feature.
     */
    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return toStringWith(valueString());
    }

    /**
     * <p>Convert a feature to its {@code name:TYPE=value} representation with the separately supplied {@code value}</p>
     *
     * <p>This method is used when the feature has to be formatted in a way that should take the fact into account that
     * the string representation of the feature may be multi-line. In that case the value string is specially
     * formatted.</p>
     *
     * @param value the string representation of the value
     * @return the string representation of the feature
     */
    String toStringWith(String value) {
        return name + type.colonedToString() + "=" + value;
    }

    /**
     * @return the string representation of the value of the feature.
     */
    public String valueString() {
        return type.stringer.apply(type.objecter.apply(this));
    }

    /**
     * Convert a feature to byte array. The bytes will have the following structure
     *
     * <pre>
     *      [4-byte type][4-byte name length][4-byte value length][name][value]
     *  </pre>
     * <p>
     * or
     *
     * <pre>
     *      [4-byte type][4-byte name length][name][value]
     *  </pre>
     * <p>
     * if the length of the value can be determined from the type (some types have fixed length values).
     *
     * @return the byte array representation of the feature
     */
    public byte[] serialized() {
        final var nameBuffer = name.getBytes(StandardCharsets.UTF_8);
        final var typeLength = Integer.BYTES;
        final var nameLength = Integer.BYTES + nameBuffer.length;
        final var valueLength = type.fixedSize == VARIABLE_LENGTH ? Integer.BYTES + value.length : type.fixedSize;
        final var buffer = ByteBuffer.allocate(typeLength + nameLength + valueLength)
            .putInt(type.serialized)
            .putInt(nameBuffer.length);
        if (type.fixedSize == VARIABLE_LENGTH) {
            buffer.putInt(value.length);
        }
        buffer.put(nameBuffer).put(value);
        return buffer.array();
    }

    /*!jamal
    //<editor-fold id="is$Type() for $Type in (Binary,String,Byte,Short,Int,Long,Float,Double,BigInteger,BigDecimal,Date,UUID)">
    {%!@for $Type in (Binary,String,Byte,Short,Int,Long,Float,Double,BigInteger,BigDecimal,Date,UUID)=
    public boolean is$Type() {
        return type == Type.{%@case:upper $Type%};
    }
    %}
    //</editor-fold>
     */
    //<editor-fold id="is$Type() for $Type in (Binary,String,Byte,Short,Int,Long,Float,Double,BigInteger,BigDecimal,Date,UUID)">

    public boolean isBinary() {
        return type == Type.BINARY;
    }

    public boolean isString() {
        return type == Type.STRING;
    }

    public boolean isByte() {
        return type == Type.BYTE;
    }

    public boolean isShort() {
        return type == Type.SHORT;
    }

    public boolean isInt() {
        return type == Type.INT;
    }

    public boolean isLong() {
        return type == Type.LONG;
    }

    public boolean isFloat() {
        return type == Type.FLOAT;
    }

    public boolean isDouble() {
        return type == Type.DOUBLE;
    }

    public boolean isBigInteger() {
        return type == Type.BIGINTEGER;
    }

    public boolean isBigDecimal() {
        return type == Type.BIGDECIMAL;
    }

    public boolean isDate() {
        return type == Type.DATE;
    }

    public boolean isUUID() {
        return type == Type.UUID;
    }

    //</editor-fold>
    // __END__

    /*!jamal
    //<editor-fold id="get$Type for $Type in (Byte, Short, ... Date)">
    {%@comment UUID and BigDecimal are handled separately, because they differ significantly%}\
    {%!@for [separator="\\s*\n\\s*" skipEmpty]($Type,$return,$rType) in `LOOP`
    Binary|value|byte[]
    String|new String(value, StandardCharsets.UTF_8)|String
    Byte|value[0]|byte
    Short|ByteBuffer.wrap(value).getShort()|short
    Int|ByteBuffer.wrap(value).getInt()|int
    Long|ByteBuffer.wrap(value).getLong()|long
    Float|ByteBuffer.wrap(value).getFloat()|float
    Double|ByteBuffer.wrap(value).getDouble()|double
    BigInteger|new BigInteger(value)|BigInteger
    Date|new Date(ByteBuffer.wrap(value).getLong())|Date
    `LOOP`=
    public $rType get$Type() {
        if (type != Type.{%@case:upper $Type%}) {
            throw new IllegalArgumentException("Feature is not {%@case:upper $Type%}");
        }
        return $return;
    }
    %}
    //</editor-fold>
     */
    //<editor-fold id="get$Type for $Type in (Byte, Short, ... Date)">

    public byte[] getBinary() {
        if (type != Type.BINARY) {
            throw new IllegalArgumentException("Feature is not BINARY");
        }
        return value;
    }

    public String getString() {
        if (type != Type.STRING) {
            throw new IllegalArgumentException("Feature is not STRING");
        }
        return new String(value, StandardCharsets.UTF_8);
    }

    public byte getByte() {
        if (type != Type.BYTE) {
            throw new IllegalArgumentException("Feature is not BYTE");
        }
        return value[0];
    }

    public short getShort() {
        if (type != Type.SHORT) {
            throw new IllegalArgumentException("Feature is not SHORT");
        }
        return ByteBuffer.wrap(value).getShort();
    }

    public int getInt() {
        if (type != Type.INT) {
            throw new IllegalArgumentException("Feature is not INT");
        }
        return ByteBuffer.wrap(value).getInt();
    }

    public long getLong() {
        if (type != Type.LONG) {
            throw new IllegalArgumentException("Feature is not LONG");
        }
        return ByteBuffer.wrap(value).getLong();
    }

    public float getFloat() {
        if (type != Type.FLOAT) {
            throw new IllegalArgumentException("Feature is not FLOAT");
        }
        return ByteBuffer.wrap(value).getFloat();
    }

    public double getDouble() {
        if (type != Type.DOUBLE) {
            throw new IllegalArgumentException("Feature is not DOUBLE");
        }
        return ByteBuffer.wrap(value).getDouble();
    }

    public BigInteger getBigInteger() {
        if (type != Type.BIGINTEGER) {
            throw new IllegalArgumentException("Feature is not BIGINTEGER");
        }
        return new BigInteger(value);
    }

    public Date getDate() {
        if (type != Type.DATE) {
            throw new IllegalArgumentException("Feature is not DATE");
        }
        return new Date(ByteBuffer.wrap(value).getLong());
    }

    //</editor-fold>
    // __END__
    public BigDecimal getBigDecimal() {
        if (type != Type.BIGDECIMAL) {
            throw new IllegalArgumentException("Feature is not BIGDECIMAL");
        }
        var bb = ByteBuffer.wrap(value);
        var scale = bb.getInt(value.length - Integer.BYTES);

        return new BigDecimal(new BigInteger(Arrays.copyOf(value, value.length - Integer.BYTES)), scale);
    }

    public UUID getUUID() {
        if (type != Type.UUID) {
            throw new IllegalArgumentException("Feature is not UUID");
        }
        var bb = ByteBuffer.wrap(value);
        final var ls = bb.getLong();
        final var ms = bb.getLong();
        return new UUID(ms, ls);
    }

    /*!jamal
    private enum Type {{%@counter:define id=typeSerial%}\
    {%!@for [separator="\\s*\n\\s*" skipEmpty]($type,$length,$cast,$stringer,$unstringer,$sep) in `LOOP`
    binary|VARIABLE_LENGTH|byte[]|ba -> Base64.getEncoder().encodeToString((byte[]) ba)|enc -> Base64.getDecoder().decode(enc)|
    string|VARIABLE_LENGTH|||s -> s|
    byte|||b -> String.format("0x%02X", (Byte) b)|NumericParser.Byte::parse|
    short||||NumericParser.Short::parse|
    int|Integer.BYTES|Integer||NumericParser.Int::parse|
    long||||NumericParser.Long::parse|
    float||||Float::parseFloat|
    double||||Double::parseDouble|
    bigInteger|VARIABLE_LENGTH|||BigInteger::new|
    bigDecimal|VARIABLE_LENGTH|||BigDecimal::new|
    date|Long.BYTES||Feature::dateFormat|Feature::dateParse|
    UUID|2 * Long.BYTES|java.util.UUID||java.util.UUID::fromString|;
    `LOOP`=
        {%@case:upper $type%}({%typeSerial%}, {%#if/$length/$length/{%@case:cap $type%}.BYTES%},
                Feature::get{%@case:cap $type%},
                (name, value) -> Create.{%@replace /$type/UUID/uuid%}Feature(name, ({%#if/$cast/$cast/{%@case:cap $type%}%}) value),
                {%#if/$stringer/$stringer/Object::toString%}, $unstringer){%@if/$sep/$sep/,%}
%}
     */
    private enum Type {    
        BINARY(1, VARIABLE_LENGTH,
                Feature::getBinary,
                (name, value) -> Create.binaryFeature(name, (byte[]) value),
                ba -> Base64.getEncoder().encodeToString((byte[]) ba), enc -> Base64.getDecoder().decode(enc)),

        STRING(2, VARIABLE_LENGTH,
                Feature::getString,
                (name, value) -> Create.stringFeature(name, (String) value),
                Object::toString, s -> s),

        BYTE(3, Byte.BYTES,
                Feature::getByte,
                (name, value) -> Create.byteFeature(name, (Byte) value),
                b -> String.format("0x%02X", (Byte) b), NumericParser.Byte::parse),

        SHORT(4, Short.BYTES,
                Feature::getShort,
                (name, value) -> Create.shortFeature(name, (Short) value),
                Object::toString, NumericParser.Short::parse),

        INT(5, Integer.BYTES,
                Feature::getInt,
                (name, value) -> Create.intFeature(name, (Integer) value),
                Object::toString, NumericParser.Int::parse),

        LONG(6, Long.BYTES,
                Feature::getLong,
                (name, value) -> Create.longFeature(name, (Long) value),
                Object::toString, NumericParser.Long::parse),

        FLOAT(7, Float.BYTES,
                Feature::getFloat,
                (name, value) -> Create.floatFeature(name, (Float) value),
                Object::toString, Float::parseFloat),

        DOUBLE(8, Double.BYTES,
                Feature::getDouble,
                (name, value) -> Create.doubleFeature(name, (Double) value),
                Object::toString, Double::parseDouble),

        BIGINTEGER(9, VARIABLE_LENGTH,
                Feature::getBigInteger,
                (name, value) -> Create.bigIntegerFeature(name, (BigInteger) value),
                Object::toString, BigInteger::new),

        BIGDECIMAL(10, VARIABLE_LENGTH,
                Feature::getBigDecimal,
                (name, value) -> Create.bigDecimalFeature(name, (BigDecimal) value),
                Object::toString, BigDecimal::new),

        DATE(11, Long.BYTES,
                Feature::getDate,
                (name, value) -> Create.dateFeature(name, (Date) value),
                Feature::dateFormat, Feature::dateParse),

        UUID(12, 2 * Long.BYTES,
                Feature::getUUID,
                (name, value) -> Create.uuidFeature(name, (java.util.UUID) value),
                Object::toString, java.util.UUID::fromString);

        //__END__
        /**
         * Every type has a serialized value that is used in the serialized format. This value is fixed and does not
         * depend on the ordinal value of the enum. This is to keep the serialized format backward compatible if ever
         * there is a new type inserted, or an old type deleted from this enum.
         */
        final int serialized;
        /**
         * The size if a value of this type when it is serialized. When the value cannot be represented on a fixed number
         * of bytes then this value is {@link #VARIABLE_LENGTH}, that is {@code -1}.
         */
        final int fixedSize;
        /**
         * A function that extracts the value from a feature and returns it as an object of the appropriate type.
         */
        final Function<Feature, Object> objecter;
        /**
         * A bi-function that creates a new feature from a name and an object.
         */
        final BiFunction<String, Object, Feature> factory;
        final Function<Object, String> stringer;
        final Function<String, Object> unstringer;

        Type(int serialized,
             int fixedSize,
             Function<Feature, Object> objecter,
             BiFunction<String, Object, Feature> factory,
             Function<Object, String> stringer,
             Function<String, Object> unstringer) {
            this.serialized = serialized;
            this.fixedSize = fixedSize;
            this.stringer = stringer;
            this.objecter = objecter;
            this.unstringer = unstringer;
            this.factory = factory;
        }

        /**
         * <p>Get the string representation of the type with a preceding colon in front of it. When the type is
         * {@code STRING} then the return value is empty string considering that {@code STRING} is the default
         * type in the string representation of a feature.</p>
         *
         * @return a '{@code :}' character and the name of the type or empty string in case the type is {@code STRING}.
         */
        public String colonedToString() {
            return this == Type.STRING ? "" : ":" + this;
        }

    }

    public static class Create {
        private Create() {
        }

        /*!jamal
        //<editor-fold id="xxFeatures">
        {%!@for [separator="\\s*\n\\s*" skipEmpty] ($type,$vType,$value) in `LOOP`
Binary|byte[]|value
String|String|value.getBytes(StandardCharsets.UTF_8)
Byte|Byte|new byte[]{value}
Short|Short|ByteBuffer.allocate(Short.BYTES).putShort(value).array()
Int|Integer|ByteBuffer.allocate(Integer.BYTES).putInt(value).array()
Long|Long|ByteBuffer.allocate(Long.BYTES).putLong(value).array()
Float|Float|ByteBuffer.allocate(Float.BYTES).putFloat(value).array()
Double|Double|ByteBuffer.allocate(Double.BYTES).putDouble(value).array()
BigInteger|BigInteger|value.toByteArray()
uuid|java.util.UUID|ByteBuffer.allocate(2 * Long.BYTES).putLong(value.getLeastSignificantBits()).putLong(value.getMostSignificantBits()).array()
Date|Date|ByteBuffer.allocate(Long.BYTES).putLong(value.getTime()).array()
        `LOOP`=
        /**
         * Create a new {%@case:decap $type%} feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         *{%@comment%}/
        public static Feature {%@case:decap $type%}Feature(String name, $vType value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.{%@case:upper $type%}, $value);
        }
%}
        //</editor-fold>
         */
        //<editor-fold id="xxFeatures">

        /**
         * Create a new binary feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature binaryFeature(String name, byte[] value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.BINARY, value);
        }

        /**
         * Create a new string feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature stringFeature(String name, String value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.STRING, value.getBytes(StandardCharsets.UTF_8));
        }

        /**
         * Create a new byte feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature byteFeature(String name, Byte value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.BYTE, new byte[]{value});
        }

        /**
         * Create a new short feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature shortFeature(String name, Short value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.SHORT, ByteBuffer.allocate(Short.BYTES).putShort(value).array());
        }

        /**
         * Create a new int feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature intFeature(String name, Integer value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.INT, ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
        }

        /**
         * Create a new long feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature longFeature(String name, Long value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.LONG, ByteBuffer.allocate(Long.BYTES).putLong(value).array());
        }

        /**
         * Create a new float feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature floatFeature(String name, Float value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.FLOAT, ByteBuffer.allocate(Float.BYTES).putFloat(value).array());
        }

        /**
         * Create a new double feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature doubleFeature(String name, Double value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.DOUBLE, ByteBuffer.allocate(Double.BYTES).putDouble(value).array());
        }

        /**
         * Create a new bigInteger feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature bigIntegerFeature(String name, BigInteger value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.BIGINTEGER, value.toByteArray());
        }

        /**
         * Create a new uuid feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature uuidFeature(String name, java.util.UUID value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.UUID, ByteBuffer.allocate(2 * Long.BYTES).putLong(value.getLeastSignificantBits()).putLong(value.getMostSignificantBits()).array());
        }

        /**
         * Create a new date feature.
         * @param name the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature dateFeature(String name, Date value) {
            Objects.requireNonNull(value);
            return new Feature(name, Type.DATE, ByteBuffer.allocate(Long.BYTES).putLong(value.getTime()).array());
        }

        //</editor-fold>
        //__END__

        /**
         * Create a new BigDecimal feature.
         *
         * @param name  the name of the new feature
         * @param value the value for the new feature. {@code null} value will throw an exception
         * @return the newly created feature object
         */
        public static Feature bigDecimalFeature(String name, BigDecimal value) {
            Objects.requireNonNull(value);
            byte[] b = value.unscaledValue().toByteArray();
            return new Feature(name, Type.BIGDECIMAL, ByteBuffer.allocate(Integer.BYTES + b.length)
                .put(b)
                .putInt(value.scale())
                .array());
        }

        /**
         * Create a feature from a string representation of the feature. The feature has to have the following format
         * <pre>
         *     name:TYPE=value
         * </pre>
         * the {@code :TYPE} part may be missing in case the feature type is {@code STRING}. The value has to be the
         * string representation of the value that is different for each type.
         *
         * @param s the feature as string
         * @return the new object created from the string
         */
        public static Feature from(String s) {
            Objects.requireNonNull(s);
            final var parts = Feature.splitString(s);
            return getFeature(parts[0], parts[1], parts[2]);
        }

        /**
         * Create the feature from the binary serialized format. The format is defined in the documentation of
         * the method {@link #serialized()}.
         *
         * @param serialized the serialized format.
         * @return a new feature object.
         */
        public static Feature from(byte[] serialized) {
            Objects.requireNonNull(serialized);
            if (serialized.length < Integer.BYTES * 2) {
                throwBinaryWayTooShort(serialized.length);
            }
            var bb = ByteBuffer.wrap(serialized);
            var typeSerialized = bb.getInt();
            final Type type = typeFrom(typeSerialized);
            final var nameLength = bb.getInt();
            if (nameLength < 0) {
                throwBinaryTooLong("Name");
            }
            final var valueLength = type.fixedSize == VARIABLE_LENGTH ? bb.getInt() : type.fixedSize;
            if (valueLength < 0) {
                throwBinaryTooLong("Value");
            }
            final var nameBuffer = new byte[nameLength];
            if (nameLength > 0) {
                if (bb.remaining() < nameLength) {
                    throwBinaryTooShort(valueLength + nameLength - bb.remaining());
                }
                bb.get(nameBuffer);
            }
            final var value = new byte[valueLength];
            if (valueLength > 0) {
                if (bb.remaining() < valueLength) {
                    throwBinaryTooShort(valueLength - bb.remaining());
                }
                bb.get(value);
            }
            if (bb.remaining() > 0) {
                throwBinaryTooLong(serialized.length, bb.remaining());
            }
            final var name = new String(nameBuffer, StandardCharsets.UTF_8);
            return new Feature(name, type, value);
        }

        public static void throwBinaryWayTooShort(int len) {
            throw new IllegalArgumentException("Cannot load feature from a byte array that has "
                + len + " bytes which is < " + (2 * Integer.BYTES));
        }

        public static void throwBinaryTooShort(int len) {
            throw new IllegalArgumentException("Feature binary is too short. It is " + len + " bytes shy.");
        }

        public static void throwBinaryTooLong(int len, int extra) {
            throw new IllegalArgumentException("Cannot load feature from a byte array that has "
                + len + " bytes which is " + extra + " bytes too long");
        }

        public static void throwBinaryTooLong(String s) {
            throw new IllegalArgumentException(s + " size is too big. 31bit length should be enough.");
        }

        private static Type typeFrom(int typeSerialized) {
            for (final var type : Type.values()) {
                if (type.serialized == typeSerialized) {
                    return type;
                }
            }
            throw new IllegalArgumentException("The deserialized form has a type value " + typeSerialized + " which is not valid.");
        }
    }
}






