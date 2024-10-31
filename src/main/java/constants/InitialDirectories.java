package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.CreateDirectoryFilesOptions;
import utils.FilesUtils;

@Getter
@AllArgsConstructor
public enum InitialDirectories {
    CONTROLLER("controller", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createControllerDirectoryFiles(options);
        }
    },
    MODEL("model", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createModelDirectoryFiles(options);
        }
    },
    SERVICE("service", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createServiceDirectoryFiles(options);
        }
    },
    REPOSITORY("repository", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createRepositoryDirectoryFiles(options);
        }
    },
    RESPONSE("response", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createResponseDirectoryFiles(options);
        }
    },
    REQUEST("request", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createRequestDirectoryFiles(options);
        }
    },
    DTO("dto", false) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createDtoDirectoryFiles(options);
        }
    },
    MAPPER("mapper", true) {
        @Override
        public void createFiles(CreateDirectoryFilesOptions options) {
            FilesUtils.createMapperDirectoryFiles(options);
        }
    };

    private final String directoryName;
    private final boolean isUsedInCombinedFolder;

    public abstract void createFiles(CreateDirectoryFilesOptions options) throws Exception;
}