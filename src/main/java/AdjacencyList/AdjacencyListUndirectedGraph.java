package AdjacencyList;

import java.util.ArrayList;
import java.util.List;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedGraph {

    // --------------------------------------------------
    // Class variables
    // --------------------------------------------------

    protected List<UndirectedNode> nodes; // list of the nodes in the graph
    protected List<Edge> edges; // list of the edges in the graph
    protected int nbNodes; // number of nodes
    protected int nbEdges; // number of edges

    // --------------------------------------------------
    // Constructors
    // --------------------------------------------------

    public AdjacencyListUndirectedGraph() {
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();
        this.nbNodes = 0;
        this.nbEdges = 0;
    }

    public AdjacencyListUndirectedGraph(List<UndirectedNode> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.nbNodes = nodes.size();
        this.nbEdges = edges.size();

    }

    public AdjacencyListUndirectedGraph(int[][] matrix) {
        this.nbNodes = matrix.length;
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();

        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrix[n1.getLabel()].length; j++) {
                UndirectedNode n2 = this.getNodes().get(j);
                if (matrix[n1.getLabel()][j] != 0) {
                    Edge e1 = new Edge(n1, n2);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                    n2.addEdge(e1);
                    this.nbEdges++;
                }
            }
        }
    }

    public AdjacencyListUndirectedGraph(AdjacencyListUndirectedGraph g) {
        super();
        this.nbNodes = g.getNbNodes();
        this.nbEdges = g.getNbEdges();
        this.nodes = new ArrayList<UndirectedNode>();
        this.edges = new ArrayList<Edge>();

        for (UndirectedNode n : g.getNodes()) {
            this.nodes.add(new UndirectedNode(n.getLabel()));
        }

        for (Edge e : g.getEdges()) {
            this.edges.add(e);
            UndirectedNode new_n = this.getNodes().get(e.getFirstNode().getLabel());
            UndirectedNode other_n = this.getNodes().get(e.getSecondNode().getLabel());
            new_n.addEdge(new Edge(e.getFirstNode(), e.getSecondNode(), e.getWeight()));
            other_n.addEdge(new Edge(e.getSecondNode(), e.getFirstNode(), e.getWeight()));
        }
    }

    // ------------------------------------------
    // Accessors
    // ------------------------------------------

    /**
     * Returns the list of nodes in the graph
     */
    public List<UndirectedNode> getNodes() {
        return this.nodes;
    }

    /**
     * Returns the list of edges in the graph
     */
    public List<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Returns the number of nodes in the graph
     */
    public int getNbNodes() {
        return this.nbNodes;
    }

    /**
     * @return the number of edges in the graph
     */
    public int getNbEdges() {
        return this.nbEdges;
    }

    /**
     * @return true if there is an edge between x and y
     */
    public boolean isEdge(UndirectedNode x, UndirectedNode y) {
        return this.edges.contains(new Edge(x, y)) || this.edges.contains(new Edge(y, x));
    }

    /**
     * Removes edge (x,y) if there exists one. And remove this edge and the inverse
     * in the list of edges from the two extremities (nodes)
     */
    public void removeEdge(UndirectedNode x, UndirectedNode y) {
        Edge e1 = new Edge(x, y);
        Edge e2 = new Edge(y, x);
        if (this.edges.remove(e1) || this.edges.remove(e2)) {
            this.nbEdges--;
        }
        x.getIncidentEdges().removeIf(e -> e.getSecondNode().equals(y));
        y.getIncidentEdges().removeIf(e -> e.getSecondNode().equals(x));

    }

    /**
     * Adds edge (x,y) if it is not already present in the graph, requires that
     * nodes x and y already exist.
     * And adds this edge to the incident list of both extremities (nodes) and into
     * the global list "edges" of the graph.
     * In non-valued graph, every edge has a cost equal to 0.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y) {
        addEdge(x, y, 0);
    }

    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        Edge e1 = new Edge(x, y, cost);
        if (!isEdge(x, y)) {
            this.edges.add(e1);
            this.getNodeOfList(x).addEdge(e1);
            this.getNodeOfList(y).addEdge(e1);
            this.nbEdges++;
        }
        // Replace the cost if already exists
        else {
            for (Edge e : this.getNodeOfList(x).getIncidentEdges()) {
                if (e.getSecondNode().equals(y) || e.getFirstNode().equals(y)) {
                    e.setWeight(cost);
                }
            }
            for (Edge e : this.getNodeOfList(y).getIncidentEdges()) {
                if (e.getSecondNode().equals(x) || e.getFirstNode().equals(x)) {
                    e.setWeight(cost);
                }
            }
        }
    }

    // --------------------------------------------------
    // Methods
    // --------------------------------------------------

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public UndirectedNode getNodeOfList(UndirectedNode v) {
        return this.getNodes().get(v.getLabel());
    }

    /**
     * @return a matrix representation of the graph
     */
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[nbNodes][nbNodes];
        for (int i = 0; i < edges.size(); i++) {
            Edge e = edges.get(i);
            UndirectedNode n1 = e.getFirstNode();
            UndirectedNode n2 = e.getSecondNode();
            int i1 = n1.getLabel();
            int i2 = n2.getLabel();
            matrix[i1][i2] = e.getWeight() == 0 ? 1 : e.getWeight();
            matrix[i2][i1] = e.getWeight() == 0 ? 1 : e.getWeight();
        }
        return matrix;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("List of nodes and their neighbours :\n");
        for (UndirectedNode n : this.nodes) {
            s.append("Node ").append(n).append(" : ");
            s.append("\nList of incident edges : ");
            for (Edge e : n.getIncidentEdges()) {
                s.append(e).append("  ");
            }
            s.append("\n");
        }
        s.append("\nList of edges :\n");
        for (Edge e : this.edges) {
            s.append(e).append("  ");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] mat = GraphTools.generateGraphData(10, 20, false, true, false, 100001);
        GraphTools.afficherMatrix(mat);
        AdjacencyListUndirectedGraph al = new AdjacencyListUndirectedGraph(mat);
        System.out.println(al);
        System.out.println("(n_2,n_5) is it in the graph ? " + al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));

        // A completer
    }

}
