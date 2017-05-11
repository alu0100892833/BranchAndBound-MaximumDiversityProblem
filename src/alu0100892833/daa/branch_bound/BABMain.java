package alu0100892833.daa.branch_bound;

import alu0100892833.daa.branch_bound.algorithms.BranchAndBound;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

/**
 *
 */
public class BABMain {
    public static void main(String[] args) {
        int minSolutionSize = 2;
        int maxSolutionSize = 5;
        MaximumDiversitySet problem = new MaximumDiversitySet(args[0]);

        for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
            BranchAndBound bab = new BranchAndBound();
            MaximumDiversitySet result = bab.solve(problem, i, BranchAndBound.GRASP, BranchAndBound.DEPTH_FIRST);
            result.print();
        }

        //BranchAndBound bab = new BranchAndBound();
        //MaximumDiversitySet result = bab.solve(problem, maxSolutionSize, BranchAndBound.GRASP, BranchAndBound.DEPTH_FIRST);
        //result.print();
    }
}
