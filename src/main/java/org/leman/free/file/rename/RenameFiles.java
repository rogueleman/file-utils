package org.leman.free.file.rename;

import static java.io.File.separator;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.REPLACE_SPACES_WITH_UNDERSCORES;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.leman.free.file.utils.GenFileCommandLineOptions;
import org.leman.free.file.utils.RenamingType;

public class RenameFiles {
    public static final String EXTENSION_SEPARATOR = ".";
    public static final String REGEX_TO_FIND_SPACES = "\\s";
    public static final String UNDERSCORE = "_";
    public static final String VOID_STRING = "";

    /**
     * @param f - file to rename
     * @param renamingTypes - values for type of renaming
     * @return - the new name of the file
     */
    protected String getNewFileName(final File f, final List<RenamingType> renamingTypes) {
        final String oldName = f.getName();
        String extension = VOID_STRING;
        String name = oldName;
        final Integer dotPos = oldName.lastIndexOf(EXTENSION_SEPARATOR);

        StringBuffer newName = new StringBuffer(name);

        if (dotPos > 0) {
            extension = oldName.substring(dotPos);
            name = oldName.substring(0, dotPos);
        }

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
        }
        return checkTypeOfLettersCase(newName.toString(), renamingTypes);
    }

    private String checkTypeOfLettersCase(final String fileName, final List<RenamingType> renamingTypes) {
        if(renamingTypes.contains(LOWERCASE_ALL)) {
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

    public void renaming(final File currentDirectory, final GenFileCommandLineOptions commandLineArguments) {
        final List<RenamingType> renamingTypes = getRenamingTypesConstants(commandLineArguments.getRenamingType());

        final String fileName = commandLineArguments.getFileName();
        if (isNotBlank(fileName)) {
            final File file = new File(currentDirectory + separator + fileName);
            renameFile(file, file.getParent(), getNewFileName(file, renamingTypes));
        } else {
            final File[] files = currentDirectory.listFiles();
            for (final File file : files) {
                renameFile(file, file.getParent(), getNewFileName(file, renamingTypes));
            }
        }
    }

    public void swap(final File currentDirectory, final GenFileCommandLineOptions commandLineArguments) {
        final String swap = commandLineArguments.getSwap();
        final String fileName = commandLineArguments.getFileName();

        if (isNotBlank(fileName)) {
            final String[] nameSplits = getFileExtension(fileName)[0].split(swap, 2);
            final File file = new File(currentDirectory + separator + fileName);
            renameFile(file, file.getParent(), nameSplits, swap, getFileExtension(fileName)[1]);
        } else {
            final File[] files = currentDirectory.listFiles();
            for (final File file : files) {
                final String[] nameSplits = getFileExtension(file.getName())[0].split(swap, 2);
                renameFile(file, file.getParent(), nameSplits, swap, getFileExtension(file.getName())[1]);
            }
        }
    }

    private String[] getFileExtension(String fileName) {
        String extension = VOID_STRING;
        String name = fileName;
        final Integer dotPos = fileName.lastIndexOf(EXTENSION_SEPARATOR);

        if (dotPos > 0) {
            extension = fileName.substring(dotPos);
            name = fileName.substring(0, dotPos);
        }

        final String[] returnValue = {name,  extension};
        return returnValue;
    }

    /**
     * @param file        - file to rename
     * @param parent      - path to file to rename
     * @param nameSplits  - the splits Array
     */
    private void renameFile(final File file, final String parent, final String[] nameSplits, final String swap,
                            final String extension) {
        if (nameSplits.length == 2) {
            if (!file.isDirectory() && !file.getName().equals("file-utils.jar")) {
                StringBuffer pathWithSlash = new StringBuffer(parent).insert(parent.length(), separator);
                //TODO check the answer of renameTo
                final String pathName = pathWithSlash + nameSplits[1] + swap + nameSplits[0] + extension;
                final boolean b = file.renameTo(new File(pathName));
                System.out.println("aaaaaa " + b);
            }
        } else {
            System.out.println("File " + file.getName() + "does not contains the swap character");
        }
    }

    /**
     * @param f           - file to rename
     * @param parent      - path to file to rename
     * @param newFileName - the new name of the file
     */
    protected void renameFile(final File f, final String parent, final String newFileName) {
        if (!f.isDirectory() && !f.getName().equals("file-utils.jar")) {
            StringBuffer pathWithSlash = new StringBuffer(parent).insert(parent.length(), separator);
            //TODO check the answer of renameTo
            final boolean b = f.renameTo(new File(pathWithSlash + newFileName));
        }
    }
}