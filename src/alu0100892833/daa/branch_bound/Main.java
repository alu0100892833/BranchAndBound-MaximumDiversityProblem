package alu0100892833.daa.branch_bound;

import alu0100892833.daa.branch_bound.algorithms.GreedyConstructive;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

public class Main {

    public static void main(String[] args) {
        MaximumDiversitySet problem = new MaximumDiversitySet(args[0]);
        GreedyConstructive greedyConstructiveSolution = new GreedyConstructive();
        MaximumDiversitySet solvedGreedy = greedyConstructiveSolution.solve(problem, 3);
        solvedGreedy.print();
    }
}
