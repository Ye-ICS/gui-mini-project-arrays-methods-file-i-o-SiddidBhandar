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
    static int playerHealth = 125;
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
    static boolean tankDead = false;
    static boolean supportDead = false;
    static boolean attackDead = false;
    static Label tankAction = new Label(" ");
    static Label supportAction =  new Label(" ");
    static Label attackAction =  new Label(" ");
    static Label playerHealthBar = new Label(playerHealth + "/125");
    static Label tankHealthBar = new Label(tankHealth + "/70");
    static Label attackHealthBar = new Label(attackHealth + "/50");
    static Label supportHealthBar =  new Label(supportHealth + "/25");
    
    @Override
    public void start(Stage stage) 
    {    
        // Create components to add.
        BorderPane contentBox = new BorderPane();
        contentBox.setStyle("-fx-padding: 30;");
        HBox healthBars = new HBox(25);
        VBox actions =  new VBox(10);
        playerHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        playerHealthBar.setStyle("-fx-text-fill: black");
        playerHealthBar.setTranslateX(-300);
        tankHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        tankHealthBar.setStyle("-fx-text-fill: black");
        tankHealthBar.setTranslateX(500);
        attackHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        attackHealthBar.setStyle("-fx-text-fill: black");
        attackHealthBar.setTranslateX(500);
        supportHealthBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        supportHealthBar.setStyle("-fx-text-fill: black");
        supportHealthBar.setTranslateX(500);
        VBox enemyInfo =  new VBox();
        FlowPane enemies = new FlowPane();
        Button tankEnemy =  new Button("Tank");
        Button supportEnemy =  new Button("Support");
        Button attackEnemy = new Button("Attack");
        VBox superGauge = new VBox(10);
        Label superBar = new Label(superAction + "/100");
        superBar.setFont(Font.font("Arial", FontWeight.NORMAL, 50));
        superBar.setStyle("-fx-text-fill: black");
        VBox enemyManuel = new VBox(10);
        Label tankStats = new Label("Tank HP = 70, Tank Atk = 10. Taunt forces you to attack the tank.");
        Label supportStats =  new Label("Support HP = 25, Support Atk = 5. Heals the enemy with the lowest amount of HP percentage based on max HP");
        Label attackStats = new Label("Attack HP = 50, Attack Atk = 20. Buff multiplies the Atk of all enemies by 2");

        contentBox.setTop(healthBars);
        contentBox.setCenter(enemyInfo);
        contentBox.setBottom(actions);
        contentBox.setLeft(superGauge);
        contentBox.setRight(enemyManuel);

        healthBars.getChildren().addAll(playerHealthBar, tankHealthBar, attackHealthBar, supportHealthBar);
        healthBars.setAlignment(Pos.BOTTOM_CENTER);
        actions.setAlignment(Pos.CENTER);
        enemyInfo.getChildren().addAll(enemies, tankAction, supportAction, attackAction);
        enemies.getChildren().addAll(tankEnemy, supportEnemy, attackEnemy);
        enemies.setAlignment(Pos.CENTER);
        superGauge.getChildren().add(superBar);
        enemyManuel.getChildren().addAll(tankStats, supportStats, attackStats);

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
                        if(taunted)
                        {
                            choice = 1;
                            taunted = false;
                        }
                        if(choice == 1 && turn == 1)
                        {
                            setEnemyTurns();
                            Attack(playerBaseDamage);
                            checkDeath();
                            tankHealthBar.setText(tankHealth + "/70");
                            EnemyActionChoosing();
                            setHealthBars();
                        }
                        else if(choice == 2 && turn == 1)
                        {
                            setEnemyTurns();
                            Attack(playerBaseDamage);
                            checkDeath();
                            supportHealthBar.setText(supportHealth + "/25");
                            EnemyActionChoosing();
                            setHealthBars();
                        }
                        else if(choice == 3 && turn == 1)
                        {
                            setEnemyTurns();
                            Attack(playerBaseDamage);
                            checkDeath();
                            attackHealthBar.setText(attackHealth + "/50");
                            EnemyActionChoosing();
                            setHealthBars();
                        }
                        
                        superAction += 25;
                        if(superAction > 100)
                        {
                            superAction = 100;
                        }
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
                        playerHealthBar.setText(playerHealth + "/125");
                        turn = 0;
                        tankTurn = 1;
                        supportTurn = 1;
                        attackTurn = 1;
                        EnemyActionChoosing();
                        playerHealthBar.setText(playerHealth + "/125");
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
                superButton.setOnAction
                (
                    event ->
                    {
                        if(superAction == 100)
                        {
                            superAction = 0;
                            tankHealth -= 35;
                            attackHealth -= 25;
                            supportHealth -= 12;
                            checkDeath();
                            setHealthBars();
                        }
                    }
                );
            }
        }
        Scene scene = new Scene(contentBox, 1920, 1000);
        stage.setScene(scene);
        stage.setTitle("Not pokemon but better");
        stage.show();
    }
    static void setEnemyTurns()
    {
        tankTurn = 1;
        supportTurn = 1;
        attackTurn = 1;
    }
    static void setHealthBars()
    {
        playerHealthBar.setText(playerHealth + "/125");
        tankHealthBar.setText(tankHealth + "/70");
        supportHealthBar.setText(supportHealth + "/25");
        attackHealthBar.setText(attackHealth + "/50");
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
    static void checkDeath()
    {
        if(tankHealth < 0 || tankHealth == 0)
        {
            tankHealth = 0;
            tankDead = true;
            tankTurn = 0;
            tankAction.setText("Tank enemy has Died!");
        }
        if(supportHealth < 0 || supportHealth == 0)
        {
            supportHealth = 0;
            supportDead = true;
            supportTurn = 0;
            supportAction.setText("Support enemy has Died!");
        }
        if(attackHealth < 0 || attackHealth == 0)
        {
            attackHealth = 0;
            attackDead = true;
            attackAction.setText("Attack enemy has Died!");
        }
    }
    static void EnemyActionChoosing()
    {
        if(!tankDead)
        {
            if(tankTurn == 1 && enemyAction.nextInt(2) == 0 && tankHealth > 0)
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
                tankAction.setText("Tank enemy used Attack!");
            }
            else if(tankTurn == 1 && enemyAction.nextInt(2) == 1 && tankHealth > 0)
            {
                taunted = true;
                tankTurn = 0;
                tankAction.setText("Tank enemy used Taunt!");
            }   
        }
        if(!supportDead)
        {
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
                supportAction.setText("Support enemy used Attack!");
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
                supportAction.setText("Support enemy used Heal!");
            }
        }
        if(!attackDead)
        {
            if(attackTurn == 1 && enemyAction.nextInt(2) == 0 && attackHealth > 0)
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
                attackAction.setText("Attack enemy used Attack!");
            }
            else if(attackTurn == 1 && enemyAction.nextInt(2) == 1 && attackHealth > 0)
            {
                atkBuff = true;
                attackTurn = 0;
                attackAction.setText("Attack enemy used Buff!");
            }
        }
        turn = 1;
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}
