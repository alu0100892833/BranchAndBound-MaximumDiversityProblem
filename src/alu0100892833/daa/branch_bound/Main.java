package alu0100892833.daa.branch_bound;

import alu0100892833.daa.branch_bound.algorithms.Grasp;
import alu0100892833.daa.branch_bound.algorithms.GreedyConstructive;
import alu0100892833.daa.branch_bound.algorithms.GreedyDestructive;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

public class Main {

    public static void main(String[] args) {
        int solutionSize = 3;
        MaximumDiversitySet problem = new MaximumDiversitySet(args[0]);

        GreedyConstructive greedyConstructiveSolution = new GreedyConstructive();
        greedyConstructiveSolution.solve(problem, solutionSize);
        problem.print();

        GreedyDestructive greedyDestructive = new GreedyDestructive();
        greedyDestructive.solve(problem, solutionSize);
        problem.print();

        Grasp graspSolution = new Grasp();
        graspSolution.solve(problem, solutionSize);
        problem.print();
    }
}
