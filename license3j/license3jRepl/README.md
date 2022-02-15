# License3jRepl

Read Eval Print Loop application for License3j library to manage licenses and keys.

This application can be used to create key pairs, create licenses and to sign licenses. The typical workflow is to
create a key pair that will be used to sign and verify licenses. The private key should be stored in some secure place,
like a `.key` directory in your home directory on your machine, or on a USB drive stored in a bank vault. It all depends
on your security needs.

The public key, as it is public can be advertised, but most likely you will embed it into the code of your application.

# Start the application

## Starting Java local
To start the application you should issue the command:

```
java -jar License3jrepl-3.1.5-jar-with-dependencies.jar
```

The versions in the sample line above are one of the current versions when this documentation is created. It is
recommended to use the latest released versions. The License3j and License3Repl libraries are released with matching
version numbers.

The jar file can be downloaded from github from the URL

https://github.com/verhas/License3jRepl/releases/download/3.1.5/License3jrepl-3.1.5-jar-with-dependencies.jar

## Starting via Docker

Another possibility is to start the application using docker:

```
docker container run -it -v `pwd`:/opt  verhas/license3jrepl
```

This will start the latest version and will work in the current working directory to create keys, licenses etc. When
started this way you must have all the files in the actual directory or below because the docker container does not
allow the code to see any other files only what is below the current working directory mounted to `/opt` using the `-p`
option of the docker command.

It is recommended to use the latest released versions. The License3j and License3Repl libraries are released with
matching version numbers.

## Interacting with the program

If the command line is correct, and the libraries can be found by the Java environment then you will see the

```
L3j> $
```

prompt. At the startup the program also prints its title and the version (version 3.1.5 and newer). Check that the
version you started matches the version you expected. The first thing you can try is to ask for help.

```
L3j> $ help
Available commands:
alias myalias command
exit
help
feature name:TYPE=value
licenseLoad [format=TEXT*|BINARY|BASE64] fileName
saveLicense [format=TEXT*|BINARY|BASE64] fileName
loadPrivateKey [format=BINARY*|BASE64] keyFile
loadPublicKey [format=BINARY*|BASE64] keyFile
sign [digest=SHA-512]
generateKeys [algorithm=RSA] [size=2048] [format=BINARY*|BASE64] public=xxx private=xxx
verify
newLicense
dumpLicense
dumpPublicKey
! cmd to execute shell commands
. filename to execute the content of the file
Aliases:
ll -> licenseload
lpuk -> loadpublickey
dl -> dumplicense
dpk -> dumppublickey
lprk -> loadprivatekey
No license in memory
No keys in memory.
```

# How to issue commands in the application

You can issue commands in the application interactively typing commands after the prompt. The format of the different
commands are described in the help text. You can also use the TAB key to auto complete the commands and the parameters.

## Exiting the program

Just type `exit`. If you get the warning saying:

```
[WARNING] There is unsaved state in the application. Use 'exit confirm=yes'
```

then there is a license in the memory that was loaded, modified and not saved yet. If you are sure you want to lose the
modifications that you made you should follow the suggestion of the warning text and use `exit confirm=yes`. That will
force the exit to the operating system. Note that there cannot be generated but unsaved key in the memory because keys
are immediately saved to files after they are generated.

You can also press Control-C or terminate the Java process. The application does not keep any file open and thus there
is no danger to render anything unstable. You may, however, loose some modification from the license you manipulated in
the memory just like if you typed `exit confirm=yes`.

## Operating System Commands

You can execute OS commands if you type `!` at the start of the line. That way you can see what is in a directory, you
can type/cat the content of a file to the screen without leaving the REPL application.