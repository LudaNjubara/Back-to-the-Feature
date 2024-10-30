package utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

public class ValidationUtils {

        /**
        * Perform preliminary validation.
        *
        * @param project     the project
        * @param selectedDir the selected directory
        * @throws Exception if an error occurs
        */
        public static void preliminaryValidation(Project project, PsiDirectory selectedDir) throws Exception {
            if (selectedDir == null) {
                throw new Exception("No directory selected");
            }

            if (!DirectoryUtils.isDirectoryEmpty(selectedDir)) {
                throw new Exception("Directory must be empty!");
            }

            if(CommonUtils.getJavaVersion(project).equals("No SDK configured")) {
                throw new Exception("No SDK configured. Please configure a Java SDK before using the plugin.");
            }
        }
}
