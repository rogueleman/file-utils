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

#3 - done
swap filename for a given string, here " - " is default
java -jar file-utils.jar -f "Titlu 2 - Nume autor2.mobi" -swap
expected: file will be renamed to Nume autor2 - Titlu 2.mobi

#4 - done
user will choose the characters
java -jar file-utils.jar -f "Titlu 2 - Nume autor2.mobi" -swap --findString " " --replaceString "-"
expected: file will be renamed to "Titlu-2---Nume-autor2.mobi"

todo 99 - all above in a zip file -
java -jar file-utils.jar --file abc test.zip -zip -rt underscore
expected: all files from zip file will be renamed with _ in place of spaces


Categories that can not be used together. If used the first one is used
I    lowercase, uppercase
II   underscore, space
III  swap with any other