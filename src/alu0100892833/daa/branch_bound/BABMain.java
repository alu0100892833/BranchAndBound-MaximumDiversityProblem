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

        for (int file = 0; file < args.length; file++) {
            MaximumDiversitySet problem = new MaximumDiversitySet(args[file]);

            for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
                BranchAndBound bab = new BranchAndBound();
                MaximumDiversitySet result = bab.solve(problem, i, BranchAndBound.GRASP, BranchAndBound.DEPTH_FIRST, true);
                result.print();
            }
        }

        //BranchAndBound bab = new BranchAndBound();
        //MaximumDiversitySet result = bab.solve(problem, 5, BranchAndBound.GREEDY_DESTRUCTIVE, BranchAndBound.DEPTH_FIRST);
        //result.print();
    }
}
