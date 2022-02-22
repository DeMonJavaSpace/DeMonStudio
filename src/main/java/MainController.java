import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import util.Log;
import util.Utils;
import widget.NumTextField;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final String TAG = "MainController";
    @FXML
    public MenuBar menuBar;
    @FXML
    public Menu menu0;
    @FXML
    public Menu menu1;
    @FXML
    public Menu menu2;
    @FXML
    public Menu menu3;
    @FXML
    public Menu menu4;
    @FXML
    public ScrollPane main_pane;
    @FXML
    public AnchorPane home_pane;
    @FXML
    public NumTextField tfText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenus();
        tfText.textProperty().addListener((observable, oldValue, newValue) -> {
            Log.i(TAG, newValue);
            int value = Integer.parseInt(newValue);
            if (value <= 0 || value > 525600) {
                tfText.setText("30");
            }
        });
    }

    /**
     * 实现Menu的点击事件
     * https://blog.csdn.net/Michean/article/details/103190820
     */
    public void initMenus() {
        Label label1 = new Label("首页");
        label1.setOnMouseClicked(event -> {
            main_pane.setContent(home_pane);
        });
        menu0.setGraphic(label1);
        Label label3 = new Label("二维码");
        label3.setOnMouseClicked(event -> {
            showView("fxml/qr_code.fxml");
        });
        menu3.setGraphic(label3);
        Label label4 = new Label("Apk签名");
        label4.setOnMouseClicked(event -> {
            showView("fxml/apk_signed.fxml");
        });
        menu4.setGraphic(label4);
    }

    public void openStringReplace(ActionEvent event) {
        showView("fxml/string_replace.fxml");
    }

    public void openDES(ActionEvent event) {
        showView("fxml/coding_des.fxml");
    }


    public void openAES(ActionEvent event) {
        showView("fxml/coding_aes.fxml");
    }

    public void openMD5(ActionEvent event) {
        showView("fxml/coding_md5.fxml");
    }


    public void openBase64(ActionEvent event) {
        showView("fxml/coding_base64.fxml");
    }

    private void showView(String xml) {
        try {
            main_pane.setContent(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(xml))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(ActionEvent event) {
        int value = Integer.parseInt(tfText.getText());
        tfText.setText(value + 30 + "");
    }

    public void reduce(ActionEvent event) {
        int value = Integer.parseInt(tfText.getText());
        tfText.setText(value - 30 + "");
    }

    public void shutdown(ActionEvent event) {
        try {
            int value = Integer.parseInt(tfText.getText()) * 60;
            Utils.exeCmd("Shutdown.exe -s -t " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent event) {
        try {
            Utils.exeCmd("Shutdown.exe /a");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
