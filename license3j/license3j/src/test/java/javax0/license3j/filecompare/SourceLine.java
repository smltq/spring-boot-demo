package javax0.license3j.filecompare;

class SourceLine implements Comparable<SourceLine> {
    final private String line;

    SourceLine(String line) {
        this.line = line;
    }

    boolean eof() {
        return line == null;
    }

    boolean isComment() {
        return line != null && line.startsWith("#");
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return (line == null && ((SourceLine) other).line == null)
            || (line != null && line.equals(((SourceLine) other).line));
    }

    public String toString() {
        return line;
    }

    @Override
    public int compareTo(SourceLine o) {
        return line.compareTo(o.line);
    }
}