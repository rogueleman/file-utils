package org.leman.free.file.rename;

import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.util.List;

import org.leman.free.file.utils.RenamingType;

public class RenameFiles {
    public static final String EXTENSION_SEPARATOR = ".";
    public static final String REGEX_TO_FIND = "\\s";
    public static final String CHAR_TO_REPLACE_WITH = "_";
    public static final String VOID_STRING = "";

    /**
     * @param f - file to rename
     * @param renamingTypes - values for type of renaming
     * @return - the new name of the file
     */
    public String getNewFileName(final File f, final List<RenamingType> renamingTypes) {
        final String oldName = f.getName();
        final String extension, name;
        final Integer dotPos = oldName.lastIndexOf(EXTENSION_SEPARATOR);

        if (dotPos <= 0) {
            return checkTypeOfLettersCase(oldName, renamingTypes);
        }
        extension = oldName.substring(dotPos);
        name = oldName.substring(0, dotPos);

        final String[] nameSplit = name.split(REGEX_TO_FIND);

        StringBuffer newName = new StringBuffer(VOID_STRING);

        if (nameSplit.length > 1) {
            for (int j = 0; j < nameSplit.length; j++) {
                if (!nameSplit[j].toString().equals(VOID_STRING)) {
                    newName = newName.append(nameSplit[j]);
                    if (j != nameSplit.length - 1) {
                        newName = newName.append(CHAR_TO_REPLACE_WITH);
                    }
                }
            }
            newName = newName.append(extension);
        } else {
            newName = newName.append(name).append(extension);
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

    /**
     * @param f        - file to rename
     * @param path     - path to file to rename
     * @param newFileName - the new name of the file
     */
    public void renameFile(final File f, final String path, final String newFileName) {
        StringBuffer pathWithSlash = new StringBuffer(path);
        pathWithSlash = pathWithSlash.insert(path.length(), File.separator);
        f.renameTo(new File(pathWithSlash + newFileName));
    }
}