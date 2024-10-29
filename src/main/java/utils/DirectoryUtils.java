package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.IncorrectOperationException;
import constants.InitialDirectories;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryUtils {
    /**
     * Create the initial directories for the feature.
     *
     * @param options the initial directories options
     * @throws IncorrectOperationException if an error occurs
     */
    public static void createInitialDirectories(InitialDirectoriesOptions options) throws IncorrectOperationException {
        List<String> directoryNames;

        if (options.useSeparateFolders()) {
            directoryNames = new ArrayList<>(Arrays.stream(InitialDirectories.values()).map(InitialDirectories::getDirectoryName).toList());
        } else {
            directoryNames = new ArrayList<>(Arrays.stream(InitialDirectories.values()).filter(InitialDirectories::isUsedInCombinedFolder).map(InitialDirectories::getDirectoryName).toList());
        }

        try {
            WriteCommandAction.runWriteCommandAction(options.project(), () -> {
                for (String directoryName : directoryNames) {
                    options.selectedDir().createSubdirectory(directoryName);
                }
            });
        } catch (IncorrectOperationException e) {
            throw new RuntimeException("Error creating directories: " + e.getMessage());
        }
    }

    /**
     * Populate the directories with files.
     *
     * @param options the populate directories options
     * @throws Exception if an error occurs
     */
    public static void populateDirectories(PopulateDirectoriesOptions options) throws Exception {
        List<InitialDirectories> directories = options.useSeparateFolders()
                ? Arrays.stream(InitialDirectories.values()).toList()
                : Arrays.stream(InitialDirectories.values()).filter(InitialDirectories::isUsedInCombinedFolder).toList();

        // populate the parent directory with the initial files first
        if (!options.useSeparateFolders()) {
            CreateInitialFilesOptions cifOptions = CreateInitialFilesOptions.builder()
                    .project(options.project())
                    .selectedDir(options.selectedDir())
                    .useSeparateFolders(false)
                    .build();
            FilesUtils.createInitialFiles(cifOptions);
        }

        // populate the subdirectories with files
        for (InitialDirectories directory : directories) {
            PsiDirectory dir = options.selectedDir().findSubdirectory(directory.getDirectoryName());
            if (dir == null) {
                throw new Exception("Directory not found: " + directory.getDirectoryName());
            }

            // populate the directory with files
            PopulateDirectoryOptions pdOptions = PopulateDirectoryOptions.builder()
                    .project(options.project())
                    .dir(dir)
                    .selectedDir(options.selectedDir())
                    .useSeparateFolders(options.useSeparateFolders())
                    .build();
            populateDirectory(pdOptions);
        }
    }

    /**
     * Populate the directory with corresponding files.
     *
     * @param options the populate directory options
     * @throws Exception if an error occurs
     */
    public static void populateDirectory(PopulateDirectoryOptions options) throws Exception {
        CreateDirectoryFilesOptions cdfOptions = CreateDirectoryFilesOptions.builder()
                .project(options.project())
                .dir(options.dir())
                .selectedDir(options.selectedDir())
                .useSeparateFolders(options.useSeparateFolders())
                .build();

        for (InitialDirectories directory : InitialDirectories.values()) {
            if (directory.getDirectoryName().equals(options.dir().getName())) {
                directory.createFiles(cdfOptions);
                return;
            }
        }

        throw new Exception("Unknown directory: " + options.dir().getName());
    }

    /**
     * Check if the directory is empty.
     *
     * @param selectedDir the selected directory
     * @return true if the directory is empty, false otherwise
     */
    public static boolean isDirectoryEmpty(PsiDirectory selectedDir) {
        return selectedDir.getChildren().length == 0;
    }
}
