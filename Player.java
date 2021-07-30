import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

/**
 * 
 * The Player class manages the GUI of the play area of a player. The GUI includes the player name,
 * the bet/balance information, the player's hand panel, and the hand value label. 
 * The Player class also takes care the functionality of the player's available actions, such as hit, stand, doube, etc.
 * 
 * 
 * @author Zhaoliang Ni
 */
public class Player {

    public String[] ERROR_MESSAGES = {"Enter a bet amount.",
                                      "Invalid amount. Bet cannot be less than 1.",
                                      "Invalid amount. You cannot bet more than your balance.",
                                      "Invalid amount. Enter a number. Range: [1, balance]"};

    public VBox content;
    public GridPane infoGrid;
    public String name;
    public double balance;
    public double currentBet;
    public Label betValue;
    public Label balValue;
    public Label result;
    public Deck deck;
    public boolean doneStand;
    public boolean doneBet;
    public Button hit;
    public Button stand;
    public Button doub;
    public Button bet;
    public TextField betField;
    public HandPanel panel;
    public AbstractGameStage gs;
    public ArrayList<Card> hand;
    public String type;
    public boolean lost;
    public boolean blackjack;

    /**
     * This constructor constructs a special type of player, or dealer. Hence the constructor does not take an initial balance as parameter.
     * @param g The game stage for which the player/dealer is in. This game stage manages the player/dealer and the flow of the game.
     * @param d The deck that the game is using, pre-shuffled in the initialization of game stage.
     * @param s The string that indicates the name or identifier of the player/dealer.
     */
    public Player(final AbstractGameStage g, final Deck d, final String s){
        type = "dealer";
        name = s;
        deck = d;
        if(g instanceof GameStage1P){
            gs = (GameStage1P)g;
        }
        if(g instanceof GameStage2P){
            gs = (GameStage2P)g;
        }
        hand = new ArrayList<Card>();
        final Label nameLB = new Label(name);
        nameLB.setAlignment(Pos.CENTER);
        panel = new HandPanel();

        setStyle(nameLB, "Arial", 18, Color.WHITE);

        content = new VBox(nameLB, panel);
        content.setAlignment(Pos.CENTER);
    }

    /**
     * This constructor constructs a regular player, which has an initial balance as parameter
     * @param g The game stage for which the player/dealer is in. This game stage manages the player/dealer and the flow of the game.
     * @param d The deck that the game is using, pre-shuffled in the initialization of game stage.
     * @param s The string that indicates the name or identifier of the player/dealer.
     * @param bal The initial balance the player has.
     */
    public Player(final AbstractGameStage g, final Deck d, final String s, final double bal){
        type = "player";
        name = s;
        deck = d;
        if(g instanceof GameStage1P){
            gs = (GameStage1P)g;
        }
        if(g instanceof GameStage2P){
            gs = (GameStage2P)g;
        }
        if(g instanceof GameStage3P){
            gs = (GameStage3P)g;
        }
        lost = false;
        blackjack = false;
        hand = new ArrayList<Card>();
        balance = bal;
        currentBet = 0;
        doneStand = false;
        doneBet = false;
        betValue = new Label();
        balValue = new Label(bal+"");
        result = new Label();

        panel = new HandPanel();

        final Label nameLB = new Label(name);
        final Label lb1 = new Label("Your bet: ");
        final Label lb2 = new Label("Balance: ");

        infoGrid = new GridPane();
        infoGrid.setGridLinesVisible(true);
        infoGrid.setAlignment(Pos.CENTER);
        infoGrid.setHgap(10);
        infoGrid.add(lb1, 0, 0);
        infoGrid.add(betValue, 1, 0);
        infoGrid.add(lb2, 2, 0);
        infoGrid.add(balValue, 3, 0);
        infoGrid.add(result, 4, 0);

        hit = new Button("Hit");
        hit.setDisable(true);
        stand = new Button("Stand");
        stand.setDisable(true);
        doub = new Button("Double");
        doub.setDisable(true);
        bet = new Button("Bet");
        betField = new TextField();
        final HBox buttonBox = new HBox(hit, stand, doub, betField, bet);
        buttonBox.setSpacing(10);

        hit.setOnAction(e-> {
            hit(true);
            doub.setDisable(true);
        });

        stand.setOnAction(e-> {
            stand();
        });

        doub.setOnAction(e-> {
            doubAction();
        });

        bet.setOnAction(e-> {
            if(betAction(gs)){
                bet.setDisable(true);
                if(!blackjack){
                    hit.setDisable(false);
                    stand.setDisable(false);
                    doub.setDisable(false);
                }
            }
        });

        setStyle(nameLB, "Arial", 24, Color.WHITE);
        setStyle(lb1, "Arial", 18, Color.WHITE);
        setStyle(lb2, "Arial", 18, Color.WHITE);
        setStyle(betValue, "Arial", 18, Color.WHITE);
        setStyle(balValue, "Arial", 18, Color.WHITE);
        setStyle(result, "Arial", 18, Color.RED);
        

        content = new VBox(nameLB, infoGrid, panel, buttonBox);
        content.setAlignment(Pos.CENTER);
    }

    /**
     * The method manages the font style, font size, and color of the label
     * @param l name of the Label object
     * @param s String name of the font style
     * @param d size of the font
     * @param c color of the text
     */
    public void setStyle(final Label l, final String s, final double d, final Color c){
        l.setFont(new Font(s, d));
        l.setTextFill(c);
    }

    /**
     * The hit() method takes care of the hit action when the playe click "Hit" button.
     * The method deal a new card from the deck to the player. Depending on the boolean parameter 'show',
     * the card is either hidden or shown. See method show() in class Card.
     * The card is then added to player's hand.
     * @param show boolean variable that determine whether to show the face or the back of the card. True - face; False - back.
     */
    public void hit(final boolean show){
        final Card card = deck.draw();
        card.show(show);
        hand.add(card);
        if(type.equals("dealer")) {
            panel.setValue(hand.get(0).getIntValue()+" + ?");
        }
        else{
            panel.setValue(findValue()+"");
        }
        if(type.equals("player") && findValue()>21){
            lost = true;
            result.setText("Busted");
            stand();
        }
        panel.Hand.getChildren().add(new ImageView(card.img));
    }

    /**
     * The stand() method manages the stand action when player clicks "Stand" button.
     * The method toggles the clickability of certain buttons.
     * The method changes the boolean variable doneStand to true, indicating that the player has no more available moves.
     * The method also calls the showDown() method in the gameStage*P class
     */
    public void stand(){
        stand.setDisable(true);
        hit.setDisable(true);
        doub.setDisable(true);
        doneStand = true;
        gs.showDown(gs.playerList, gs.dealer);
    }

    /**
     * The doubAction() method manages the double action when player clicks "Double" button.
     * The method ensures that player does no bet more than the balance, then doubles the current 
     * bet of the player, deducting the current balance in the process.
     * The method calls the hit() method to draw a new card from the deck and call stand immediately.
     */
    public void doubAction(){
        final Alert a = new Alert(AlertType.ERROR);
        if(currentBet < balance){
            balance -= currentBet;
            currentBet *= 2;

            balValue.setText(balance+"");
            betValue.setText(currentBet+"");

            hit(true);
            stand();
        }
        else{
            a.setContentText(ERROR_MESSAGES[2]);
            a.show();
        }
    }

    /**
     * The method betAction() manages the bet action when the player clicks "Bet" button.
     * The boolean variable key indicates whether the player has entered a valid bet.
     * If valid bet is entered, the key is set to true, and certain instance variables of the player are changed.
     * The method changes the boolean indicator doneBet to true and calls the gameStart() method in the gameStage*P class
     * @param gs The game stage for which the player/dealer is in. This game stage manages the player/dealer and the flow of the game.
     * @return boolean variable key, which indicates if the player has successfully ented a valid bet.
     */
    public boolean betAction(final AbstractGameStage gs){
        boolean key = false;
        final Alert a = new Alert(AlertType.ERROR);
        final String s = betField.getText();
        if(s.equals("")){
            a.setContentText(ERROR_MESSAGES[0]);
            a.show();
        }
        try{
            final double bet = Double.parseDouble(s);
            if(bet < 1){
                a.setContentText(ERROR_MESSAGES[1]);
                a.show();
                key = false;
            }
            else if(bet > balance){
                a.setContentText(ERROR_MESSAGES[2]);
                a.show();
            }
            else{
                currentBet += bet;
                betValue.setText(currentBet+"");
                balance -= currentBet;
                balValue.setText(balance+"");
                doneBet = true;
                gs.gameStart(gs.playerList, gs.dealer);
                key = true;
            }
        }
        catch(final NumberFormatException e){
            a.setContentText(ERROR_MESSAGES[3]);
            a.show();
        }
        return key;
    }

    /**
     * The method finds the hand value and returns it as int
     * @return the hand value of the player
     */
    public int findValue(){
        int value = 0;
        int numAce = 0;
        for(int i=0; i<this.hand.size(); i++){
            if(this.hand.get(i).getCharValue()=='A'){
                numAce++;
            }
        }
        for(int i=0; i<this.hand.size(); i++){
            value += this.hand.get(i).getIntValue();
        }
        if(hand.size()==2 && hand.get(0).getCharValue()=='A' && hand.get(1).getCharValue()=='A'){
            value = 12;
        }
        if(!(hand.size()==2)){
            value = (value - numAce*10);
        }
        return value;
    }

    public void setResult(final String s){
        result.setText(s);
    }

    /**
     * This method takes care the status of the buttons, clearing the hand, and setting some instance variables to default 
     * when a "Reset" button is pressed.
     */
    public void reset(){
        hand.clear();
        panel.setValue(findValue()+"");
        panel.Hand.getChildren().clear();
        if(type.equals("player")){
            bet.setDisable(false);
            hit.setDisable(true);
            stand.setDisable(true);
            doub.setDisable(true);
            doneBet = false;
            doneStand = false;
            lost = false;
            blackjack = false;
            currentBet = 0;
            betValue.setText(currentBet+"");
            result.setText("");
        }
    }

    /**
     * This method changes the label for the hand value of the player.
     */
    public void setBalance(){
        balValue.setText(balance+"");
    }

}