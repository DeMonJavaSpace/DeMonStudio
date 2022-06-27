package util;

import javafx.application.Platform;

/**
 * @author DeMon
 * Created on 2021/6/4.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
public class ThreadUtils {


    public static void runOnUiThread(Runnable run) {
        Platform.runLater(run);
    }


    public static void runOnIOThread(Runnable run) {
        new Thread(run).start();
    }
}
