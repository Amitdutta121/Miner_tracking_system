package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static int count = 1;

    @FXML
    public Circle toggleBtn;

    ConnectionClass conn = null;
    Connection connection = null;

    @FXML
    private void dashboard(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("dash.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void home(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void sendmessage(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("sendmessage.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    private void datalog(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("datalog.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    private void trackminer(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("minerTracker.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void visual(javafx.event.ActionEvent event) throws IOException {
        Parent dash = FXMLLoader.load(getClass().getResource("visual.fxml"));
        Scene scene = new Scene(dash);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = new ConnectionClass();
        connection = conn.getConnection();

        toggleBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (count%2 == 0){
                    toggleBtn.setFill(Paint.valueOf("1e90ff"));
                    String sql = "UPDATE alert SET alert_value='"+0+"' WHERE alert_id='"+1+"'";
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    toggleBtn.setFill(Paint.valueOf("f00"));
                    String sql = "UPDATE alert SET alert_value=1 WHERE alert_id=1";
                    Statement statement = null;
                    try {
                        statement = connection.createStatement();
                        statement.executeUpdate(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                count++;
            }
        });
    }
}
