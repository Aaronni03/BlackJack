import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * The AbstractGameStage class provides methods that supports the flow of the game in the background.
 * The method does not affect the layout of the program, but takes care the game logic backstage.
 * This method relates the player's interface, which includes the Player class and the HandPanel class, 
 * with the Game Stage interface, which includes the GameStage1P, GameStage2P, and GameStage3P classes.
 * 
 * @author Zhaoliang Ni
 */

public abstract class AbstractGameStage extends Stage {
    
    public Player dealer;
    public ArrayList<Player> playerList;

    /**
     * The method gameStart() has three stages.
     * Stage 1: Check if all players have bet. This is done by using a boolean variable called key, which is true when all players bet, false otherwise.
     * Stage 2: After all players have bet, the gameStart() method deals two cards to each player and dealer. One of dealer's card is hidden.
     * Stage 3: Once the first cards are dealt, the gameStart() method check if any player has Blackjack and make modification to the player's instance variables. See class Player
     * It is worth to note that the gameStart() method is called everytime a player clicks "Bet" button. 
     * @param playerList a list of Player object in the current game.
     * @param dealer a special type of Player object. 
     */
    public void gameStart(ArrayList<Player> playerList, Player dealer){
        boolean key = true;
        for(Player p : playerList){
            if(!p.doneBet){
                key = false;
            }
        }
        if(key){
            dealer.hit(true);
            for(Player p : playerList){
                p.hit(true);
            }
            dealer.hit(false);
            for(Player p : playerList){
                p.hit(true);
            }
            for(Player p : playerList){
                if(p.findValue()==21){
                    p.hit.setDisable(true);
                    p.stand.setDisable(true);
                    p.doub.setDisable(true);
                    p.balance += 2.5*p.currentBet;
                    p.blackjack = true;
                    p.setBalance();
                    p.stand();
                    p.result.setText("Blackjack");
                }
            }
        }
    }

    /**
     * The method showDown() uses a boolean variable called key to check if all players have stand or are forced to stand(i.e. busted)
     * Once all players have stand, the showDown() method reveals the dealer's hidden card. Then the dealer draws until a soft 17
     * If the dealer does not bust, the showDown() method compare the daeler's hand to each player through a for each loop
     * Once win/lose/push if determined for a player, the method changes the player's balance and makes a win/lose/push label in the 
     * player's panel.
     * @param playerList a list of Player object in the current game.
     * @param dealer a special type of Player object. 
     */
    public void showDown(ArrayList<Player> playerList, Player dealer){
        boolean key = true;
        for(Player p : playerList){
            if(!p.doneStand){
                key = false;
            }
        }
        if(key){
            Card card = dealer.hand.get(1);
            card.show(true);
            dealer.panel.Hand.getChildren().remove(1);
            dealer.panel.Hand.getChildren().add(new ImageView(card.img));
            while(dealer.findValue()<17){
                dealer.hit(true);
                if(dealer.findValue()>21){
                    for(Player p : playerList){
                        if(!p.lost && !p.blackjack){
                            p.balance += 2*p.currentBet;
                            p.setBalance();
                            p.result.setText("Win");
                        }
                    }
                }
            }
            for(Player p : playerList){
                if(dealer.findValue()<=21 && dealer.findValue()>p.findValue()){
                    p.result.setText("Lost");
                }
                if(!p.lost && dealer.findValue()<p.findValue()){
                    p.balance += 2*p.currentBet;
                    p.setBalance();
                    p.result.setText("Win");
                }
                if(!p.lost && dealer.findValue()==p.findValue()){
                    p.balance += p.currentBet;
                    p.setBalance();
                    p.result.setText("Push");
                }
            }
            dealer.panel.setValue(dealer.findValue()+"");
        }
    }

}