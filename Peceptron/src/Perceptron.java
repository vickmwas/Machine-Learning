import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by abed on 4/28/17.
 */
public class Perceptron {
    public static ArrayList<ArrayList<Double>> sensor_Values = new ArrayList<ArrayList<Double>>();
    public static ArrayList<Double> desired_Output = new ArrayList<>();
    public static ArrayList<Double> clusters = new ArrayList<Double>();
    //    public static ArrayList<Double> weight = new ArrayList<>();
    public static double threshold = 0.5;
    public static double network = 0.5;
    public static double error_Rate = 0.0;
    public static double correction = 0.0;
    public static double learning_Rate = 0.1;
    static Double trainingset[][] = {
            {1.0, 0.0, 0.0},
            {1.0, 0.0, 1.0},
            {1.0, 1.0, 0.0},
            {1.0, 1.0, 1.0}
    };
    static double sum = 0.0;
    public static ArrayList<Double> intial_Weights = new ArrayList<>();


    public static void main(String[] args) {
/*

 */
        intial_Weights.add(0.0);
        intial_Weights.add(0.0);
        intial_Weights.add(0.0);


        desired_Output.add(1.0);
        desired_Output.add(1.0);
        desired_Output.add(1.0);
        desired_Output.add(0.0);


        for (int i = 0; i < trainingset.length; i++) {
            ArrayList<Double> list = new ArrayList<>();
            list.addAll(Arrays.asList(trainingset[i]));
            sensor_Values.add(list);
        }
//        for(int i=0;i<desired_Output.size();i++) {
        System.out.println("Training Set[");
        print_array_of_list(sensor_Values);
        System.out.print("]");

        System.out.print("\nInitial Weight : ");
        print_array(intial_Weights);

        populate_Cluster();
//            System.out.println("\nClusters[" + "] ");
//            print_array(clusters);
//        }
    }

    public static void print_array(ArrayList<Double> wholeArray) {
        System.out.print("[ ");
        for (Double rValue : wholeArray) {
            System.out.print(rValue + "  ");
        }

        System.out.print(" ]");


    }

    public static void print_array_of_list(ArrayList<ArrayList<Double>> wholeArray) {
        for (ArrayList<Double> line : wholeArray) {
            for (Double rValue : line) {
                System.out.print(rValue + "  ");
            }
            System.out.println("");
        }
    }

    public static void populate_Cluster() {

        for (int i = 0; i < sensor_Values.size(); i++) {
            clusters.clear();
            ArrayList<Double> line = sensor_Values.get(i);
            for (int j = 0; j < line.size(); j++) {
                double lineValue = line.get(j);
                double weight = intial_Weights.get(j);
                clusters.add(lineValue * weight);

            }

            System.out.print("\nClusters : ");
            print_array(clusters);


            sum_of_Cluster();
            System.out.println("\n Sum " + sum);
/*
    calculate the network
 */
            network();
 /*
    calculate the error_Rate
 */
            error_Rate(i);
  /*
    calculate the correction
 */
            correction();
/*
    calculate the get_Final_Weights
 */
            get_Final_Weights();


        }

    }

    public static void sum_of_Cluster() {
        sum = 0.0;
        for (int i = 0; i < clusters.size(); i++) {
            sum += clusters.get(i);
        }

    }

    public static void network() {
        if (sum > threshold) {
            network = 1;
        } else {
            network = 0;
        }
        System.out.println("Network: " + network);
    }

    public static void error_Rate(int i) {
        error_Rate = desired_Output.get(i) - network;
        System.out.println("Error :" + error_Rate);
    }

    public static void correction() {
        correction = learning_Rate * error_Rate;
        System.out.println("Correction : " + correction);
    }

    public static void get_Final_Weights() {
        intial_Weights.clear();
        for (int i = 0; i < trainingset[0].length; i++) {
            intial_Weights.add(sensor_Values.get(0).get(i) * correction);
        }
        System.out.print("New Weights : [ ");
        for (Double rValue : intial_Weights) {
            System.out.print(rValue + "  ");
        }
        System.out.print(" ] \n\n");

    }
}
