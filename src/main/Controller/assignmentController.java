package main.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Model.Contract;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.*;
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
    private ComboBox<String> counselorCBox;
    @FXML private ComboBox therapyTypeCBox;
    private Connection connection;
    public int clientSSN;
    public int counselorID;
    public Contract contract;
    public Counselor counselor;
    @FXML
    CheckBox courtOrdered;
    @FXML Button saveBtn;

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

    public void setCounselorID(int counselorID) {
        this.counselorID = counselorID;
    }


    public void getCounselorInfo() {
        try {
            counselor = new Counselor();
            ObservableList<String> counselors = FXCollections.observableArrayList();

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
                    counselors.add(counselor.toString());

                } while (rs.next());

                counselorCBox.setItems(counselors);



            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void assignCounselor(){


        String output = counselorCBox.getSelectionModel().getSelectedItem().toString();
       output.substring(0,1);

       int counselorID = Integer.parseInt(output.substring(0,1));
    try {
        String query = "INSERT INTO contract (counID,clientSSN,dateStarted,dateTerminated,courtOrdered) VALUES (?,?,?,?,?) ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, counselorID);
        ps.setInt(2, clientSSN);
        ps.setDate(3, Date.valueOf(contractStartPicker.getValue()));
        ps.setDate(4, Date.valueOf(contractEndPicker.getValue()));

        if(courtOrdered.isSelected()) {
            ps.setString(5, "Y");
        }else{
            ps.setString(5, "N");

        }

        ps.executeUpdate();


        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Contract has been assigned!",ButtonType.OK);
        alert.setTitle("Contract Assignment");
        alert.setHeaderText("Assigned to Contract");
        alert.showAndWait();
        ps.close();
        alert.close();
        handleSaveButtonAction();

    }catch(SQLException e){
e.printStackTrace();
    }

    }



    @FXML
    public void handleSaveButtonAction() {
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }



}
