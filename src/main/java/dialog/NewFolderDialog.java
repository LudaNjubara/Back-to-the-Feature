package dialog;


import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.ui.JBColor;
import exception.ValidationException;
import io.sentry.Sentry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import utils.CommonUtils;
import utils.DirectoryUtils;
import utils.FeatureUtils;
import utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class NewFolderDialog extends JDialog implements Dialog {
    private final Project project;
    private final PsiDirectory parentDir;

    private String newFolderName;
    private PsiDirectory newDir;
    private JTextField folderNameField;
    private JLabel validationMessage;
    private List<String> existingFolderNames;

    @Override
    public void initDialog() {

        existingFolderNames = Arrays.stream(parentDir.getSubdirectories())
                .map(PsiDirectory::getName)
                .collect(Collectors.toList());


        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Generate Feature From a New Folder");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(350, 150));

        // Input field
        folderNameField = new JTextField(20);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Folder Name:"));
        inputPanel.add(folderNameField);
        add(inputPanel, BorderLayout.CENTER);

        // Validation message
        validationMessage = new JLabel("", SwingConstants.CENTER);
        validationMessage.setForeground(JBColor.RED);
        add(validationMessage, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("Generate");
        JButton cancelButton = new JButton("Cancel");

        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> {
            if (validateDialog()) {
                newFolderName = folderNameField.getText();
                onOK();
            }
        });

        cancelButton.addActionListener(e -> {
            onCancel();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void onCancel() {
        resetDialog();
        dispose();
    }

    @Override
    public void onOK() {
        try {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                newDir = DirectoryUtils.createDirectory(newFolderName, parentDir);
            });

            FeatureUtils.generateFeature(project, newDir);

            dispose();
        } catch (ValidationException vEx) {
            Messages.showMessageDialog(project, vEx.getMessage(), "Error", Messages.getErrorIcon());
        } catch (Exception ex) {
            try {
                CommonUtils.revertChanges(project, newDir);
            } catch (Exception ex1) {
                dispose();
                Sentry.captureException(ex1);
                Messages.showMessageDialog(project, "Error: " + ex1.getMessage(), "Error", Messages.getErrorIcon());
            }

            dispose();
            Sentry.captureException(ex);
            Messages.showMessageDialog(project, ex.getMessage(), "Error", Messages.getErrorIcon());
        }
    }

    @Override
    public boolean validateDialog() {
        try {
            ValidationUtils.validateNewFolderName(folderNameField.getText(), existingFolderNames);
            validationMessage.setText("");
            return true;
        } catch (ValidationException ex) {
            validationMessage.setText(ex.getMessage());
            return false;
        }
    }

    @Override
    public void resetDialog() {
        folderNameField.setText("");
        validationMessage.setText("");
    }
}
