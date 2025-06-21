package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import AdjacencyList.AdjacencyListUndirectedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 *
	 */
	private List<Edge> binh;

	public BinaryHeapEdge() {
		this.binh = new ArrayList<Edge>();
	}

	public boolean isEmpty() {
		return binh.isEmpty();
	}

	/**
	 * Insert a new edge in the binary heap
	 *
	 * @param from one node of the edge
	 * @param to   one node of the edge
	 * @param val  the edge weight
	 */
	public void insert(UndirectedNode from, UndirectedNode to, int val) {
		Edge newEdge = new Edge(from, to, val);
		binh.add(newEdge);
		int current = binh.size() - 1; // index of the newly added edge
		int parent = (current - 1) / 2;
		while (current > 0 && binh.get(current).getWeight() < binh.get(parent).getWeight()) {
			swap(current, parent);
			current = parent;
			parent = (current - 1) / 2;
		}
	}

	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid
	 * binary heap
	 *
	 * @return the edge with the minimal value (root of the binary heap)
	 *
	 */
	public Edge remove() {
		if (isEmpty()) {
			return null; // no edge to remove
		}
		Edge minEdge = binh.get(0);
		int lastIndex = binh.size() - 1;
		binh.set(0, binh.get(lastIndex));
		binh.remove(lastIndex);
		int current = 0;

		while (current < binh.size()) {
			int bestChild = getBestChildPos(current);
			if (bestChild == Integer.MAX_VALUE || binh.get(current).getWeight() <= binh.get(bestChild).getWeight()) {
				break; // the current node is smaller than its best child
			}
			swap(current, bestChild);
			current = bestChild;
		}
		return minEdge;
	}

	/**
	 * From an edge indexed by src, find the child having the least weight and
	 * return it
	 *
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
	private int getBestChildPos(int src) {
		int lastIndex = binh.size() - 1;
		if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
			return Integer.MAX_VALUE;
		}
		int left = 2 * src + 1;
		int right = 2 * src + 2;
		if (right > lastIndex) {
			return left; // only one child
		}
		if (binh.get(left).getWeight() < binh.get(right).getWeight()) {
			return left; // return the index of the smallest child
		}
		return right;
	}

	private boolean isLeaf(int src) {
		return 2 * src + 1 >= binh.size();
	}

	/**
	 * Swap two edges in the binary heap
	 *
	 * @param father an index of the list edges
	 * @param child  an index of the list edges
	 */
	private void swap(int father, int child) {
		Edge fatherEdge = binh.get(father);
		Edge childEdge = binh.get(child);
		Edge temp = new Edge(fatherEdge.getFirstNode(), fatherEdge.getSecondNode(), fatherEdge.getWeight());

		fatherEdge.setFirstNode(childEdge.getFirstNode());
		fatherEdge.setSecondNode(childEdge.getSecondNode());
		fatherEdge.setWeight(childEdge.getWeight());

		childEdge.setFirstNode(temp.getFirstNode());
		childEdge.setSecondNode(temp.getSecondNode());
		childEdge.setWeight(temp.getWeight());
	}

	/**
	 * Create the string of the visualisation of a binary heap
	 *
	 * @return the string of the binary heap
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Edge no : binh) {
			s.append(no).append(", ");
		}
		return s.toString();
	}

	private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < x; i++) {
			res.append(" ");
		}
		return res.toString();
	}

	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 *
	 */
	public void lovelyPrinting() {
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1 + (int) (Math.log(this.binh.size()) / Math.log(2));
		int index = 0;

		for (int h = 1; h <= depth; h++) {
			int left = ((int) (Math.pow(2, depth - h - 1))) * nodeWidth - nodeWidth / 2;
			int between = ((int) (Math.pow(2, depth - h)) - 1) * nodeWidth;
			int i = 0;
			System.out.print(space(left));
			while (i < Math.pow(2, h - 1) && index < binh.size()) {
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}

	public static List<Edge> prim(AdjacencyListUndirectedGraph graph) {
		BinaryHeapEdge heap = new BinaryHeapEdge();
		List<UndirectedNode> nodes = graph.getNodes();
		List<Edge> mstEdges = new ArrayList<>();
		List<Boolean> inMST = new ArrayList<>(Collections.nCopies(nodes.size(), false));
		inMST.set(0, true);
		for (Edge edge : nodes.get(0).getIncidentEdges()) {
			heap.insert(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
		}
		while (inMST.contains(false) && !heap.isEmpty()) {
			Edge minEdge = heap.remove();
			if (inMST.get(minEdge.getFirstNode().getLabel()) &&
					inMST.get(minEdge.getSecondNode().getLabel())) {
				continue; // both nodes are already in the MST, this would create a cycle
			}
			UndirectedNode newNode = inMST.get(minEdge.getFirstNode().getLabel())
					? minEdge.getSecondNode()
					: minEdge.getFirstNode();
			inMST.set(newNode.getLabel(), true);
			mstEdges.add(minEdge);
			for (Edge edge : newNode.getIncidentEdges()) {
				if (!inMST.get(edge.getFirstNode().getLabel()) || !inMST.get(edge.getSecondNode().getLabel())) {
					// Add to heap all edges that do not create a cycle
					heap.insert(edge.getFirstNode(), edge.getSecondNode(), edge.getWeight());
				}
			}
		}
		return mstEdges;

	}

	// ------------------------------------
	// TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 *
	 * @return a boolean equal to True if the binary tree is compact from left to
	 *         right
	 *
	 */
	private boolean test() {
		return this.isEmpty() || testRec(0);
	}

	private boolean testRec(int root) {
		System.out.println("root= " + root);
		int lastIndex = binh.size() - 1;
		if (isLeaf(root)) {
			return true;
		} else {
			int left = 2 * root + 1;
			int right = 2 * root + 2;
			System.out.println("left = " + left);
			System.out.println("right = " + right);
			if (right >= lastIndex) {
				return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left);
			} else {
				return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left)
						&& binh.get(right).getWeight() >= binh.get(root).getWeight() && testRec(right);
			}
		}
	}

	public static void main(String[] args) {
		BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
		System.out.println(jarjarBin.isEmpty() + "\n");
		int k = 10;
		int m = k;
		int min = 2;
		int max = 20;
		while (k > 0) {
			int rand = min + (int) (Math.random() * ((max - min) + 1));
			jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k + 30), rand);
			k--;
		}
		// A completer
		System.out.println(jarjarBin);
		System.out.println(jarjarBin.test());
		// Supprimer les éléments un par un
		while (!jarjarBin.isEmpty()) {
			System.out.print("remove " + jarjarBin.remove() + " ");
			System.out.println(jarjarBin.test());
		}
		// Prim
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, true, true, 100001);

		GraphTools.afficherMatrix(Matrix);
		AdjacencyListUndirectedValuedGraph al = new AdjacencyListUndirectedValuedGraph(Matrix);
		Random rand = new Random(42);
		for (Edge edge : al.getEdges()) {
			int newWeight = rand.nextInt(10) + 1; // Assign random weights between 1 and 10
			edge.setWeight(newWeight);
			al.getNodes().get(edge.getFirstNode().getLabel()).addEdge(edge);
			al.getNodes().get(edge.getSecondNode().getLabel()).addEdge(edge);
			System.out.println("Edge: " + edge);
		}
		System.out.println(al);
		System.out.println("Prim's algorithm result:");
		System.out.println(prim(al));
	}

}
