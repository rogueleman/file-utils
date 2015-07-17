package org.leman.free.file.main;

import java.io.File;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.leman.free.file.rename.RenameFiles;
import org.leman.free.file.utils.GenFileCommandLineOptions;
import org.leman.free.file.utils.RenamingType;

public class FileUtilsMain {

    // set start directory = current directory
    private static File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));

    /**
     * @param args
     */
    public static void main(String[] args) {
        final GenFileCommandLineOptions commandLineArguments = getCommandLineArguments(args);

        final String renamingType = commandLineArguments.getRenamingType();

        final RenameFiles renameFiles = new RenameFiles();

        final List<RenamingType> renamingTypes = renameFiles.getRenamingTypesConstants(renamingType);

        final String fileName = commandLineArguments.getFileName();
        if (StringUtils.isNotBlank(fileName)) {
            final File file = new File(CURRENT_DIRECTORY + File.pathSeparator + fileName);
            renameFiles.renameFile(file, file.getParent(), renameFiles.getNewFileName(file, renamingTypes));
        } else {
            final File[] files = CURRENT_DIRECTORY.listFiles();
            for (File file : files) {
                renameFiles.renameFile(file, file.getParent(), renameFiles.getNewFileName(file, renamingTypes));
            }
        }
    }

    private static GenFileCommandLineOptions getCommandLineArguments(String[] args) {
        final GenFileCommandLineOptions cliOpts = new GenFileCommandLineOptions();
        final CmdLineParser parser = new CmdLineParser(cliOpts);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage() + "\n");
            parser.printUsage(System.err);

            System.exit(0);
        }
        return cliOpts;
    }
}
