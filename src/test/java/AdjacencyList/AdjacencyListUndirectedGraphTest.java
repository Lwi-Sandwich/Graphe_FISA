package AdjacencyList;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedGraphTest {

    private AdjacencyListUndirectedValuedGraph graph;

    private int[][] matrix = {
        {0, 1, 0, 1},
        {1, 0, 1, 0},
        {0, 1, 0, 1},
        {1, 0, 1, 0}
    };

    @BeforeEach
    void setUp() {
        graph = new AdjacencyListUndirectedValuedGraph(matrix);
    }

    @Test
    void TestGetMatrix() {
        assertArrayEquals(this.matrix, graph.toAdjacencyMatrix());
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
    void TestIsEdge() {
        assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(1)));
    }

    @Test
    void TestIsNotEdge() {
        assertFalse(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
    }

    @Test
    void TestRemoveEdge() {
        graph.removeEdge(new UndirectedNode(0), new UndirectedNode(1));
        assertFalse(graph.isEdge(new UndirectedNode(0), new UndirectedNode(1)));
    }

    @Test
    void TestRemoveEdgeNotExist() {
        graph.removeEdge(new UndirectedNode(0), new UndirectedNode(2));
        assertFalse(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
    }

    @Test
    void TestAddEdge() {
        graph.addEdge(new UndirectedNode(0), new UndirectedNode(2));
        assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
    }

    @Test
    void TestAddEdgeAlreadyExist() {
        graph.addEdge(new UndirectedNode(0), new UndirectedNode(1));
        assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(1)));
    }

    @Test
    void TestAddEdgeWithCost() {
        graph.addEdge(new UndirectedNode(0), new UndirectedNode(2), 5);
        assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(2)));
        assertEquals(5, graph.toAdjacencyMatrix()[0][2]);
    }

    @Test
    void TestAddEdgeWithCostAlreadyExist() {
        graph.addEdge(new UndirectedNode(0), new UndirectedNode(1), 5);
        assertTrue(graph.isEdge(new UndirectedNode(0), new UndirectedNode(1)));
        assertEquals(5, graph.toAdjacencyMatrix()[0][1]);
    }
}
