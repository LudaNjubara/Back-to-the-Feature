package model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import lombok.Builder;

@Builder
public record CreateInitialFilesOptions(Project project, PsiDirectory selectedDir, boolean useSeparateFolders) {
}
