package qr;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.QRCodeUtils;
import util.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QRController implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    public ImageView ivCode;
    @FXML
    public ImageView ivImgCode;
    @FXML
    public TextArea tfText;
    @FXML
    public TextField tfPic;
    @FXML
    public Label tvConsole;

    private BufferedImage qrCode;
    private BufferedImage qrImgCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void actionQr() {
        String textStr = tfText.getText();
        if (Utils.isEmpty(textStr)) {
            tvConsole.setText("生成的内容不能为空！");
            return;
        }
        try {
            qrCode = QRCodeUtils.createImage(textStr);
            ivCode.setImage(QRCodeUtils.fxImage(qrCode));
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    public void actionSave1() {
        saveImg(ivCode.getImage());
    }

    public void actionSave2() {
        saveImg(ivImgCode.getImage());
    }

    public void actionAdd() {
        if (qrCode == null) {
            tvConsole.setText("请先生成普通二维码！");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择密钥配置");
        File file = new File(tfPic.getText()).getParentFile();
        if (file != null && file.exists()) {
            fileChooser.setInitialDirectory(file);
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg"));
        File picFile = fileChooser.showOpenDialog(getStage());
        if (picFile == null) return;
        tfPic.setText(picFile.getAbsolutePath());
        try {
            qrImgCode = qrCode;
            QRCodeUtils.insertImage(qrImgCode, picFile.getAbsolutePath());
            ivImgCode.setImage(QRCodeUtils.fxImage(qrImgCode));
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
            e.printStackTrace();
        }
    }


    private Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }


    private void saveImg(Image img) {
        if (img == null) {
            tvConsole.setText("请先生成二维码！");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存图片");
        File file = new File(tfPic.getText()).getParentFile();
        if (file != null && file.exists()) {
            fileChooser.setInitialDirectory(file);
        } else {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        }
        fileChooser.setInitialFileName("qrcode.png");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png", "*.jpg"));
        File picFile = fileChooser.showSaveDialog(getStage());
        if (picFile == null) return;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", picFile);
            tvConsole.setText("保存图片到" + picFile.getAbsolutePath() + "成功！");
        } catch (IOException e) {
            tvConsole.setText(e.getMessage());
            e.printStackTrace();
        }
    }

}
