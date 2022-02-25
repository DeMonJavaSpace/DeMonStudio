package convert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Log;
import util.ThreadUtils;
import util.TimeUtils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author DeMon
 * Created on 2022/2/25.
 * E-mail idemon_liu@qq.com
 * Desc:
 */
public class TimestampController implements Initializable {
    private static final String TAG = "TimestampController";
    @FXML
    private TextField etMillisecond;
    @FXML
    private TextField etSecond;
    @FXML
    private TextField etTime1;
    @FXML
    private TextField etTime2;
    @FXML
    private TextField etTime3;
    @FXML
    private TextField etTime4;
    @FXML
    private TextField etDateTime;
    @FXML
    private TextField etTimeStamp;
    @FXML
    private Label tvConsole;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> cbHour;
    @FXML
    private ComboBox<String> cbMin;
    @FXML
    private ComboBox<String> cbSecond;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        syncTime();
        datePicker.setValue(LocalDate.now());
        cbHour.setValue(TimeUtils.getHour() + "");
        cbMin.setValue(TimeUtils.getMinutes() + "");
        cbSecond.setValue(TimeUtils.getSeconds() + "");
        etMillisecond.setText(System.currentTimeMillis() + "");
        etSecond.setText(System.currentTimeMillis() / 1000 + "");
        for (int i = 1; i < 25; i++) {
            cbHour.getItems().add(i + "");
        }
        for (int i = 1; i < 61; i++) {
            cbMin.getItems().add(i + "");
            cbSecond.getItems().add(i + "");
        }
    }

    public void change1(ActionEvent event) {
        try {
            long time = Long.parseLong(etMillisecond.getText());
            etTime1.setText(TimeUtils.format(time));
        } catch (Exception e) {
            e.printStackTrace();
            tvConsole.setText(e.getLocalizedMessage());
        }
    }


    public void change2(ActionEvent event) {
        try {
            long time = Long.parseLong(etSecond.getText()) * 1000;
            etTime2.setText(TimeUtils.format(time));
        } catch (Exception e) {
            e.printStackTrace();
            tvConsole.setText(e.getLocalizedMessage());
        }
    }

    public void change3(ActionEvent event) {
        try {
            String date = datePicker.getValue().toString();
            String hour = cbHour.getValue();
            String min = cbMin.getValue();
            String second = cbSecond.getValue();
            String time = date + " " + hour + ":" + min + ":" + second;
            Log.i(TAG, "change3: " + time);
            long timestamp = TimeUtils.parse(time);
            etTime3.setText(timestamp + "");
            etTime4.setText(timestamp / 1000 + "");
        } catch (Exception e) {
            e.printStackTrace();
            tvConsole.setText(e.getLocalizedMessage());
        }
    }


    private void syncTime() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                ThreadUtils.runOnUiThread(() -> {
                    etDateTime.setText(TimeUtils.getNowTimeStr());
                    etTimeStamp.setText(System.currentTimeMillis() + "");
                });
            }
        }, 0, 1000);
    }
}
