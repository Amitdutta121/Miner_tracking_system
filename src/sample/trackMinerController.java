package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class trackMinerController implements Initializable{
    @FXML
    public Circle circle;

    @FXML
    public Circle circle2;

    @FXML
    private Label distance_label;

    ConnectionClass conn = null;
    Connection connection = null;

    @FXML
    private void home(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = new ConnectionClass();
        connection = conn.getConnection();
        Timer timer = new Timer(true); //set it as a deamon
        timer.schedule(new MyTimer(), 0, 100);
    }


    public class MyTimer extends TimerTask {

        @Override
        public void run() {
            try {
                Statement statement = connection.createStatement();

                String sql = "SELECT * FROM alldata ORDER BY data_id DESC";
                ResultSet set = statement.executeQuery(sql);


                set.next();
                String connectedRouter = set.getString("router_mac_address");
                String d = set.getString("distance");
                double mainDistance = calculateDistance(Double.parseDouble(d),2400.0);



                Platform.runLater(()->{
                    if (connectedRouter.equals("RIP")){
                        circle2.setVisible(false);
                        circle.setLayoutX(Math.round(mainDistance*10));
                    }else{
                        circle.setVisible(false);
                        circle2.setLayoutX(Math.round(750-mainDistance*10));
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }
}
