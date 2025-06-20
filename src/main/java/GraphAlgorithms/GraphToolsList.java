package GraphAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.UndirectedNode;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	// A completer

	public static List<DirectedNode> bfs(AdjacencyListDirectedGraph graph) {
		List<DirectedNode> nodes = graph.getNodes();
		List<DirectedNode> result = new ArrayList<DirectedNode>();
		Queue<DirectedNode> fifo = new LinkedList<DirectedNode>();
		Set<DirectedNode> visited = new HashSet<>();

		fifo.add(nodes.get(0));
		visited.add(nodes.get(0));

		while (!fifo.isEmpty()) {
			DirectedNode node = fifo.poll();
			result.add(node);
			for (Arc arc : node.getArcSucc()) {
				DirectedNode succ = arc.getSecondNode();
				if (!visited.contains(succ)) {
					fifo.add(succ);
					visited.add(succ);
				}
			}
		}
		return result;
	}

	// DFS
	public static List<DirectedNode> explorerSommet(AdjacencyListDirectedGraph graph, DirectedNode node) {
		List<DirectedNode> result = new ArrayList<>();
		Set<DirectedNode> visited = new HashSet<>();
		Stack<DirectedNode> stack = new Stack<DirectedNode>();

		stack.push(node);
		visited.add(node);

		while (!stack.isEmpty()) {
			DirectedNode current = stack.pop();
			result.add(current);
			for (Arc arc : current.getArcSucc()) {
				DirectedNode succ = arc.getSecondNode();
				if (!visited.contains(succ)) {
					stack.push(succ);
					visited.add(succ);
				}
			}
		}
		return result;
	}

	public static List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph graph) {
		ArrayList<DirectedNode> nodes = new ArrayList<DirectedNode>(graph.getNodes());
		List<DirectedNode> result = new ArrayList<>();
		while (!nodes.isEmpty()) {
			DirectedNode node = nodes.remove(0);
			if (!result.contains(node)) {
				List<DirectedNode> subResult = explorerSommet(graph, node);
				subResult.stream().filter(n -> !result.contains(n)).forEach(result::add);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		System.out.println(al);
		System.out.println("BFS");
		System.out.println(bfs(al));
		System.out.println("DFS");
		System.out.println(explorerGraphe(al));

		// A completer
	}
}