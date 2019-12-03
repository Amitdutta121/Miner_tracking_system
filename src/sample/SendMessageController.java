package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SendMessageController implements Initializable {


    @FXML
    private TextField message;

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


    @FXML
    private void sendMessage(ActionEvent event){
        String msg = message.getText();
        System.out.println(msg);



        try {
            String sql = "INSERT INTO message VALUES(null,'"+msg+"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = new ConnectionClass();
        connection = conn.getConnection();
    }
}
