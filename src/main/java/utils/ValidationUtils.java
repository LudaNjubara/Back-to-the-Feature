package utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import exception.ValidationException;

public class ValidationUtils {

        /**
        * Perform preliminary validation.
        *
        * @param project     the project
        * @param selectedDir the selected directory
        * @throws ValidationException if an error occurs
        */
        public static void preliminaryValidation(Project project, PsiDirectory selectedDir) throws ValidationException {
            if (selectedDir == null) {
                throw new ValidationException("No directory selected");
            }

            if (!DirectoryUtils.isDirectoryEmpty(selectedDir)) {
                throw new ValidationException("Directory must be empty!");
            }

            if(CommonUtils.getJavaVersion(project).equals("No SDK configured")) {
                throw new ValidationException("No SDK configured. Please configure a Java SDK before using the plugin.");
            }
        }
}
