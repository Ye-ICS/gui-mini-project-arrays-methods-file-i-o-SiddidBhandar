import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Template JavaFX application.
 */
public class App extends Application 
{
    
    
    @Override
    public void start(Stage stage) 
    {
        int playerHealth = 100;
        // Create components to add.
        BorderPane contentBox = new BorderPane();
        contentBox.setStyle("-fx-padding: 30;");
        HBox healthBars = new HBox();
        VBox actions =  new VBox();
        Label playerHealthBar = new Label(playerHealth + "/100");
        playerHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        playerHealthBar.setStyle("-fx-text-fill: black");
        

        contentBox.setTop(playerHealthBar);
        contentBox.setBottom(actions);

        healthBars.getChildren().add(playerHealthBar);
        healthBars.setAlignment(Pos.BOTTOM_CENTER);
        actions.setAlignment(Pos.CENTER);

        String[] playerAction = "Attack,Special,Parry,Super".split(",");
        for(int i = 0; i < playerAction.length; i++)
        {
            if(playerAction[i].equalsIgnoreCase("Attack"))
            {
                Button attackButton = new Button("Attack");
                attackButton.setStyle("-fx-background-color: red;");
                actions.getChildren().add(attackButton);
            }
            else if(playerAction[i].equalsIgnoreCase("Special"))
            {
                Button specialButton = new Button("Special");
                specialButton.setStyle("-fx-background-color: green;");
                actions.getChildren().add(specialButton);
            }
            else if(playerAction[i].equalsIgnoreCase("Parry"))
            {
                Button parryButton = new Button("Parry");
                parryButton.setStyle("-fx-background-color: blue;");
                actions.getChildren().add(parryButton);
            }
            else if(playerAction[i].equalsIgnoreCase("Super"))
            {
                Button superButton = new Button("Super");
                superButton.setStyle("-fx-background-color: yellow;");
                actions.getChildren().add(superButton);
            }
        }

        // Set up the window and display it.
        Scene scene = new Scene(contentBox, 1920, 1000);
        stage.setScene(scene);
        stage.setTitle("Not pokemon but better");
        stage.show();
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}
