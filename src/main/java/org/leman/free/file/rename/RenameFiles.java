package org.leman.free.file.rename;

import static java.io.File.separator;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.util.zip.ZipFile.OPEN_READ;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.REPLACE_SPACES_WITH_UNDERSCORES;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.leman.free.file.utils.GenFileCommandLineOptions;
import org.leman.free.file.utils.RenamingType;

public class RenameFiles {
    public static final String EXTENSION_SEPARATOR = ".";
    public static final String REGEX_TO_FIND_SPACES = "\\s";
    public static final String UNDERSCORE = "_";
    public static final String VOID_STRING = "";

    public void renaming(final File currentDirectory, final GenFileCommandLineOptions commandLineArguments)
            throws IOException {

        final String fileName = commandLineArguments.getFileName();
        if (isNotBlank(fileName)) {
            if (commandLineArguments.isZipFile()) {
                final ZipFileInfos zipFileInfos = new ZipFileInfos(currentDirectory, fileName).getZipFileInfo();
                final Enumeration<? extends ZipEntry> entries = zipFileInfos.getEntries();

                while (entries.hasMoreElements()) {
                    final String inZipFileName = entries.nextElement().getName();
                    renameFileInZip(zipFileInfos.getZipFilePath(),
                                    inZipFileName,
                                    getNewFileName(inZipFileName, commandLineArguments));
                }
                zipFileInfos.getZipFile().close();
            } else {
                final File file = new File(currentDirectory + separator + fileName);
                renameFile(file, file.getParent(), getNewFileName(file, commandLineArguments));
            }
        } else {
            final File[] files = currentDirectory.listFiles();
            for (final File file : files) {
                renameFile(file, file.getParent(), getNewFileName(file, commandLineArguments));
            }
        }
    }

    public void swap(final File currentDirectory, final GenFileCommandLineOptions commandLineArguments)
            throws IOException {
        final String swap = commandLineArguments.getSwap();
        final String fileName = commandLineArguments.getFileName();

        if (isNotBlank(fileName)) {
            if (commandLineArguments.isZipFile()) {
                final ZipFileInfos zipFileInfos = new ZipFileInfos(currentDirectory, fileName).getZipFileInfo();
                final Enumeration<? extends ZipEntry> entries = zipFileInfos.getEntries();

                while (entries.hasMoreElements()) {
                    final String inZipFileName = entries.nextElement().getName();
                    final String[] nameSplits = getFileNameAndExtension(inZipFileName)[0].split(swap, 2);
                    renameFileInZip(zipFileInfos.getZipFilePath(),
                                    inZipFileName,
                                    getNewFileName(nameSplits, swap, getFileNameAndExtension(inZipFileName)[1]));
                }
                zipFileInfos.getZipFile().close();
            } else {
                final String[] nameSplits = getFileNameAndExtension(fileName)[0].split(swap, 2);
                final File file = new File(currentDirectory + separator + fileName);
                renameFileForSwap(file, file.getParent(), nameSplits, swap, getFileNameAndExtension(fileName)[1]);
            }
        } else {
            final File[] files = currentDirectory.listFiles();
            for (final File file : files) {
                final String[] nameSplits = getFileNameAndExtension(file.getName())[0].split(swap, 2);
                renameFileForSwap(file, file.getParent(), nameSplits, swap, getFileNameAndExtension(file.getName())[1]);
            }
        }
    }

    /**
     * @param f           - file to rename
     * @param parent      - path to file to rename
     * @param newFileName - the new name of the file
     */
    protected void renameFile(final File f, final String parent, final String newFileName) {
        if (!f.isDirectory() && !f.getName().equals("file-utils.jar")) {
            final StringBuffer pathWithSlash = new StringBuffer(parent).insert(parent.length(), separator);
            //TODO check the answer of renameTo
            final boolean b = f.renameTo(new File(pathWithSlash + newFileName));
        }
    }

    /**
     * @param file                 - file to rename
     * @param commandLineArguments - command line arguments
     * @return - the new name of the file
     */
    private String getNewFileName(final File file, final GenFileCommandLineOptions commandLineArguments) {
        final String renamingType = commandLineArguments.getRenamingType();
        if (isNotBlank(renamingType)) {
            return getNewFileName(file, getRenamingTypesConstants(renamingType));
        } else {
            return getNewFileName(file, commandLineArguments.getFindString(), commandLineArguments.getReplaceString());
        }
    }

    private String getNewFileName(final String fileName, final GenFileCommandLineOptions commandLineArguments) {
        final String renamingType = commandLineArguments.getRenamingType();
        if (isNotBlank(renamingType)) {
            return getNewFileName(fileName, getRenamingTypesConstants(renamingType));
        } else {
            return getNewFileName(fileName, commandLineArguments.getFindString(), commandLineArguments.getReplaceString());
        }
    }

    protected String getNewFileName(final File f, final List<RenamingType> renamingTypes) {
        final FileInfos fileInfos = new FileInfos(f).getFileInfo();
        final String name = fileInfos.getName();
        final String extension = fileInfos.getExtension();

        StringBuffer newName = new StringBuffer(name);

        if (renamingTypes.contains(REPLACE_SPACES_WITH_UNDERSCORES)) {
            final String[] nameSplit = name.split(REGEX_TO_FIND_SPACES);

            newName = new StringBuffer(VOID_STRING);

            if (nameSplit.length > 1) {
                for (int j = 0; j < nameSplit.length; j++) {
                    if (!nameSplit[j].toString().equals(VOID_STRING)) {
                        newName = newName.append(nameSplit[j]);
                        if (j != nameSplit.length - 1) {
                            newName = newName.append(UNDERSCORE);
                        }
                    }
                }
                newName = newName.append(extension);
            } else {
                newName = newName.append(name).append(extension);
            }
        } else {
            newName = newName.append(extension);
        }
        return checkTypeOfLettersCase(newName.toString(), renamingTypes);
    }

    private String getNewFileName(final String fileName, final List<RenamingType> renamingTypes) {
        final String[] fileNameAndExtension = getFileNameAndExtension(fileName);
        final String name = fileNameAndExtension[0];
        final String extension = fileNameAndExtension[1];
        StringBuffer newName = new StringBuffer(name);

        if (renamingTypes.contains(REPLACE_SPACES_WITH_UNDERSCORES)) {
            final String[] nameSplit = name.split(REGEX_TO_FIND_SPACES);

            newName = new StringBuffer(VOID_STRING);

            if (nameSplit.length > 1) {
                for (int j = 0; j < nameSplit.length; j++) {
                    if (!nameSplit[j].toString().equals(VOID_STRING)) {
                        newName = newName.append(nameSplit[j]);
                        if (j != nameSplit.length - 1) {
                            newName = newName.append(UNDERSCORE);
                        }
                    }
                }
                newName = newName.append(extension);
            } else {
                newName = newName.append(name).append(extension);
            }
        } else {
            newName = newName.append(extension);
        }
        return checkTypeOfLettersCase(newName.toString(), renamingTypes);
    }

    private String getNewFileName(final File file, final String findString, final String replaceString) {
        final FileInfos fileInfos = new FileInfos(file).getFileInfo();
        final String name = fileInfos.getName();
        final String extension = fileInfos.getExtension();

        StringBuffer newName = new StringBuffer(VOID_STRING);

        final String[] nameSplit = name.split(findString);

        if (nameSplit.length > 1) {
            for (int j = 0; j < nameSplit.length; j++) {
                if (!nameSplit[j].toString().equals(VOID_STRING)) {
                    newName = newName.append(nameSplit[j]);
                    if (j != nameSplit.length - 1) {
                        newName = newName.append(replaceString);
                    }
                }
            }
            newName = newName.append(extension);
        } else {
            newName = newName.append(name).append(extension);
        }
        return newName.toString();
    }

    private String getNewFileName(final String fileName, final String findString, final String replaceString) {
        final String[] fileNameAndExtension = getFileNameAndExtension(fileName);
        final String name = fileNameAndExtension[0];
        final String extension = fileNameAndExtension[1];

        StringBuffer newName = new StringBuffer(VOID_STRING);

        final String[] nameSplit = name.split(findString);

        if (nameSplit.length > 1) {
            for (int j = 0; j < nameSplit.length; j++) {
                if (!nameSplit[j].toString().equals(VOID_STRING)) {
                    newName = newName.append(nameSplit[j]);
                    if (j != nameSplit.length - 1) {
                        newName = newName.append(replaceString);
                    }
                }
            }
            newName = newName.append(extension);
        } else {
            newName = newName.append(name).append(extension);
        }
        return newName.toString();
    }

    private String checkTypeOfLettersCase(final String fileName, final List<RenamingType> renamingTypes) {
        if (renamingTypes.contains(LOWERCASE_ALL)) {
            return fileName.toLowerCase();
        } else if (renamingTypes.contains(UPPERCASE_ALL)) {
            return fileName.toUpperCase();
        } else {
            return fileName;
        }
    }

    protected List<RenamingType> getRenamingTypesConstants(final String renamingType) {
        final String[] renamingTypeSplit = renamingType.split(REGEX_TO_FIND_SPACES);

        final List<RenamingType> renamingTypes = new ArrayList<>();

        //TODO: try for EnumConstantNotPresentException answer something useful for the user
        // see test when_fileName_uppercase_without_spaces_wrong_renaming_type_ok

        if (renamingTypeSplit.length > 1) {
            for (final String type : renamingTypeSplit) {
                renamingTypes.add(RenamingType.getByValue(type));
            }
        } else {
            renamingTypes.add(RenamingType.getByValue(renamingType));
        }
        return renamingTypes;
    }

    private String[] getFileNameAndExtension(final String fileName) {
        String extension = VOID_STRING;
        String name = fileName;
        final Integer dotPos = fileName.lastIndexOf(EXTENSION_SEPARATOR);

        if (dotPos > 0) {
            extension = fileName.substring(dotPos);
            name = fileName.substring(0, dotPos);
        }

        final String[] returnValue = { name, extension };
        return returnValue;
    }

    /**
     * @param file       - file to rename
     * @param parent     - path to file to rename
     * @param nameSplits - the splits Array
     */
    private void renameFileForSwap(final File file, final String parent, final String[] nameSplits, final String swap,
                                   final String extension) {
        if (nameSplits.length == 2) {
            if (!file.isDirectory() && !file.getName().equals("file-utils.jar")) {
                final StringBuffer pathWithSlash = new StringBuffer(parent).insert(parent.length(), separator);
                //TODO check the answer of renameTo
                final String pathName = pathWithSlash + getNewFileName(nameSplits, swap, extension);
                final boolean b = file.renameTo(new File(pathName));
            }
        } else {
            System.out.println("File \"" + file.getName() + "\" does not contains the swap character");
        }
    }

    private String getNewFileName(String[] nameSplits, String swap, String extension) {
        return nameSplits[1] + swap + nameSplits[0] + extension;
    }

    private void renameFileInZip(final File zipFilePath, final String fileNameToRename,
                                 final String newFileName) throws IOException {
        /* Define ZIP File System Properies in HashMap */
        Map<String, String> zip_properties = new HashMap<>();
        /* We want to read an existing ZIP File, so we set this to False */
        zip_properties.put("create", "false");

        /* Specify the path to the ZIP File that you want to read as a File System */
        URI zip_disk = URI.create("jar:file:///" + zipFilePath);

        /* Create ZIP file System */
        try (FileSystem zipfs = FileSystems.newFileSystem(zip_disk, zip_properties)) {
            /* Access file that needs to be renamed */
            Path pathInZipfile = zipfs.getPath(fileNameToRename);
            /* Specify new file name */
            Path renamedZipEntry = zipfs.getPath(newFileName);
            /* Execute rename */
            Files.move(pathInZipfile, renamedZipEntry, ATOMIC_MOVE);
            //System.out.println(pathInZipfile + "File successfully renamed to " + renamedZipEntry);
        }

    }

    private class FileInfos {
        private final File f;
        private String extension;
        private String name;

        public FileInfos(File f) {
            this.f = f;
        }

        public String getExtension() {
            return extension;
        }

        public String getName() {
            return name;
        }

        public FileInfos getFileInfo() {
            final String oldName = f.getName();
            extension = VOID_STRING;
            name = oldName;
            final Integer dotPos = oldName.lastIndexOf(EXTENSION_SEPARATOR);

            if (dotPos > 0) {
                extension = oldName.substring(dotPos);
                name = oldName.substring(0, dotPos);
            }
            return this;
        }
    }

    private class ZipFileInfos {
        private File currentDirectory;
        private String fileName;
        private File zipFilePath;
        private ZipFile zipFile;
        private Enumeration<? extends ZipEntry> entries;

        public ZipFileInfos(File currentDirectory, String fileName) {
            this.currentDirectory = currentDirectory;
            this.fileName = fileName;
        }

        public File getZipFilePath() {
            return zipFilePath;
        }

        public ZipFile getZipFile() {
            return zipFile;
        }

        public Enumeration<? extends ZipEntry> getEntries() {
            return entries;
        }

        public ZipFileInfos getZipFileInfo() throws IOException {
            zipFilePath = new File(currentDirectory, fileName);
            zipFile = new ZipFile(zipFilePath, OPEN_READ);
            entries = zipFile.entries();
            return this;
        }
    }
}