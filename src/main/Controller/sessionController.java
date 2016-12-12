package main.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Model.Contract;
import main.Model.Counselor;
import main.Resources.DBConnect;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by blakejoynes on 12/5/16.
 */



public class sessionController implements Initializable {


    @FXML
    private DatePicker sessionDatePicker;
    @FXML
    private Label counselorNameLbl;
    @FXML
    private ComboBox sessionTypeCBox;
    @FXML private ComboBox therapyTypeCBox;
    @FXML private TextArea violationTextArea;
    @FXML Button saveBtn;


    private Connection connection;
    public int clientSSN;
    public String counselorID;
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
        counselor = new Counselor();
        sessionDatePicker.setValue(LocalDate.now());

        sessionTypeCBox.setItems(FXCollections.observableArrayList("Select Session Type", new Separator(), "Group", "Individual")

        );
        sessionTypeCBox.getSelectionModel().selectFirst();

        therapyTypeCBox.setItems(FXCollections.observableArrayList("Select Therapy Type", new Separator(),"Cognitive-Behavioral", "Psychodynamic", "Dialectical Behavioral", "Humanistic")

        );
        therapyTypeCBox.getSelectionModel().selectFirst();
         getCounselorInfo();
         violationTextArea.setText("");
    }


    public void setClientSSN(int clientSSN) {
        this.clientSSN = clientSSN;

    }

    public void setCounselorID(String counselorID) {
        this.counselorID = counselorID;
    }


    public void getCounselorInfo() {
        try {
            String query = "SELECT c_id,fname,mInit,lname FROM Person p,counselor c WHERE p.SSN = c.SSN AND c_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,String.valueOf(counselorID));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    counselor.setCounselorID(rs.getInt("c_id"));
                    counselor.setFirstName(rs.getString("fname"));
                    counselor.setMiddleInit(rs.getString("mInit"));
                    counselor.setLastName(rs.getString("lname"));

                } while (rs.next());


              counselorNameLbl.setText(counselor.getFirstName()+" "+ counselor.getMiddleInit()+ " "+ counselor.getLastName());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addNewSession(){



    }



    @FXML
    public void handleSaveButtonAction() {
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }



}
