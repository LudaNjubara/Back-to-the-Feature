package settings.components;

import settings.PluginSettingsState;
import settings.SettingComponent;

import javax.swing.*;


public class AutomaticGitIntegrationComponent extends SettingComponent {
    private final JCheckBox checkBox;
    private final JLabel descriptionLabel;
    private final String label;
    private final boolean initialValue;

    public AutomaticGitIntegrationComponent(String label, boolean initialValue, String description) {
        this.label = label;
        this.initialValue = initialValue;
        this.checkBox = new JCheckBox(label, initialValue);
        this.descriptionLabel = new JLabel("<html><body style='width: 400px;'>" + description + "</body></html>");
        this.descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
    }

    @Override
    public JComponent createComponent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(checkBox);
        panel.add(descriptionLabel);
        component = panel;

        return component;
    }

    @Override
    public boolean isModified() {
        return checkBox.isSelected() != initialValue;
    }

    @Override
    public void apply() {
        PluginSettingsState settings = PluginSettingsState.getInstance();
        settings.automaticGitIntegration = checkBox.isSelected();
    }

    @Override
    public void reset() {
        checkBox.setSelected(initialValue);
    }
}