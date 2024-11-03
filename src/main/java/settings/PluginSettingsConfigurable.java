package settings;

import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;
import settings.components.AutomaticGitIntegrationComponent;
import settings.components.UseSeparateFoldersComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PluginSettingsConfigurable implements Configurable {
    private JPanel settingsPanel;
    private final List<SettingComponent> settingComponents = new ArrayList<>();

    public PluginSettingsConfigurable() {
        PluginSettingsState settings = PluginSettingsState.getInstance();
        settingComponents.add(new UseSeparateFoldersComponent(
                "Use Separate Folders",
                settings.useSeparateFolders,
                "Generate folder for each aspect of the feature. If unchecked, generated files will be generated without accompanying parent folders."));
        settingComponents.add(new AutomaticGitIntegrationComponent(
                "Automatic Git Integration",
                settings.automaticGitIntegration,
                "Automatically add generated feature files to Git VCS."));
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Back to the Feature";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        for (SettingComponent settingComponent : settingComponents) {
            settingsPanel.add(settingComponent.createComponent());
            settingsPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing
        }
        return settingsPanel;
    }

    @Override
    public boolean isModified() {
        for (SettingComponent settingComponent : settingComponents) {
            if (settingComponent.isModified()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void apply() {
        for (SettingComponent settingComponent : settingComponents) {
            settingComponent.apply();
        }
    }

    @Override
    public void reset() {
        for (SettingComponent settingComponent : settingComponents) {
            settingComponent.reset();
        }
    }

    @Override
    public void disposeUIResources() {
        settingsPanel = null;
        settingComponents.clear();
    }
}