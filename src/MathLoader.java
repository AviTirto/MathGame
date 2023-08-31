// --== CS400 File Header Information ==--
// Name: Kelly Weng
// Email: kweng3@wisc.edu
// Team: AQ
// TA: MingHao Yan
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Loads edges and vertexes from the .dot file into an adjacency matrix and linked list of vertices
 */
public class MathLoader implements IMathLoader {

    // file name of the dot file
    private static String fileName = "MathValues.dot";

    /**
     * This method loads a linked list of vertices from a dot file
     *
     * @return a linked list of vertices
     * @throws FileNotFoundException
     */
    @Override
    public LinkedList<String> loadVertices() throws FileNotFoundException {

        // instantiate the scanner and the file
        File file = new File(MathLoader.fileName);
        Scanner reader = new Scanner(file);

        // make a linked list for the vertices to be loaded into
        LinkedList<String> vertices = new LinkedList<String>();

        // keep iterating while there's a next line in the scanner
        while (reader.hasNextLine()) {

            // set the line to a variable
            String currLine = reader.nextLine();

            // only continue if the current line isn't the "digraph" or the "}" at the end
            if (!currLine.contains("digraph") && !currLine.contains("}")) {

                // instantiate a variable to hold the String value of the first vertex
                String vert = String.valueOf(currLine.charAt(currLine.indexOf("\"") + 1));

                // if the vertices linked list doesn't already have vertex, then continue
                if (!vertices.contains(vert)) {
                    // add the vertex to the vertices linked list at the specified index
                    vertices.add(findIndex(vertices, vert), vert);
                }

                // set the vert variable to the String value of the second vertex
                vert = String.valueOf(currLine.charAt(currLine.lastIndexOf("\"") - 1));

                // if the vertices linked list doesn't already have vertex, then continue
                if (!vertices.contains(vert)) {
                    // add the vertex to the vertices linked list at the specified index
                    vertices.add(findIndex(vertices, vert), vert);

                }

            }

        }

        // return the vertices linked list
        return vertices;

    }

    /**
     * private helper method to find the index of the linked list that the vertex belongs in through
     * ascii comparisons
     *
     * @param vertices linked list with vertexes to be compared to
     * @param vert vertex needed to be added into the vertices linked list
     * @return index that the vertex should be in
     */
    private int findIndex(LinkedList<String> vertices, String vert) {

        // make a variable to hold the index of where the vertex belongs in, set to the size of the list
        int index = vertices.size();

        // iterate through the vertices linked list
        for (int i = vertices.size() - 1; i >= 0;  i--) {

            // if the ascii value of the new vertex is less than the one of the previous vertex...
            if (Character.getNumericValue(vert.charAt(0)) <
                    Character.getNumericValue(vertices.get(i).charAt(0))) {

                // then set the index to the index of the previous index
                index = i;

            }

        }

        // if the ascii value is the largest, then return the size of the linked list
        return index;

    }

    /**
     * This method loads a 2D array of numbers representing the edges of Node in the order
     * of the sequence in the linked list
     *
     * @return a 2D number array representing the edges
     * @throws FileNotFoundException
     */
    @Override
    public Number[][] loadEdges() throws FileNotFoundException {

        // instantiate the scanner and the file
        File file = new File(MathLoader.fileName);
        Scanner reader = new Scanner(file);

        // make a 2D array with the parameters being the number of vertices
        Number[][] matrix = new Number[loadVertices().size()][loadVertices().size()];

        int asciiA = Character.getNumericValue('A');

        // keep iterating while there's a next line in the scanner
        while (reader.hasNextLine()) {

            // set the line to a variable
            String currLine = reader.nextLine();

            // only continue if the current line isn't the "digraph" or the "}" at the end
            if (!currLine.contains("digraph") && !currLine.contains("}")) {

                // make two variables holding the ascii equivalent of their character values
                // subtracted by the ascii value of A as to get a number of from 0 to 25
                int v1 = Character.getNumericValue(currLine.charAt(currLine.indexOf("\"") + 1)) - asciiA;
                int v2 = Character.getNumericValue(currLine.charAt(currLine.lastIndexOf("\"") - 1)) - asciiA;

                // set the weight to null
                Number weight = null;

                // if the line contains a weight, then continue
                if (currLine.contains("weight")) {

                    // set a character to represent the beginning of the weight and the end of the weight
                    char equals = currLine.charAt(currLine.indexOf("=") + 1);
                    char bracket = currLine.charAt(currLine.indexOf("]"));

                    // set the weight to the value of the weight between the two characters
                    weight = Integer.parseInt(currLine.substring(currLine.indexOf(equals), currLine.indexOf(bracket)));

                }

                // set the value of the matrix to the weight
                matrix[v1][v2] = weight;

            }

        }

        // iterate through the matrix and every value that's still null, set to infinity
        for (int i = 0; i < 12; i++) {
            for (int k = 0; k < 12; k++) {
                if (matrix[i][k] == null) {
                    matrix[i][k] = Integer.MAX_VALUE;
                }
            }
        }

        // return the matrix
        return matrix;

    }

    /**
     * returns a 2D array with the grid representation of adjacency matrix
     *
     * @return 2D array with the grid matrix for frontend applications
     * @throws FileNotFoundException
     */
    @Override
    public String[][] gridMatrix() throws FileNotFoundException {

        // make a new matrix and vertex object to hold the respective values
        Number[][] matrix = loadEdges();
        LinkedList<String> vertices = loadVertices();

        // make variables to hold the size and the expected size of the grid
        int size = vertices.size();
        int rows = (int) Math.sqrt(size);
        int columns = (int) size / rows;

        int newYSize = (2 * rows) - 1;
        int newXSize = (2 * columns) - 1;

        // make a new 2D array object to hold the values of the vertices and edges
        String[][] newMatrix = new String[newYSize][newXSize];

        // create two counters to hold the current vertex and to hold the coord offset
        int counter = 0;
        int coord = 0;

        // iterate through the grid matrix
        for (int y = 0; y < newYSize; y++) {
            for (int x = 0; x < newXSize; x++) {

                // if the row is an even row, then it'll contain the values of the vertices and right/left edges
                if (y % 2 == 0) {

                    // reset the coord offset back to 0
                    coord = 0;

                    // if the column is an even column, then populate it with the value of vertex
                    // and then increment counter
                    if (x % 2 == 0) {
                        newMatrix[y][x] = vertices.get(counter);
                        counter++;
                    }

                    // otherwise, if the x coordinate is not the last one, then populate it with the
                    // value of the edge between the last and current counter vertex
                    else if (x < newXSize - 1){
                        newMatrix[y][x] = matrix[counter-1][counter].toString();
                    }

                }

                // otherwise, this is an up/down edge
                else {

                    // if the x value is even, then populate it with the up/down edge between the previous
                    // row and the current row
                    if (x % 2 == 0) {

                        // increment the coord offset to get the next index
                        newMatrix[y][x] = matrix[(counter - 1 + coord) - (columns - 1)][counter + coord].toString();
                        coord++;

                    }

                }

            }


        }

        // return the new matrix
        return newMatrix;

    }

}
