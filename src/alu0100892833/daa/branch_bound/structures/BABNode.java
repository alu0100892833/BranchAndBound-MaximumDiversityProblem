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

    public BABNode(MaximumDiversitySet set) {
        this.set = set;
        this.father = null;
        this.links = new ArrayList<>();
    }

    public BABNode(MaximumDiversitySet set, BABNode father) {
        this.set = set;
        this.father = father;
        this.links = new ArrayList<>();
    }

    public void addLink(BABNode son) {
        links.add(son);
    }

    public List<BABNode> getLinks() {
        return this.links;
    }

    public BABNode getFather() {
        return this.father;
    }

    public MaximumDiversitySet getSet() {
        return set;
    }

    public void bound() {
        set = null;
        links.clear();
    }

    public void generateSons() {
        for (int i = 0; i < set.getSolution().size(); i++) {
            if (!set.isInSolution(i)) {
                MaximumDiversitySet sonSet = new MaximumDiversitySet(set);
                sonSet.addToSolution(i);
                links.add(new BABNode(sonSet, this));
            }
        }
    }
}
