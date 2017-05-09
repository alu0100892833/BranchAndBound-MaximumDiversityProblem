package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;



/**
 *
 */
public class BranchAndBound {
    public static final int GREEDY_CONSTRUCTIVE = 0;
    public static final int GREEDY_DESTRUCTIVE = 1;
    public static final int GRASP = 2;

    public void solve(MaximumDiversitySet problem, int solutionSize, int algorithm) {
        initialQuota(problem, solutionSize, algorithm);
        System.out.print("Initial quota : ");
        problem.print();


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
