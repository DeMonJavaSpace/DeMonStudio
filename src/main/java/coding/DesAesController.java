package coding;

import util.DESUtil;
import util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DesAesController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField tfText;
    @FXML
    private TextField tfResult;
    @FXML
    private TextField tfKey;
    @FXML
    private Label tvConsole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    public void actionOpen1(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要加/解密的文件");
        File file = new File(tfText.getText()).getParentFile();
        if (file != null && file.exists()) {
            fileChooser.setInitialDirectory(file);
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        File chooserFile = fileChooser.showOpenDialog(getStage());
        if (chooserFile != null && chooserFile.exists()) {
            tfText.setText(chooserFile.getAbsolutePath());
        }
    }

    public void actionOpen2(ActionEvent event) {
        File file = new File(tfResult.getText());
        if (file.exists()) {
            Utils.openUrl(file.getParent());
        } else {
            tvConsole.setText("无法打开加/解密后的文件！");
        }
    }

    public void actionDesEncrypt(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            File file = new File(textStr);
            String result;
            if (file.exists()) {
                result = DESUtil.getInstance().encryptFile(tfKey.getText(), file.getAbsolutePath());
            } else {
                result = DESUtil.getInstance().encrypt(tfKey.getText(), textStr);
            }
            tfResult.setText(result);
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

    public void actionDesDecrypt(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            File file = new File(textStr);
            String result;
            if (file.exists()) {
                result = DESUtil.getInstance().decryptFile(tfKey.getText(), file.getAbsolutePath());
            } else {
                result = DESUtil.getInstance().decrypt(tfKey.getText(), textStr);
            }
            tfResult.setText(result);
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

}
