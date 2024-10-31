package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.util.IncorrectOperationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void revertChanges(Project project, PsiDirectory selectedDir) throws IncorrectOperationException {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                Arrays.stream(selectedDir.getChildren()).forEach(PsiElement::delete);
            } catch (IncorrectOperationException e) {
                throw new RuntimeException("Error reverting changes: " + e.getMessage());
            }
        });
    }

    public static String getJavaVersion(Project project) {
        Sdk projectSdk = ProjectRootManager.getInstance(project).getProjectSdk();
        if (projectSdk != null) {
            return projectSdk.getVersionString();
        } else {
            return "No SDK configured";
        }
    }

    public static Integer convertJavaVersionToInteger(String javaVersion) {
        // Regular expression to match version numbers
        Pattern pattern = Pattern.compile("(\\d+)(\\.\\d+)?(\\.\\d+)?");
        Matcher matcher = pattern.matcher(javaVersion);

        if (matcher.find()) {
            // Extract the major version number
            String majorVersion = matcher.group(1);
            return Integer.parseInt(majorVersion);
        } else {
            throw new IllegalArgumentException("Unsupported Java version: " + javaVersion);
        }
    }

    /**
     * Calculate the package path from the selected directory.
     *
     * @param project     the project
     * @param dir         the parent directory of the file
     * @param selectedDir the selected directory
     * @return the package path
     */
    public static String calculatePackagePath(Project project, PsiDirectory dir, PsiDirectory selectedDir) {
        PsiManager psiManager = PsiManager.getInstance(project);
        StringBuilder packagePath = new StringBuilder();

        // Traverse upwards to find the source root
        PsiDirectory currentDir = selectedDir;
        while (currentDir != null && psiManager.findDirectory(currentDir.getVirtualFile()) != null) {
            if (isSourceRoot(project, currentDir)) {
                break;
            }
            if (!packagePath.isEmpty()) {
                packagePath.insert(0, ".");
            }
            packagePath.insert(0, currentDir.getName());
            currentDir = currentDir.getParent();
        }

        // Only append subdirectories if dir and selectedDir are not the same
        if (!dir.equals(selectedDir)) {
            appendSubdirectories(selectedDir, dir, packagePath);
        }

        return packagePath.toString();
    }

    /**
     * Append subdirectories to the package path.
     *
     * @param selectedDir the selected directory
     * @param currDir     the current directory
     * @param packagePath the package path
     */
    private static void appendSubdirectories(PsiDirectory selectedDir, PsiDirectory currDir, StringBuilder packagePath) {
            PsiDirectory dir = selectedDir.findSubdirectory(currDir.getName());

            if (!packagePath.isEmpty()) {
                packagePath.append(".");
            }

            assert dir != null;
            packagePath.append(dir.getName());
    }

    /**
     * Check if the directory is a source root.
     *
     * @param project the project
     * @param dir     the directory
     * @return true if the directory is a source root, false otherwise
     */
    private static boolean isSourceRoot(Project project, PsiDirectory dir) {
        VirtualFile[] sourceRoots = ProjectRootManager.getInstance(project).getContentSourceRoots();
        VirtualFile dirVirtualFile = dir.getVirtualFile();
        for (VirtualFile sourceRoot : sourceRoots) {
            if (sourceRoot.equals(dirVirtualFile)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] loadFile(String path) {
        try {
            return readFileAsBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Error loading file: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            throw new RuntimeException("File is too large: " + e.getMessage());
        } catch (SecurityException e) {
            throw new RuntimeException("Security error loading file: " + e.getMessage());
        }
    }

    private static byte[] readFileAsBytes(String resourcePath) throws IOException {
        try (InputStream inputStream = CommonUtils.class.getResourceAsStream(resourcePath);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }

            byte[] data = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }

            return buffer.toByteArray();
        }
    }
}
