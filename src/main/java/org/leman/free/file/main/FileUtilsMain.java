package org.leman.free.file.main;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.leman.free.file.rename.RenameFiles;
import org.leman.free.file.utils.GenFileCommandLineOptions;

public class FileUtilsMain {

    // set start directory = current directory
    private static File CURRENT_DIRECTORY = new File(System.getProperty("user.dir"));

    /**
     * @param args
     */
    public static void main(String[] args) {
        final GenFileCommandLineOptions commandLineArguments = getCommandLineArguments(args);
        final RenameFiles renameFiles = new RenameFiles();

        final Boolean renameAndSwapFilled = isNotBlank(commandLineArguments.getRenamingType())
                                  && isNotBlank(commandLineArguments.getSwap());

        final Boolean findAndReplaceNotFilled = (isNotBlank(commandLineArguments.getFindString())
                                           && isBlank(commandLineArguments.getReplaceString()))
                                           || (isBlank(commandLineArguments.getFindString())
                                           && isNotBlank(commandLineArguments.getReplaceString()));

        if (renameAndSwapFilled || findAndReplaceNotFilled) {
            System.err.println("Please use the arguments properly: renamingType "
                               + "OR swap "
                               + "OR findString AND replaceString!!!");
        }

        if (isNotBlank(commandLineArguments.getFindString()) && isNotBlank(commandLineArguments.getReplaceString())) {
            renameFiles.renaming(CURRENT_DIRECTORY, commandLineArguments);
        } else if (isNotBlank(commandLineArguments.getRenamingType())) {
                renameFiles.renaming(CURRENT_DIRECTORY, commandLineArguments);
        } else if (isNotBlank(commandLineArguments.getSwap())) {
            renameFiles.swap(CURRENT_DIRECTORY, commandLineArguments);
        } else {
            System.err.println("your command does not contains the right arguments!!!");
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
