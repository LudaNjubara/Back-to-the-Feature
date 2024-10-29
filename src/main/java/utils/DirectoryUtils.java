package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.util.IncorrectOperationException;
import constants.InitialDirectories;
import model.InitialDirectoriesOptions;
import model.PopulateDirectoriesOptions;

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
            FilesUtils.createInitialFiles(options.project(), options.selectedDir());
        }

        // populate the subdirectories with files
        for (InitialDirectories directory : directories) {
            PsiDirectory dir = options.selectedDir().findSubdirectory(directory.getDirectoryName());
            if (dir == null) {
                throw new Exception("Directory not found: " + directory.getDirectoryName());
            }

            // populate the directory with files
            populateDirectory(options.project(), dir, options.selectedDir());
        }
    }

    /**
     * Populate the directory with corresponding files.
     *
     * @param project the project
     * @param dir     the directory
     * @throws Exception if an error occurs
     */
    public static void populateDirectory(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {

        for (InitialDirectories directory : InitialDirectories.values()) {
            if (directory.getDirectoryName().equals(dir.getName())) {
                directory.createFiles(project, dir, selectedDir);
                return;
            }
        }

        throw new Exception("Unknown directory: " + dir.getName());
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
