package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class dashboard implements Initializable {

    @FXML
    public Label temprature;

    @FXML
    public Label pressure;

    @FXML
    public Label altidute;

    @FXML
    public Label naturalgassensor;

    @FXML
    public Label cosensor;

    ConnectionClass conn = null;
    Connection connection = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = new ConnectionClass();
        connection = conn.getConnection();

        Timer timer = new Timer(true); //set it as a deamon
        timer.schedule(new MyTimer(), 0, 1000);

    }
    @FXML
    private void home(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public class MyTimer extends TimerTask {

        @Override
        public void run() {

            try {
                Statement statement = connection.createStatement();

                String sql = "SELECT * FROM alldata ORDER BY data_id DESC";
                ResultSet set = statement.executeQuery(sql);


                set.next();
                String d = set.getString("data");
                String[] strarr = d.split(",");
//                System.out.println(Arrays.toString(strarr));

                Platform.runLater(()->{
                    temprature.setText(strarr[0]+"c");
                    pressure.setText(strarr[1]+"pa");
                    altidute.setText(strarr[2]+"");
                    naturalgassensor.setText(strarr[3]);
                    cosensor.setText(strarr[4]);

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
