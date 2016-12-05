package main.Controller;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainPageController {

    @FXML
    private Button clientButton;
    @FXML
    public void activateCounselorGui(ActionEvent actionEvent) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/counselorConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Counselor Window");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void acttivateClientGui(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/clientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Client Window");
                stage.setScene(new Scene(root, 1027, 592));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void allClientActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/allClientConfig.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("All Clients");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @FXML
    public void statsActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/statsView.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Client Stats");
                stage.setScene(new Scene(root, 600, 400));
                
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void faqActivation(ActionEvent actionEvent) throws IOException {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/View/faqView.fxml"));
            if (root != null) {
                Stage stage = new Stage();
                stage.setTitle("Frequently Accessed Queries");
                stage.setScene(new Scene(root, 600, 400));
                
                stage.show();
                // Hide this current window (if this is what you want)
                //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}