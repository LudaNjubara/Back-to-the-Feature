package group;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class MainGroup extends DefaultActionGroup {

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void update(AnActionEvent e) {
        Project project = e.getProject();

        // Set the availability based on whether a project is open
        e.getPresentation().setEnabledAndVisible(project != null);

        // Set the availability based on whether a directory is selected
        PsiElement selectedEl = e.getData(CommonDataKeys.PSI_ELEMENT);
        boolean isDirectory = selectedEl instanceof PsiDirectory;
        e.getPresentation().setEnabled(isDirectory);

        // Take this opportunity to set an icon for the group.
        //event.getPresentation().setIcon(SdkIcons.Sdk_default_icon); TODO: Add icon


    }
}
