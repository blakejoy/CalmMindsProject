package main.Controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Model.Client;
import main.Model.Contract;
import main.Model.Counselor;
import main.Model.Session;
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
    private Label clientNameLbl;
    @FXML
    private ComboBox sessionTypeCBox;
    @FXML private ComboBox therapyTypeCBox;
    @FXML private TextArea violationTextArea;
    @FXML Button saveBtn;

    private Connection connection;
    public String clientSSN;
    public String counselorID;
    public Contract contract;
    public Counselor counselor;
    public Client client;
    public Session session;

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
        client = new Client();
        session = new Session();
        sessionTypeCBox.setItems(FXCollections.observableArrayList("Select Session Type", new Separator(), "Group", "Individual")

        );
        sessionTypeCBox.getSelectionModel().selectFirst();

        therapyTypeCBox.setItems(FXCollections.observableArrayList("Select Therapy Type", new Separator(),"Cognitive-Behavioral", "Psychodynamic", "Dialectical Behavioral", "Humanistic")

        );
        therapyTypeCBox.getSelectionModel().selectFirst();
         violationTextArea.setText("");
    }


    public void setClientSSN(String clientSSN) {
        this.clientSSN = clientSSN;
        clientNameLbl.setText(clientSSN);
        getClientInfo(clientSSN);
    }

    public void setCounselorID(String counselorID) {
        this.counselorID = counselorID;
        counselorNameLbl.setText(counselorID);
        getCounselorInfo(counselorID);

    }


    public void getCounselorInfo(String counselorID) {
        try {
            String query = "SELECT c_id,fname,mInit,lname FROM Person p,counselor c WHERE p.SSN = c.SSN AND c_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,counselorID);

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

    public void getClientInfo(String clientSSN) {
        try {
            String query = "SELECT fname,mInit,lname FROM Person WHERE SSN = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,clientSSN);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                do {
                    client.setSSN(Integer.valueOf(clientSSN));
                    client.setFirstName(rs.getString("fname"));
                    client.setMiddleInit(rs.getString("mInit"));
                    client.setLastName(rs.getString("lname"));

                } while (rs.next());

                clientNameLbl.setText(client.getFirstName()+" "+ client.getMiddleInit()+ " "+ client.getLastName());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewSession(){


        try {
            String query = "INSERT INTO sessions (sessionType,therapyType,leadCounID,s_date) VALUES (?,?,?,?) ";
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sessionTypeCBox.getSelectionModel().getSelectedItem().toString());
            ps.setString(2, therapyTypeCBox.getSelectionModel().getSelectedItem().toString());
            ps.setInt(3, counselor.getCounselorID());
            ps.setDate(4, Date.valueOf(sessionDatePicker.getValue()));


            ps.executeUpdate();



            ResultSet sessions = ps.getGeneratedKeys();
            sessions.next();
            int sessionID = sessions.getInt(1);


            session.setSessionID(sessionID);

            query = "INSERT INTO client_attendance (cSSN,sessionID,violation) VALUES (?,?,?) ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, client.getSSN());
            ps.setInt(2, session.getSessionID());
            ps.setString(3, violationTextArea.getText());

            ps.executeUpdate();


            query = "INSERT INTO schedule (clientSSN,counID,session_id) VALUES (?,?,?) ";
            ps = connection.prepareStatement(query);
            ps.setInt(1, client.getSSN());
            ps.setInt(2,counselor.getCounselorID());
            ps.setInt(3, session.getSessionID());


            ps.executeUpdate();



            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Session has been added!",ButtonType.OK);
            alert.setTitle("Session Assignment");
            alert.setHeaderText("Assigned to Session");
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
