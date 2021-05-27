package apk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.TilePane;
import util.FileUtil;
import util.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author DeMon
 * Created on 2021/5/27.
 * E-mail 757454343@qq.com
 * Desc:
 */
public class ChannelController implements Initializable {

    @FXML
    private TextArea tfChannel;
    @FXML
    private TilePane tilePane;
    @FXML
    public Label tvConsole;

    private File channelFile;
    private File selectedFile;
    private List<String> channelList = new ArrayList<>();

    private List<String> selectedList = new ArrayList<>();

    private List<CheckBox> checkBoxList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            selectedFile = new File(System.getProperty("user.dir") + "/channel_selected.txt");
            if (!selectedFile.exists()) {
                selectedFile.createNewFile();
            } else {
                String selects = FileUtil.readText(selectedFile.getPath());
                if (!Utils.isEmpty(selects)) {
                    selectedList.clear();
                    channelList.addAll(Arrays.asList(selects.split(";")));
                }
            }
            channelFile = new File(System.getProperty("user.dir") + "/channel.txt");
            if (!channelFile.exists()) {
                channelFile.createNewFile();
            } else {
                String channels = FileUtil.readText(channelFile.getPath());
                if (!Utils.isEmpty(channels)) {
                    tfChannel.setText(channels);
                    updateChannels(channels);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateChannels(String channels) {
        channelList.clear();
        tilePane.getChildren().clear();
        checkBoxList.clear();
        String[] channelArray = channels.split(";");
        for (String channel : channelArray) {
            if (!channelList.contains(channel)) {
                channelList.add(channel);
            }
        }
        for (String channel : channelList) {
            CheckBox checkBox = new CheckBox(channel);
            checkBox.setSelected(selectedList.contains(channel));
            checkBoxList.add(checkBox);
            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println(channel + ":" + newValue);
                if (newValue) {
                    selectedList.add(channel);
                } else {
                    selectedList.remove(channel);
                }
            });
            tilePane.getChildren().add(checkBox);
        }
    }


    @FXML
    private void doRefresh(ActionEvent actionEvent) {
        String channels = tfChannel.getText();
        FileUtil.writeTxt(channelFile.getPath(), channels);
        selectedList.clear();
        FileUtil.writeTxt(selectedFile.getPath(), "");
        updateChannels(channels);
    }

    @FXML
    private void doAll(ActionEvent actionEvent) {
        for (CheckBox box : checkBoxList) {
            box.setSelected(true);
        }
    }

    @FXML
    private void doAllNo(ActionEvent actionEvent) {
        for (CheckBox box : checkBoxList) {
            box.setSelected(false);
        }
    }

    @FXML
    private void doSave(ActionEvent actionEvent) {
        if (selectedList.isEmpty()) {
            tvConsole.setText("请至少选择一个渠道！");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : selectedList) {
            sb.append(s);
            sb.append(";");
        }
        System.out.println(sb);
        FileUtil.writeTxt(selectedFile.getPath(), sb.toString());
    }
}
