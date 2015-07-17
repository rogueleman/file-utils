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
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;

public class RenameFilesTest {

    public static final String SLASH_END = separator + "end";
    public static final String SLASH_START = separator + "start";
    public static final String USER_DIR = "user.dir";
    public static final String FILE_NOT_EXISTS = "test not exists.txt";
    public static final String FILE_NOT_EXISTS_WITH_UNDERSCORES = "test_not_exists.txt";
    public static final String FILE_NAME_WITH_SPACES = "test with spaces.txt";
    public static final String FILE_NAME_WITH_SPACES_1 = "test with spaces 1.txt";
    public static final String FILE_NAME_WITH_UNDERSCORES = "test_with_spaces.txt";
    public static final String FILE_NAME_WITH_SPACES_1_UPPERCASE = "TEST WITH SPACES 1.TXT";
    public static final String FILE_NAME_WITHOUT_EXTENSION = "test without extension";
    public static final String FILE_NAME_WITHOUT_EXTENSION_UPPERCASE = "TEST WITHOUT EXTENSION";
    public static final String FILE_NAME_WITHOUT_SPACES = "WithoutSpaces.txt";
    public static final String FILE_NAME_UPPERCASE = "UPPERCASE.txt";
    public static final String FILE_NAME_UPPERCASE_LOWERCASE = "uppercase.txt";
    public static final String FILE_NAME_TWO_CONVERSIONS = "test two conversions.txt";
    public static final String FILE_NAME_TWO_CONVERSIONS_UPPERCASE_AND_UNDERSCORE = "TEST_TWO_CONVERSIONS.TXT";

    @Mock
    final GenFileCommandLineOptions genFileCommandLineOptions;

    public static File currentDirectory;

    public RenameFilesTest() {
        genFileCommandLineOptions = new GenFileCommandLineOptions();
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
    }

    @AfterClass
    public static void afterClass() throws Exception {
        // delete all files from end directory
        cleanDirectory(new File(currentDirectory + SLASH_END));
    }

    @Test
    public void testGetNewFileName() throws Exception {
        System.out.println(currentDirectory);
    }

    @Test
    public void when_fileName_with_spaces_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_WITH_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_WITH_UNDERSCORES).exists(), true);
    }

    @Test
    public void when_fileName_with_spaces_does_not_exist_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NOT_EXISTS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NOT_EXISTS_WITH_UNDERSCORES).exists(), false);
    }

    @Test
    public void when_fileName_with_spaces_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_WITH_SPACES_1);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_WITH_SPACES_1_UPPERCASE).exists(), true);
    }

    @Test
    public void when_fileName_with_spaces_without_extension_to_be_renamed_uppercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_WITHOUT_EXTENSION);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_WITHOUT_EXTENSION_UPPERCASE).exists(), true);
    }

    @Test
    public void when_fileName_without_spaces_to_be_renamed_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants
                (REPLACE_SPACES_WITH_UNDERSCORES.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_WITHOUT_SPACES);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_WITHOUT_SPACES).exists(), true);
    }

    @Test
    public void when_fileName_uppercase_without_spaces_to_be_renamed_lowercase_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(LOWERCASE_ALL.getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_UPPERCASE);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_UPPERCASE_LOWERCASE).exists(), true);
    }

    @Test
    public void when_fileName_lowercase_with_spaces_to_be_renamed_uppercase_and_with_underscores_ok() throws Exception {
        //given
        final RenameFiles renameFiles = new RenameFiles();
        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(UPPERCASE_ALL.getValue()
                                                                                       + " "
                                                                                       + REPLACE_SPACES_WITH_UNDERSCORES
                .getValue());

        final File fileToBeRenamed = new File(currentDirectory + SLASH_END + separator + FILE_NAME_TWO_CONVERSIONS);

        //when
        renameFiles.renameFile(fileToBeRenamed,
                               fileToBeRenamed.getParent(),
                               renameFiles.getNewFileName(fileToBeRenamed, renamingTypes));

        //then
        assertEquals(new File(currentDirectory + SLASH_END, FILE_NAME_TWO_CONVERSIONS_UPPERCASE_AND_UNDERSCORE).exists(), true);
    }

}