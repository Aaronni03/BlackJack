import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

/**
 * The HandPanel class is a subclass of GridPane.
 * The class manages the player's hand of cards using a FlowPane and the hand value using a Label.
 * 
 * @author Zhaoliang Ni
 */
public class HandPanel extends GridPane{

    public Label valueLB;
    public FlowPane Hand;

    public HandPanel(){

        setGridLinesVisible(true);

        Pane p = new Pane();
        p.setPrefSize(0, 90);

        valueLB = new Label("Hand Value: ");
        Hand = new FlowPane();

        valueLB.setFont(new Font("Arial", 18));
        valueLB.setTextFill(Color.WHITE);

        add(Hand, 0, 0);
        add(p, 1, 0);
        add(valueLB, 0, 1);
        
        
        setAlignment(Pos.CENTER);
        setHgap(8.5);
        setVgap(8.5); 
    }

    /**
     * The method setValue() changes the label that indicates the hand value of the player's panel.
     * @param s the content of the value label
     */
    public void setValue(String s){
        valueLB.setText("Hand Value: " + s);
    }
}