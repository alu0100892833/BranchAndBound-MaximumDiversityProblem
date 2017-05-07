package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.util.ArrayList;

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
     * @return The ArrayList of Boolean that details the solution.
     */
    public ArrayList<Boolean> solve(MaximumDiversitySet problem) {
        problem.addToSolution(problem.getFarthest(true));
        while (/* TODO STOPPING CONDITION */)
            problem.addToSolution(problem.getFarthest(false));

        return problem.getSolution();
    }
}
