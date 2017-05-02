package alu0100892833.daa.branch_bound.structures;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class represents a set of vectors for the Maximum Diversity Problem.
 * It is just a HashSet of ArrayList, with the necessary methods.
 * This kind of objects can also represent a solution for the Maximum Diversity Problem.
 * @author Óscar Darias Plasencia
 * @since 02/05/2017
 */
public class MaximumDiversitySet {

    private HashMap<ArrayList<Integer>, Boolean> set;
    private int elementSize;

    /**
     * Constructor that builds a set from a file.
     * This file must have an specific syntax.
     * @param filename Name of the file containing the set.
     */
    public MaximumDiversitySet(String filename) {
        BufferedReader setReader;
        set = new HashMap<>();
        try {
            setReader = new BufferedReader(new FileReader(filename));
            String line = setReader.readLine();
            int setLength = Integer.parseInt(line);
            line = setReader.readLine();
            this.elementSize = Integer.parseInt(line);

            for (int n = 0; n < setLength; n++) {
                line = setReader.readLine();
                String[] splittedVector = line.split("\\s+");
                if (splittedVector.length != setLength)
                    throw new IOException("Entry file does not have the correct syntax.");
                ArrayList<Integer> newElement = new ArrayList<>();
                for (String value : splittedVector)
                    newElement.add(Integer.parseInt(value));
                set.put(newElement, false);
            }
        } catch (IOException|NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtain the number of elements in each ArrayList of the set.
     * @return Length of an ArrayList of the set.
     */
    public int getElementSize() {
        return elementSize;
    }

    /**
     * Adds a new element of the set to the solution, setting its value to true.
     * @param component New element that is going to be part of the solution.
     */
    public void addToSolution(ArrayList<Integer> component) {
        set.put(component, true);
    }

    /**
     * Removes the specified component from the solution, setting its value to false.
     * @param component The element that is going to be retired from the solution.
     */
    public void removeFromSolution(ArrayList<Integer> component) {
        set.put(component, false);
    }

    /**
     * Static method that calculates the euclidean distance between two vectors.
     * If these vectors have different dimensions, distance cannot be calculated.
     * @param from ArrayList of Integers that represents the first vector.
     * @param to ArrayList of Integers that represents the second vector.
     * @return Euclidean distance between the given vectors.
     */
    public static double euclideanDistance(ArrayList<Integer> from, ArrayList<Integer> to) {
        if (from.size() != to.size())
            return Double.POSITIVE_INFINITY;
        double auxiliary = 0.0;
        for (int i = 0; i < from.size(); i++) {
            double value = from.get(i) - to.get(i);
            auxiliary += Math.pow(value, 2);
        }
        return Math.sqrt(auxiliary);
    }
}

















//END