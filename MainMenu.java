import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The MainMenu class is an instance of the Stage class. 
 * The MainMenu class takes care of setting up the Menu stage where program user selects how many
 * players and how much initial balance before the game.
 * 
 * @author Zhaoliang Ni
 */


public class MainMenu extends Stage {

    private String[] ERROR_MESSAGES = {"Invalid number. Player range: [1, 3]",
                                        "Invalid number. Balance range: [10, 1000]"};
    private int num_player;
    private double i_balance;

    private TextField player_field = new TextField();
    private TextField balance_field = new TextField();

    /**
     * The constructor for the MainMenu. 
     */
    public MainMenu(){
        
            GridPane grid = new GridPane();
            //grid.setGridLinesVisible(true);
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5.5);
            grid.setVgap(5.5); 

            Label numPlayer = new Label("Number of players: ");
            Label iBalance = new Label("Initial Balance: ");
            Label playerRange = new Label("(Range: 1 ~ 3 players)");
            Label balanceRange = new Label("(Range: 10 ~ 10000)");
            Button Start = new Button("Start Game");
            Button Quit = new Button("Quit");
            HBox buttonbox = new HBox(Start, Quit);
            buttonbox.setAlignment(Pos.CENTER);
            buttonbox.setSpacing(10);
    
            Pane p1 = new Pane(); 
            p1.setPrefSize(0, 30); 
            Pane p2 = new Pane(); 
            p2.setPrefSize(0, 30); 

            grid.add(numPlayer, 0, 0);
            grid.add(iBalance, 0, 3);
            grid.add(playerRange, 1, 1);
            grid.add(balanceRange, 1, 4);
            grid.add(player_field, 1, 0);
            grid.add(balance_field, 1, 3);
            grid.add(buttonbox, 0, 6);
            grid.add(p1, 0, 2);
            grid.add(p2, 0, 5);

            numPlayer.setFont(new Font("Arial", 28));
            numPlayer.setTextFill(Color.BLACK);
            iBalance.setFont(new Font("Arial", 28));
            iBalance.setTextFill(Color.BLACK);
            playerRange.setFont(new Font("Arial", 20));
            playerRange.setTextFill(Color.BLACK);
            balanceRange.setFont(new Font("Arial", 20));
            balanceRange.setTextFill(Color.BLACK);
            
            Start.setOnAction(e-> {
                start();
            });
            Quit.setOnAction(e-> {
                close();
            });
  
        // This block of codes takes care of setting up the background image.
        try{
            FileInputStream input = new FileInputStream("resource/table.png");
            Image imageback = new Image(input);
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(imageback, 
                                                                BackgroundRepeat.REPEAT, 
                                                                BackgroundRepeat.NO_REPEAT, 
                                                                BackgroundPosition.CENTER, 
                                                                backgroundSize);
            Background background = new Background(backgroundImage);
            grid.setBackground(background);
        }
        catch(FileNotFoundException e) {
            System.out.println("Background image not found.");
        } 

        Scene scene = new Scene(grid, 1350, 750);
        setTitle("Blackjack");
        setScene(scene);
        show();
    }

    /**
     * The the "Start Game" button calls the start method, which waits for users to fill in player number and initial balance.
     * The selections are handled by the methods setPlayerCount() and setBalance().
     * Once both of the two methods return true, the start method closes the Main Menu stage.
     * Then, based on the number of players enterd, the method construct a new GameStage object of given players and initial balance.
     */
    public void start(){
        if(setPlayerCount() && setBalance()){
            System.out.println(num_player + "  " + i_balance);
            close();
            if(num_player==1){
                new GameStage1P(i_balance);
            }
            if(num_player==2){
                new GameStage2P(i_balance);
            }
            if(num_player==3){
                new GameStage3P(i_balance);
            }
        }
    }

    /**
     * Called by the start() method. The setPlayerCount() method ensures the user enter a valid player 
     * number. 
     * @return True if player entered a valid number of players. False if the number is invalid, upon which the program does not progress
     * and the user is asked to enter a valid number.
     */
    public boolean setPlayerCount() {
        boolean key = false;
        String s = player_field.getText();
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(ERROR_MESSAGES[0]);
        try{
            num_player = Integer.parseInt(s);
            if(num_player<1 || num_player>3){
                a.show();
            }
            else{
                key = true;
            }
        }
        catch(NumberFormatException e){
            a.show();
        }
        return key;
    }

    /**
     * Called by the start() method. The setBalance() method ensures that the user enters a valid initial balance.
     * @return True if the entered intial balance is valid. False if the number is invalid, upon which the program does not progress
     * and the user is asked to enter a valid number
     */
    public boolean setBalance(){
        boolean key = false;
        String s = balance_field.getText();
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText(ERROR_MESSAGES[1]);
        try{
            i_balance = Double.parseDouble(s);
            if(i_balance<10 || i_balance>10000){
                a.show();
            }
            else{
                key = true;
            }
        }
        catch(NumberFormatException e){
            a.show();
        }
        return key;
    }
}
