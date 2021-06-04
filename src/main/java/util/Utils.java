package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public static String exeCmd(String commandStr) throws Exception {
        System.out.println("exeCmd: " + commandStr);
        BufferedReader br = null;
        Process p = Runtime.getRuntime().exec(commandStr);
        br = new BufferedReader(new InputStreamReader(p.getInputStream(), "GBK"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        System.out.println("exeCmd：" + sb.toString());
        br.close();
        return sb.toString();
    }


    public static void openUrl(String url) {
        String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
