package alu0100892833.daa.branch_bound.algorithms;

import alu0100892833.daa.branch_bound.structures.MaximumDiversitySet;


/**
 * This class allows to perform a local search on the solution of the maximum diversity problem.
 * It is needed a MaximumDiversitySet object.
 * @author Óscar Darias Plasencia
 * @since 04/05/2017
 */
public class LocalSearch {
    public void perform(MaximumDiversitySet problem) {
        MaximumDiversitySet alternativeSolution = new MaximumDiversitySet(problem);
        int outIndex = -1;
        int inIndex = -1;
        double diversity = alternativeSolution.diversity();
        for (int inSolution = 0; inSolution < alternativeSolution.getSet().size(); inSolution++) {
            for (int outSolution = 0; outSolution < alternativeSolution.getSet().size(); outSolution++) {
                if ((alternativeSolution.isInSolution(inSolution)) && (!alternativeSolution.isInSolution(outSolution))) {
                    alternativeSolution.removeFromSolution(inSolution);
                    alternativeSolution.addToSolution(outSolution);
                    if (alternativeSolution.diversity() > diversity) {
                        diversity = alternativeSolution.diversity();
                        outIndex = inSolution;
                        inIndex = outSolution;
                    }
                    alternativeSolution.removeFromSolution(outSolution);
                    alternativeSolution.addToSolution(inSolution);
                }
            }
        }
        if (outIndex != -1) {
            problem.removeFromSolution(outIndex);
            problem.addToSolution(inIndex);
        }
    }
}
