package alu0100892833.daa.branch_bound;

import alu0100892833.daa.branch_bound.algorithms.Grasp;
import alu0100892833.daa.branch_bound.algorithms.GreedyConstructive;
import alu0100892833.daa.branch_bound.algorithms.GreedyDestructive;
import alu0100892833.daa.branch_bound.algorithms.LocalSearch;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

public class Main {

    public static void main(String[] args) {
        int minSolutionSize = 2;
        int maxSolutionSize = 5;
        MaximumDiversitySet problem = new MaximumDiversitySet(args[0]);

        for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
            long initialTime = System.nanoTime();
            System.out.println("==========================================");
            System.out.println("GREEDY CONSTRUCTIVE WITH M = " + i);
            GreedyConstructive greedyConstructiveSolution = new GreedyConstructive();
            greedyConstructiveSolution.solve(problem, i);
            problem.print();
            long finalTime = System.nanoTime();
            System.out.println("Necessary time: " + (finalTime - initialTime) + " nanoseconds");
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println("==========================================");
        System.out.println("==========================================");

        for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
            long initialTime = System.nanoTime();
            System.out.println("==========================================");
            System.out.println("GREEDY DESTRUCTIVE WITH M = " + i);
            GreedyDestructive greedyDestructive = new GreedyDestructive();
            greedyDestructive.solve(problem, i);
            problem.print();
            long finalTime = System.nanoTime();
            System.out.println("Necessary time: " + (finalTime - initialTime) + " nanoseconds");
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println("==========================================");
        System.out.println("==========================================");

        for (int i = minSolutionSize; i <= maxSolutionSize; i++) {
            long initialTime = System.nanoTime();
            System.out.println("==========================================");
            System.out.println("GRASP WITH M = " + i);
            Grasp graspSolution = new Grasp();
            graspSolution.solve(problem, i, 10);
            problem.print();
            long finalTime = System.nanoTime();
            System.out.println("Necessary time: " + (finalTime - initialTime) + " nanoseconds");
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println("==========================================");
        System.out.println("==========================================");
    }
}
