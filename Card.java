import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The Card class manages the creating of a standard poker card.
 * 
 * @author Zhaoliang Ni
 */
public class Card{
    
    private int intValue;
    private char suit;
    private char charValue;
    private String cardface;
    public Image img; 
    private String addr = "resource/";

    /**
     * Construct the Card class
     * @param s String representation of a card. For example, ace of spade = "SA", 10 of heart = "HT"
     */
    public Card(String s){
        this.cardface = s;
        this.suit = s.charAt(0);
        char c = s.charAt(1);
        this.charValue = c;
        if(c == 'A') {
            this.intValue = 11;
        }
        else if(c == 'T'||c == 'J'||c == 'Q'||c == 'K'){
            this.intValue = 10;
        }
        else{
            this.intValue = Character.getNumericValue(c);
        }

        try{
            String str = addr + s + ".jpg";
            FileInputStream input = new FileInputStream(str);
            this.img = new Image(input, 53, 105, true, false);
            input.close();
        }
        catch(FileNotFoundException ec){
            System.out.println("Image File Not Found!!!");
        }
        catch(IOException io){
            System.out.println("IO error");
        }
    }

    /**
     * Assign the ImageView instance variable to either the card or the back of the card base on bool
     * @param bool Card is shown if true, hidden if false
     */
    public void show(boolean bool){
        try{
            String s = bool ? (addr + cardface + ".jpg") : (addr + "back.jpg");
            FileInputStream input = new FileInputStream(s);
            this.img = new Image(input, 53, 105, true, false);
            input.close();
        }
        catch(FileNotFoundException ec){
            System.out.println("Image File Not Found!!!");
        }
        catch(IOException io){
            System.out.println("IO error");
        }
    }
    
    /**
     * gets the integer representation of the card value
     * @return the value of the card as an integer
     */
    public int getIntValue(){
        return this.intValue;
    }

    /**
     * gets the character representation of the card value
     * @return the value of the card as a character
     */
    public char getCharValue(){
        return this.charValue;
    }    

    /**
     * String representation of the suit
     * @return a String of the suit of the card
     */
    public String getSuit(){
        if(this.suit=='S'){
            return "spade";
        }
        if(this.suit=='H'){
            return "heart";
        }
        if(this.suit=='D'){
            return "diamond";
        }
        if(this.suit=='C'){
            return "club";
        }
        return "";
    }

    /**
     * String representation of the Card
     * @return a string of the card in "value" of "suit" format
     */
    public String toString(){
        char c = cardface.charAt(1);
        if(c=='A'){
            return "ace of " + getSuit(); 
        }
        if(c=='J'){
            return "jack of " + getSuit();  
        }
        if(c=='Q'){
            return "queen of " + getSuit(); 
        }
        if(c=='K'){
            return "king of " + getSuit();
        }
        if(c=='T'){
            return "10 of " + getSuit();
        }
        else{
            return this.intValue + " of " + getSuit();
        }
    }

    /*
    public static void main(String[] args){
        Card card = new Card("S2");
        System.out.println(card.toString());
        System.out.println(card.getIntValue());
        System.out.println(card.getCharValue());
    }
    */

}