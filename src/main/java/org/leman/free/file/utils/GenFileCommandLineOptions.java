package org.leman.free.file.utils;

import org.kohsuke.args4j.Option;

public class GenFileCommandLineOptions {

    @Option(name = "-f", usage = "file to be renamed", metaVar = "string", aliases = "--file", required = false)
    private String fileName;

    @Option(name = "-rt", usage = "renaming type: lower, upper, dash, underscore, space", metaVar = "string",
            aliases = "--renamingType",
            required = false)
    private String renamingType;

    @Option(name = "-s", usage = "swap position of the strings that that are on each side of first position of the" +
                                    " defined character", metaVar = "string",
            aliases = "--swap",
            required = false)
    private String swap;

    @Option(name = "-fs", usage = "string defined by the user to be searched in the file name", metaVar = "string",
            aliases = "--findString",
            required = false)
    private String findString;

    @Option(name = "-rs", usage = "string defined by the user to replace the searched string in the file name",
            metaVar = "string",
            aliases = "--replaceString",
            required = false)
    private String replaceString;

    @Option(name = "-z", usage = "rename all files from a zip file", metaVar = "string", aliases = "--zipFile",
            required = false)
    private boolean zipFile;

   public String getFileName() {
        return fileName;
    }

    public String getRenamingType() {
        return renamingType;
    }

    public boolean isZipFile() {
        return zipFile;
    }

    public String getSwap() {
        return swap;
    }

    public String getFindString() {
        return findString;
    }

    public String getReplaceString() {
        return replaceString;
    }

    //for tests

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setRenamingType(String renamingType) {
        this.renamingType = renamingType;
    }

    public void setSwap(String swap) {
        this.swap = swap;
    }

    public void setFindString(String findString) {
        this.findString = findString;
    }

    public void setReplaceString(String replaceString) {
        this.replaceString = replaceString;
    }

    public void setZipFile(boolean zipFile) {
        this.zipFile = zipFile;
    }
}
