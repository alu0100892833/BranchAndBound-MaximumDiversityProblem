package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class allows to solve the maximum diversity problem using a GRASP algorithm.
 * @author Óscar Darias Plasencia
 * @since 04/05/2017
 */
public class Grasp {
    private static final int DEFAULT_RCL_SIZE = 3;

    /**
     * Main method of this class; solves a Maximum Diversity Problem using a GRASP algorithm.
     * @param problem MaximumDiversitySet that represents the problem.
     * @param solutionSize The number of elements in the solution.
     */
    public void solve(MaximumDiversitySet problem, int solutionSize, int times) {
        problem.reset();
        MaximumDiversitySet tries = new MaximumDiversitySet(problem);
        for (int i = 0; i < times; i++) {
            tries.reset();
            tries.addToSolution(tries.getFarthest(true, tries.getSolution()));
            while (tries.solutionSize() != solutionSize) {
                ArrayList<Integer> rcl = generateRCL(tries,
                        new ArrayList<>(tries.getSolution()), DEFAULT_RCL_SIZE);
                Random selector = new Random();
                tries.addToSolution(rcl.get(selector.nextInt(rcl.size())));
            }
            LocalSearch improve = new LocalSearch();
            improve.perform(tries);
            if (tries.diversity() > problem.diversity())
                problem.setAs(tries);
        }
    }

    /**
     * Method that generates the RCL.
     * @param problem MaximumDiversitySet that represents the problem.
     * @param exceptions Boolean vector that specifies those elements that cannot be selected.
     * @param rclSize Desired size for the rcl.
     * @return ArrayList of Integers, the indexes of the selected candidates.
     */
    private ArrayList<Integer> generateRCL(MaximumDiversitySet problem, ArrayList<Boolean> exceptions, int rclSize) {
        ArrayList<Integer> rcl = new ArrayList<>();

        while (rcl.size() != rclSize) {
            int candidate = problem.getFarthest(false, exceptions);
            if (candidate == -1)
                break;
            exceptions.set(candidate, true);
            rcl.add(candidate);
        }
        return rcl;
    }
}
