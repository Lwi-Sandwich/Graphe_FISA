package GraphAlgorithms;

public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    // Complexité O(log n)
    public void insert(int element) {
        if (pos >= nodes.length) {
            resize();
        }
        nodes[pos] = element;
        int current = pos;
        pos++;
        int parent = (current - 1) / 2;
        while (current > 0 && nodes[current] < nodes[parent]) {
            swap(current, parent);
            current = parent;
            parent = (current - 1) / 2;
        }
    }

    // Complexité O(log n)
    public int remove() {
        if (isEmpty()) {
            return Integer.MAX_VALUE;
        }
        int min = nodes[0];
        nodes[0] = nodes[pos - 1];
        nodes[pos - 1] = Integer.MAX_VALUE;
        pos--;
        int current = 0;
        // Put the new root in the right place
        while (current < pos) {
            int bestChild = getBestChildPos(current);
            if (bestChild == Integer.MAX_VALUE || nodes[current] <= nodes[bestChild]) {
                break; // the current node is smaller than its best child
            }
            swap(current, bestChild);
            current = bestChild;
        }
        return min;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            int left = 2 * src + 1;
            int right = 2 * src + 2;
            if (right >= pos) {
                return left; // only one child
            } else {
                return nodes[left] < nodes[right] ? left : right; // return the index of the smallest child
            }
        }
    }

    /**
     * Test if the node is a leaf in the binary heap
     *
     * @returns true if it's a leaf or false else
     *
     */
    private boolean isLeaf(int src) {
        return 2 * src + 1 >= pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
     * Recursive test to check the validity of the binary heap
     *
     * @returns a boolean equal to True if the binary tree is compact from left to
     *          right
     *
     */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty() + "\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.print("insert " + rand);
            jarjarBin.insert(rand);
            k--;
        }
        // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
        // Suppression des éléments
        while (!jarjarBin.isEmpty()) {
            System.out.print("remove " + jarjarBin.remove() + " ");
            System.out.println(jarjarBin);
            System.out.println(jarjarBin.test());
        }
    }

}
