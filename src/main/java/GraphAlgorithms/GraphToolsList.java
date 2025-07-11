package GraphAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Pair;
import Collection.Triple;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;
import Nodes_Edges.UndirectedNode;

public class GraphToolsList extends GraphTools {

	private static int _DEBBUG = 0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt = 0;

	// --------------------------------------------------
	// Constructors
	// --------------------------------------------------

	public GraphToolsList() {
		super();
	}

	// ------------------------------------------
	// Accessors
	// ------------------------------------------

	// ------------------------------------------
	// Methods
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

	public static void explorerSommet(AdjacencyListDirectedGraph graph, DirectedNode node,
			int[] visite, int[] debut, int[] fin, List<DirectedNode> ordreFin) {
		visite[node.getLabel()] = 1;
		debut[node.getLabel()] = cpt++;

		for (Arc arc : node.getArcSucc()) {
			DirectedNode succ = arc.getSecondNode();
			if (visite[succ.getLabel()] == 0) {
				explorerSommet(graph, succ, visite, debut, fin, ordreFin);
			}
		}

		// Racine à la fin, parcours postfixe
		visite[node.getLabel()] = 2; // totalement visité
		fin[node.getLabel()] = cpt++;
		ordreFin.add(node);
	}

	// Complexité : O(V + E)
	public static List<DirectedNode> explorerGraphe(AdjacencyListDirectedGraph graph) {
		cpt = 0; // Réinitialiser le compteur pour les timestamps
		int n = graph.getNbNodes();
		int[] visite = new int[n];
		int[] debut = new int[n];
		int[] fin = new int[n];
		// Si x est exploré et que y est son successeur direct ou indirect,
		// alors debut[x] < debut[y] et fin[x] > fin[y]
		List<DirectedNode> ordreFin = new ArrayList<>();

		for (DirectedNode node : graph.getNodes()) {
			if (visite[node.getLabel()] == 0) {
				explorerSommet(graph, node, visite, debut, fin, ordreFin);
			}
		}

		// Affichage ou traitement des timestamps si besoin
		for (DirectedNode node : graph.getNodes()) {
			System.out.println("Node " + node.getLabel() +
					": début=" + debut[node.getLabel()] +
					", fin=" + fin[node.getLabel()]);
		}

		return ordreFin; // ordre de fin utile pour la 2ème passe de Kosaraju
	}

	public static List<List<DirectedNode>> explorerGrapheBis(AdjacencyListDirectedGraph inverseGraph,
			List<DirectedNode> ordreFin) {
		int n = inverseGraph.getNbNodes();
		boolean[] visite = new boolean[n];
		List<List<DirectedNode>> composantes = new ArrayList<>();

		// Ordre inverse de fin : on commence par le dernier terminé
		List<DirectedNode> ordreInverse = new ArrayList<>(ordreFin);
		Collections.reverse(ordreInverse);

		for (DirectedNode node : ordreInverse) {
			if (!visite[node.getLabel()]) {
				List<DirectedNode> composante = new ArrayList<>();
				explorerSommetBis(inverseGraph, node, visite, composante);
				composantes.add(composante);
			}
		}
		return composantes;
	}

	public static void explorerSommetBis(AdjacencyListDirectedGraph inverseGraph, DirectedNode node,
			boolean[] visite, List<DirectedNode> composante) {
		visite[node.getLabel()] = true;
		composante.add(node);
		for (Arc arc : node.getArcSucc()) {
			DirectedNode succ = arc.getSecondNode();
			if (!visite[succ.getLabel()]) {
				explorerSommetBis(inverseGraph, succ, visite, composante);
			}
		}
	}

	public static Pair<Integer[], DirectedNode[]> dijkstra(AdjacencyListDirectedValuedGraph graph,
			DirectedNode source) {
		int n = graph.getNbNodes();
		Integer[] distances = new Integer[n];
		DirectedNode[] predecessors = new DirectedNode[n];
		ArrayList<Boolean> visited = new ArrayList<>(Collections.nCopies(n, false));
		for (int i = 0; i < n; i++) {
			distances[i] = Integer.MAX_VALUE;
			predecessors[i] = null;
		}
		distances[source.getLabel()] = 0;
		predecessors[source.getLabel()] = source;
		while (visited.contains(false)) {
			DirectedNode node = null;
			// Trouver le noeud non visité avec la distance minimale
			int minDistance = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (!visited.get(i) && (distances[i] < minDistance)) {
					minDistance = distances[i];
					node = graph.getNodes().get(i);
				}
			}
			if (minDistance == Integer.MAX_VALUE) {
				break; // Tous les noeuds accessibles ont été visités
			}
			// Mise à jour des successeurs
			visited.set(node.getLabel(), true);
			for (Arc arc : node.getArcSucc()) {
				DirectedNode succ = arc.getSecondNode();
				int newDistance = distances[node.getLabel()] + arc.getWeight();
				if (newDistance < distances[succ.getLabel()]) {
					distances[succ.getLabel()] = newDistance;
					predecessors[succ.getLabel()] = node;
				}
			}
		}
		return new Pair<>(distances, predecessors);
	}

	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(Matrix);
		Random rand = new Random(42);
		for (Arc arc : al.getArcs()) {
			arc.setWeight(rand.nextInt(10) + 1); // Assign random weights between 1 and 10
		}
		System.out.println(al);
		System.out.println("BFS");
		System.out.println(bfs(al));
		System.out.println("DFS");
		List<DirectedNode> ordreFin = explorerGraphe(al);
		System.out.println("DFS ordre de fin : " + ordreFin);
		AdjacencyListDirectedGraph inverseGraph = al.computeInverse();
		// Remplacer ordreFin par les noeuds du graph inverse avec les bons labels
		List<DirectedNode> ordreFinInverse = new ArrayList<>();
		for (DirectedNode node : ordreFin) {
			ordreFinInverse.add(inverseGraph.getNodeOfList(node));
		}
		System.out.println("DFS sur le graphe inverse");
		List<List<DirectedNode>> cfcs = explorerGrapheBis(inverseGraph, ordreFinInverse); // Affiche les CFCs
		System.out.println("CFCs : " + cfcs);
		System.out.println("Dijkstra depuis le noeud 0");
		DirectedNode source = al.getNodes().get(0);
		Pair<Integer[], DirectedNode[]> result = dijkstra(al, source);
		Integer[] distances = result.getLeft();
		DirectedNode[] predecessors = result.getRight();
		System.out.println("Noeud\tDistance\tPredecesseur");
		for (int i = 0; i < distances.length; i++) {
			System.out.println(
					i + "\t" + distances[i] + "\t\t" + (predecessors[i] != null ? predecessors[i].getLabel() : "null"));
		}
	}
}