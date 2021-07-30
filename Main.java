import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The only purpose of the Main class is to create a new Blackjack game by constructing
 * a new MainMenu object.
 * 
 * 
 * @author Zhaoliang Ni
 */

public class Main extends Application {

    /*
      Lookback:
      Zhaoliang Ni: The quarter project extended my knowledge of object-oriented programming. The design phase was not as difficult 
                    as the development phase. Mostly because I was used to write codes in one single class, but the complexity of 
                    the project would require a division of responsibilities, many coorporating classes, to work. It was interesting
                    to learn that many of the methods or classes written from the failed versions can be recycled and put into use
                    for the final version. For example, the Card class, which was the most easy to write class, was reused with slight
                    changes. JavaFX was fairly new to me before the project, so I had to consult the library frequently throughout the 
                    development process. For example There was an issue with a image of a card being "stolen" from one hand to another. 
                    Later I found a solution on Geeks-for-Geeks, I found that the ImageView object is unique in JavaFX and can only 
                    appear once in one parent node in a scene. The solution was to use Image objects to store the card images. Overall, 
                    I think the development experience of the quarter project had been greatly valuable to me.
	
	Jawad Almaatouk: The quarter project taught me a lot about process of creating and maintaining a program that addresses a specific problem.
			 The Design phase is by far the hardest because usually the first design either doesn't work or is heavily flawed, which 
			 was the case with this program. Division of responsibility among class is something new to me that I had to experiment with
			 since I learned how code in big blocks only. The Violet app was also a new idea to me but was very useful. Having a class
			 diagram helped me keep track of said classes and gave a better idea of how they interact. The project, overall, was high valuable 
			 experience to me that is sure to help smoothen my transition into the professional world.        
     */

    /**
     * the JavaFx start method
     * @param primaryStage the main stage of the program
     */
    @Override
    public void start(Stage primaryStage){
        new MainMenu();
    }

    /**
     * The main method
     * @param args the method takes no input
     */
    public static void main(String[] args){
        Application.launch(args);
    }
    
}

