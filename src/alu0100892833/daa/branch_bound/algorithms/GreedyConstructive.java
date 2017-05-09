package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;


/**
 * This class allows to solve the maximum diversity problem using a Greedy Constructive algorithm.
 * It has just one method that solves the problem.
 * @author Óscar Darias Plasencia
 * @since 04/05/2017
 */
public class GreedyConstructive {
    /**
     * Solves the Maximum Diversity Problem for the given set. Uses a greedy constructive algorithm.
     * @param problem MaximumDiversitySet for the problem.
     * @param solutionSize Number of vectors in the solution.
     */
    public void solve(MaximumDiversitySet problem, int solutionSize) {
        problem.reset();
        problem.addToSolution(problem.getFarthest(true, problem.getSolution()));
        while (problem.solutionSize() != solutionSize)
            problem.addToSolution(problem.getFarthest(false, problem.getSolution()));
    }
}
