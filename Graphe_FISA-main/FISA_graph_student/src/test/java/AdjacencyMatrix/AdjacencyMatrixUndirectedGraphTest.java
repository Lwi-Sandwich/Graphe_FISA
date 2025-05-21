package AdjacencyMatrix;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixUndirectedGraphTest {
    private AdjacencyMatrixUndirectedValuedGraph graph;
    private int[][] matrix = {
        {0, 1, 0, 1},
        {1, 0, 1, 0},
        {0, 1, 0, 1},
        {1, 0, 1, 0}
    };

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixUndirectedValuedGraph(matrix);
    }

    @Test
    void TestGetMatrix() {
        assertArrayEquals(this.matrix, graph.getMatrix());
    }

    @Test
    void TestGetNbNodes() {
        assertArrayEquals(new int[] { 4 }, new int[] { graph.getNbNodes() });
    }

    @Test
    void TestGetNbEdges() {
        assertArrayEquals(new int[] { 4 }, new int[] { graph.getNbEdges() });
    }

    @Test
    void TestGetNeighbours() {
        int[] expected = { 1, 3 };
        assertArrayEquals(expected, graph.getNeighbours(0));
    }

    @Test
    void TestIsEdge() {
        assertTrue(graph.isEdge(0, 1));
    }

    @Test
    void TestIsNotEdge() {
        assertFalse(graph.isEdge(0, 2));
    }

    @Test
    void TestRemoveEdge() {
        graph.removeEdge(0, 1);
        assertFalse(graph.isEdge(0, 1));
    }

    @Test
    void TestRemoveEdgeNotExist() {
        graph.removeEdge(0, 2);
        assertFalse(graph.isEdge(0, 2));
    }

    @Test
    void TestAddEdge() {
        graph.addEdge(0, 2);
        assertTrue(graph.isEdge(0, 2));
    }

    @Test
    void TestAddEdgeAlreadyExist() {
        graph.addEdge(0, 1);
        assertTrue(graph.isEdge(0, 1));
    }

    @Test
    void TestAddEdgeWithCost() {
        graph.addEdge(0, 2, 5);
        assertTrue(graph.isEdge(0, 2));
        assertEquals(5, graph.getMatrix()[0][2]);
    }

    @Test
    void TestAddEdgeWithCostAlreadyExist() {
        graph.addEdge(0, 1, 5);
        assertTrue(graph.isEdge(0, 1));
        assertEquals(5, graph.getMatrix()[0][1]);
    }
}