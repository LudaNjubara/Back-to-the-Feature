package utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import exception.ValidationException;

import java.util.List;

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

        public static void validateNewFolderName(String newFolderName, List<String> existingFolderNames) throws ValidationException {
            // only accept non-empty folder names that don't start with numbers, or special characters, or contain spaces
            if (newFolderName == null || newFolderName.isEmpty()) {
                throw new ValidationException("Folder name cannot be empty");
            }

            if (existingFolderNames.contains(newFolderName.toLowerCase())) {
                throw new ValidationException("Folder already exists");
            }

            if (newFolderName.matches("^[0-9].*")) {
                throw new ValidationException("Folder name cannot start with a number");
            }

            if (newFolderName.matches(".*[!@#$%^&*(),.?\":{}|<>-].*")) {
                throw new ValidationException("Folder name cannot contain special characters");
            }

            if (newFolderName.contains(" ")) {
                throw new ValidationException("Folder name cannot contain spaces");
            }
        }
}
