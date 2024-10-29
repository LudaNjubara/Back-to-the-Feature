package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

import java.io.IOException;

public class FilesUtils {
    /**
     * Create the initial files for the feature. The files are created in the selected directory.
     *
     * @param project     the project
     * @param selectedDir the selected directory
     */
    public static void createInitialFiles(Project project, PsiDirectory selectedDir)  {
        createControllerDirectoryFiles(project, selectedDir, selectedDir);
        createModelDirectoryFiles(project, selectedDir, selectedDir);
        createServiceDirectoryFiles(project, selectedDir, selectedDir);
        createRepositoryDirectoryFiles(project, selectedDir, selectedDir);
        createRequestDirectoryFiles(project, selectedDir, selectedDir);
        createDtoDirectoryFiles(project, selectedDir, selectedDir);
        createMapperDirectoryFiles(project, selectedDir, selectedDir);
    }

    /**
     * Create the files for the controller directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createControllerDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] controllerTemplate = CommonUtils.loadFile("/templates/controller/defaultControllerTemplate.txt");

        // Replace the template placeholders
        String controllerContent = new String(controllerTemplate);
        controllerContent = controllerContent.replace("$PREFIX_CAPITALIZED$", prefix);
        controllerContent = controllerContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalControllerContent = controllerContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile controllerFile = dir.createFile(prefix + "Controller.java");

            try {
                controllerFile.getVirtualFile().setBinaryContent(finalControllerContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the response directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createResponseDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] responseTemplate = CommonUtils.loadFile("/templates/response/defaultResponseTemplate.txt");

        // Replace the template placeholders
        String responseContent = new String(responseTemplate);
        responseContent = responseContent.replace("$PREFIX_CAPITALIZED$", prefix);

        String finalResponseContent = responseContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile responseFile = dir.createFile(prefix + "Response.java");

            try {
                responseFile.getVirtualFile().setBinaryContent(finalResponseContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the request directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createRequestDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] requestTemplate = CommonUtils.loadFile("/templates/request/defaultRequestTemplate.txt");

        // Replace the template placeholders
        String requestContent = new String(requestTemplate);
        requestContent = requestContent.replace("$PREFIX_CAPITALIZED$", prefix);

        String finalRequestContent = requestContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile requestFile = dir.createFile(prefix + "Request.java");

            try {
                requestFile.getVirtualFile().setBinaryContent(finalRequestContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the model directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createModelDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] modelTemplate = CommonUtils.loadFile("/templates/model/defaultModelTemplate.txt");

        // Replace the template placeholders
        String modelContent = new String(modelTemplate);
        modelContent = modelContent.replace("$PREFIX_CAPITALIZED$", prefix);
        modelContent = modelContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalModelContent = modelContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile modelFile = dir.createFile(prefix + ".java");

            try {
                modelFile.getVirtualFile().setBinaryContent(finalModelContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the service directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createServiceDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] serviceTemplate = CommonUtils.loadFile("/templates/service/defaultServiceTemplate.txt");

        // Replace the template placeholders
        String serviceContent = new String(serviceTemplate);
        serviceContent = serviceContent.replace("$PREFIX_CAPITALIZED$", prefix);
        serviceContent = serviceContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalServiceContent = serviceContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile serviceFile = dir.createFile(prefix + "Service.java");

            try {
                serviceFile.getVirtualFile().setBinaryContent(finalServiceContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the repository directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createRepositoryDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] repositoryTemplate = CommonUtils.loadFile("/templates/repository/defaultRepositoryTemplate.txt");

        // Replace the template placeholders
        String repositoryContent = new String(repositoryTemplate);
        repositoryContent = repositoryContent.replace("$PREFIX_CAPITALIZED$", prefix);
        repositoryContent = repositoryContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalRepositoryContent = repositoryContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile repositoryFile = dir.createFile(prefix + "Repository.java");

            try {
                repositoryFile.getVirtualFile().setBinaryContent(finalRepositoryContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the dto directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createDtoDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] dtoTemplate = CommonUtils.loadFile("/templates/dto/defaultDTOTemplate.txt");

        // Replace the template placeholders
        String dtoContent = new String(dtoTemplate);
        dtoContent = dtoContent.replace("$PREFIX_CAPITALIZED$", prefix);
        dtoContent = dtoContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalDtoContent = dtoContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile dtoFile = dir.createFile(prefix + "DTO.java");

            try {
                dtoFile.getVirtualFile().setBinaryContent(finalDtoContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Create the files for the mapper directory.
     *
     * @param project     the project
     * @param dir         the directory
     * @param selectedDir the selected directory
     */
    public static void createMapperDirectoryFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir)  {
        final String prefix = CommonUtils.capitalize(selectedDir.getName());

        final byte[] mapperTemplate = CommonUtils.loadFile("/templates/mapper/defaultMapperTemplate.txt");

        // Replace the template placeholders
        String mapperContent = new String(mapperTemplate);
        mapperContent = mapperContent.replace("$PREFIX_CAPITALIZED$", prefix);
        mapperContent = mapperContent.replace("$PREFIX_LOWERCASE$", prefix.toLowerCase());

        String finalMapperContent = mapperContent;

        WriteCommandAction.runWriteCommandAction(project, () -> {
            PsiFile mapperFile = dir.createFile(prefix + "Mapper.java");

            try {
                mapperFile.getVirtualFile().setBinaryContent(finalMapperContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
