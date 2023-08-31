// --== CS400 File Header Information ==--
// Name: Kelly Weng
// Email: kweng3@wisc.edu
// Team: AQ
// TA: MingHao Yan
// Lecturer: Gary Dahl
// Notes to Grader: N/A

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * tests the MathLoader class
 */
public class DataWranglerTest {

    // make a new MathLoader object
    MathLoader loader;
    // make a new 2D array and linked list to hold the value of the adjacency matrix and vertices
    Number[][] matrix;
    LinkedList<String> vertices;

    /**
     * setup and instantiate/call methods before each test
     */
    @BeforeEach
    public void setup() {

        try {

            // make a new loader object and populate a linked list and a 2D array with the returned
            // values from the methods of math loader
            loader = new MathLoader();

            vertices = loader.loadVertices();
            matrix = loader.loadEdges();

        } catch (Exception e) {
            fail("setup() threw an unhandled exception");
        }

    }

    /**
     * tests whether the weights of select indexes in the adjacency matrix return the correct value
     */
    @Test
    public void testEdges() {

        try {

            // test whether the weight of the first element of the adjacency matrix return the correct value
            assertEquals(15, matrix[0][1], "weight not correct for start");

            // test whether the weight of the middle element of the adjacency matrix return the correct value
            assertEquals(19, matrix[8][9], "weight not correct for middle");

            // test whether the weight of the last element of the adjacency matrix return the correct value
            assertEquals(11, matrix[11][10], "weight not correct for end");

        } catch (Exception e) {
            fail("testEdges() threw an unhandled exception");
        }

    }

    /**
     * tests whether the vertices of select indexes return the correct value
     */
    @Test
    public void testVertices() {

        try {

            // test whether the first element of the linked list returns the correct result
            assertEquals("A", vertices.get(0), "vertex not right for start");

            // test whether the middle element of the linked list returns the correct result
            assertEquals("F", vertices.get(5), "vertex not right for middle");

            // test whether the last element of the linked list returns the correct result
            assertEquals("L", vertices.get(11), "vertex not right for end");

        } catch (Exception e) {
            fail("testVertices() threw an unhandled exception");
        }

    }

    /**
     * tests that the size of the matrix and the vertices are correct
     */
    @Test
    public void testSize() {

        try {

            // test that the size of the linked list returns the correct value
            assertEquals(12, vertices.size(), "vertex size not correct");

            // test that the size of the 2D array returns the correct value
            assertEquals(12, matrix.length, "matrix size not correct");

            // test that the size of the linked list and 2D array returns the same value
            assertEquals(vertices.size(), matrix.length, "vertex and matrix size not equal");

        } catch (Exception e) {
            fail("testSize() threw an unhandled exception");
        }

    }

    /**
     * tests whether the correct number of cells have been populated
     */
    @Test
    public void testInfinity() {

        // make a counter variable to hold how many infinite values there are
        int counter = 0;

        // iterate through the matrix and if the value is equal to infinity, then increment counter
        for (int i = 0; i < matrix.length; i++) {

            for (int k = 0; k < matrix.length; k++) {

                if ((int) matrix[i][k] == Integer.MAX_VALUE) {
                    counter++;
                }

            }

        }

        // test the correct number of values in adjacency are infinity
        assertEquals(110, counter);

    }

    /**
     * tests whether the vertex linked list returns the correct value
     */
    @Test
    public void testCompleteVertices() {

        try {

            // make a expected string
            String expected = "[A, B, C, D, E, F, G, H, I, J, K, L]";

            // test that the entire vertices linked list is sorted and in order
            assertEquals(expected, vertices.toString(), "vertices to string not equal");

        } catch (Exception e) {
            fail("testCompleteVertices() threw an unhandled exception");
        }

    }

    /**
     * tests a row in the adjacency matrix to make sure it holds the correct values
     */
    @Test
    public void testEdgeRow() {

        try {

            // make a new linked list to hold the expected number values
            LinkedList<Number> expected = new LinkedList<Number>();

            // add expected values to the linked list
            expected.add(2147483647);
            expected.add(16);
            expected.add(2147483647);
            expected.add(20);
            expected.add(2147483647);
            expected.add(2147483647);
            expected.add(13);
            expected.add(2147483647);
            expected.add(2147483647);
            expected.add(2147483647);
            expected.add(2147483647);
            expected.add(2147483647);

            // iterate through and verify that the expected values are equal to the values of the matrix
            for (int i = 0; i < matrix.length; i++) {
                assertEquals(expected.get(i), matrix[2][i], "row matrix didn't return the correct value");
            }

        } catch (Exception e) {
            fail("testEdgeRow() threw an unhandled exception");
        }

    }

    /**
     * tests whether grid matrix returns the correct number of nulls and whether all vertices are accounted for
     */
    @Test
    public void testGrid() {

        try {

            // make a new matrix object with the grid matrix
            String[][] newMatrix = loader.gridMatrix();

            // make a counter to hold the amount of nulls
            int counter = 0;

            // make two variables, one to hold the expected and one to hold the vertexes in grid matrix
            String verts = "";
            String expected = "A, B, C, D, E, F, G, H, I, J, K, L, ";

            // iterate through the matrix
            for (int i = 0; i < newMatrix.length; i++) {
                for (int k = 0; k <= newMatrix.length + 1; k++) {

                    // if the value is null, then increment the null counter
                    if (newMatrix[i][k] == null) {
                        counter++;
                    }

                    // otherwise, try to parse int
                    else {
                        // if unable to parse int, then add it to the verts as a vertex
                        try {
                            Integer.parseInt(newMatrix[i][k]);
                        } catch (NumberFormatException n) {
                            verts += newMatrix[i][k] + ", ";
                        }

                    }

                }

            }

            // assert that nulls and vertices equal their respective values
            assertEquals(6, counter, "testGrid() failed to return the current number of nulls");
            assertEquals(expected, verts, "testGrid() failed to return all vertices");

        } catch (Exception e) {
            fail("testGrid() threw an unhandled exception");
        }

    }

}
