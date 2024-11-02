package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiFile;
import constants.Constants;
import constants.FileType;
import constants.InitialDirectories;
import model.CreateDirectoryFilesOptions;
import model.CreateInitialFilesOptions;

import java.io.IOException;

public class FilesUtils {
    /**
     * Create the initial files for the feature. The files are created in the selected directory.
     *
     * @param options the initial files options
     */
    public static void createInitialFiles(CreateInitialFilesOptions options) throws Exception {
        CreateDirectoryFilesOptions cdfOptions = CreateDirectoryFilesOptions.builder()
                .project(options.project())
                .dir(options.selectedDir())
                .selectedDir(options.selectedDir())
                .useSeparateFolders(options.useSeparateFolders())
                .build();

        for(InitialDirectories entry : InitialDirectories.values()) {
            if(entry.isUsedInCombinedFolder()) continue;
            entry.createFiles(cdfOptions);
        }
    }

    /**
     * Create the files for the controller directory.
     *
     * @param options Options for creating the files
     */
    public static void createControllerDirectoryFiles(CreateDirectoryFilesOptions options) {
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());
        final Integer javaVersion = CommonUtils.convertJavaVersionToInteger(CommonUtils.getJavaVersion(options.project()));

        final byte[] controllerTemplate = CommonUtils.loadFile(FileType.CONTROLLER.getTemplatePath());

        // Replace the template placeholders
        String controllerContent = new String(controllerTemplate);
        controllerContent = controllerContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath)
                .replace("$JAVAX_OR_JAKARTA$", javaVersion >= Constants.MIN_JAVA_VERSION_FOR_JAKARTA ? "jakarta" : "javax");

        if (options.useSeparateFolders()) {
            String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

            controllerContent = controllerContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "service." + prefix + "Service;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n"
                                    + "import " + packagePathWoCurrDir + "response." + prefix + "Response;\n"
                                    + "import " + packagePathWoCurrDir + "mapper." + prefix + "Mapper;\n");
        } else {
            controllerContent = controllerContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePath + ".response." + prefix + "Response;\n"
                                    + "import " + packagePath + ".mapper." + prefix + "Mapper;\n"
                                    + "import " + packagePath + ".request." + prefix + "Request;\n");
        }

        String finalControllerContent = controllerContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile controllerFile = options.dir().createFile(prefix + FileType.CONTROLLER.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] responseTemplate = CommonUtils.loadFile(FileType.RESPONSE.getTemplatePath());

        // Replace the template placeholders
        String responseContent = new String(responseTemplate);
        responseContent = responseContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
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
                            "$IMPORTS$", "");
        }

        String finalResponseContent = responseContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile responseFile = options.dir().createFile(prefix + FileType.RESPONSE.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());
        final Integer javaVersion = CommonUtils.convertJavaVersionToInteger(CommonUtils.getJavaVersion(options.project()));

        final byte[] requestTemplate = CommonUtils.loadFile(FileType.REQUEST.getTemplatePath());

        // Replace the template placeholders
        String requestContent = new String(requestTemplate);
        requestContent = requestContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath)
                .replace("$JAVAX_OR_JAKARTA$", javaVersion >= Constants.MIN_JAVA_VERSION_FOR_JAKARTA ? "jakarta" : "javax");

        String finalRequestContent = requestContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile requestFile = options.dir().createFile(prefix + FileType.REQUEST.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());
        final Integer javaVersion = CommonUtils.convertJavaVersionToInteger(CommonUtils.getJavaVersion(options.project()));

        final byte[] modelTemplate = CommonUtils.loadFile(FileType.MODEL.getTemplatePath());

        // Replace the template placeholders
        String modelContent = new String(modelTemplate);
        modelContent = modelContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath)
                .replace("$JAVAX_OR_JAKARTA$", javaVersion >= Constants.MIN_JAVA_VERSION_FOR_JAKARTA ? "jakarta" : "javax");

        String finalModelContent = modelContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile modelFile = options.dir().createFile(prefix + FileType.MODEL.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());
        final String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

        final byte[] serviceTemplate = CommonUtils.loadFile(FileType.SERVICE.getTemplatePath());

        // Replace the template placeholders
        String serviceContent = new String(serviceTemplate);
        serviceContent = serviceContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        if(options.useSeparateFolders()) {
            serviceContent = serviceContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "model." + prefix + ";\n"
                                    + "import " + packagePathWoCurrDir + "repository." + prefix + "Repository;\n"
                                    + "import " + packagePathWoCurrDir + "mapper." + prefix + "Mapper;\n"
                                    + "import " + packagePathWoCurrDir + "response." + prefix + "Response;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n");
        } else {
            serviceContent = serviceContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePath + ".mapper." + prefix + "Mapper;\n"
                                    + "import " + packagePath + ".response." + prefix + "Response;\n"
                                    + "import " + packagePath + ".request." + prefix + "Request;\n");
        }

        String finalServiceContent = serviceContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile serviceFile = options.dir().createFile(prefix + FileType.SERVICE.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] repositoryTemplate = CommonUtils.loadFile(FileType.REPOSITORY.getTemplatePath());

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
            PsiFile repositoryFile = options.dir().createFile(prefix + FileType.REPOSITORY.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());

        final byte[] dtoTemplate = CommonUtils.loadFile(FileType.DTO.getTemplatePath());

        // Replace the template placeholders
        String dtoContent = new String(dtoTemplate);
        dtoContent = dtoContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);

        String finalDtoContent = dtoContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile dtoFile = options.dir().createFile(prefix + FileType.DTO.getDefaultFileName());

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
        final String prefix = standardizeFileName(options.selectedDir().getName());
        final String packagePath = CommonUtils.calculatePackagePath(options.project(), options.dir(), options.selectedDir());
        final String packagePathWoCurrDir = packagePath.replace(options.dir().getName(), "");

        final byte[] mapperTemplate = CommonUtils.loadFile(FileType.MAPPER.getTemplatePath());

        // Replace the template placeholders
        String mapperContent = new String(mapperTemplate);
        mapperContent = mapperContent
                .replace("$PREFIX_CAPITALIZED$", prefix)
                .replace("$PREFIX_LOWERCASE$", prefix.toLowerCase())
                .replace("$SELECTED_DIR_PACKAGE_PATH$", packagePath);


        if (options.useSeparateFolders()) {

            mapperContent = mapperContent
                    .replace(
                            "$IMPORTS$",
                            "import " + packagePathWoCurrDir + "model." + prefix + ";\n"
                                    + "import " + packagePathWoCurrDir + "response." + prefix + "Response;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n");
        } else {
            mapperContent = mapperContent
                    .replace(
                            "$IMPORTS$", "import " + packagePathWoCurrDir + prefix + ";\n"
                                    + "import " + packagePathWoCurrDir + "response." + prefix + "Response;\n"
                                    + "import " + packagePathWoCurrDir + "request." + prefix + "Request;\n");
        }

        String finalMapperContent = mapperContent;

        WriteCommandAction.runWriteCommandAction(options.project(), () -> {
            PsiFile mapperFile = options.dir().createFile(prefix + FileType.MAPPER.getDefaultFileName());

            try {
                mapperFile.getVirtualFile().setBinaryContent(finalMapperContent.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Transforms the prefix into a standardized file name. It removes any dashes or underscores and creates a pascal case name as a single word.
     *
     * @param prefix   the prefix
     * @return the standardized file name
     */
    private static String standardizeFileName(String prefix) {
        // strip away any dashes or underscores and crate a pascal case name as single word
        String[] parts = prefix.split("[_-]");
        StringBuilder sb = new StringBuilder();

        for (String part : parts) {
            sb.append(CommonUtils.capitalize(part));
        }

        return sb.toString();
    }
}
