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
import java.util.Random;
/**
 * Template JavaFX application.
 */
public class App extends Application 
{
    static int choice;
    static int playerHealth = 100;
    static int playerBaseDamage = 10;
    static int tankHealth = 70;
    static int attackHealth = 50;
    static int supportHealth = 25;
    static int turn = 1;
    static int tankTurn;
    static int supportTurn;
    static int attackTurn;
    static int tankAttack = 10;
    static int supportAttack = 5;
    static int attackAttack = 20;
    static int superAction = 0;
    static Random enemyAction = new Random();
    static boolean taunted = false;
    static boolean atkBuff = false;
    
    @Override
    public void start(Stage stage) 
    {    
        // Create components to add.
        BorderPane contentBox = new BorderPane();
        contentBox.setStyle("-fx-padding: 30;");
        HBox healthBars = new HBox(25);
        VBox actions =  new VBox(10);
        Label playerHealthBar = new Label(playerHealth + "/100");
        playerHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        playerHealthBar.setStyle("-fx-text-fill: black");
        playerHealthBar.setTranslateX(-300);
        Label tankHealthBar = new Label(tankHealth + "/70");
        tankHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        tankHealthBar.setStyle("-fx-text-fill: black");
        tankHealthBar.setTranslateX(500);
        Label attackHealthBar = new Label(attackHealth + "/50");
        attackHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        attackHealthBar.setStyle("-fx-text-fill: black");
        attackHealthBar.setTranslateX(500);
        Label supportHealthBar =  new Label(supportHealth + "/25");
        supportHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        supportHealthBar.setStyle("-fx-text-fill: black");
        supportHealthBar.setTranslateX(500);
        FlowPane enemies = new FlowPane();
        Button tankEnemy =  new Button("Tank");
        Button supportEnemy =  new Button("Support");
        Button attackEnemy = new Button("Attack");
        VBox superGauge = new VBox(10);
        Label superBar = new Label(superAction + "/100");
        superBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        superBar.setStyle("-fx-text-fill: black");

        contentBox.setTop(healthBars);
        contentBox.setCenter(enemies);
        contentBox.setBottom(actions);
        contentBox.setLeft(superGauge);

        healthBars.getChildren().addAll(playerHealthBar, tankHealthBar, attackHealthBar, supportHealthBar);
        healthBars.setAlignment(Pos.BOTTOM_CENTER);
        actions.setAlignment(Pos.CENTER);
        enemies.getChildren().addAll(tankEnemy, supportEnemy, attackEnemy);
        enemies.setAlignment(Pos.CENTER);
        superGauge.getChildren().add(superBar);

        tankEnemy.setOnAction
        (
            event -> 
            {
                choice = 1;
            }
        );
        supportEnemy.setOnAction
        (
            event ->
            {
                choice = 2;
            }
        );
        attackEnemy.setOnAction
        (
            event ->
            {
                choice = 3;
            }
        );

        String[] playerAction = "Attack,Special,Parry,Super".split(",");
        for(int i = 0; i < playerAction.length; i++)
        {
            if(playerAction[i].equalsIgnoreCase("Attack"))
            {
                Button attackButton = new Button("Attack");
                attackButton.setStyle("-fx-background-color: red;");
                attackButton.setMinSize(1000 , 50);
                attackButton.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
                actions.getChildren().add(attackButton);
                attackButton.setOnAction
                (
                    event ->
                    {
                        if(choice == 1 && turn == 1)
                        {
                            Attack(playerBaseDamage);
                            tankHealthBar.setText(tankHealth + "/70");
                            tankTurn = 1;
                            supportTurn = 1;
                            attackTurn = 1;
                            EnemyActionChoosing();
                            playerHealthBar.setText(playerHealth + "/100");
                            tankHealthBar.setText(tankHealth + "/70");
                            attackHealthBar.setText(attackHealth + "/50");
                            supportHealthBar.setText(supportHealth + "/25");
                        }
                        else if(choice == 2 && turn == 1)
                        {
                            Attack(playerBaseDamage);
                            supportHealthBar.setText(supportHealth + "/25");
                            tankTurn = 1;
                            supportTurn = 1;
                            attackTurn = 1;
                            EnemyActionChoosing();
                            playerHealthBar.setText(playerHealth + "/100");
                            tankHealthBar.setText(tankHealth + "/70");
                            attackHealthBar.setText(attackHealth + "/50");
                            supportHealthBar.setText(supportHealth + "/25");
                        }
                        else if(choice == 3 && turn == 1)
                        {
                            Attack(playerBaseDamage);
                            attackHealthBar.setText(attackHealth + "/50");
                            tankTurn = 1;
                            supportTurn = 1;
                            attackTurn = 1;
                            EnemyActionChoosing();
                            playerHealthBar.setText(playerHealth + "/100");
                            tankHealthBar.setText(tankHealth + "/70");
                            attackHealthBar.setText(attackHealth + "/50");
                            supportHealthBar.setText(supportHealth + "/25");
                        }
                        superAction += 25;
                        superBar.setText(superAction + "/100");
                    }
                );
            }
            else if(playerAction[i].equalsIgnoreCase("Special"))
            {
                Button specialButton = new Button("Special");
                specialButton.setStyle("-fx-background-color: green;");
                specialButton.setMinSize(1000 , 50);
                specialButton.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
                actions.getChildren().add(specialButton);
                specialButton.setOnAction
                (
                    event ->
                    {
                        playerHealth += 25;
                        playerHealthBar.setText(playerHealth + "/100");
                        turn = 0;
                        tankTurn = 1;
                        supportHealth = 1;
                        attackTurn = 1;
                    }
                );
            }
            else if(playerAction[i].equalsIgnoreCase("Parry"))
            {
                Button parryButton = new Button("Parry");
                parryButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");
                parryButton.setMinSize(1000 , 50);
                parryButton.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
                actions.getChildren().add(parryButton);
            }
            else if(playerAction[i].equalsIgnoreCase("Super"))
            {
                Button superButton = new Button("Super");
                superButton.setStyle("-fx-background-color: yellow;");
                superButton.setMinSize(1000 , 50);
                superButton.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
                actions.getChildren().add(superButton);
            }
        }
        Scene scene = new Scene(contentBox, 1920, 1000);
        stage.setScene(scene);
        stage.setTitle("Not pokemon but better");
        stage.show();
    }
    static void Attack(int playerDamage)
    {
        if(choice == 1)
        {
            tankHealth -= playerDamage;
        }
        else if (choice == 2)
        {
            supportHealth -= playerDamage;
        }
        else if (choice == 3)
        {
            attackHealth -= playerDamage;
        }
        turn--;
    }
    static void EnemyActionChoosing()
    {
        if(tankTurn == 1 && enemyAction.nextInt(2) == 0)
        {
            if(atkBuff)
            {
                tankAttack *= 2;
                playerHealth -= tankAttack;
                tankAttack /= 2;
            }
            else
            {
                playerHealth -= tankAttack;
            }
            tankTurn = 0;
        }
        else if(tankTurn == 1 && enemyAction.nextInt(2) == 1)
        {
            taunted = true;
            tankTurn = 0;
        }
        if(supportTurn == 1 && enemyAction.nextInt(2) == 0)
        {
            
            if(atkBuff)
            {
                supportAttack *= 2;
                playerHealth -= supportAttack;
                supportAttack /= 2;
            }
            else
            {
                playerHealth -= supportAttack;
            }
            supportTurn = 0;
        }
        else if(supportTurn == 1 && enemyAction.nextInt(2) == 1)
        {
            if(tankHealth < 70 && tankHealth / 70 < attackHealth / 50 && tankHealth / 70 < supportHealth / 25)
            {
                tankHealth += 20;
                if(tankHealth > 70)
                {
                    tankHealth = 70;
                }
            }
            else if(supportHealth < 25 && supportHealth / 25 < attackHealth / 50 && supportHealth / 25 < tankHealth / 70)
            {
                supportHealth += 10;
                if(supportHealth > 25)
                {
                    supportHealth = 25;
                }
            }
            else if(attackHealth < 50 && attackHealth / 50 < tankHealth / 70 && attackHealth / 50 < supportHealth / 25)
            {
                attackHealth += 15;
                if(attackHealth > 50)
                {
                    attackHealth = 50;
                }
            }
            else
            {
                playerHealth -= supportAttack;
            }
            supportTurn = 0;
        }
        if(attackTurn == 1 && enemyAction.nextInt(2) == 0)
        {
            if(atkBuff)
            {
                attackAttack *= 2;
                playerHealth -= attackAttack;
                attackAttack /= 2;
                atkBuff = false;
            }
            else
            {
                playerHealth -= attackAttack;
            }
            attackTurn = 0;
        }
        else if(attackTurn == 1 && enemyAction.nextInt(2) == 1)
        {
            atkBuff = true;
            attackTurn = 0;
        }
        turn = 1;
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}
