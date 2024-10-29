package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;
import model.CreateDirectoryFilesOptions;
import model.CreateInitialFilesOptions;

import java.io.IOException;

public class FilesUtils {
    /**
     * Create the initial files for the feature. The files are created in the selected directory.
     *
     * @param options the initial files options
     */
    public static void createInitialFiles(CreateInitialFilesOptions options) {
        CreateDirectoryFilesOptions cdfOptions = CreateDirectoryFilesOptions.builder()
                .project(options.project())
                .dir(options.selectedDir())
                .selectedDir(options.selectedDir())
                .useSeparateFolders(options.useSeparateFolders())
                .build();

        createControllerDirectoryFiles(cdfOptions);
        createModelDirectoryFiles(cdfOptions);
        createServiceDirectoryFiles(cdfOptions);
        createRepositoryDirectoryFiles(cdfOptions);
        createRequestDirectoryFiles(cdfOptions);
        createDtoDirectoryFiles(cdfOptions);
        createMapperDirectoryFiles(cdfOptions);
    }

    /**
     * Create the files for the controller directory.
     *
     * @param options Options for creating the files
     */
    public static void createControllerDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] controllerTemplate = CommonUtils.loadFile("/templates/controller/defaultControllerTemplate.txt");

        // Replace the template placeholders
        String controllerContent = new String(controllerTemplate);
        controllerContent = controllerContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if (options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            controllerContent = controllerContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "service." + prefix + "Service;\n"
                                    + "import " + packagePathWoCurrDir + "dto." + prefix + "DTO;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n"
                                    + "import " + packagePathWoCurrDir + "response." + prefix + "Response;\n");
        } else {
            controllerContent = controllerContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePath + ".response." + prefix + "Response;\n");
        }

        String finalControllerContent = controllerContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile controllerFile = options.dir().createFile(prefix + "Controller.java");

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
     * @param options Options for creating the files
     */
    public static void createResponseDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] responseTemplate = CommonUtils.loadFile("/templates/response/defaultResponseTemplate.txt");

        // Replace the template placeholders
        String responseContent = new String(responseTemplate);
        responseContent = responseContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if(options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            responseContent = responseContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "dto." + prefix + "DTO;\n");
        } else {
            responseContent = responseContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePath.replace(options.dir().getName(), "") + prefix + "DTO;\n");
        }

        String finalResponseContent = responseContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile responseFile = options.dir().createFile(prefix + "Response.java");

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
     * @param options Options for creating the files
     */
    public static void createRequestDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] requestTemplate = CommonUtils.loadFile("/templates/request/defaultRequestTemplate.txt");

        // Replace the template placeholders
        String requestContent = new String(requestTemplate);
        requestContent = requestContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        String finalRequestContent = requestContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile requestFile = options.dir().createFile(prefix + "Request.java");

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
     * @param options Options for creating the files
     */
    public static void createModelDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] modelTemplate = CommonUtils.loadFile("/templates/model/defaultModelTemplate.txt");

        // Replace the template placeholders
        String modelContent = new String(modelTemplate);
        modelContent = modelContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        String finalModelContent = modelContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile modelFile = options.dir().createFile(prefix + ".java");

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
     * @param options Options for creating the files
     */
    public static void createServiceDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] serviceTemplate = CommonUtils.loadFile("/templates/service/defaultServiceTemplate.txt");

        // Replace the template placeholders
        String serviceContent = new String(serviceTemplate);
        serviceContent = serviceContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if(options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            serviceContent = serviceContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "model." + prefix + ";\n"
                                    + "import " + packagePathWoCurrDir + "repository." + prefix + "Repository;\n"
                                    + "import " + packagePathWoCurrDir + "mapper." + prefix + "Mapper;\n"
                                    + "import " + packagePathWoCurrDir + "dto." + prefix + "DTO;\n");
        } else {
            serviceContent = serviceContent
                    .replace(
                            "$IMPORTS$", "");
        }

        String finalServiceContent = serviceContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile serviceFile = options.dir().createFile(prefix + "Service.java");

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
     * @param options Options for creating the files
     */
    public static void createRepositoryDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] repositoryTemplate = CommonUtils.loadFile("/templates/repository/defaultRepositoryTemplate.txt");

        // Replace the template placeholders
        String repositoryContent = new String(repositoryTemplate);
        repositoryContent = repositoryContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if(options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            repositoryContent = repositoryContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "model." + prefix + ";\n");
        } else {
            repositoryContent = repositoryContent
                    .replace(
                            "$IMPORTS$", "");
        }

        String finalRepositoryContent = repositoryContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile repositoryFile = options.dir().createFile(prefix + "Repository.java");

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
     * @param options Options for creating the files
     */
    public static void createDtoDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] dtoTemplate = CommonUtils.loadFile("/templates/dto/defaultDTOTemplate.txt");

        // Replace the template placeholders
        String dtoContent = new String(dtoTemplate);
        dtoContent = dtoContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        String finalDtoContent = dtoContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile dtoFile = options.dir().createFile(prefix + "DTO.java");

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
     * @param options Options for creating the files
     */
    public static void createMapperDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = CommonUtils.capitalize(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] mapperTemplate = CommonUtils.loadFile("/templates/mapper/defaultMapperTemplate.txt");

        // Replace the template placeholders
        String mapperContent = new String(mapperTemplate);
        mapperContent = mapperContent.replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if (options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            mapperContent = mapperContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "model." + prefix + ";\n"
                                    + "import " + packagePathWoCurrDir + "dto." + prefix + "DTO;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n");
        } else {
            mapperContent = mapperContent
                    .replace(
                            "$IMPORTS$", "");
        }

        String finalMapperContent = mapperContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile mapperFile = options.dir().createFile(prefix + "Mapper.java");

            try {
                mapperFile.getVirtualFile().setBinaryContent(finalMapperContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
