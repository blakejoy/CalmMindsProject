package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import main.Model.Client;
import main.Model.Contract;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/5/16.
 */



public class assignmentController implements Initializable {


    @FXML
    private DatePicker contractStartPicker;
    @FXML
    private DatePicker contractEndPicker;
    @FXML
    private ComboBox<Counselor> counselorCBox;
    @FXML private ComboBox therapyTypeCBox;
    private Connection connection;
    public int clientSSN;
    public Contract contract;
    public Counselor counselor;


    /**
     * Initializes the assignment information for a client based on the Client's SSN. Still needs work.
     *
     * @param location  ignored
     * @param resources ignored
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = DBConnect.getConnection();
        contractEndPicker.setValue(null);
        contractStartPicker.setValue(null);
        getCounselorInfo();

    }


    public void setClientSSN(int clientSSN) {
        this.clientSSN = clientSSN;

    }


    public void getCounselorInfo() {
        try {
            counselor = new Counselor();
            ObservableList<Counselor> counselors = FXCollections.observableArrayList();

            String query = "SELECT c_id,fname,mInit,lname FROM Person p,counselor c WHERE p.SSN = c.SSN";
            PreparedStatement ps = connection.prepareStatement(query);


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    counselor = new Counselor();
                    counselor.setCounselorID(rs.getInt("c_id"));
                    counselor.setFirstName(rs.getString("fname"));
                    counselor.setMiddleInit(rs.getString("mInit"));
                    counselor.setLastName(rs.getString("lname"));
                    counselorCBox.setItems(counselors);
                } while (rs.next());




            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void assignCounselor(){
    try {
        String query = "INSERT INTO contract (counID,clientSSN,dateStarted,dateTerminated) VALUES (?,?,?,?) ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, counselor.getCounselorID());
        ps.setInt(2, clientSSN);
        ps.setDate(3, Date.valueOf(contractStartPicker.getValue()));
        ps.setDate(4, Date.valueOf(contractEndPicker.getValue()));


        ps.executeUpdate();

        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Contract has been assigned!",ButtonType.OK);
        alert.setTitle("Contract Assignment");
        alert.setHeaderText("Assigned to Contract");
        alert.showAndWait();
        ps.close();
        alert.close();


    }catch(SQLException e){
e.printStackTrace();
    }

    }









}
