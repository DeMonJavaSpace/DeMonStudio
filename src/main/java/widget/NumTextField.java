package widget;

import javafx.scene.control.TextField;

/**
 * @author DeMon
 * Created on 2022/2/18.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
public class NumTextField extends TextField {
    private static final String TAG = "NumTextField";

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]")) {
            super.replaceText(start, end, text);
        }
    }


    @Override
    public void replaceSelection(String replacement) {
        if (replacement.matches("[0-9]")) {
            super.replaceSelection(replacement);
        }
    }
}
