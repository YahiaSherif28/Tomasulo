package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class scene1Controller {

    @FXML
    private TextField addLatency,subLatency,mulLatency,divLatency,loadLatency,storeLatency;
    @FXML
    private TextArea assemblyInstructions;

    public void submit(ActionEvent e) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
            Parent root = loader.load();
            scene2Controller controller = loader.getController();
//            Stage stage = new Stage();
//            stage.setScene(new Scene (root));
//            stage.show();
            Stage stage = (Stage)((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("TOMASULO SIMULATOR");
            stage.show();
            controller.showInfo(addLatency.getText(),subLatency.getText(),mulLatency.getText(),divLatency.getText(),loadLatency.getText(),storeLatency.getText(),assemblyInstructions.getText());


        }catch (Exception err){
            System.out.println(err.getMessage());
        }


    }

}
