import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The gameStage2P class manages the GUI of a double-player version of the Blackjack game.
 * The gameStage2P class is a subclass of AbstractGameStage.
 * The purpose of this class is to set up a game stage for a double player version of the Blackjack game.
 * The constructor creates an instance of the super class, which holds an instance of ArrayList of Player called playerList.
 * The gameStage2P class creates instances of a Deck object deck and two Player objects, p1, p2 using the deck. 
 * The Player objects are then added to the playerList.
 * 
 * @author Zhaoliang Ni
 */

public class GameStage2P extends AbstractGameStage {
    
    public Deck deck;
    public Player p1;
    public Player p2;

    /**
     * The constructor of the class takes an initial balance of type double. This initial balance is 
     * then passed to the Player objects.
     * @param bal initial balance passed from inside the MainMenu class
     */
    public GameStage2P(double bal){
        super();

        deck = new Deck(4);
        deck.shuffle();

        dealer = new Player(this, deck, "Dealer");
        p1 = new Player(this, deck, "Player 1", bal);
        p2 = new Player(this, deck, "Player 2", bal);
        playerList = new ArrayList<Player>();
        playerList.add(p1);
        playerList.add(p2);

        Pane pane = new Pane();
        pane.setPrefSize(0, 50);

        Button reset = new Button("Reset");
        reset.setOnAction(e-> {
            dealer.reset();
            for(Player p : playerList){
                p.reset();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.add(dealer.content, 1, 0);
        grid.add(reset, 1, 1);
        grid.add(pane, 2, 1);
        grid.add(p1.content, 0, 2);
        grid.add(p2.content, 2, 2);

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

}