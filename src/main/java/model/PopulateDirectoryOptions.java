package model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import lombok.Builder;

@Builder
public record PopulateDirectoryOptions(Project project, PsiDirectory dir, PsiDirectory selectedDir, boolean useSeparateFolders) {
}
