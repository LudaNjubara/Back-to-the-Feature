package settings;

import lombok.Getter;

import javax.swing.*;

@Getter
public abstract class SettingComponent {
    protected JComponent component;

    public abstract JComponent createComponent();

    public abstract boolean isModified();

    public abstract void apply();

    public abstract void reset();

}