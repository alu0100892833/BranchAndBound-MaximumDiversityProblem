package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.util.ArrayList;

/**
 * This class allows to solve the maximum diversity problem using a Greedy destructive algorithm.
 * It has just one method that solves the problem.
 * @author Óscar Darias Plasencia
 * @since 09/05/2017
 */
public class GreedyDestructive {
    /**
     * Solves the problem using the Greedy Destructive Algorithm.
     */
    public void solve(MaximumDiversitySet problem, int solutionSize) {
        prepareInitialState(problem);
        ArrayList<Double> center = problem.generalGravityCenter();
        while (problem.solutionSize() > solutionSize) {
            problem.removeFromSolution(problem.getClosest(center));
            center = problem.solutionGravityCenter();
        }
    }

    /**
     * The initial state for this algorithm has all elements in the solution.
     * @param problem MaximumDiversitySet that represents the problem.
     */
    private void prepareInitialState(MaximumDiversitySet problem) {
        for (int i = 0; i < problem.getSet().size(); i++) {
            problem.getSolution().set(i, true);
        }
    }
}
