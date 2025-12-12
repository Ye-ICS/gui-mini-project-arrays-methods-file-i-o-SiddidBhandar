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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Template JavaFX application.
 */
public class App extends Application 
{
    // setting global variables.
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
    static int wins = 0;
    static int loss = 0;
    static Random enemyAction = new Random();
    static boolean taunted = false;
    static boolean atkBuff = false;
    static boolean tankDead = false;
    static boolean supportDead = false;
    static boolean attackDead = false;
    static boolean parry = false;
    static boolean dead = false;
    static Label tankAction = new Label(" ");
    static Label supportAction =  new Label(" ");
    static Label attackAction =  new Label(" ");
    static Label playerHealthBar = new Label(playerHealth + "/125");
    static Label tankHealthBar = new Label(tankHealth + "/70");
    static Label attackHealthBar = new Label(attackHealth + "/50");
    static Label supportHealthBar =  new Label(supportHealth + "/25");
    static Label winsAndLosses = new Label();

    @Override
    public void start(Stage stage) throws IOException 
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
        
        // Create scanner for file I/O.
        Scanner fin = new Scanner(new FileReader("WinsAndLosses.txt"));
        winsAndLosses.setText(fin.nextLine() + " " + fin.nextLine());
        fin.close();
        
        // Setting positions of parent components within the root.
        contentBox.setTop(healthBars);
        contentBox.setCenter(enemyInfo);
        contentBox.setBottom(actions);
        contentBox.setLeft(superGauge);
        contentBox.setRight(enemyManuel);

        // Adding child components to parent components.
        healthBars.getChildren().addAll(playerHealthBar, tankHealthBar, attackHealthBar, supportHealthBar);
        healthBars.setAlignment(Pos.BOTTOM_CENTER);
        actions.setAlignment(Pos.CENTER);
        enemyInfo.getChildren().addAll(enemies, tankAction, supportAction, attackAction, winsAndLosses);
        enemies.getChildren().addAll(tankEnemy, supportEnemy, attackEnemy);
        enemies.setAlignment(Pos.CENTER);
        superGauge.getChildren().add(superBar);
        enemyManuel.getChildren().addAll(tankStats, supportStats, attackStats);

        // Basic enemy choosing mechanic.
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


        // Creating and adding player actions within the app.
        String[] playerAction = "Attack,Special,Parry,Super".split(",");
        for(int i = 0; i < playerAction.length; i++)
        {
            // Attack Action.
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
                        // If player taunted, forced to attack tank.
                        if(taunted)
                        {
                            choice = 1;
                            taunted = false;
                        }

                        // Attacking the chosen enemy depending on choice.
                        if(choice == 1 && turn == 1 && !dead)
                        {
                            setEnemyTurns();
                            attack(playerBaseDamage);
                            checkDeath();
                            tankHealthBar.setText(tankHealth + "/70");
                            enemyActionChoosing();
                            setHealthBars();
                            choice = 0;
                        }
                        else if(choice == 2 && turn == 1 && !dead)
                        {
                            setEnemyTurns();
                            attack(playerBaseDamage);
                            checkDeath();
                            supportHealthBar.setText(supportHealth + "/25");
                            enemyActionChoosing();
                            setHealthBars();
                            choice = 0;
                        }
                        else if(choice == 3 && turn == 1 && !dead)
                        {
                            setEnemyTurns();
                            attack(playerBaseDamage);
                            checkDeath();
                            attackHealthBar.setText(attackHealth + "/50");
                            enemyActionChoosing();
                            setHealthBars();
                            choice = 0;
                        }

                        // Adding super into the super gauge and refreshing.
                        superAction += 25;
                        if(superAction > 100)
                        {
                            superAction = 100;
                        }
                        superBar.setText(superAction + "/100");
                        // Check if you have won or lost.
                        try 
                        {
                            checkWinLosses();
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                );
            }
            // Special/Heal Action.
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
                        if(!dead)
                        {
                            // Healing player.
                            playerHealth += 25;
                            setHealthBars();
                            turn = 0;
                            setEnemyTurns();
                            enemyActionChoosing();
                            setHealthBars();
                            try 
                            {
                                checkWinLosses();
                            } 
                            catch (IOException e) 
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                );
            }
            // Parry Action.
            else if(playerAction[i].equalsIgnoreCase("Parry"))
            {
                Button parryButton = new Button("Parry");
                parryButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");
                parryButton.setMinSize(1000 , 50);
                parryButton.setFont(Font.font("Arial", FontWeight.NORMAL, 25));
                actions.getChildren().add(parryButton);
                parryButton.setOnAction
                (
                    event ->
                    {
                        // Sets parry boolean to true.
                        parry = true;
                        setEnemyTurns();
                        enemyActionChoosing();
                        checkDeath();
                        setHealthBars();
                        try 
                        {
                            checkWinLosses();
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                );
            }
            // Super Action.
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
                        // Checks if the super gauge is maxed out, if true, super action is used.
                        if(superAction == 100)
                        {
                            // Sets super gauge back to 0 and damages all enemies by 50% of their max health.
                            superAction = 0;
                            tankHealth -= 35;
                            attackHealth -= 25;
                            supportHealth -= 12;
                            superBar.setText(superAction + "/100");
                            checkDeath();
                            enemyActionChoosing();
                            setHealthBars();
                            try 
                            {
                                checkWinLosses();
                            } 
                            catch (IOException e) 
                            {
                                e.printStackTrace();
                            }
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
/**
 * Sets the enemies turn so they may attack.
 */
    static void setEnemyTurns()
    {
        tankTurn = 1;
        supportTurn = 1;
        attackTurn = 1;
    }
/**
 * Refresh the health bars in the GUI
 */
    static void setHealthBars()
    {
        playerHealthBar.setText(playerHealth + "/125");
        tankHealthBar.setText(tankHealth + "/70");
        supportHealthBar.setText(supportHealth + "/25");
        attackHealthBar.setText(attackHealth + "/50");
    }
/**
 * Reduce enemy health by player damage.
 */
    static void attack(int playerDamage)
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
/**
 * Checks if the health conditions are met to consider the enemy dead.
 */
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
/**
 * Chooses what enemies' action will be.
 */
    static void enemyActionChoosing()
    {
        // Checks if tank enemy is not dead. 
        if(!tankDead)
        {
            // Chooses number between 0 and 1.
            if(tankTurn == 1 && enemyAction.nextInt(2) == 0 && tankHealth > 0)
            {
                // If number is 0, attack the player.
                // If parry is true, and if player choice is the tank enemy, tank enemy gets parried and will not do any damage.
                if(parry && choice == 1)
                {
                    tankAction.setText("Tank enemy has been Parried!");
                    parry = false;
                }
                else
                {
                    // If attack buff is true, double tank damage. Else, deal base damage.
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
            }
            // If number is 1, perform this action.
            else if(tankTurn == 1 && enemyAction.nextInt(2) == 1 && tankHealth > 0)
            {
                // If player is already taunted, attack instead.
                if(taunted)
                {
                    if(parry && choice == 1)
                    {
                        tankAction.setText("Tank enemy has been Parried!");
                        parry = false;
                    }
                    else
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
                        tankAction.setText("Tank enemy used Attack!");
                    }
                    tankTurn = 0;
                }
                // Else, taunt the player, forcing them to attack the tank.
                else
                {
                    taunted = true;
                    tankTurn = 0;
                    tankAction.setText("Tank enemy used Taunt!");
                }
                
            }   
        }
        // Checks if support enemy is not dead, if true, chooses number between 0 and 1
        if(!supportDead)
        {
            // If number 0, perform this action.
            if(supportTurn == 1 && enemyAction.nextInt(2) == 0)
            {
                // If parry is true and player choice is support enemy, support enemy parried and will deal no damage.
                if(parry && choice == 2)
                {
                    supportAction.setText("Support has been Parried!");
                    parry = false;
                }
                // Else, attack and deal damage.
                else
                {
                    // Checks for attack buff, if true, double damage. Else, deal base damage.
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
                    supportAction.setText("Support enemy used Attack!");
                }
                supportTurn = 0;
            }
            // If number is 1, perform this action.
            else if(supportTurn == 1 && enemyAction.nextInt(2) == 1)
            {
                // Checks percentages of enemies based on max health, lowest will be prioritized for healing.
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
                supportTurn = 0;
                supportAction.setText("Support enemy used Heal!");
            }
        }
        // Checks if attack enemy is not dead. If true, picks number 0 or 1.
        if(!attackDead)
        {
            // If number is 0, perform this action.
            if(attackTurn == 1 && enemyAction.nextInt(2) == 0 && attackHealth > 0)
            {
                // Attacks, if parry is true and player choice is attack enemy, attack enemy gets parried and deals no damage.
                if(parry && choice == 3)
                {
                    attackAction.setText("Attack enemy has been Parried!");
                    parry = false;
                    if(atkBuff)
                    {
                        atkBuff = false;
                    }
                }
                // Else, attack the enemy, if attack buff is true, deal double damage, else deal base damage.
                else
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
                    attackAction.setText("Attack enemy used Attack!");
                }
                attackTurn = 0;
            }
            // If number is 1, perform this action.
            else if(attackTurn == 1 && enemyAction.nextInt(2) == 1 && attackHealth > 0)
            {
                if(parry && choice == 3)
                {
                    attackAction.setText("Attack enemy has been Parried!");
                    parry = false;
                }
                else
                {
                    // If attack buff already is true, attack instead, else, enable attack buff to true.
                    if(atkBuff)
                    {
                        attackAttack *= 2;
                        playerHealth -= attackAttack;
                        attackAttack /= 2;
                        atkBuff = false;
                        attackAction.setText("Attack enemy used Attack!");
                    }
                    else
                    {
                        // Attack buff doubles damage of all enemies.
                        atkBuff = true;
                        attackTurn = 0;
                        attackAction.setText("Attack enemy used Buff!");
                    }
                }
                attackTurn = 0;
            }
        }
        turn = 1;
    }
/**
 * Check if you have won or lost after each action is made.
 */    
    static void checkWinLosses() throws IOException
    {
        // If all enemy health bars are 0, increase wins and save it in text file to be displayed in the app.
        if(tankHealth == 0 && supportHealth == 0 && attackHealth == 0)
        {
            wins++;
            PrintWriter fileWriter = new PrintWriter(new FileWriter("WinsAndLosses.txt"));
            fileWriter.println("Wins: " + wins);
            fileWriter.println("Losses: " + loss);
            fileWriter.close();
            Scanner fin = new Scanner(new FileReader("WinsAndLosses.txt"));
            winsAndLosses.setText(fin.nextLine() + " " + fin.nextLine());
        }
        // If player health is 0, increase loses and save it in text file to be displayed in the app.
        else if (playerHealth <= 0)
        {
            playerHealth = 0;
            setHealthBars();
            loss++;
            PrintWriter fileWriter =  new PrintWriter(new FileWriter("WinsAndLosses.txt"));
            fileWriter.println("Wins: " + wins);
            fileWriter.println("Losses: " + loss);
            fileWriter.close();
            Scanner fin = new Scanner(new FileReader("WinsAndLosses.txt"));
            winsAndLosses.setText(fin.nextLine() + " " + fin.nextLine());
            fin.close();
        }
    }
    public static void main(String[] args) 
    {
        launch(args);
    }
}
