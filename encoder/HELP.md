# 主流加密算法

## 对称加密

对称加密指加密和解密使用相同密钥的加密算法，有时又叫传统密码算法而在大多数的对称算法中，加密密钥和解密密钥是相同的，所以也称这种加密算法为秘密密钥算法或单密钥算法。它要求发送方和接收方在安全通信之前，商定一个密钥。对称算法的安全性依赖于密钥，泄漏密钥就意味着任何人都可以对他们发送或接收的消息解密，所以密钥的保密性对通信性至关重要。对称加密算法的优点是算法公开、计算量小、加密速度快、加密效率高。常见的对称加密算法有：

### 1.DES

DES全称为Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法，1976年被美国联邦政府的国家标准局确定为联邦资料处理标准（FIPS），随后在国际上广泛流传开来。DES算法的入口参数有三个:Key、Data、Mode。其中Key为7个字节共56位,是DES算法的工作密钥;Data为8个字节64位,是要被加密或被解密的数据;Mode为DES的工作方式,有两种:加密或解密。

### 2.3DES

3DES（或称为Triple DES）是三重数据加密算法（TDEA，Triple Data Encryption Algorithm）块密码的通称。它相当于是对每个数据块应用三次DES加密算法。由于计算机运算能力的增强，原版DES密码的密钥长度变得容易被暴力破解；3DES即是设计用来提供一种相对简单的方法，即通过增加DES的密钥长度来避免类似的攻击，而不是设计一种全新的块密码算法。

### 3.Blowfish

Blowfish算法是一个64位分组及可变密钥长度的对称密钥分组密码算法，可用来加密64比特长度的字符串。32位处理器诞生后，Blowfish算法因其在加密速度上超越了DES而引起人们的关注。Blowfish算法具有加密速度快、紧凑、密钥长度可变、可免费使用等特点，已被广泛使用于众多加密软件。

### 4.国际数据加密算法（IDEA）

IDEA是International Data Encryption Algorithm的缩写，即国际数据加密算法，它的原型是1990年由瑞士联邦技术学院X.J.Lai和Massey提出的PES。1992年，Lai和Massey对PES进行了改进和强化，产生了IDEA。这是一个非常成功的分组密码，并且广泛的应用在安全电子邮件PGP中

### 5.RC5

RC5是一种因简洁著称的对称分组加密算法。由罗纳德·李维斯特于1994年设计，“RC”代表“Rivest Cipher”，或者“Ron's Code”（相较于RC2和RC4）。高级加密标准（AES）的候选算法之一RC6是基于RC5的。

### 6.AES

高级加密标准（英语：Advanced Encryption Standard，缩写：AES），在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。经过五年的甄选流程，高级加密标准由美国国家标准与技术研究院（NIST）于2001年11月26日发布于FIPS PUB 197，并在2002年5月26日成为有效的标准。2006年，高级加密标准已然成为对称密钥加密中最流行的算法之一。

## 非对称加密

### 1.RSA算法
RSA公钥加密算法是1977年由罗纳德·李维斯特（Ron Rivest）、阿迪·萨莫尔（Adi Shamir）和伦纳德·阿德曼（Leonard Adleman）一起提出的。当时他们三人都在麻省理工学院工作。RSA就是他们三人姓氏开头字母拼在一起组成的。
RSA是目前最有影响力的公钥加密算法，它能够抵抗到目前为止已知的绝大多数密码攻击，已被ISO推荐为公钥数据加密标准。

[点击查看阮一峰对RSA算法的介绍](http://www.ruanyifeng.com/blog/2013/06/rsa_algorithm_part_one.html)

### 2.DSA

DSA（Digital Signature Algorithm）是Schnorr和ElGamal签名算法的变种，被美国NIST作为DSS(DigitalSignature Standard)。
DSA加密算法主要依赖于整数有限域离散对数难题，素数P必须足够大，且p-1至少包含一个大素数因子以抵抗Pohlig &Hellman算法的攻击。M一般都应采用信息的HASH值。DSA加密算法的安全性主要依赖于p和g，若选取不当则签名容易伪造，应保证g对于p-1的大素数因子不可约。其安全性与RSA相比差不多。
DSA 一般用于数字签名和认证。在DSA数字签名和认证中，发送者使用自己的私钥对文件或消息进行签名，接受者收到消息后使用发送者的公钥来验证签名的真实性。DSA只是一种算法，和RSA不同之处在于它不能用作加密和解密，也不能进行密钥交换，只用于签名,它比RSA要快很多

### 3.ECC椭圆曲线加密算法

椭圆曲线加密算法(ECC) 是基于椭圆曲线数学的一种公钥加密的算法,随着分解大整数方法的进步以及完善、计算机计算速度的提高以及网络的发展，RSA的使用率越来越高.但是为了安全。其密钥的长度一直保守诟病，于是ECC这种新算法逐步走上了现在加密算法的这个大舞台，其使用率和重要性都在逐年上升

### 4.MD5

MD5消息摘要算法（英语：MD5 Message-Digest Algorithm），一种被广泛使用的密码散列函数，可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致。MD5由美国密码学家罗纳德·李维斯特（Ronald Linn Rivest）设计，于1992年公开，用以取代MD4算法。这套算法的程序在 RFC 1321 中被加以规范。

将数据（如一段文字）运算变为另一固定长度值，是散列算法的基础原理。

1996年后被证实存在弱点，可以被加以破解，对于需要高度安全性的数据，专家一般建议改用其他算法，如SHA-2。2004年，证实MD5算法无法防止碰撞（collision），因此不适用于安全性认证，如SSL公开密钥认证或是数字签名等用途。

### 5.Bcrypt

bcrypt是一个由Niels Provos以及David Mazières根据Blowfish加密算法所设计的密码散列函数，于1999年在USENIX中展示。实现中bcrypt会使用一个加盐的流程以防御彩虹表攻击，同时bcrypt还是适应性函数，它可以借由增加迭代之次数来抵御日益增进的计算机运算能力透过暴力法破解。

由bcrypt加密的文件可在所有支持的操作系统和处理器上进行转移。它的口令必须是8至56个字符，并将在内部被转化为448位的密钥。然而，所提供的所有字符都具有十分重要的意义。密码越强大，您的数据就越安全。

除了对您的数据进行加密，默认情况下，bcrypt在删除数据之前将使用随机数据三次覆盖原始输入文件，以阻挠可能会获得您的计算机数据的人恢复数据的尝试。如果您不想使用此功能，可设置禁用此功能。

具体来说，bcrypt使用保罗·柯切尔的算法实现。随bcrypt一起发布的源代码对原始版本作了略微改动。

### 6.scrypt

在密码学中，scrypt（念作“ess crypt”）是Colin Percival于2009年所发明的金钥推衍函数，当初设计用在他所创立的Tarsnap服务上。设计时考虑到大规模的客制硬件攻击而刻意设计需要大量内存运算。2016年，scrypt算法发布在RFC 7914。scrypt的简化版被用在数个密码货币的工作量证明（Proof-of-Work）上。


## Spring Security 5.x的PasswordEncoderFactories解析

PasswordEncoderFactories是Spring Security创建DelegatingPasswordEncoder对象的工厂类。该工厂所创建的DelegatingPasswordEncoder默认使用bcrypt算法用于加密，并且能够用于匹配以下几种密码类型 :

- ldap
- MD4
- MD5
- noop (明文密码)
- pbkdf2
- scrypt
- SHA-1
- SHA-256
- sha256

### 源代码

```java
	/**
	 * Creates a {@link DelegatingPasswordEncoder} with default mappings. Additional
	 * mappings may be added and the encoding will be updated to conform with best
	 * practices. However, due to the nature of {@link DelegatingPasswordEncoder} the
	 * updates should not impact users. The mappings current are:
	 *
	 * <ul>
	 * <li>bcrypt - {@link BCryptPasswordEncoder} (Also used for encoding)</li>
	 * <li>ldap - {@link org.springframework.security.crypto.password.LdapShaPasswordEncoder}</li>
	 * <li>MD4 - {@link org.springframework.security.crypto.password.Md4PasswordEncoder}</li>
	 * <li>MD5 - {@code new MessageDigestPasswordEncoder("MD5")}</li>
	 * <li>noop - {@link org.springframework.security.crypto.password.NoOpPasswordEncoder}</li>
	 * <li>pbkdf2 - {@link Pbkdf2PasswordEncoder}</li>
	 * <li>scrypt - {@link SCryptPasswordEncoder}</li>
	 * <li>SHA-1 - {@code new MessageDigestPasswordEncoder("SHA-1")}</li>
	 * <li>SHA-256 - {@code new MessageDigestPasswordEncoder("SHA-256")}</li>
	 * <li>sha256 - {@link org.springframework.security.crypto.password.StandardPasswordEncoder}</li>
	 * </ul>
	 *
	 * @return the {@link PasswordEncoder} to use
	 */
	@SuppressWarnings("deprecation")
	public static PasswordEncoder createDelegatingPasswordEncoder() {
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new org.springframework.security.crypto.password.LdapShaPasswordEncoder());
		encoders.put("MD4", new org.springframework.security.crypto.password.Md4PasswordEncoder());
		encoders.put("MD5", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("SHA-1", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());

		return new DelegatingPasswordEncoder(encodingId, encoders);
	}
```

创建 DelegatingPasswordEncoder 实例的工厂方法，encodingId为默认加密算法，将Spring Security提供的所有PasswordEncoder实现都包装到所创建的DelegatingPasswordEncoder中，当用于匹配密码时，密码密文的格式是 : "{encoderId}abc"，DelegatingPasswordEncoder会解析出密码中的"encoderId"从encoders map中找到相应的PasswordEncoder去检验输入的密码和密码密文中的"abc"是否匹配

### 示例代码

```java
@Slf4j
public class PasswordEncoderFactoriesTest {
    public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        log.info("PasswordEncoderFactories默认加密算法是bcrypt");
        String encodeStr1 = passwordEncoder.encode(password);
        String encodeStr2 = passwordEncoder.encode(password);
        log.info("明文==>{},密文==>{},是否匹配==>{}", password, encodeStr1, passwordEncoder.matches(password, encodeStr1));
        log.info("明文==>{},密文==>{},是否匹配==>{}", password, encodeStr2, passwordEncoder.matches(password, encodeStr2));

        String[] encodes = {"bcrypt", "ldap", "MD4", "MD5", "noop", "pbkdf2", "scrypt", "SHA-1", "SHA-256", "sha256"};

        log.info("=========================================批量测试=========================================");
        List<String> encodeList = new ArrayList();
        for (String encode : encodes) {
            passwordEncoder = newPasswordEncoder(encode);
            String encodeStr = passwordEncoder.encode(password);
            encodeList.add(encodeStr);
            log.info("{}算法,明文==>{},密文==>{}", encode, password, encodeStr);
            log.info("密文和明文匹配 :{}", passwordEncoder.matches(password, encodeStr));
        }
    }

    public static PasswordEncoder newPasswordEncoder(final String encoderType) {

        switch (encoderType) {
            case "bcrypt":
                return new BCryptPasswordEncoder();
            case "ldap":
                return new org.springframework.security.crypto.password.LdapShaPasswordEncoder();
            case "MD4":
                return new org.springframework.security.crypto.password.Md4PasswordEncoder();
            case "MD5":
                return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("MD5");
            case "noop":
                return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
            case "pbkdf2":
                return new Pbkdf2PasswordEncoder();
            case "scrypt":
                return new SCryptPasswordEncoder();
            case "SHA-1":
                return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-1");
            case "SHA-256":
                return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
            case "sha256":
                return new org.springframework.security.crypto.password.StandardPasswordEncoder();
            default:
                return NoOpPasswordEncoder.getInstance();
        }
    }
}
```

### 示例代码控制台输出

```cfml
10:55:57.445 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - PasswordEncoderFactories默认加密算法是bcrypt
10:55:57.774 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 明文==>123456,密文==>{bcrypt}$2a$10$GyBTqqaxJ71LK8TruE4jqOkjgEfUVUSR9HpVKXHmPKHcj3sZRHJEq,是否匹配==>true
10:55:57.868 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 明文==>123456,密文==>{bcrypt}$2a$10$JXwZWyZ5agNLuABZm03bju7CY1a6oafu623bOfQOBAZvviVPw9/eS,是否匹配==>true
10:55:57.868 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - =========================================批量测试=========================================
10:55:57.977 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - bcrypt算法,明文==>123456,密文==>$2a$10$YzAS9Kt3lAmoKVP1XXqSnu8F8O/t.wu/7rhePtsS41ZactBPf5g5i
10:55:58.086 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:58.088 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - ldap算法,明文==>123456,密文==>{SSHA}6plLy61WbK9UQhn1CWdpCcXitaFadSZye/j25A==
10:55:58.089 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - MD4算法,明文==>123456,密文==>{Qzz1t9tzRfKL9pORm48J+7y5WixLz9FCKwhQIlwOqBI=}c86bf96ce1d0ede7b410c4ef9863359b
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - MD5算法,明文==>123456,密文==>{L0S2FjwL2tXdwotBho0fgUrIRpBaVMPgJsxUiSWtV9E=}7f8fb8fac4922ceed555f223bcdf8361
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - noop算法,明文==>123456,密文==>123456
10:55:58.091 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:58.903 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - pbkdf2算法,明文==>123456,密文==>b25a5d4b5a352d5506b05c37b89b53117c5952a4f8a28d06f60bc9eaecc7a9ebe0325f077f6ab6b7
10:55:59.355 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:59.545 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - scrypt算法,明文==>123456,密文==>$e0801$sy5MK3+yFPMdBHHUiheU6oUfYrTNAhdzRSp/qHkfHb6jTPOiCOpumILt1yKBbUDyPJu0gCo/40oTEI8mm9yxaw==$s3GapoqS1ZRsWiLBJBUKN2lazcovqgr/dP1yAEYCdvQ=
10:55:59.616 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:59.616 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - SHA-1算法,明文==>123456,密文==>{nXs3ooYFHHvv5mp/Dl/gHoeXwPdx2YWt9lZH3GeNXVg=}f5c986dddd9997626dffad2f9b4cad5e19fedef2
10:55:59.616 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:59.616 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - SHA-256算法,明文==>123456,密文==>{HmaQe0Qx9Tw3KKM6UoDxX9udUJtIiN4XD83qwDYunsg=}642c273f94b9ec30572f25cea0c958d7b62a48aa4527573b0e936b97cc762a4d
10:55:59.616 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
10:55:59.618 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - sha256算法,明文==>123456,密文==>cbbf80ab6f00508cbd016da31cceb613205059b5e38b4a8ec24e7d0cc7bcb296ef7db11cba2e1409
10:55:59.620 [main] INFO com.easy.encoder.PasswordEncoderFactoriesTest - 密文和明文匹配 :true
```

### 注意事项

使用spring boot 2.1.7版本，pom.xml需要引入加解密码包，如下所示：

```xml
        <!-- 加解密包 -->
        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.62</version>
        </dependency>
```

否则执行会报错：java.lang.NoClassDefFoundError: org/bouncycastle/crypto/generators/SCrypt，详细报错代码

```java
Exception in thread "main" java.lang.NoClassDefFoundError: org/bouncycastle/crypto/generators/SCrypt
	at org.springframework.security.crypto.scrypt.SCryptPasswordEncoder.digest(SCryptPasswordEncoder.java:167)
	at org.springframework.security.crypto.scrypt.SCryptPasswordEncoder.encode(SCryptPasswordEncoder.java:126)
	at com.easy.encoder.PasswordEncoderFactoriesTest.main(PasswordEncoderFactoriesTest.java:32)
Caused by: java.lang.ClassNotFoundException: org.bouncycastle.crypto.generators.SCrypt
	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 3 more
```

查看报错代码，发现SCrypt类找不到引起的异常，引入加解密包解决问题。