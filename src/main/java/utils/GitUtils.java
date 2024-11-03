package utils;

import exception.GitException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class GitUtils {

    public static void addFilesToGit(String projectRootPath, String directoryPath) throws IOException, InterruptedException {
        File gitDir = new File(projectRootPath, ".git");
        if (gitDir.exists() || !gitDir.isDirectory()) {
            throw new GitException(
                    "Git is not configured for the current project",
                    "Please configure Git for this project to enable automatic VCS integration with newly generated features. Check plugins' settings for additional configuration!");
        }

        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("git", "add", ".");
        processBuilder.directory(new File(directoryPath));

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

            String line;

            // Handle errors if any
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new GitException("Failed to add generated feature to VCS", errorOutput.toString());
            }
        }
    }
}