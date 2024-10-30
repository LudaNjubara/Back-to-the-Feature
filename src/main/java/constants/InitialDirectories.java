package constants;

import lombok.Getter;
import model.CreateDirectoryFilesOptions;
import utils.FilesUtils;

@Getter
public enum InitialDirectories {
    CONTROLLER("controller", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createControllerDirectoryFiles(options);
        }
    },
    MODEL("model", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createModelDirectoryFiles(options);
        }
    },
    SERVICE("service", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createServiceDirectoryFiles(options);
        }
    },
    REPOSITORY("repository", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createRepositoryDirectoryFiles(options);
        }
    },
    RESPONSE("response", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createResponseDirectoryFiles(options);
        }
    },
    REQUEST("request", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createRequestDirectoryFiles(options);
        }
    },
    DTO("dto", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createDtoDirectoryFiles(options);
        }
    },
    MAPPER("mapper", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) throws Exception {
            FilesUtils.createMapperDirectoryFiles(options);
        }
    };

    private final String directoryName;
    private final boolean isUsedInCombinedFolder;

    InitialDirectories(String directoryName, boolean isUsedInCombinedFolder) {
        this.directoryName = directoryName;
        this.isUsedInCombinedFolder = isUsedInCombinedFolder;
    }

    public abstract void createFiles(CreateDirectoryFilesOptions options) throws Exception;
}