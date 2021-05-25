import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public MenuBar menuBar;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenus();
    }

    /**
     * 实现Menu的点击事件
     * https://blog.csdn.net/Michean/article/details/103190820
     */
    public void initMenus() {
        Label label1 = new Label("字符串替换");
        label1.setOnMouseClicked(event -> {
            showView("fxml/string_replace.fxml");
        });
        menu1.setGraphic(label1);
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

    public void openDES(ActionEvent event) {
        showView("fxml/coding_des.fxml");
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
}
