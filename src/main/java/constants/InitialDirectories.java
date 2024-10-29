package constants;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import lombok.Getter;
import utils.FilesUtils;

@Getter
public enum InitialDirectories {
    CONTROLLER("controller", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createControllerDirectoryFiles(project, dir, selectedDir);
        }
    },
    MODEL("model", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createModelDirectoryFiles(project, dir, selectedDir);
        }
    },
    SERVICE("service", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createServiceDirectoryFiles(project, dir, selectedDir);
        }
    },
    REPOSITORY("repository", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createRepositoryDirectoryFiles(project, dir, selectedDir);
        }
    },
    RESPONSE("response", true) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createResponseDirectoryFiles(project, dir, selectedDir);
        }
    },
    REQUEST("request", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createRequestDirectoryFiles(project, dir, selectedDir);
        }
    },
    DTO("dto", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createDtoDirectoryFiles(project, dir, selectedDir);
        }
    },
    MAPPER("mapper", false) {
        @Override
        public void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception {
            FilesUtils.createMapperDirectoryFiles(project, dir, selectedDir);
        }
    };

    private final String directoryName;
    private final boolean isUsedInCombinedFolder;

    InitialDirectories(String directoryName, boolean isUsedInCombinedFolder) {
        this.directoryName = directoryName;
        this.isUsedInCombinedFolder = isUsedInCombinedFolder;
    }

    public abstract void createFiles(Project project, PsiDirectory dir, PsiDirectory selectedDir) throws Exception;
}