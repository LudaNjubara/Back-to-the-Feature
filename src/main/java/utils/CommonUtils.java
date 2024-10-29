package utils;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
