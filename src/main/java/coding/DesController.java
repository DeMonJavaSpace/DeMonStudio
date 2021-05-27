package coding;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import util.DESUtil;
import util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class DesController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TextArea tfText;
    @FXML
    private TextArea tfResult;
    @FXML
    private TextField tfKey;
    @FXML
    private TextField tfParameter;
    @FXML
    private Label tvConsole;
    @FXML
    private ChoiceBox cbCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbCode.getItems().addAll("UTF-8", "GB2312", "GBK");
        cbCode.getSelectionModel().selectFirst();

        cbCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue + " " + newValue);
            DESUtil.getInstance().setCharset((String) newValue);
        });
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

    public void actionEncrypt(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            File file = new File(textStr);
            String result;
            DESUtil.getInstance().setDesKey(tfKey.getText());
            DESUtil.getInstance().setIvParameter(tfParameter.getText());
            if (file.exists()) {
                result = DESUtil.getInstance().encryptFile(file.getAbsolutePath());
            } else {
                result = DESUtil.getInstance().encrypt(textStr);
            }
            System.out.println(result);
            tfResult.setText(result);
            tvConsole.setText("");
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

    public void actionDecrypt(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            File file = new File(textStr);
            String result;
            DESUtil.getInstance().setDesKey(tfKey.getText());
            DESUtil.getInstance().setIvParameter(tfParameter.getText());
            if (file.exists()) {
                result = DESUtil.getInstance().decryptFile(file.getAbsolutePath());
            } else {
                result = DESUtil.getInstance().decrypt(textStr);
            }
            System.out.println(result);
            tfResult.setText(result);
            tvConsole.setText("");
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

}
