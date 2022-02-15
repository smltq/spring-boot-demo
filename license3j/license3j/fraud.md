# Fraud license3j

There is no 100% secure license handling and license3j can be overcome. This is
not the question how to make it total secure, but rather how much a code can do
to make so secure that there is no reason to brake the license enforcement.

License3j runs in a Java environment. The easiest way to store the key
file is in a resource file inside a jar or war file. The format of this file is
simple and a ZIP program can be used even by a novice user to replace the key
file to their own and generate their own licenses. To avoid this
the application can check the SHA-512 of the key or the application can embed the public key into the
Java code and thus when compiled into the `.class` file. That way it is much harder to replace the key
because it requires that the class file, your code's class file is also changed.

The checksum of the key or the key itself has to be copied into the Java source code
and you may even apply (if you want) some obfuscation code so it will be
hard to replace it and when the key is loaded it will ensure that the
key itself was not tampered. Note that License3j prior version 3.0.0 provided an API to load the public key
from a file or resource giving the digest as an extra argument. Starting with version 3.0.0 it is recommended
to embed the whole public key into the application and not only the digest of the key, and thus this API
is no longer supported.

License3j provides easy way to get this byte array in Java syntax. Just dump an encoded license onto your screen using
the program `java -cp license3j-3.0.0.jar javax0.license3j.Repl` and you will be able to get the license in Java source
code dumped to the screen.
