package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class SendMessageController {


    @FXML
    private TextField message;


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

        ConnectionClass conn = new ConnectionClass();
        Connection connection = conn.getConnection();

        try {
            String sql = "INSERT INTO message VALUES(null,'"+msg+"')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
