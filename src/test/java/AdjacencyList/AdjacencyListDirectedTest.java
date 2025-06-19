package AdjacencyList;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import Nodes_Edges.DirectedNode;

import org.junit.jupiter.api.BeforeEach;

public class AdjacencyListDirectedTest {

    private AdjacencyListDirectedValuedGraph graph;
    private int[][] matrix = {
        {0, 1, 0, 1},
        {0, 0, 1, 0},
        {0, 0, 0, 1},
        {0, 0, 0, 0}
    };

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListDirectedValuedGraph(matrix);
    }

    @Test
    void TestGetMatrix() {
        assertArrayEquals(this.matrix, graph.toAdjacencyMatrix());
    }

    @Test
    void TestGetNbNodes() {
        assertEquals(4, graph.getNbNodes());
    }
    @Test
    void TestGetNbArcs() {
        assertEquals(4, graph.getNbArcs());
    }

    @Test
    void TestIsArc() {
        assertTrue(graph.isArc(new DirectedNode(0), new DirectedNode(1)));
    }

    @Test
    void TestIsNotArc() {
        assertFalse(graph.isArc(new DirectedNode(0), new DirectedNode(2)));
    }

    @Test
    void TestRemoveArc() {
        graph.removeArc(new DirectedNode(0), new DirectedNode(1));
        assertFalse(graph.isArc(new DirectedNode(0), new DirectedNode(1)));
    }

    @Test
    void TestRemoveArcNotExist() {
        graph.removeArc(new DirectedNode(0), new DirectedNode(2));
        assertFalse(graph.isArc(new DirectedNode(0), new DirectedNode(2)));
    }

    @Test
    void TestAddEdge() {
        DirectedNode from = new DirectedNode(0);
        DirectedNode to = new DirectedNode(2);
        graph.addArc(from, to);
        assertTrue(graph.isArc(from, to));
    }

    @Test
    void TestAddEdgeAlreadyExists() {
        DirectedNode from = new DirectedNode(0);
        DirectedNode to = new DirectedNode(1);
        graph.addArc(from, to);
        assertTrue(graph.isArc(from, to));
    }

    @Test
    void TestAddEdgeWithCost() {
        DirectedNode from = new DirectedNode(0);
        DirectedNode to = new DirectedNode(2);
        graph.addArc(from, to, 5);
        assertTrue(graph.isArc(from, to));
        assertEquals(5, graph.toAdjacencyMatrix()[0][2]);
    }

    @Test
    void TestAddEdgeWithCostAlreadyExists() {
        DirectedNode from = new DirectedNode(0);
        DirectedNode to = new DirectedNode(1);
        graph.addArc(from, to, 5);
        assertTrue(graph.isArc(from, to));
        assertEquals(5, graph.toAdjacencyMatrix()[0][1]);
    }

    @Test
    void TestComputeInverse() {
        AdjacencyListDirectedGraph inverseGraph = graph.computeInverse();
        int[][] expectedInverseMatrix = {
            {0, 0, 0, 0},
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {1, 0, 1, 0}
        };
        assertArrayEquals(expectedInverseMatrix, inverseGraph.toAdjacencyMatrix());
    }
}

