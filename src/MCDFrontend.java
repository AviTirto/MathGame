import java.util.LinkedList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MCDFrontend extends Application implements IMCDFrontend {
	protected ICMDijkstrasBackend backend = new CMDijkstrasBackend();
	private String green = "-fx-background-color: #33FF9F;";
	private String orange = "-fx-background-color: #FF8A33;";
	private String blue = "-fx-background-color: #33A9FF;";
	private String lightBlue = "-fx-background-color: #33E6FF;";
	private String yellow = "-fx-background-color: #FFE033;";
	
	/**
	 * 
	 * Configures the JavaFX program upon it's launch.
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Gets front end graph matrix from backend to be used as the game boards blue
		// print
		String[][] graphFormat = backend.gridMatrixForFD();

		// Creates game tiles pane
		BorderPane gameTilesPane = new BorderPane();
		VBox rowPane = new VBox();
		gameTilesPane.setMinWidth(570);
		gameTilesPane.setCenter(rowPane);
		gameTilesPane.setMargin(rowPane, new Insets(20));

		// Sets up game board and gets all the newly generated tile buttons
		LinkedList<Button> buttons = setBoard(gameTilesPane, rowPane, graphFormat);

		// Creates a JavaFX label to display questions to the user
		Label question = new Label();

		// gives the question label an id for testing
		question.setId("questionLbl");
		question.setWrapText(true);

		// Generates the first question to be asked
		generateQuestion(question);

		// Creates a JavaFX label to display the users points
		// Sets the first display of points to the initial score of the user when the
		// program is first launched
		Label points = new Label("Points: " + backend.getPoints());

		// gives the points label an id for testing
		points.setId("pointsLbl");

		// Creates a message label to display instructions, results, etc.
		// Sets first message to display the game instructions
		Label message = new Label("Instructions: Select the shortest path from the tiles in the question!"
				+ " When you are done press submit!\nRemember: You can only move between tiles"
				+ " connected by a line. If you want to unselect a tile you have to do it in the order"
				+ " that you selected the tiles in.");
		// Set message labels word wrap to true so that it can be seen in the users
		// screen
		message.setId("messageLbl");
		message.setWrapText(true);

		// Creates Menu Buttons and defines their click functionalities

		// Creates a JavaFX button to submit the users answers
		Button submitBtn = new Button("Submit");
		// Gives the submit button an id for testing
		submitBtn.setId("submitBtn");
		submitBtn.setOnAction((event -> {
			// Parses the question label to get the starting tile of the current question
			String firstVertex = question.getText().split("from")[1].split("to")[0].trim();
			// Parses the question label to get the ending tile of the current question
			String lastVertex = question.getText().split("from")[1].split("to")[1].replace("?", "").trim();

			// Feeds the starting tile and ending tile to backend's checkSubmissions()
			// method so that the users points are properly changed
			// backend's checkSubmissions() method will return true if the users answer is
			// correct and false otherwise
			if (backend.checkSubmission(firstVertex, lastVertex)) {
				// Then the users answer was correct

				// Sets the message label to tell the user that their answer was correct and to
				// do another puzzle
				message.setText("Correct! Press New Puzzle to do another!");

				// Updates the points label to properly reflect to the user their score after
				// their latest submission
				points.setText("Points: " + backend.getPoints());

				// Sets the users selections to green to indicate that their answer was correct
				showCorrect(buttons);
			} else {
				// Then the users answer was incorrect

				// Sets the message label to tell the user that their answer was incorrect and
				// that they should try again.
				message.setText("Incorrect! Press Reset to try again");

				// Updates the points label to properly reflect to the user their score after
				// their latest submission
				points.setText("Points: " + backend.getPoints());

				// Sets the users selections to red to indicate that their answer was incorrect
				showIncorrect(buttons);
			}

			// Tells backend to create a new slate for the users next answers
			backend.reset();
		}));

		// Creates a JavaFX button that allows users to create a new question
		Button newPuzzleBtn = new Button("New Puzzle");

		// Sets id of the new puzzle button for testing
		newPuzzleBtn.setId("newPuzzleBtn");
		newPuzzleBtn.setOnAction((event -> {
			// Tells backend to reset the users previous input
			backend.reset();

			// Creates a new question and displays it to the user using the question label
			generateQuestion(question);

			// Unselects all the selected tiles on the game board
			resetButtons(buttons);
		}));

		// Creates a JavaFX button that allows user to see the answer to the current
		// question
		Button giveUpBtn = new Button("Give Up");

		// Sets id of the give up button for testing
		giveUpBtn.setId("giveUpBtn");
		giveUpBtn.setOnAction((event -> {
			// Parses the question label to get the starting tile of the current question
			String firstVertex = question.getText().split("from")[1].split("to")[0].trim();
			// Parses the question label to get the ending tile of the current question
			String lastVertex = question.getText().split("from")[1].split("to")[1].replace("?", "").trim();

			// Tells backend to reset the users previous input
			backend.reset();
			
			// Unselects all selected buttons
			resetButtons(buttons);
			
			// Feeds the starting tile and ending tile to backend's giveUp method to get the
			// correct path
			// Shows the user the correct answer given by backend by highlighting the tiles
			// yellow
			giveUp(backend.giveUp(firstVertex, lastVertex), buttons);
			
			

		}));

		// Creates a JavaFX button that resets the users current selections
		Button resetBtn = new Button("Reset");

		// Sets id of the reset button for testing
		resetBtn.setId("resetBtn");
		resetBtn.setOnAction((event -> {
			// Sets the message label to display the instructions
			message.setText("Instructions: Select the shortest path from the tiles in the question!"
					+ " When you are done press submit!\nRemember: You can only move between tiles"
					+ " connected by a line. If you want to unselect a tile you have to do it in the order"
					+ " that you selected the tiles in.");

			// Tells backend to reset the users previous input
			backend.reset();

			// Unselects all selected buttons
			resetButtons(buttons);
		}));
		Button hintBtn = new Button("Hint");
		hintBtn.setId("hintBtn");
		hintBtn.setOnAction((event -> {
			String firstVertex = question.getText().split("from")[1].split("to")[0].trim();
			String lastVertex = question.getText().split("from")[1].split("to")[1].replace("?", "").trim();
			String hint = backend.hint(firstVertex, lastVertex);
			message.setText(hint);
		}));
		
		Button cheatSheetBtn = new Button("Cheat Sheet");
		cheatSheetBtn.setId("cheatSheetBtn");
		cheatSheetBtn.setOnAction((event -> {
			String firstVertex = question.getText().split("from")[1].split("to")[0].trim();
			String[][] cheatSheet = backend.cheatSheet(firstVertex);
			String format = "";
			for(int i = 0; i < cheatSheet.length; i++) {
				format += "Path to " + cheatSheet[i][0] + ": " + cheatSheet[i][1] + "\n";
			}
			message.setText(format);
		}));
		cheatSheetBtn.setId("cheatSheetBtn");

		// Set menu buttons dimension
		submitBtn.setMinSize(230, 50);
		newPuzzleBtn.setMinSize(230, 50);
		giveUpBtn.setMinSize(230, 50);
		resetBtn.setMinSize(230, 50);
		hintBtn.setMinSize(230, 50);
		cheatSheetBtn.setMinSize(230, 50);

		// Make menu pane by putting the menu buttons into a VBox, so that they stack on
		// top of each other
		VBox menuPane = new VBox(question, submitBtn, newPuzzleBtn, giveUpBtn, hintBtn, resetBtn, cheatSheetBtn, points, message);

		// Sets margins for the question, points, and message labels so they can be
		// displayed clearly on the screen
		menuPane.setMargin(question, new Insets(10, 20, 10, 20));
		menuPane.setMargin(points, new Insets(10, 20, 10, 20));
		menuPane.setMargin(message, new Insets(10, 20, 10, 20));

		// Adds menu pane and game tiles pane
		SplitPane splitPane = new SplitPane(gameTilesPane, menuPane);

		// Sets scene to split pane and displays the entire program to the user
		Scene scene = new Scene(splitPane, 800, 650);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * 
	 * main method that launches JavaFX application
	 * 
	 */
	public static void main(String[] args) {
		Application.launch();
	}

	/**
	 * 
	 * Calls backend's newPuzzle() method to generate a question the next two tiles
	 * the user must find the shortest path between. This method returns a String
	 * array of length 2. The first element in the String array is the starting tile
	 * and the second element is the ending tile. Then it will set the text of the
	 * label it receives as a parameter to a formatted question with the starting
	 * tile and ending tile.
	 * 
	 * @param question
	 * @return the resulting String array of backend's newPuzzle() method
	 */
	@Override
	public String[] generateQuestion(Label question) {
		// Calls backend's newPuzzle() method to get the tiles of the next question
		String[] vertices = backend.newPuzzle();
		// Sets the text of the JavaFX Label to a formatted question using the return of
		// backend's newPuzzle() method
		question.setText("Find the shortest way from " + vertices[0] + " to " + vertices[1] + "?");

		// Returns the result of backend's newPuzzle() method
		return vertices;
	}

	/**
	 * 
	 * This method is used to show users that their submission is correct. It will
	 * find all the selected tile buttons(the ones colored light blue) and make them
	 * green
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile
	 *                buttons on the board.
	 */
	@Override
	public void showCorrect(LinkedList<Button> buttons) {
		// Iterates through every tile button on the board
		for (Button button : buttons) {
			// Checks if they were selected by the user
			if (button.getStyle().equals(lightBlue)) {
				// Make the tile green
				button.setStyle(green);
			}
		}
	}

	/**
	 * 
	 * This method is used to show users that their submission is incorrect. It will
	 * find all the selected tile buttons(the ones colored light blue) and make them
	 * orange
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile
	 *                buttons on the board.
	 */
	@Override
	public void showIncorrect(LinkedList<Button> buttons) {
		// Iterates through every tile button on the board
		for (Button button : buttons) {
			// Checks if they were selected by the user
			if (button.getStyle().equals(lightBlue)) {
				// Make the tile orange
				button.setStyle(orange);
			}
		}
	}

	/**
	 * 
	 * It will find all the selected tile buttons(the ones colored light blue) and
	 * put put them in the unselected state(colored blue)
	 * 
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile
	 *                buttons on the board.
	 */
	@Override
	public void resetButtons(LinkedList<Button> buttons) {
		// Iterates through every tile button on the board
		for (Button button : buttons) {
			// Checks if they were not unselected by the user
			if (!button.getStyle().equals(blue)) {
				// Make the tile blue
				button.setStyle(blue);
			}
		}
	}

	/**
	 * 
	 * This method is used to show the users the correct path of the question by
	 * setting the colors of the tiles in this path to yellow. It takes two
	 * parameters, the first being a list of the labels of the tiles in the correct
	 * path and the second being a list of all the tile buttons on the board.
	 * 
	 * @param correct - a list of the labels of the tiles in the correct path
	 * @param buttons - LinkedList of JavaFx Button objects which holds all the tile
	 *                buttons on the board.
	 */
	@Override
	public void giveUp(LinkedList<String> correct, LinkedList<Button> buttons) {
		// Iterates through every label of the correct tiles
		for (String label : correct) {
			// Iterates through every tile button on the board
			for (Button button : buttons) {
				// Finds the button with the label found in correct
				if (button.getText().equals(label)) {
					button.setStyle(yellow);
				}
			}
		}
	}

	/**
	 * 
	 * Configures game tiles pane upon the start up of the game. Uses a front end
	 * matrix generated by back end as the blue prints for board. Generates the tile
	 * buttons that are used for other game operations and sets its click and
	 * unclick functionality.
	 * 
	 * @param gameTilesPane a BorderPane used as an overlay reference point for line
	 *                      objects
	 * @param colPane       a VBox used to stack rows on top of each other
	 * @param graphFormat   a front end matrix generated by back end to be used as
	 *                      the blue print for the board
	 * @return buttons, a linked list of JavaFX button objects which represent the
	 *         tiles in the program.
	 */
	private LinkedList<Button> setBoard(BorderPane gameTilesPane, VBox colPane, String[][] graphFormat) {
		// Initializes a list of tile buttons to be returned
		LinkedList<Button> buttons = new LinkedList<Button>();

		// Uses the front end matrix created by back end to determine the tiles, paths,
		// and weights associated to paths to be displayed
		// on the board

		// Gets length of the rows with buttons from the first array in the 2D array
		// returned from back end, since the first row
		// in the graph format will always have buttons, ie, will be longer than the
		// rows which only have vertical weights
		int rowLength = graphFormat[0].length;

		// Initializes the first X coordinate and Y coordinates of the vertical lines,
		// which represent the paths between tiles
		int vLineX = 90;
		int[] vLineY = new int[] { 100, 157 };

		// Initializes the first X coordinate and Y coordinates of the horizontal lines,
		// which represent the paths between tiles
		int hLineX = 70;
		int[] hLineY = new int[] { 125, 178 };

		// Iterating through every row of the graph format generated by back end
		for (int i = 0; i < graphFormat.length; i++) {

			// Checks if the current row contains buttons or only contains vertical edges
			if (graphFormat[i].length == rowLength) {
				// Creates a Horizontal Box Pane to format the buttons and paths in a single row
				// on the screen
				HBox row = new HBox();
				// Positions all elements added to the Horizontal Box Pane to be in the center
				row.setAlignment(Pos.CENTER);

				// Iterates through every element in the row with buttons
				for (int j = 0; j < graphFormat[i].length; j++) {

					// Checks if the current element is an integer which would indicate that it is a
					// weight
					try {
						// If it is an integer than the current element is a weight
						Integer.parseInt(graphFormat[i][j]);

						// Creates a margin which is standardized for all elements that are weights in
						// rows
						// with buttons.
						Insets insets = new Insets(20);

						// Increases the size of strings that are only one character long to make the
						// margin
						// between single character integer and two character integers more simmilar
						if (graphFormat[i][j].length() == 1) {
							graphFormat[i][j] = " " + graphFormat[i][j] + " ";
						}

						// Creates a JavaFX label to display the path weight to the screen
						Label label = new Label(graphFormat[i][j]);

						// Adds the label to the row(HBox)
						row.getChildren().add(label);

						// Sets label's margin to the standard margin
						row.setMargin(label, insets);

						// Creates a horizontal line to represent the paths between tiles in the same
						// row
						Line line = new Line();
						line.setStartX(hLineY[0]);
						line.setStartY(hLineX);
						line.setEndX(hLineY[1]);
						line.setEndY(hLineX);

						// Adds horizontal line to the parent of the VBox so that it is overlaid to the
						// elements in the row
						// and not counted as another element to stack
						gameTilesPane.getChildren().add(line);

						// Increments the X position of the next horizontal line so that it connects the
						// next two tiles in the row
						hLineX += 137;

						// Then the element in the row with buttons is not a weight
					} catch (NumberFormatException n) {

						// Creates a button to represent a tile where its text is set to the String
						// given by back end's graph format matrix
						Button btn = new Button(graphFormat[i][j]);

						// Sets the tiles color to blue which represents its state when it has not been
						// selected by the used
						btn.setStyle(blue);

						// Give the button a unique id for testing using its text content, ie "A" -->
						// btnA
						btn.setId("btn" + graphFormat[i][j]);

						// Set the buttons click and unclick functionality
						btn.setOnAction((event -> {

							// Checks if it buttons current color is light blue, indicating has been
							// selected by the user
							if (btn.getStyle().equals(lightBlue)) {

								// Uses backend's unclick method to determine if this button can be unclicked
								// which only happens
								// if it is the last button selected by the user. This is managed by back end.
								if (backend.unclick(btn.getText())) {

									// Then back end has confirmed the button can be unclicked so it is set to the
									// unselected state
									// of blue.
									btn.setStyle(blue);
								}
								// For all other possible colors, ie, green, blue, and yellow
							} else {
								// Uses backend's addCurrent method to determine if the button can be clicked
								// which only happens
								// if it is adjacent to the last tile selected.
								if (backend.addCurrent(btn.getText())) {
									// Then back end has confirmed the button can be clicked so it is set to the
									// selected state of
									// light blue
									btn.setStyle(lightBlue);
								}
							}
						}));

						// Sets the dimensions of the buttons
						btn.setMinSize(80, 80);

						// Adds the new tile button to the list of tile buttons to be returned
						buttons.add(btn);

						// adds the tile button to the row(HBox)
						row.getChildren().add(btn);
					}
				}
				// After all elements in the row have been incremented through the X coordinate
				// of the horizontal lines is reset to it's original
				// to be used for other rows with horizontal lines
				hLineX = 70;

				// The Y coordinates are incremented to create lines at lower rows
				hLineY[0] += 133;
				hLineY[1] += 133;

				// Adds the newly built row with tile buttons to the Vertical Box so that they
				// are stacked on top of each other to form a grid
				colPane.getChildren().add(row);

				// Then the row only contains the vertical weights between tiles on two
				// different rows
			} else {
				// Creates a grid pane to hold each edge in its own grid
				GridPane grid = new GridPane();

				// Creates a horizontal space between grids so that the weights line up to the
				// x-coordinates of the two tiles that they connect
				grid.setHgap(115);

				// Creates a standardized margin object for every weight
				Insets insets = new Insets(20, 0, 20, 0);

				// Iterates through every element in the row
				for (int k = 0; k < graphFormat[i].length; k++) {

					// Increases the size of strings that are only one character long to make the
					// margin
					// between single character integer and two character integers more simmilar
					if (graphFormat[i][k].length() == 1) {
						graphFormat[i][k] = " " + graphFormat[i][k] + " ";
					}

					// Creates a JavaFX label to display the path weight to the screen
					Label label = new Label(graphFormat[i][k]);

					// Set dimensions of the label to ensure that the entire weight is shown
					label.setMinWidth(15);

					// Adds the JavaFX label of every weight in the row to the same row in the grid
					grid.add(label, k, 0);

					// Sets each labels margin to the standardized margin
					grid.setMargin(label, insets);

					// Creates vertical line to connect tiles on different rows
					Line line = new Line();
					line.setStartX(vLineX);
					line.setStartY(vLineY[0]);
					line.setEndX(vLineX);
					line.setEndY(vLineY[1]);

					// Adds line to the parent of the VBox so that it is overlaid to the elements in
					// the row
					// and not counted as another element to stack
					gameTilesPane.getChildren().add(line);

					// Increments the X position of the vertical line so that it connects the next
					// two tiles in different rows
					vLineX += 130;
				}

				// After all elements in the row have been incremented through the X coordinate
				// of the vertical lines is reset to it's original
				// to be used for other rows with vertical lines
				vLineX = 90;

				// The Y coordinates are incremented to create vertical lines at lower rows
				vLineY[0] += 137;
				vLineY[1] += 137;

				// Adds grid to to the VBox to be stacked as a row
				colPane.getChildren().add(grid);

				// Sets a margin within the vertical box for the grid so it doesn't stick to the
				// sides of the screen and is more centered.=
				colPane.setMargin(grid, new Insets(0, 50, 0, 50));

			}
		}
		// Returns the linked list of tile buttons
		return buttons;
	}
}
