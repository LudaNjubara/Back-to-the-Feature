package settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "settings.PluginSettingsState",
        storages = @Storage("PluginSettings.xml")
)
public class PluginSettingsState implements PersistentStateComponent<PluginSettingsState> {
    public boolean useSeparateFolders = false;
    public boolean automaticGitIntegration = true;

    @Nullable
    @Override
    public PluginSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettingsState state) {
        this.useSeparateFolders = state.useSeparateFolders;
        this.automaticGitIntegration = state.automaticGitIntegration;
    }

    public static PluginSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(PluginSettingsState.class);
    }
}