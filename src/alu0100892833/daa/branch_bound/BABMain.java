package alu0100892833.daa.branch_bound;

import alu0100892833.daa.branch_bound.algorithms.BranchAndBound;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

/**
 * Main program for trying the Branch and Bound algorithm with the Maximum Diversity Problem.
 */
public class BABMain {
    public static void main(String[] args) {
        int minSolutionSize = 2;
        int maxSolutionSize = 5;

        for (String file : args) {
            MaximumDiversitySet problem = new MaximumDiversitySet(file);
            for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
                BranchAndBound bab = new BranchAndBound();
                MaximumDiversitySet result = bab.solve(problem, i, BranchAndBound.GREEDY_CONSTRUCTIVE,
                        BranchAndBound.SMALLEST_SUPERIOR_QUOTE, true);
                result.print();
            }
        }
    }
}
