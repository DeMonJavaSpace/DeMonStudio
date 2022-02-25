package coding;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.MD5Utils;
import util.Utils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MD5Controller implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TextArea tfText;
    @FXML
    private TextField tfResult1;
    @FXML
    private TextField tfResult2;
    @FXML
    private TextField tfResult3;
    @FXML
    private TextField tfResult4;
    @FXML
    private Label tvConsole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

    public void actionOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择需要加密的文件");
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

    public void actionMD5(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            File file = new File(textStr);
            String result;
            if (file.exists()) {
                result = MD5Utils.MD5EncodeObject(file);
            } else {
                result = MD5Utils.MD5Encode(textStr);
            }
            tfResult1.setText(result.toUpperCase());
            tfResult2.setText(result);
            tfResult3.setText(result.toUpperCase().substring(8, 24));
            tfResult4.setText(result.substring(8, 24));
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }


}
