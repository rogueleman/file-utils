package org.leman.free.file.rename;

import static java.lang.System.getProperty;
import static org.apache.commons.io.FileUtils.cleanDirectory;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.fest.assertions.Assertions.assertThat;
import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.REPLACE_SPACES_WITH_UNDERSCORES;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.leman.free.file.utils.GenFileCommandLineOptions;
import org.leman.free.file.utils.RenamingType;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RenameFilesTest {
    public static final String USER_DIR = "user.dir";

    public static final String END = "end";
    public static final String START = "start";
    public static final String SWAP = "swap";
    public static final String SPACE = "space";
    public static final String DASH = "dash";

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

    public static final String FILE_NAME_TEST_REPLACE_SPACE_WITH_DASH_SIGN = "test space with dash.txt";
    public static final String FILE_NAME_TEST_REPLACE_SPACE_WITH_DASH_SIGN_END = "test-space-with-dash.txt";

    public static final String FILE_NAME_TEST_REPLACE_SPACE_WITH_DOUBLE_UNDERSCORE =
                                                                        "test space with double underscore.txt";
    public static final String FILE_NAME_TEST_REPLACE_SPACE_WITH__DOUBLE_UNDERSCORE_END =
                                                                    "test__space__with__double__underscore.txt";

    public static final String FILE_NAME_TEST_REPLACE_DASH_SIGN_WITH_UNDERSCORE_END = "test_dash with_underscore.txt";
    public static final String FILE_NAME_TEST_REPLACE_DASH_SIGN_WITH_UNDERSCORE_END_TWO =
                                                                    "test_dash_with_underscore.txt";

    public static File currentDirectory;

    public RenameFilesTest() {
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        //get the folder resource by other method, not using Spring method ClassPathResource
        // can use this
        // URL resource = FlatUploadMappingFileTest.class.getResource("/");
        URL resource = RenameFilesTest.class.getResource("/file-utils");
        final File file = Paths.get(resource.toURI()).toFile();

        //final File file = new ClassPathResource("file-utils").getFile();
        System.setProperty(USER_DIR, file.getPath());

        currentDirectory = new File(getProperty(USER_DIR));

        // copy all files from start directory to end directory
        final File source = new File(currentDirectory, START);
        final File destination = new File(currentDirectory, END);
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

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_WITH_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_WITH_UNDERSCORES).exists()).isTrue();
    }

    @Test
    public void when_fileName_with_spaces_does_not_exist_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NOT_EXISTS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NOT_EXISTS_WITH_UNDERSCORES).exists()).isFalse();
    }

    @Test
    public void when_fileName_with_spaces_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_WITH_SPACES_1);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_WITH_SPACES_1_UPPERCASE).exists()).isTrue();
    }

    @Test
    public void when_fileName_with_spaces_without_extension_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_WITHOUT_EXTENSION);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_WITHOUT_EXTENSION_UPPERCASE).exists()).isTrue();
    }

    @Test
    public void when_fileName_without_spaces_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_WITHOUT_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_WITHOUT_SPACES).exists()).isTrue();
    }

    @Test
    public void when_fileName_uppercase_without_spaces_to_be_renamed_lowercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(LOWERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_UPPERCASE);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_UPPERCASE_LOWERCASE).exists()).isTrue();
    }

    @Test
    public void when_fileName_lowercase_with_spaces_to_be_renamed_uppercase_and_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue()
                                                                                       + " "
                                                                                       + REPLACE_SPACES_WITH_UNDERSCORES
                .getValue());

        final File fileToBeRenamed = new File(currentDirectory, FILE_NAME_TWO_CONVERSIONS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertThat(new File(currentDirectory, FILE_NAME_TWO_CONVERSIONS_UPPERCASE_AND_UNDERSCORE).exists()).isTrue();
    }

    @Test(expected = EnumConstantNotPresentException.class)
    public void when_fileName_uppercase_without_spaces_wrong_renaming_type_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants("wrongRenamingType");

        final File fileToBeRenamed = new File(currentDirectory, END + FILE_NAME_WITHOUT_SPACES);

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
        assertThat(new File(currentDirectory, FILE_NAME_AUTOR_DASH_TITLU).exists()).isTrue();
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
        assertThat(new File(currentDirectory, FILE_NAME_TITLU_AUTOR).exists()).isTrue();
    }

    @Test
    public void when_fileName_contains_dash_in_given_folder_swap_sides() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setSwap(" - ");

        final File workDirectory = new File(currentDirectory, SWAP);
        //when
        renameFiles.swap(workDirectory, genFileCommandLineOptions);

        //then
        assertThat(new File(workDirectory, FILE_NAME_AUTOR1_DASH_TITLU1).exists()).isTrue();
        assertThat(new File(workDirectory, FILE_NAME_AUTOR2_DASH_TITLU2).exists()).isTrue();
    }

    @Test
    public void when_fileName_in_folder_with_spaces_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setRenamingType(UPPERCASE_ALL.getValue());

        final File workDirectory = new File(currentDirectory, SPACE);

        //when
        renameFiles.renaming(workDirectory, genFileCommandLineOptions);

        //then
        assertThat(new File(workDirectory, FILE_NAME_WITH_SPACES_1_UPPERCASE).exists()).isTrue();
        assertThat(new File(workDirectory, FILE_NAME_WITHOUT_EXTENSION_UPPERCASE).exists()).isTrue();
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
        assertThat(new File(currentDirectory, FILE_NAME_TEST_UNDERSCORE_UPPERCASE).exists()).isTrue();
    }

    @Test
    public void when_fileName_contains_space_replace_with_minus_sign_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setFindString(" ");
        genFileCommandLineOptions.setReplaceString("-");
        genFileCommandLineOptions.setFileName(FILE_NAME_TEST_REPLACE_SPACE_WITH_DASH_SIGN);

        //when
        renameFiles.renaming(currentDirectory, genFileCommandLineOptions);

        //then
        assertThat(new File(currentDirectory, FILE_NAME_TEST_REPLACE_SPACE_WITH_DASH_SIGN_END).exists()).isTrue();
    }

    @Test
    public void when_fileName_contains_space_replace_with_double_underscore_sign_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setFindString(" ");
        genFileCommandLineOptions.setReplaceString("__");
        genFileCommandLineOptions.setFileName(FILE_NAME_TEST_REPLACE_SPACE_WITH_DOUBLE_UNDERSCORE);

        //when
        renameFiles.renaming(currentDirectory, genFileCommandLineOptions);

        //then
        assertThat(new File(currentDirectory, FILE_NAME_TEST_REPLACE_SPACE_WITH__DOUBLE_UNDERSCORE_END).exists()).isTrue();
    }

    @Test
    public void when_fileName_in_folder_with_spaces_to_be_renamed_with_dash_sign_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();

        final GenFileCommandLineOptions genFileCommandLineOptions = new GenFileCommandLineOptions();
        genFileCommandLineOptions.setFindString("-");
        genFileCommandLineOptions.setReplaceString("_");

        final File workDirectory = new File(currentDirectory, DASH);

        //when
        renameFiles.renaming(workDirectory, genFileCommandLineOptions);

        //then
        assertThat(new File(workDirectory, FILE_NAME_TEST_REPLACE_DASH_SIGN_WITH_UNDERSCORE_END).exists()).isTrue();
        assertThat(new File(workDirectory, FILE_NAME_TEST_REPLACE_DASH_SIGN_WITH_UNDERSCORE_END_TWO).exists()).isTrue();
    }
}