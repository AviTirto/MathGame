import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public interface IMCDFrontend {
	/**
	 * 
	 * Calls backend's newPuzzle() method to generate a question the next two tiles the user must find the shortest path between. This method returns 
	 * a String array of length 2. The first element in the String array is the starting tile and the second element is the ending tile. Then it will
	 * set the text of the label it receives as a parameter to a formatted question with the starting tile and ending tile.
	 * 
	 * @param question
	 * @return the resulting String array of backend's newPuzzle() method
	 */
	public String[] generateQuestion(Label question);

	/**
	 * CMDijkstra
	 * This method is used to show users that their submission is correct. It will find all the selected tile buttons(the ones colored light blue) 
	 * and make them green
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile buttons on the board.
	 */
	public void showCorrect(LinkedList<Button> buttons);

	/**
	 * 
	 * This method is used to show users that their submission is incorrect. It will find all the selected tile buttons(the ones colored light blue) 
	 * and make them orange
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile buttons on the board.
	 */
	public void showIncorrect(LinkedList<Button> buttons);

	/**
	 * 
	 * It will find all the selected tile buttons(the ones colored light blue) and put put them in the unselected state(colored blue)
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile buttons on the board.
	 */
	public void resetButtons(LinkedList<Button> buttons);

	/**
	 * 
	 * This method is used to show the users the correct path of the question by setting the colors of the tiles in this path to yellow.
	 * It takes two parameters, the first being a list of the labels of the tiles in the correct path and the second being a list of
	 * all the tile buttons on the board.
	 * 
	 * @param correct - a list of the labels of the tiles in the correct path
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile buttons on the board.
	 */
	public void giveUp(LinkedList<String> correct, LinkedList<Button> buttons);

}