package org.leman.free.file.main;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.leman.free.file.rename.RenameFiles;
import org.leman.free.file.utils.GenFileCommandLineOptions;

public class FileUtilsMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final GenFileCommandLineOptions commandLineArguments = getCommandLineArguments(args);
        final RenameFiles renameFiles = new RenameFiles();
        renameFiles.renaming(commandLineArguments, commandLineArguments.getRenamingType());
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
