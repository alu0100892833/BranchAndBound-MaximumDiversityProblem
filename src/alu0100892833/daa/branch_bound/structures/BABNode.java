package alu0100892833.daa.branch_bound.structures;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows to represent nodes from a tree.
 */
public class BABNode {

    private MaximumDiversitySet set;
    private BABNode father;
    private List<BABNode> links;
    private int level;

    /**
     * Constructor for a node with no father.
     * @param set
     */
    public BABNode(MaximumDiversitySet set) {
        this.set = set;
        this.father = null;
        this.links = new ArrayList<>();
        this.level = 0;
    }

    /**
     * Constructor for a node with a father.
     * @param set The MaximumDiversitySet for the node.
     * @param father The node father.
     */
    public BABNode(MaximumDiversitySet set, BABNode father) {
        this.set = set;
        this.father = father;
        this.links = new ArrayList<>();
        this.level = father.getLevel() + 1;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Returns all links to son nodes.
     * @return
     */
    public List<BABNode> getLinks() {
        return this.links;
    }

    /**
     * Returns a reference to its father.
     * @return
     */
    public BABNode getFather() {
        return this.father;
    }

    public MaximumDiversitySet getSet() {
        return set;
    }

    /**
     * Bounds the node, eliminating it with all its sons.
     */
    public void bound() {
        set = null;
        links.clear();
    }

    /**
     * Generates the sons of the node. These are all possible variations when adding one element to the solution.
     */
    public void generateSons() {
        for (int i = 0; i < set.getSolution().size(); i++) {
            if (!set.isInSolution(i)) {
                MaximumDiversitySet sonSet = new MaximumDiversitySet(set);
                sonSet.addToSolution(i);
                links.add(new BABNode(sonSet, this));
            }
        }
    }

    /**
     * Overloaded equals method.
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BABNode))
            return false;
        BABNode compare = (BABNode) obj;
        return (this.getSet().equals(compare.getSet()));
    }

    /**
     * Clears all saved memory for the node.
     */
    void clear() {
        set.reset();
        set = null;
        father = null;
        links.clear();
        links = null;
    }
}
