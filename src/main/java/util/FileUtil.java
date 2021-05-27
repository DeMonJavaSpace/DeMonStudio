package util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author DeMon
 * Created on 2021/5/27.
 * E-mail 757454343@qq.com
 * Desc:
 */
public class FileUtil {
    /**
     * 往文件中写入字符串
     *
     * @param filePath 文件保存路径
     * @param content  文件内容
     * @param append   是否追加
     */
    public static void writeTxt(String filePath, String content, boolean append) {
        try {
            File file = new File(filePath);
            //构造函数中的第二个参数true表示以追加形式写文件
            FileWriter fw = new FileWriter(file.getAbsolutePath(), append);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeTxt(String filePath, String content) {
        writeTxt(filePath, content, false);
    }

    /**
     * 读取文件中的字符串
     *
     * @param filePath
     * @return
     */
    public static String readText(String filePath) {
        StringBuilder sb = new StringBuilder();
        try {
            FileReader fr = new FileReader(filePath);
            char[] buf = new char[1024];
            int num = 0;
            while ((num = fr.read(buf)) != -1) {
                sb.append(new String(buf, 0, num));
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
