package org.leman.free.file.rename;

import static org.leman.free.file.utils.RenamingType.LOWERCASE_ALL;
import static org.leman.free.file.utils.RenamingType.REPLACE_SPACES_WITH_UNDERSCORES;
import static org.leman.free.file.utils.RenamingType.UPPERCASE_ALL;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    public String getNewFileName(final File f, final List<RenamingType> renamingTypes) {
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

    /**
     * @param f        - file to rename
     * @param path     - path to file to rename
     * @param newFileName - the new name of the file
     */
    public void renameFile(final File f, final String path, final String newFileName) {
        if (!f.isDirectory() && !f.getName().equals("file-utils.jar")) {
            StringBuffer pathWithSlash = new StringBuffer(path);
            pathWithSlash = pathWithSlash.insert(path.length(), File.separator);
            f.renameTo(new File(pathWithSlash + newFileName));
        }
    }

    public List<RenamingType> getRenamingTypesConstants(final String renamingType) {
        final String[] renamingTypeSplit = renamingType.split(REGEX_TO_FIND_SPACES);

        final List<RenamingType> renamingTypes = new ArrayList<>();

        if (renamingTypeSplit.length > 1) {
            for (final String type : renamingTypeSplit) {
                renamingTypes.add(RenamingType.getByValue(type));
            }
        } else {
            renamingTypes.add(RenamingType.getByValue(renamingType));
        }

        return renamingTypes;
    }
}