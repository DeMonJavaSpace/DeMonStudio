package string;


import util.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StringController implements Initializable {
    @FXML
    private TextField tfText;
    @FXML
    private TextField tfReplace1;
    @FXML
    private TextField tfReplace2;
    @FXML
    private TextField tfResult;
    @FXML
    private Label tvConsole;
    @FXML
    private RadioButton rbAll;
    @FXML
    private RadioButton rbFirst;

    private ToggleGroup rbGroup;

    private int replaceRuler = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rbGroup = new ToggleGroup();
        rbAll.setToggleGroup(rbGroup);
        rbAll.setSelected(true);
        rbFirst.setToggleGroup(rbGroup);
        rbGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            replaceRuler = Integer.parseInt(newValue.getUserData().toString());
        });

    }

    public void actionReplace(ActionEvent event) {
        try {
            String textStr = tfText.getText();
            System.out.println("要处理的文本内容---" + textStr);
            if (Utils.isEmpty(textStr)) {
                tvConsole.setText("要处理的文本内容不能为空！");
                return;
            }
            String replace1Str = tfReplace1.getText();
            System.out.println("被替换的内容---" + replace1Str);
            if (Utils.isEmpty(replace1Str)) {
                tvConsole.setText("被替换的内容不能为空！");
                return;
            }
            String replace2Str = Utils.isEmpty(tfReplace2.getText()) ? "" : tfReplace2.getText();
            System.out.println("要替换的内容---" + replace2Str);
            String result = "";
            switch (replaceRuler) {
                case 1:
                    result = textStr.replaceFirst(replace1Str, replace2Str);
                    break;
                default:
                    result = textStr.replaceAll(replace1Str, replace2Str);
                    break;
            }
            tfResult.setText(result);
            tvConsole.setText("替换成功！");
        } catch (Exception e) {
            tvConsole.setText(e.getMessage());
        }
    }

}
