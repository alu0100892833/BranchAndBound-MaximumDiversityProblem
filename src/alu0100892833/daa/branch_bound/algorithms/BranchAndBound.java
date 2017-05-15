package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.BABTree;
import alu0100892833.daa.branch_bound.structures.BABNode;
import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class has all necessary methods to solve a Maximum Diversity problem using a Branch and Bound algorithms.
 * It provides several options such as selecting how to generate the initial solution or how to explore the tree.
 * It uses BABNode and BABTree objects to represent its data structures.
 * @author Óscar Darias Plasencia
 * @since 10-9-2017
 */
public class BranchAndBound {
    public static final String GREEDY_CONSTRUCTIVE = "GreedyConstructive";
    public static final String GREEDY_DESTRUCTIVE = "GreedyDestructive";
    public static final String DEPTH_FIRST = "DEPTH-FIRST";
    public static final String SMALLEST_SUPERIOR_QUOTE = "SMALLEST-SUPERIOR-QUOTE";
    public static final String GRASP = "GRASP";
    private static final int GRASP_ITERATIONS = 10;
    private static final String OUTPUT_FILE = "BABResults";
    private static final String OUTPUT_FILE_EXTENSION = ".csv";
    private static final String FILE_HEADER = "Problema; n; K; m; z; S; Tiempo; Nodos generados;";

    private MaximumDiversitySet bestFound;
    private BABTree tree;
    private String exploreStrategy;
    private int solutionSize;
    private ArrayList<BABNode> alreadyExpanded;

    /**
     * This method solves the problem using a Branch and Bound algorithm.
     * @param problem Represents the problem to solve.
     * @param solutionSize Size of the solution.
     * @param algorithm Specifies what algorithm to use for the initial lower quote. It should be BranchAndBound.GREEDY_CONSTRUCTIVE,
     *                  BranchAndBound.GREEDY_DESTRUCTIVE or BranchAndBound.GRASP.
     * @param exploreStrategy Specifies the strategy for exploring the tree. It should be BranchAndBound.DEPTH_FIRST o
     *                        BranchAndBound.SMALLER_SUPERIOR_QUOTE.
     * @return The MaximumDiversitySet with the final solution.
     */
    public MaximumDiversitySet solve(MaximumDiversitySet problem, int solutionSize, String algorithm, String exploreStrategy, boolean print) {
        clean();
        long initialTime = System.currentTimeMillis();
        this.exploreStrategy = exploreStrategy;
        this.solutionSize = solutionSize;

        alreadyExpanded = new ArrayList<>();

        initialQuota(problem, algorithm);
        bestFound = problem;

        MaximumDiversitySet startingSet = new MaximumDiversitySet(problem);
        for (int i = 0; i < startingSet.getSolution().size(); i++) {
            startingSet.getSolution().set(i, false);
        }
        tree = new BABTree(startingSet);
        branchOut(tree.getOrigin());
        long finalTime = System.currentTimeMillis();

        if (print)
            writeOutput(algorithm, finalTime - initialTime);

        return new MaximumDiversitySet(bestFound);
    }

    /**
     * Branches out the given node, and explores its sons, grand-sons...
     * @param set The current node.
     */
    private void branchOut(BABNode set) {
        if (set.getSet().solutionSize() != solutionSize) {
            set.generateSons();
            if (exploreStrategy.equals(DEPTH_FIRST))
                exploreDepthFirst(set);
            else if (exploreStrategy.equals(SMALLEST_SUPERIOR_QUOTE))
                expandSmallerSuperiorQuote(set);

        } else {
            if (bestFound.diversity() < set.getSet().diversity())
                bestFound = set.getSet();
        }
    }

    /**
     * Check if a node is worth branching. If it has already been visited, it is not worthy.
     * @param node The current node.
     * @return True or false, if it is, or not, worth exploring.
     */
    private boolean worthy(BABNode node) {
        if (alreadyExpanded.contains(node))
            return false;
        if (node.getFather() == null)
            return true;

        int adding = -1;
        for (int i = 0; i < node.getSet().getSolution().size(); i++) {
            if ((node.getSet().getSolution().get(i))
                && (!node.getFather().getSet().getSolution().get(i))) {
                adding = i;
                break;
            }
        }

        for (int i = 0; i < node.getFather().getSet().getSolution().size(); i++) {
            if (!node.getSet().getSolution().get(i)) {
                if (node.getSet().minDistance(i) > node.getSet().maxDistance(adding))
                    return false;
            }
        }

        return (node.getSet().superiorQuote(solutionSize) >= bestFound.diversity());
    }

    /**
     * Continue exploring a node with depth first method.
     * @param currentNode The current node
     */
    private void exploreDepthFirst(BABNode currentNode) {
        for (BABNode son : currentNode.getLinks()) {
            if (worthy(son)) {
                alreadyExpanded.add(0, son);
                branchOut(son);
            }
        }
    }

    /**
     * Continue exploring using the node with the highest diversity as preferred.
     * @param currentNode The current node
     */
    private void expandSmallerSuperiorQuote(BABNode currentNode) {
        ArrayList<Integer> branched = new ArrayList<>();
        while (branched.size() != currentNode.getLinks().size()) {
            double highestValue = Double.NEGATIVE_INFINITY;
            int index = -1;
            for (int i = 0; i < currentNode.getLinks().size(); i++) {
                if ((!branched.contains(i)) && (currentNode.getLinks().get(i).getSet().diversity() > highestValue)) {
                    highestValue = currentNode.getLinks().get(i).getSet().superiorQuote(solutionSize);
                    index = i;
                }
            }
            if (index != -1) {
                if (worthy(currentNode.getLinks().get(index))) {
                    alreadyExpanded.add(0, currentNode.getLinks().get(index));
                    branchOut(currentNode.getLinks().get(index));
                }
                branched.add(index);
            }
        }
    }

    /**
     * Creates an initial quota using the specified algorithm.
     * @param problem
     * @param algorithm
     */
    private void initialQuota(MaximumDiversitySet problem, String algorithm) throws IllegalArgumentException {
        if (algorithm.equals(GREEDY_CONSTRUCTIVE)) {
            GreedyConstructive greedy = new GreedyConstructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm.equals(GREEDY_DESTRUCTIVE)) {
            GreedyDestructive greedy = new GreedyDestructive();
            greedy.solve(problem, solutionSize);
        } else if (algorithm.equals(GRASP)) {
            Grasp grasp = new Grasp();
            grasp.solve(problem, solutionSize, GRASP_ITERATIONS);
        } else
            throw new IllegalArgumentException("Did not recognize algorithm.");
    }

    /**
     * Cleans everything so we can start again or clean memory.
     */
    private void clean() {
        if (bestFound != null)
            bestFound.reset();
        bestFound = null;
        if (tree != null)
            tree.clear();
        tree = null;
        if (alreadyExpanded != null)
            alreadyExpanded.clear();
        alreadyExpanded = null;
    }

    /**
     * This function writes the results of the algorithm in an output file.
     * It appends the new content. This output file is a csv file.
     * @param usedAlgorithm The algorithm use to generate the initial lower quote.
     */
    private void writeOutput(String usedAlgorithm, long time) {
        BufferedWriter outputFile = null;
        File file = null;
        try {
            boolean header = true;
            file = new File(OUTPUT_FILE + usedAlgorithm + exploreStrategy + OUTPUT_FILE_EXTENSION);
            if ((file.exists()) && (!file.isDirectory())) {
                header = false;
                outputFile = new BufferedWriter(new FileWriter(OUTPUT_FILE + usedAlgorithm
                        + exploreStrategy + OUTPUT_FILE_EXTENSION, true));
            } else
                outputFile = new BufferedWriter(new FileWriter(OUTPUT_FILE + usedAlgorithm
                        + exploreStrategy + OUTPUT_FILE_EXTENSION));
            String solution = bestFound.printSolution();
            if (header)
                outputFile.write(FILE_HEADER + "\n");
            outputFile.write(bestFound.getFilename() + "; " + bestFound.getSet().size() + "; ");
            outputFile.write(bestFound.getElementSize() + "; " + bestFound.solutionSize() + "; ");
            outputFile.write(bestFound.diversity() + "; " + solution + "; ");
            outputFile.write(time + "; " + alreadyExpanded.size() + ";");
            outputFile.newLine();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputFile != null)
                    outputFile.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }


}
