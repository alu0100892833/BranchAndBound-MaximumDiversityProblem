package alu0100892833.daa.branch_bound.structures;

/**
 * Class that represents a tree. Intended to use for the Maximum Diversity Problem, more specifically, with the Branch and Bound algorithm.
 */
public class BABTree {

    private BABNode origin;

    public BABTree(MaximumDiversitySet set) {
        origin = new BABNode(set);
    }
}
