package org.leman.free.file.rename;

import static java.io.File.separator;
import static java.lang.System.getProperty;
import static junit.framework.Assert.assertEquals;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.REPLACE_SPACES_WITH_UNDERSCORES;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.leman.free.file.utils.GenFileCommandLineOptions;
import org.leman.free.file.utils.RenamingType;
import org.springframework.core.io.ClassPathResource;

public class RenameFilesTest {

    public static final String USER_DIR = "user.dir";

    public static final String SLASH_END = separator + "end";
    public static final String SLASH_START = separator + "start";
    public static final String SLASH_DASH = separator + "dash";
    public static final String SLASH_SPACE = separator + "space";

    public static final String FILE_NOT_EXISTS = "test not exists.txt";
    public static final String FILE_NOT_EXISTS_WITH_UNDERSCORES = "test_not_exists.txt";

    public static final String FILE_NAME_WITH_SPACES = "test with spaces.txt";
    public static final String FILE_NAME_WITH_UNDERSCORES = "test_with_spaces.txt";

    public static final String FILE_NAME_WITH_SPACES_1 = "test with spaces 1.txt";
    public static final String FILE_NAME_WITH_SPACES_1_UPPERCASE = "TEST WITH SPACES 1.TXT";

    public static final String FILE_NAME_WITHOUT_EXTENSION = "test without extension";
    public static final String FILE_NAME_WITHOUT_EXTENSION_UPPERCASE = "TEST WITHOUT EXTENSION";

    public static final String FILE_NAME_WITHOUT_SPACES = "WithoutSpaces.txt";

    public static final String FILE_NAME_UPPERCASE = "UPPERCASE.txt";
    public static final String FILE_NAME_UPPERCASE_LOWERCASE = "uppercase.txt";

    public static final String FILE_NAME_TWO_CONVERSIONS = "test two conversions.txt";
    public static final String FILE_NAME_TWO_CONVERSIONS_UPPERCASE_AND_UNDERSCORE = "TEST_TWO_CONVERSIONS.TXT";

    public static final String FILE_NAME_TITLU_DASH_AUTOR = "titlu - autor.txt";
    public static final String FILE_NAME_AUTOR_DASH_TITLU = "autor - titlu.txt";
    public static final String FILE_NAME_TITLU_AUTOR = "titlu autor.txt";

    public static final String FILE_NAME_AUTOR1_DASH_TITLU1 = "autor1 - titlu1.txt";
    public static final String FILE_NAME_AUTOR2_DASH_TITLU2 = "autor2 - titlu2.txt";

    public static final String FILE_NAME_TEST_UNDERSCORE = "test_underscore.txt";
    public static final String FILE_NAME_TEST_UNDERSCORE_UPPERCASE = "TEST_UNDERSCORE.TXT";

    public static File currentDirectory;

    public RenameFilesTest() {
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        final File file = new ClassPathResource("file-utils").getFile();
        System.setProperty(USER_DIR, file.getPath());

        currentDirectory = new File(getProperty(USER_DIR));

        // copy all files from start directory to end directory
        final File source = new File(currentDirectory + SLASH_START);
        final File destination = new File(currentDirectory + SLASH_END);
        copyDirectory(source, destination);

        currentDirectory = destination;
    }

    @AfterClass
    public static void afterClass() throws Exception {
        // delete all files from end directory
       cleanDirectory(new File(currentDirectory.getPath()));
    }

    @Test
    public void when_fileName_with_spaces_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_WITH_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_WITH_UNDERSCORES).exists(), true);
    }

    @Test
    public void when_fileName_with_spaces_does_not_exist_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NOT_EXISTS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NOT_EXISTS_WITH_UNDERSCORES).exists(), false);
    }

    @Test
    public void when_fileName_with_spaces_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_WITH_SPACES_1);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_WITH_SPACES_1_UPPERCASE).exists(), true);
    }

    @Test
    public void when_fileName_with_spaces_without_extension_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_WITHOUT_EXTENSION);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_WITHOUT_EXTENSION_UPPERCASE).exists(), true);
    }

    @Test
    public void when_fileName_without_spaces_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_WITHOUT_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_WITHOUT_SPACES).exists(), true);
    }

    @Test
    public void when_fileName_uppercase_without_spaces_to_be_renamed_lowercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(LOWERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_UPPERCASE);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_UPPERCASE_LOWERCASE).exists(), true);
    }

    @Test
    public void when_fileName_lowercase_with_spaces_to_be_renamed_uppercase_and_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue()
                                                                                       + " "
                                                                                       + REPLACE_SPACES_WITH_UNDERSCORES
                .getValue());

        final File fileToBeRenamed = new File(currentDirectory + separator + FILE_NAME_TWO_CONVERSIONS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory,
                              FILE_NAME_TWO_CONVERSIONS_UPPERCASE_AND_UNDERSCORE).exists(), true);
    }

    @Test(expected = EnumConstantNotPresentException.class)
    public void when_fileName_uppercase_without_spaces_wrong_renaming_type_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants("wrongRenamingType");

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_WITHOUT_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then exception
    }

    @Test
    public void when_fileName_contains_dash_swap_sides() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setSwap(" - ");
        genFileCommandLineOptions.setFileName(FILE_NAME_TITLU_DASH_AUTOR);

        //when
        renameFiles.swap(currentDirectory, genFileCommandLineOptions);

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_AUTOR_DASH_TITLU).exists(), true);
    }

    @Test
    public void when_fileName_not_contains_dash_swap_sides() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setSwap(" - ");
        genFileCommandLineOptions.setFileName(FILE_NAME_TITLU_AUTOR);

        //when
        renameFiles.swap(currentDirectory, genFileCommandLineOptions);

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_TITLU_AUTOR).exists(), true);
    }

    @Test
    public void when_fileName_contains_dash_in_given_folder_swap_sides() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setSwap(" - ");

        final File workDirectory = new File(currentDirectory + SLASH_DASH);
        //when
        renameFiles.swap(workDirectory, genFileCommandLineOptions);

        //then
        assertEquals(new File(workDirectory, FILE_NAME_AUTOR1_DASH_TITLU1).exists(), true);
        assertEquals(new File(workDirectory, FILE_NAME_AUTOR2_DASH_TITLU2).exists(), true);
    }

    @Test
    public void when_fileName_in_folder_with_spaces_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setRenamingType(UPPERCASE_ALL.getValue());

        final File workDirectory = new File(currentDirectory + SLASH_SPACE);

        //when
        renameFiles.renaming(workDirectory, genFileCommandLineOptions);

        //then
        assertEquals(new File(workDirectory, FILE_NAME_WITH_SPACES_1_UPPERCASE).exists(), true);
        assertEquals(new File(workDirectory, FILE_NAME_WITHOUT_EXTENSION_UPPERCASE).exists(), true);
    }

    @Test
    public void when_fileName_contains_underscore_rename_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setRenamingType(UPPERCASE_ALL.getValue());
        genFileCommandLineOptions.setFileName(FILE_NAME_TEST_UNDERSCORE);

        //when
        renameFiles.renaming(currentDirectory, genFileCommandLineOptions);

        //then
        assertEquals(new File(currentDirectory, FILE_NAME_TEST_UNDERSCORE_UPPERCASE).exists(), true);
    }

}