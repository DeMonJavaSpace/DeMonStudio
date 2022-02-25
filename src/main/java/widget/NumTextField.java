package widget;

import javafx.scene.control.TextField;
import util.Log;
import util.TextUtils;

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
        Log.i(TAG, "replaceText=" + text);
        if (TextUtils.isDigitsOnly(text)) {
            super.replaceText(start, end, text);
        }
    }


    @Override
    public void replaceSelection(String replacement) {
        Log.i(TAG, "replaceSelection=" + replacement);
        if (TextUtils.isDigitsOnly(replacement)) {
            super.replaceSelection(replacement);
        }
    }
}
