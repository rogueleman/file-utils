copy the jar with dependencies in the folder that contains the files to be renamed
cp /local00/sources/learning/file-utils/target/file-utils-0.0.1-SNAPSHOT-jar-with-dependencies.jar ~/tmp/tests/file-utils.jar

#1 - done
java -jar file-utils.jar --file "a b c.txt" --renamingType underscore
java -jar file-utils.jar -f "a b c.txt" -rt underscore
expected: file will be renamed to a_b_c.txt

java -jar file-utils.jar -f "a b c.txt" -rt upper
expected: file will be renamed to A B C.txt

#2 - done
combine multiple renaming types
java -jar file-utils.jar -f "a b c.txt" -rt "underscore upper"
expected: file will be renamed to A_B_C.txt

todo 3
swap filename for a given string, here " - " is default
java -jar file-utils.jar -f "Titlu 2 - Nume autor2.mobi" -swap
expected: file will be renamed to Nume autor2 - Titlu 2.mobi

todo 4 -
user will choose the characters
java -jar file-utils.jar -f "Titlu 2 - Nume autor2.mobi" -swap --searchString " " --replaceWith "-"
expected: file will be renamed to "Titlu-2---Nume-autor2.mobi"

todo 99 - all above in a zip file -
java -jar file-utils.jar --file abc test.zip -zip -rt underscore
expected: all files from zip file will be renamed with _ in place of spaces
