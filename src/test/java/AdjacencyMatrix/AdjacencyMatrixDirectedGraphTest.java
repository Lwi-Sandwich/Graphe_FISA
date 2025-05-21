package AdjacencyMatrix;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixDirectedGraphTest {
    private AdjacencyMatrixDirectedValuedGraph graph;
    private int[][] matrix = {
        {0, 1, 0, 1},
        {0, 0, 1, 0},
        {0, 0, 0, 1},
        {0, 1, 0, 0}
    };

    @BeforeEach
    void setUp() {
        graph = new AdjacencyMatrixDirectedValuedGraph(matrix);
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
        assertArrayEquals(new int[] { 5 }, new int[] { graph.getNbArcs() });
    }

    @Test
    void TestGetPredecessors() {
        int[] expected = { 0, 2 };
        assertArrayEquals(expected, graph.getPredecessors(3));
    }

    @Test
    void TestGetSuccessors() {
        int[] expected = { 1, 3 };
        assertArrayEquals(expected, graph.getSuccessors(0));
    }

    @Test
    void TestIsArc() {
        assertTrue(graph.isArc(0, 1));
    }

    @Test
    void TestIsNotArc() {
        assertFalse(graph.isArc(0, 2));
    }

    @Test
    void TestAddArc() {
        graph.addArc(0, 2);
        assertTrue(graph.isArc(0, 2));
    }

    @Test
    void TestRemoveArc() {
        graph.removeArc(0, 1);
        assertFalse(graph.isArc(0, 1));
    }

    @Test
    void TestAddArcAlreadyExists() {
        graph.addArc(0, 1);
        assertTrue(graph.isArc(0, 1));
    }

    @Test
    void TestRemoveArcDoesNotExist() {
        graph.removeArc(0, 2);
        assertFalse(graph.isArc(0, 2));
    }

    @Test
    void TestComputeInverse() {
        AdjacencyMatrixDirectedGraph inverseGraph = graph.computeInverse();
        int[][] expectedInverseMatrix = {
            {0, 0, 0, 0},
            {1, 0, 0, 1},
            {0, 1, 0, 0},
            {1, 0, 1, 0}
        };
        assertArrayEquals(expectedInverseMatrix, inverseGraph.getMatrix());
    }

    @Test
    void TestValuedArc() {
        graph.addArc(0, 1, 5);
        assertTrue(graph.isArc(0, 1));
    }

    @Test
    void TestValuedMatrix() {
        int[][] expectedValuedMatrix = {
            {0, 5, 0, 1},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {0, 1, 0, 0}
        };
        graph.addArc(0, 1, 5);
        assertArrayEquals(expectedValuedMatrix, graph.getMatrix());
    }
}