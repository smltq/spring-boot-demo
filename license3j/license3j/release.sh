#!/usr/bin/env bash

mkdir -p release/
cp target/*.jar release/
cp pom.xml release/

cd release
rm *.sig
rm *.asc
for file in *.jar pom.xml
do
    gpg -s -b ${file}
    mv ${file}.sig ${file}.asc
done

rm *.zip
jar -c -M -f release.zip *.jar pom.xml *.asc
cd ..
cd ..
