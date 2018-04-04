package dd.ch14;

import java.util.function.Consumer;

public class P2PersistDataStruct {
    public static void main(String[] args) {
        takeTrain();
        climbTree();
    }

    private static void climbTree() {
        Tree tree = new Tree("Mary", 22,
                new Tree("Emmy", 20, new Tree("Alan", 50, null, null), new Tree("Jim", 23, null, null)),
                new Tree("Tian", 29, new Tree("Raoul", 23, null, null), null));

        System.out.println("Raoul: " + lookup("Raoul", tree));
    }

    public static int lookup(String key, Tree tree) {
        if (tree == null)
            return -1;
        if (key.equals(tree.key))
            return tree.val;
        return lookup(key, key.compareTo(tree.key) < 0 ? tree.left : tree.right);
    }
    
    private static void takeTrain() {
        Consumer<TrainJourney> prinTrip = tj -> {
            System.out.print("€ " + tj.price + " ");
        };

        TrainJourney tj1 = new TrainJourney(40, new TrainJourney(30, null));
        TrainJourney tj2 = new TrainJourney(20, new TrainJourney(50, null));
        visit(tj1, prinTrip);
        System.out.println();
        visit(tj2, prinTrip);
        System.out.println();

        TrainJourney tj12 = append(tj1, tj2);
        visit(tj12, prinTrip);
        System.out.println();
    }

    static TrainJourney append(TrainJourney cur, TrainJourney next) {
        return cur == null ? next : new TrainJourney(cur.price, append(cur.onward, next));
    }

    static void visit(TrainJourney journey, Consumer<TrainJourney> c) {
        if (journey != null) {
            c.accept(journey);
            visit(journey.onward, c);
        }
    }
}

class TrainJourney {
    public int price;
    public TrainJourney onward;

    public TrainJourney(int price, TrainJourney onward) {
        this.price = price;
        this.onward = onward;
    }

    @Override
    public String toString() {
        return "€ " + price;
    }
}

class Tree {
    String key;
    int val;
    Tree left, right;

    public Tree(String k, int v, Tree l, Tree r) {
        key = k;
        val = v;
        left = l;
        right = r;
    }
}