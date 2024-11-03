package action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import exception.GitException;
import exception.ValidationException;
import io.sentry.Sentry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.CommonUtils;
import utils.FeatureUtils;

import javax.swing.*;

public class GenerateFeatureFromExistingFolderAction extends AnAction {

    public GenerateFeatureFromExistingFolderAction() {
        super();
    }

    public GenerateFeatureFromExistingFolderAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
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

        try {
            FeatureUtils.generateFeature(project, selectedDir);
        } catch (ValidationException vEx) {
            Messages.showMessageDialog(project, vEx.getMessage(), "Error", Messages.getErrorIcon());
        } catch (GitException gitEx) {
            Sentry.captureException(gitEx);

            Notification notification = new Notification(
                    "Back_to_the_Feature",
                    gitEx.getTitle(),
                    gitEx.getMessage(),
                    NotificationType.WARNING);
            Notifications.Bus.notify(notification, project);
        } catch (Exception ex) {
            try {
                CommonUtils.revertChanges(project, selectedDir);
            } catch (Exception ex1) {
                Sentry.captureException(ex1);
                Messages.showMessageDialog(project, "Error: " + ex1.getMessage(), "Error", Messages.getErrorIcon());
            }

            Sentry.captureException(ex);
            Messages.showMessageDialog(project, ex.getMessage(), "Error", Messages.getErrorIcon());
        }
    }
}
