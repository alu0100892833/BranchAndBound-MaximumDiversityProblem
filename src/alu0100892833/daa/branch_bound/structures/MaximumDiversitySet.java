package alu0100892833.daa.branch_bound.structures;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents a set of vectors for the Maximum Diversity Problem.
 * It is just a HashSet of ArrayList, with the necessary methods.
 * This kind of objects can also represent a solution for the Maximum Diversity Problem.
 * @author Óscar Darias Plasencia
 * @since 02/05/2017
 */
public class MaximumDiversitySet {

    private ArrayList<ArrayList<Double>> set;
    private ArrayList<Boolean> solution;
    private int elementSize;

    /**
     * Constructor that builds a set from a file.
     * This file must have an specific syntax.
     * @param filename Name of the file containing the set.
     */
    public MaximumDiversitySet(String filename) {
        BufferedReader setReader;
        set = new ArrayList<>();
        solution = new ArrayList<>();
        try {
            setReader = new BufferedReader(new FileReader(filename));
            String line = setReader.readLine();
            int setLength = Integer.parseInt(line);
            line = setReader.readLine();
            this.elementSize = Integer.parseInt(line);

            for (int n = 0; n < setLength; n++) {
                line = setReader.readLine();
                line = line.replaceAll(",", ".");
                String[] splittedVector = line.split("\\s+");
                if (splittedVector.length != elementSize)
                    throw new IOException("Entry file does not have the correct syntax.");
                ArrayList<Double> newElement = new ArrayList<>();
                for (String value : splittedVector)
                    newElement.add(Double.parseDouble(value));
                set.add(newElement);
                solution.add(false);
            }
        } catch (IOException|NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public MaximumDiversitySet(MaximumDiversitySet copy) {
        this.set = new ArrayList<>(copy.getSet());
        this.solution = new ArrayList<>(copy.getSolution());
        this.elementSize = copy.getElementSize();
    }

    public ArrayList<ArrayList<Double>> getSet() {
        return set;
    }

    public ArrayList<Boolean> getSolution() {
        return solution;
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
    public void addToSolution(ArrayList<Double> component) throws InvalidArgumentException {
        int index = set.indexOf(component);
        if (index != -1)
            solution.set(index, true);
        else
            throw new InvalidArgumentException(new String[] {"The given component is not a part of the set"});
    }

    /**
     * Adds a new element of the set to the solution, setting its value to true.
     * @param index Index of the element in the set.
     * @throws IndexOutOfBoundsException When index is out of bounds.
     */
    public void addToSolution(int index) throws IndexOutOfBoundsException {
        if ((index < 0) || (index >= set.size()))
            throw new IndexOutOfBoundsException("Bad index (" + index + ") trying to add an element to the solution.");
        else
            solution.set(index, true);
    }

    /**
     * Removes the specified component from the solution, setting its value to false.
     * @param component The element that is going to be retired from the solution.
     */
    public void removeFromSolution(ArrayList<Double> component) throws InvalidArgumentException {
        int index = set.indexOf(component);
        if (index != -1)
            solution.set(index, false);
        else
            throw new InvalidArgumentException(new String[] {"The given component is not a part of the set"});
    }

    /**
     * Removes the specified component from the solution, setting its value to false.
     * @param index Index of the element in the set.
     * @throws IndexOutOfBoundsException When index is out of bounds.
     */
    public void removeFromSolution(int index) throws IndexOutOfBoundsException {
        if ((index < 0) || (index >= getElementSize()))
            throw new IndexOutOfBoundsException("Bad index trying to add an element to the solution.");
        else
            solution.set(index, false);
    }

    /**
     * Obtain the gravity center of all the elements of the problem set.
     * @return Gravity Center, an ArrayList of Double, just like the other members of the set.
     */
    public ArrayList<Double> generalGravityCenter() {
        ArrayList<Double> gravityCenter = new ArrayList<>();
        for (int i = 0; i < getElementSize(); i++) {
            Double value = 0.0;
            for (ArrayList<Double> element : set) {
                value += element.get(i);
            }
            gravityCenter.add(value / getSet().size());
        }
        return gravityCenter;
    }

    /**
     * Obtain the gravity center of the elements that are part of the current solution.
     * @return Gravity Center, an ArrayList of Double, just like the other members of the set.
     */
    public ArrayList<Double> solutionGravityCenter() {
        int divisor = 0;
        ArrayList<Double> gravityCenter = new ArrayList<>();
        for (int i = 0; i < getElementSize(); i++) {
            Double value = 0.0;
            for (ArrayList<Double> element : set) {
                if (solution.get(set.indexOf(element))) {
                    value += element.get(i);
                    divisor++;
                }
            }
            gravityCenter.add(value / divisor);
        }
        return gravityCenter;
    }

    /**
     * This method allows to obtain the farthest element from the gravity center.
     * @param general If True, uses the general gravity center. If not, uses the solution gravity center.
     * @return The index of the farthest element.
     */
    public int getFarthest(boolean general) {
        // TODO AÑADIR EXCEPCIONES
        double distance = Double.NEGATIVE_INFINITY;
        int indexOfFarthest = -1;
        ArrayList<Double> gravityCenter;
        if (general)
            gravityCenter = generalGravityCenter();
        else
            gravityCenter = solutionGravityCenter();

        for (ArrayList<Double> element : set) {
            if ((euclideanDistance(element, gravityCenter) > distance)
                    && (!isInSolution(set.indexOf(element)))) {
                distance = euclideanDistance(element, gravityCenter);
                indexOfFarthest = set.indexOf(element);
            }
        }
        return indexOfFarthest;
    }

    /**
     * Returns the current size of the solution.
     * The number of true values in the solution ArrayList.
     * @return Size of the solution.
     */
    public int solutionSize() {
        int size = 0;
        for (Boolean value : solution)
            if (value)
                size++;
        return size;
    }

    /**
     * Checks if a certain element is in the solution.
     * @param index Index of the element.
     * @return True, if it is in the solution; false otherwise.
     */
    public boolean isInSolution(int index) {
        return (solution.get(index));
    }

    /**
     * Returns the diversity value of the current solution.
     * @return Diversity of the current solution
     */
    public double diversity() {
        double diversity = 0.0;
        for (int i = 0; i < set.size() - 1; i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if ((solution.get(i)) && (solution.get(j)))
                    diversity += euclideanDistance(set.get(i), set.get(j));
            }
        }
        return diversity;
    }

    /**
     * Prints the actual state of the problem.
     */
    public void print() {
        System.out.println("=============================");
        System.out.print("SOLUTION:  ");
        for (ArrayList<Double> element : set) {
            if (solution.get(set.indexOf(element)))
                System.out.print(element + " ");
        }
        System.out.println();
        System.out.println("DIVERSITY: " + diversity());
        System.out.println("=============================");
    }

    /**
     * Static method that calculates the euclidean distance between two vectors.
     * If these vectors have different dimensions, distance cannot be calculated.
     * @param from ArrayList of Integers that represents the first vector.
     * @param to ArrayList of Integers that represents the second vector.
     * @return Euclidean distance between the given vectors.
     */
    public static double euclideanDistance(ArrayList<Double> from, ArrayList<Double> to) {
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