package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.BABTree;
import alu0100892833.daa.branch_bound.structures.BABNode;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.util.HashSet;

/**
 * This class has all necessary methods to solve a Maximum Diversity problem using a Branch and Bound algorithms.
 * It provides several options such as selecting how to generate the initial solution or how to explore the tree.
 * It uses BABNode and BABTree objects to represent its data structures.
 * @author Óscar Darias Plasencia
 * @since 10-9-2017
 */
public class BranchAndBound {
    public static final int GREEDY_CONSTRUCTIVE = 0;
    public static final int GREEDY_DESTRUCTIVE = 1;
    public static final int DEPTH_FIRST = 3;
    public static final int SMALLEST_SUPERIOR_QUOTE = 4;
    public static final int BIGGEST_VALUE = 5;
    public static final int GRASP = 2;
    private static final int GRASP_ITERATIONS = 10;

    private MaximumDiversitySet bestFound;
    private BABTree tree;
    private int exploreStrategy, solutionSize;
    private HashSet<BABNode> alreadyExpanded;

    /**
     * This method solves the problem using a Branch and Bound algorithm.
     * @param problem
     * @param solutionSize
     * @param algorithm
     * @param exploreStrategy
     * @return
     */
    public MaximumDiversitySet solve(MaximumDiversitySet problem, int solutionSize, int algorithm, int exploreStrategy) {
        clean();
        long initialTime = System.currentTimeMillis();
        this.exploreStrategy = exploreStrategy;
        this.solutionSize = solutionSize;
        alreadyExpanded = new HashSet<>();

        initialQuota(problem, algorithm);
        //System.out.println("INITIAL SOLUTION: ");
        //problem.print();
        bestFound = problem;

        MaximumDiversitySet startingSet = new MaximumDiversitySet(problem);
        for (int i = 0; i < startingSet.getSolution().size(); i++) {
            startingSet.getSolution().set(i, false);
        }
        tree = new BABTree(startingSet);
        branchOut(tree.getOrigin());
        long finalTime = System.currentTimeMillis();
        System.out.print("Necessary time: " + (finalTime - initialTime) + " milliseconds");
        return new MaximumDiversitySet(bestFound);
    }

    /**
     * Branches out the given node, and explores its sons, grand-sons...
     * @param set
     */
    public void branchOut(BABNode set) {
        alreadyExpanded.add(set);
        if (set.getSet().solutionSize() != solutionSize) {
            set.generateSons();
            if (exploreStrategy == DEPTH_FIRST)
                exploreDepthFirst(set);
            else if (exploreStrategy == SMALLEST_SUPERIOR_QUOTE)
                exploreUsingSmallerSuperiorQuote(set);
            else if (exploreStrategy == BIGGEST_VALUE)
                exploreByHighestValue(set);

        } else {
            if (bestFound.diversity() < set.getSet().diversity())
                bestFound = set.getSet();
        }
    }

    /**
     * Check if a node is worth branching. If it has already been visited, it is not worthy.
     * @param node
     * @return True or false, if it is, or not, worth exploring.
     */
    public boolean worthy(BABNode node) {
        if (alreadyExpanded.contains(node))
            return false;
        else if (node.getFather() == null)
            return true;

        int adding = -1;
        for (int i = 0; i < node.getSet().getSolution().size(); i++) {
            if ((node.getSet().getSolution().get(i))
                && (!node.getFather().getSet().getSolution().get(i))) {
                adding = i;
                break;
            }
        }

        for (int i = 0; i < node.getFather().getSet().getSolution().size(); i++) {
            if (!node.getSet().getSolution().get(i)) {
                if (node.getSet().minDistance(i) > node.getSet().maxDistance(adding))
                    return false;
            }
        }

        return true;
    }

    /**
     * Continue exploring a node with depth first method.
     * @param currentNode
     */
    private void exploreDepthFirst(BABNode currentNode) {
        for (BABNode son : currentNode.getLinks()) {
            if (worthy(son))
                branchOut(son);
        }
    }

    /**
     * Continue exploring using the smaller superior quote as preferred.
     * @param currentNode
     */
    private void exploreUsingSmallerSuperiorQuote(BABNode currentNode) {

    }

    /**
     * Continue exploring using the node with the highest diversity as preferred.
     * @param currentNode
     */
    private void exploreByHighestValue(BABNode currentNode) {
        HashSet<Integer> branched = new HashSet<>();
        while (branched.size() != currentNode.getLinks().size()) {
            double highestValue = Double.NEGATIVE_INFINITY;
            int index = -1;
            for (int i = 0; i < currentNode.getLinks().size(); i++) {
                if ((!branched.contains(i)) && (currentNode.getLinks().get(i).getSet().diversity() > highestValue)) {
                    highestValue = currentNode.getLinks().get(i).getSet().diversity();
                    index = i;
                }
            }
            if (index != -1) {
                if (worthy(currentNode.getLinks().get(index)))
                    branchOut(currentNode.getLinks().get(index));
                branched.add(index);
            }
        }
    }

    /**
     * Creates an initial quota using the specified algorithm.
     * @param problem
     * @param algorithm
     */
    private void initialQuota(MaximumDiversitySet problem, int algorithm) throws IllegalArgumentException {
        if (algorithm == GREEDY_CONSTRUCTIVE) {
            GreedyConstructive greedy = new GreedyConstructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm == GREEDY_DESTRUCTIVE) {
            GreedyDestructive greedy = new GreedyDestructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm == GRASP) {
            Grasp grasp = new Grasp();
            grasp.solve(problem, solutionSize, GRASP_ITERATIONS);
        } else
            throw new IllegalArgumentException("Did not recognize algorithm.");
    }

    /**
     * Cleans everything so we can start again or clean memory.
     */
    public void clean() {
        if (bestFound != null)
            bestFound.reset();
        bestFound = null;
        if (tree != null)
            tree.clear();
        tree = null;
        if (alreadyExpanded != null)
            alreadyExpanded.clear();
        alreadyExpanded = null;
    }
}
