import java.util.Stack;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * 
 * The class Deck manages the creating of standard 52 card deck(s).
 * 
 * 
 * @author Zhaoliang Ni
 */

public class Deck{

    private Stack<Card> deck;
    private ArrayList<Card> ListDeck;
    private String[] ArrayDeck = {"SA", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "ST", "SJ", "SQ", "SK",
                                            "DA", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DT", "DJ", "DQ", "DK",
                                            "CA", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CT", "CJ", "CQ", "CK",
                                            "HA", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "HT", "HJ", "HQ", "HK"};

    /**
     * Constructor for an empty deck
     */
    public Deck(){
        this.deck = new Stack<Card>();
        this.ListDeck = new ArrayList<Card>();
    }

    /**
     * Constructor for a deck with n standard deck of cards
     * @param n number of standard decks
     */
    public Deck(int n){
        this.deck = new Stack<Card>();
        this.ListDeck = new ArrayList<Card>();
        for(int i=0; i<ArrayDeck.length; i++){
            Card card = new Card(this.ArrayDeck[i]);
            for(int j=0; j<n; j++){
                this.ListDeck.add(card);
            }
        }
        this.deck.addAll(this.ListDeck);
    }

    /**
     * builds a deck with n number of standar deck of cards
     * if the current deck is not empty, clear the deck first.
     * @param n numer of standar decks
     */
    public void makeDeck(int n){
        if(!ListDeck.isEmpty()){
            ListDeck.clear();
            deck.clear();
        }
        for(int i=0; i<ArrayDeck.length; i++){
            Card card = new Card(this.ArrayDeck[i]);
            for(int j=0; j<n; j++){
                this.ListDeck.add(card);
            }
        }
        this.deck.addAll(this.ListDeck);
    }

    /**
     * Shuffles the deck
     */
    public void shuffle(){
        this.deck.clear();
        Collections.shuffle(this.ListDeck);
        Collections.shuffle(this.ListDeck);
        Collections.shuffle(this.ListDeck);
        this.deck.addAll(this.ListDeck);
    }

    /**
     * draw a card from the play deck
     * @return Card 
     */
    public Card draw(){
        return deck.pop();
    }

    /**
     * check if the deck is empty
     * @return if the deck is empty
     */
    public boolean empty(){
        return deck.empty();
    }

    /*
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.makeDeck(4);
        deck.shuffle();
        Card card = deck.draw();
        System.out.println(card.toString());
    }
    */
}