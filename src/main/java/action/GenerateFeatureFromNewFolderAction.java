package action;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import dialog.NewFolderDialog;
import exception.ValidationException;
import io.sentry.Sentry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.CommonUtils;
import utils.DirectoryUtils;
import utils.FeatureUtils;

import javax.swing.*;

public class GenerateFeatureFromNewFolderAction extends AnAction {

    public GenerateFeatureFromNewFolderAction() {
        super();
    }

    public GenerateFeatureFromNewFolderAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();

        // get the selected directory
        PsiElement selectedEl = e.getData(CommonDataKeys.PSI_ELEMENT);
        if (!(selectedEl instanceof PsiDirectory selectedDir)) {
            return;
        }

        NewFolderDialog dialog = new NewFolderDialog(project, selectedDir);
        dialog.initDialog();
    }
}

