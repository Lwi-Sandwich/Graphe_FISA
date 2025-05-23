package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListUndirectedGraph;

/**
 * This class represents the undirected graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbEdges;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix




	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public AdjacencyMatrixUndirectedGraph() {
		this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbEdges = 0;
	}

	public AdjacencyMatrixUndirectedGraph(int[][] mat) {
		this.nbNodes=mat.length;
		this.nbEdges = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = i; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.matrix[j][i] = mat[i][j];
				this.nbEdges += mat[i][j];
			}
		}
	}

	public AdjacencyMatrixUndirectedGraph(AdjacencyListUndirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbEdges = g.getNbEdges();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	/**
     * @return the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * @return the number of nodes in the graph (referred to as the order of the graph)
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
	 *
	 * @param v the vertex selected
	 * @return a list of vertices which are the neighbours of v
	 */
	public int[] getNeighbours(int v) {
		List<Integer> l = new ArrayList<>();
		for(int i = 0; i<matrix[v].length; i++){
			if(matrix[v][i]>0){
				l.add(i);
			}
		}
		return l.stream().mapToInt(i -> i).toArray();
	}

	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------

	/**
		* @return true if the edge is in the graph.
	*/
	public boolean isEdge(int x, int y) {
		return matrix[x][y] > 0;
	}

	/**
		* removes the edge (x,y) if there exists one between these nodes in the graph.
	 */
	public void removeEdge(int x, int y) {
		if (isEdge(x, y)) {
			this.nbEdges--;
		}
		matrix[x][y] = 0;
	}

	/**
		* adds the edge (x,y) if there is not already one.
	*/
	public void addEdge(int x, int y) {
		if (!isEdge(x, y)) {
			this.nbEdges++;
		}
		matrix[x][y] = 1;
	}


	/**
		* @return the adjacency matrix representation int[][] of the graph
	*/
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("\nAdjacency Matrix: \n");
		for (int[] ints : this.matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
}
