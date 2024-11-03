package utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import model.InitialDirectoriesOptions;
import model.PopulateDirectoriesOptions;
import settings.PluginSettingsState;

public class FeatureUtils {

    /**
     * Generate the feature.
     *
     * @param project     the project
     * @param selectedDir the selected directory
     * @throws Exception if an error occurs
     */
    public static void generateFeature(Project project, PsiDirectory selectedDir) throws Exception {
        ValidationUtils.preliminaryValidation(project, selectedDir);

        PluginSettingsState settings = PluginSettingsState.getInstance();

        InitialDirectoriesOptions idOptions = InitialDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(settings.useSeparateFolders)
                .build();

        PopulateDirectoriesOptions pdOptions = PopulateDirectoriesOptions.builder()
                .project(project)
                .selectedDir(selectedDir)
                .useSeparateFolders(settings.useSeparateFolders)
                .build();

        DirectoryUtils.createInitialDirectories(idOptions);
        DirectoryUtils.populateDirectories(pdOptions);

        if (settings.automaticGitIntegration) {
            GitUtils.addFilesToGit(project.getBasePath(), selectedDir.getVirtualFile().getPath());
        }
    }
}
