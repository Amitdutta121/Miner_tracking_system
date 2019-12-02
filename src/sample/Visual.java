package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Visual implements Initializable {
    @FXML
    private LineChart<?, ?> linecartdata;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private Button temp;

    @FXML
    private Button press;

    @FXML
    private Button alt;
    @FXML
    private Button gas1;

    @FXML
    private Button gas2;

    @FXML
    private Button home;

    @FXML
    private TextField datalength;



    public Visual() {
    }

    public int dataValue = 10;
    ConnectionClass conn = new ConnectionClass();
    Connection connection = conn.getConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ConnectionClass conn = new ConnectionClass();
        Connection connection = conn.getConnection();
        try {
            XYChart.Series series = new XYChart.Series();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM alldata ORDER BY data_id DESC");
            int count = 0;

            while (count<=dataValue){
                rs.next();
                String d = rs.getString("data");
                String[] strarr = d.split(",");
                series.getData().add(new XYChart.Data(String.valueOf(count++),Double.parseDouble(strarr[0])));

            }
            linecartdata.getData().addAll(series);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void updateData(ActionEvent event) throws Exception {
        int value = -1;
        if (event.getSource() == temp){
            value = 0;
        }else if (event.getSource() == press){
            value = 1;
        }else if (event.getSource() == alt){
            value = 2;
        }else if (event.getSource() == gas1){
            value = 3;
        }else if (event.getSource() == gas2){
            value = 4;
        }

        if (event.getSource() == home){
            Parent dash = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(dash);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
        try {
            XYChart.Series series = new XYChart.Series();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM alldata ORDER BY data_id DESC");
            int count = 0;

            while (count<=dataValue){
                rs.next();
                String d = rs.getString("data");
                String[] strarr = d.split(",");
                series.getData().add(new XYChart.Data(String.valueOf(count++),Double.parseDouble(strarr[value])));

            }
            linecartdata.getData().clear();
            linecartdata.getData().addAll(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void changeDataLength(ActionEvent event) {

        if (datalength.getText().isEmpty()){
            dataValue = 10;
        }else {
            dataValue = Integer.parseInt(datalength.getText().toString());
        }

    }
}
