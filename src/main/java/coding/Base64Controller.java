package coding;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.Utils;

import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class Base64Controller implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TextArea tfText;
    @FXML
    private TextArea tfResult;
    @FXML
    private ChoiceBox cbCode;
    @FXML
    private Label tvConsole;

    private String charset = "UTF-8";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cbCode.getItems().addAll("UTF-8", "GB2312", "GBK");
        cbCode.getSelectionModel().selectFirst();

        cbCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue + " " + newValue);
            charset = (String) newValue;
        });
    }


    public void actionEncrypt(ActionEvent event) {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("没有要处理的内容！");
            return;
        }
        try {
            String result = Base64.getEncoder().encodeToString(textStr.getBytes(charset));
            tfResult.setText(result);
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
            String result = new String(Base64.getDecoder().decode(textStr), charset);
            tfResult.setText(result);
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

}
