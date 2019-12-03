package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class dataLogController implements Initializable {




    @FXML
    private TableView <Data>table_data;
    @FXML
    private TableColumn<Data, String> col_data_id;
    @FXML
    private TableColumn<Data, String> col_user_mac_address;
    @FXML
    private TableColumn<Data, String> col_router_mac_address;
    @FXML
    private TableColumn<Data, String> col_data;
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

    ObservableList<Data> oblist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = new ConnectionClass();
        connection = conn.getConnection();
        refreshTable();

    }

    public void refreshTable(){

        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        oblist.clear();

                        try {

                            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM alldata ORDER BY data_id DESC");

                            while (rs.next()){
                                oblist.add(new Data(rs.getString("data_id"), rs.getString("user_mac_address"), rs.getString("router_mac_address"), rs.getString("data")));
                            }

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        col_data_id.setCellValueFactory(new PropertyValueFactory<>("data_id"));
                        col_user_mac_address.setCellValueFactory(new PropertyValueFactory<>("user_mac_address"));
                        col_router_mac_address.setCellValueFactory(new PropertyValueFactory<>("router_mac_address"));
                        col_data.setCellValueFactory(new PropertyValueFactory<>("data"));

                        table_data.setItems(oblist);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
