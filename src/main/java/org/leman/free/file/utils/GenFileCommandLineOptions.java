package org.leman.free.file.utils;

import org.kohsuke.args4j.Option;

public class GenFileCommandLineOptions {
    @Option(name = "-f", usage = "file to be renamed", metaVar = "string", aliases = "--file", required = false)
    private String fileName;

    @Option(name = "-rt", usage = "renaming type", metaVar = "string", aliases = "--renamingType", required = true)
    private String renamingType;

    @Option(name = "-zip", usage = "rename all files from a zip file", metaVar = "string", aliases = "--zipFile",
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
}
