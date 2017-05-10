package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.BABTree;
import alu0100892833.daa.branch_bound.structures.BABNode;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.util.HashSet;


/**
 *
 */
public class BranchAndBound {
    public static final int GREEDY_CONSTRUCTIVE = 0;
    public static final int GREEDY_DESTRUCTIVE = 1;
    public static final int DEPTH_FIRST = 3;
    public static final int SMALLEST_SUPERIOR_QUOTE = 4;
    public static final int BIGGEST_SUPERIOR_QUOTE = 5;
    public static final int GRASP = 2;

    private MaximumDiversitySet bestFound;
    private BABTree tree;
    private int exploreStrategy;
    private HashSet<BABNode> alreadyExpanded;


    public MaximumDiversitySet solve(MaximumDiversitySet problem, int solutionSize, int algorithm, int exploreStrategy) {
        this.exploreStrategy = exploreStrategy;
        alreadyExpanded = new HashSet<>();

        initialQuota(problem, solutionSize, algorithm);
        System.out.print("Initial quota : ");
        problem.print();
        bestFound = problem;

        MaximumDiversitySet startingSet = new MaximumDiversitySet(problem);
        for (int i = 0; i < startingSet.solutionSize(); i++) {
            startingSet.getSolution().set(i, false);
        }

        tree = new BABTree(startingSet);
        branchOut(tree.getOrigin(), solutionSize);
        return bestFound;
    }

    public void branchOut(BABNode set, int solutionSize) {
        alreadyExpanded.add(set);
        if (set.getSet().solutionSize() != solutionSize) {
            set.generateSons();
            for (BABNode son : set.getLinks()) {
                if (worthy(son))
                    branchOut(son, solutionSize);
            }
        } else {
            if (bestFound.diversity() < set.getSet().diversity())
                bestFound = set.getSet();
        }
    }

    public boolean worthy(BABNode node) {
        if (alreadyExpanded.contains(node))
            return false;
        return true;
    }

    /**
     * Creates an initial quota using the specified algorithm.
     * @param problem
     * @param solutionSize
     * @param algorithm
     */
    private void initialQuota(MaximumDiversitySet problem, int solutionSize, int algorithm) throws IllegalArgumentException {
        if (algorithm == GREEDY_CONSTRUCTIVE) {
            GreedyConstructive greedy = new GreedyConstructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm == GREEDY_DESTRUCTIVE) {
            GreedyDestructive greedy = new GreedyDestructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm == GRASP) {
            Grasp grasp = new Grasp();
            grasp.solve(problem, solutionSize);
        } else
            throw new IllegalArgumentException("Did not recognize algorithm.");
    }
}
