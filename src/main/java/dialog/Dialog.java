package dialog;

public interface Dialog {
    void initDialog();
    boolean validateDialog();
    void resetDialog();
    void onOK();
    void onCancel();
}
