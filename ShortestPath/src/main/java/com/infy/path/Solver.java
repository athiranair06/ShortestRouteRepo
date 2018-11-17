package com.infy.path;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Solver {
	
	//To store distances between the cities
    private static int[][] distances;
    //to store distance covered in all possible routes 
    private static int finalResults[];
    //to store all possible paths
    private static String paths[];
    //Counter to check against all possible solutions are checked or not
    private static int counter = 0;


    public static void main(String args[]) {
    	try{
    		Scanner input = new Scanner(System.in);
            // number of cities is asked
            System.out.println("Enter the number of cities");
            int number_of_cities = input.nextInt();
            
            String [] cities= new String[number_of_cities];
            
            System.out.println("Enter city names");
            for(int k=0; k < number_of_cities; k++){
          	  cities[k]=input.next();
            }
            

            // Global variables are initialized considering the size of the matrix
            int numSolutions = factorial(number_of_cities - 1);
            
            distances = new int[number_of_cities][number_of_cities];
            ///to store all possible routes
            finalResults = new int[numSolutions];
            //to store distance covered in all possible routes 
            paths = new String[numSolutions];

            System.out.println("Enter distance between cities in order");
            for (int i = 0; i < number_of_cities; i++)
            {
                for (int j = 0; j < number_of_cities; j++)
                {
                	distances[i][j] = input.nextInt();
                }
            }
            
            //include Validate call if needed

            // Initial variables to start the algorithm
            String path = "";
            int[] vertices = new int[number_of_cities - 1];

            // Filling the initial vertices array with the proper values
            for (int i = 1; i < number_of_cities; i++) {
                vertices[i - 1] = i;
            }

            // FIRST CALL TO THE RECURSIVE FUNCTION
            int distance = procedure(0, vertices, path, 0);
           // int distance = procedure(orgCityIndex, vertices, path, 0);

            int optimal = 0;
            for (int i = 0; i < numSolutions; i++) {
            	//Uncomment if we need all possible paths and distances
                //System.out.print("Path: " + paths[i] + ". Distance = " + finalResults[i] + "\n");

                // When we reach the optimal one, its index is saved
                if (finalResults[i] == distance) {
                    optimal = i;
                }
            }
            
            
            //Result in terms of nodes
            //System.out.print("Path: " + paths[optimal] + ". Distance = " + finalResults[optimal] + " (OPTIMAL)");
            String[] separated = paths[optimal].split(" - ");
            StringBuilder bestRoute=new StringBuilder();
            for(int index=0;index<=number_of_cities;index++){
            	int cityInd=Integer.parseInt(separated[index]);
            	bestRoute.append(" "+cities[cityInd]+" ");
            }
            System.out.println("Best Route to follow: "+bestRoute+" Distance covered: "+ finalResults[optimal] );
            
            
        
    	}catch(InputMismatchException  | IllegalStateException e){
        	if(e.getMessage() != null){
        		System.out.println(e.getMessage());
        	}else{
        		if (e instanceof InputMismatchException) {
	        	    System.out.println("Invalid Input");
	        	}
        	}	        	
        }
    	
    }



    /**
     * Main function which returns the distance. Recursively calls the same function with subpath.
     * @param initial
     * @param vertices
     * @param path
     * @param costUntilHere
     * @return
     */
    private static int procedure(int initial, int vertices[], String path, int costUntilHere) {

        // We concatenate the current path and the vertex taken as initial
        path = path + Integer.toString(initial) + " - ";
        int length = vertices.length;
        int newCostUntilHere;


        // Exit case, if there are no more options to evaluate (last node)
        if (length == 0) {

            // Both results, numerical distances and paths to those distances, are stored
            paths[counter] = path + "0";
        	finalResults[counter] = costUntilHere + distances[initial][0];
        	//Counter is to check whether all possible solutions are checked or not
            counter++;
            return (distances[initial][0]);
        }


        // Common case, where there are more than 1 node
        else {

            int[][] newVertices = new int[length][(length - 1)];
            int costCurrentNode, costChild;
            int bestCost = Integer.MAX_VALUE;

            // For each of the nodes of the list
            for (int i = 0; i < length; i++) {

                // Each recursion new vertices list is constructed
                for (int j = 0, k = 0; j < length; j++, k++) {

                    // The current child is not stored in the new vertices array
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newVertices[i][k] = vertices[j];
                }

                // Cost of arriving the current node from its parent
                costCurrentNode = distances[initial][vertices[i]];

                // Here the cost to be passed to the recursive function is computed
                newCostUntilHere = costCurrentNode + costUntilHere;

                // RECURSIVE CALLS TO THE FUNCTION IN ORDER TO COMPUTE THE COSTS
                costChild = procedure(vertices[i], newVertices[i], path, newCostUntilHere);

                // The cost of every child + the current node cost is computed
                int totalCost = costChild + costCurrentNode;

                // Finally we select from the minimum from all possible children costs
                if (totalCost < bestCost) {
                    bestCost = totalCost;
                }
            }

            return (bestCost);
        }
    }


    
    /**
     *Factorial function used to calculate the number of solutions
     *No of solutions=factorial(no of cities - 1)
     * @param n=no of cities - 1
     * @return
     */
    private static int factorial(int n) {

        if (n <= 1) return 1;
        else return (n * factorial(n - 1));
    }
    
    
    /**
     * Method to validate the input 
     * Currently not used.Can be used as an improvement to the current function.
     */
    /*
    public  void validate(int number_of_cities){	
		//if (number_of_cities <= 2) throw new IllegalStateException("No of cities must be greater than 2");
	}*/


}

