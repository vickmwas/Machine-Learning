package com.vickmwas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static List<List<Double>> DATA = new ArrayList<>();
    private static int K = 0;
    private static List<List<Double>> CENTROIDS, DISTANCE_MATRIX = new ArrayList<>();
    private static List<List<Double>> GROUP_MATRIX = new ArrayList<>();
    private static List<List<Double>> TEMP_GROUP_MATRIX = new ArrayList<>();


    public static void main(String[] args) {
        DATA = readInputFile();

        System.out.println("\n How many clusters do you require?");
        Scanner sc = new Scanner(System.in);
        K = sc.nextInt();

        populateInitialGroupMatrix();


        getInitialCentroids();

        getDistanceMatrix();

        while (!getGroupMatrix()){
            getOtherCentroids();

            getDistanceMatrix();

        }

//        getOtherCentroids();

    }

    private static void populateInitialGroupMatrix(){
        for (int i = 0; i < DATA.size(); i++) {
            List<Double> data = new ArrayList<>();
            for (int j = 0; j < K; j++) {
                data.add(0.0);
            }
            GROUP_MATRIX.add(data);
        }

        System.out.println("\n\n INITIAL GROUP MATRIX :\n");
        printMatrix(GROUP_MATRIX);

    }


    /**
     * Reads selected input file.
     * @return returns a multi-dimensional Double array, represented as an ArrayList
     */
    private static List<List<Double>> readInputFile() {
        System.out.println("==========================================================");
        String FILENAME = "heart.data.txt";
        System.out.printf("            PROCESSING INPUT DATA from : %s               " + "\n", FILENAME);

        List<List<Double>> rows = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(FILENAME));
            while (sc.hasNextLine()) {
                String i = sc.nextLine();

                List<Double> row = new ArrayList<>();
                String[] splitElements = i.split(" ");
                Double[] doublesArray = new Double[splitElements.length];

                for (int x = 0; x < splitElements.length; x++) {
                    if (splitElements[x] != null && !splitElements[x].equals("")) {
                        doublesArray[x] = Double.parseDouble(splitElements[x]);         //convert string to double.
                    }
                }

                row.addAll(Arrays.asList(doublesArray));   //add the double array elements to a list of doubles.

                rows.add(row);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        printMatrix(rows);

        System.out.println("            DONE!                                         ");
        System.out.println("==========================================================");

        return rows;
    }


    private static void getInitialCentroids() {
        List<List<Double>> centroidsList = new ArrayList<>();

        for (int x = 0; x < K; x++) {
            centroidsList.add(DATA.get(x));
        }
        CENTROIDS = centroidsList;

        System.out.println("\n          CENTROIDS CHOSEN : ");
        int x = 1;
        for (List<Double> yy: CENTROIDS){
            System.out.print("C" + x +" = [ ");
            for (Double y : yy) {
                System.out.print(y + "  ");
            }
            System.out.println(" ] ");
            x++;
        }

    }



    private static void getOtherCentroids(){
        List<List<Double>> clusterRows;
        CENTROIDS.clear();

            for (int i = 0; i < GROUP_MATRIX.get(i).size(); i++) {
                clusterRows = new ArrayList<>();

                for (int j = 0; j < GROUP_MATRIX.size(); j++) {
                    double element = GROUP_MATRIX.get(j).get(i);

                    if (element == 1.0){
                        clusterRows.add(DATA.get(j));
                    }
                }
                //average should take place here
                CENTROIDS.add(averageElements(clusterRows));

            }




        System.out.println("===================================================");
        System.out.println("            The new centroids are :                ");
        int x = 1;
        for (List<Double> yy: CENTROIDS){
            System.out.print("C" + x +" = [ ");
            for (Double y : yy) {
                System.out.print(y + "  ");
            }
            System.out.println(" ] ");
            x++;
        }

    }


    private static void printMatrix(List<List<Double>> listToPrint){
        for (List<Double> x : listToPrint) {
            for (Double y : x) {
                System.out.print(y + "  ");
            }
            System.out.println();
        }
    }


    private static List<Double> averageElements(List<List<Double>> lists){
        List<Double> average = new ArrayList<>();

        for (int i = 0; i < lists.get(0).size(); i++) {
            double sum = 0;
            for (List<Double> list : lists) {
                sum += list.get(i);

            }
            double math = Math.floor(sum / lists.size());
            average.add(math);
        }


        return average;
    }




    private static void getDistanceMatrix(){
        System.out.println("\n\n                  CALCULATING DISTANCE MATRIX..\n                                    ");
        DISTANCE_MATRIX.clear();

        for (List<Double> row : DATA) {
            List<Double> distancesMatrixRow = new ArrayList<>();
            for (List<Double> centroidRow : CENTROIDS) {
                double eucledianDistance = getEucledianDistance(centroidRow, row);
                distancesMatrixRow.add(eucledianDistance);
            }
            DISTANCE_MATRIX.add(distancesMatrixRow);
        }


        for (List<Double> x: DISTANCE_MATRIX){
            System.out.print("[   ");
            for (Double y : x) {
                if (y != null) {
                    System.out.print(y + "  ");
                }
            }

            System.out.println(" ]");
        }
    }


    private static double getEucledianDistance(List<Double> list1, List<Double> list2){
        double distance;
        int size = list1.size();
        double sum = 0;

        for (int i = 0; i < size; i++){
            double item1 = list1.get(i);
            double item2 = list2.get(i);
            sum += ((item1 - item2) * (item1 - item2));
        }
        distance = Math.round(Math.sqrt(sum));

        return distance;
    }


    private static boolean getGroupMatrix() {
        boolean ifStable;
        TEMP_GROUP_MATRIX = new ArrayList<>(GROUP_MATRIX);
        GROUP_MATRIX.clear();

        for (List<Double> row : DISTANCE_MATRIX){
            GROUP_MATRIX.add(rankGroupElements(row));
        }

        System.out.println("\n\n     ==   GROUP MATRIX  ==      ");
        for (int i = 0; i < GROUP_MATRIX.size(); i++) {
            System.out.print("[   ");
            for (int j = 0; j < GROUP_MATRIX.get(i).size(); j++) {
                double y = GROUP_MATRIX.get(i).get(j);
                int yy = (int) y;
                System.out.print(yy + "  ");
            }

            System.out.print(" ]");
            if (i == 2){
                System.out.print("      vs      ");
            }else{
                System.out.print("              ");
            }


            System.out.print("[   ");
            for (int j = 0; j < TEMP_GROUP_MATRIX.get(i).size(); j++) {
                double y = TEMP_GROUP_MATRIX.get(i).get(j);
                int yy = (int) y;
                System.out.print(yy + "  ");
            }
            System.out.println(" ]");

        }

        ifStable = compareGroupMatrices();
        if (ifStable){
            System.out.println(" ======== The Matrix is STABLE ========\n\n");
        }else{
            System.out.println(" ======== The Matrix is NOT yet STABLE ========\n\n");
        }

        return ifStable;
    }








    private static boolean compareGroupMatrices(){
        boolean check = true;
        for (int i = 0; i < GROUP_MATRIX.size(); i++) {
            for (int j = 0; j < GROUP_MATRIX.get(i).size(); j++) {
                Double y = GROUP_MATRIX.get(i).get(j);

                if (!Objects.equals(y, TEMP_GROUP_MATRIX.get(i).get(j))){
                    check = false;
                }

            }
            System.out.println();
        }
        return check;
    }



    private static List<Double> rankGroupElements(List<Double> row){
        ArrayList<Double> temp = new ArrayList<>(row);
        List<Double> finalList = new ArrayList<>();

        double leastValue = Collections.min(temp);

        for (double element : row){
            if (element == leastValue){
                finalList.add(1.0);
            }else{
                finalList.add(0.0);
            }
        }
        return finalList;
    }



}