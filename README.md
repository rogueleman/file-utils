copy the jar with dependencies in the folder that contains the files to be renamed
cp /local00/sources/learning/file-utils/target/file-utils-0.0.1-SNAPSHOT-jar-with-dependencies.jar ~/tmp/tests/file-utils.jar

todo 1 - done
java -jar file-utils.jar --file "a b c.txt" --renamingType underscore
java -jar file-utils.jar -f "a b c.txt" -rt underscore
expected: file will be renamed to a_b_c.txt

java -jar file-utils.jar -f "a b c.txt" -rt upper
expected: file will be renamed to A B C.txt

java -jar file-utils.jar --file abc test.zip -zip -rt underscore
expected: all files from zip file will be renamed with _ in place of spaces

todo 2
combine multiple renaming types
java -jar file-utils.jar -f "a b c.txt" -rt "underscore upper"
expected: file will be renamed to A_B_C.txt
