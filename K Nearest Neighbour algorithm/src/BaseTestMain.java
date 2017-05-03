import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.System.exit;


public class BaseTestMain {

    private static int x1, x2;
    private static int k;   // Number of nearest neighbours to be considered
    private static ArrayList <BaseSingleEntry> baseInitialSamples = new ArrayList<BaseSingleEntry>();
    private static ArrayList <Integer> listOfSquares = new ArrayList<Integer>();

    public static void main(String[] args) {

        initialize(); // Set the initial training samples

        getValueOfK(); // Get the number of nearest neighbours (k)

        getQueryInstance(); // Get value of x1 and x2

        euclideanDifference();// Get the Euclidean distance between query and training samples

        sortDistancesAscending(); // Sorts distances in ascending order and trims selection to first k neighbours

        giveMajorityWinner(); // Gives the winning position to the majority state condition, bad or good
    }

    private static void initialize(){

        baseInitialSamples.add(new BaseSingleEntry(7, 7, 0, BaseSingleEntry.State.BAD));
        baseInitialSamples.add(new BaseSingleEntry(7, 4, 0, BaseSingleEntry.State.BAD));
        baseInitialSamples.add(new BaseSingleEntry(3, 4, 0, BaseSingleEntry.State.GOOD));
        baseInitialSamples.add(new BaseSingleEntry(1, 4, 0, BaseSingleEntry.State.GOOD));

    }

    private static void getValueOfK(){

        System.out.printf("Enter K (Number of nearest neighbours to be considered):");
        Scanner reader = new Scanner(System.in);
        int q = reader.nextInt();
        if(q % 2 != 0){
            k = q;
        }else{
            System.out.println("K must be odd!");
            exit(0);
        }
    }

    private static void getQueryInstance(){

        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the query instances:");
        System.out.println("x1:");
        x1 = reader.nextInt();
        System.out.println("x2:");
        x2 = reader.nextInt();
    }

    private static void euclideanDifference(){

        for (BaseSingleEntry initialSample : baseInitialSamples) {

            initialSample.square_x1_x2 = (int) (Math.pow(initialSample.val_x1 - x1, 2) + Math.pow(initialSample.val_x2 - x2, 2));
            listOfSquares.add(initialSample.square_x1_x2);

        }
    }

    private static void sortDistancesAscending(){

        Collections.sort(listOfSquares);
        System.out.println("Ascending Order of squares...");
        System.out.println(listOfSquares);

        if(k > listOfSquares.size()){
            System.out.println("K value larger than available training space!");

        }else{
            listOfSquares.subList(k, listOfSquares.size()).clear();
            System.out.println("After taking first k values...");;
            System.out.println(listOfSquares);
        }
    }


    private static void giveMajorityWinner(){

        int counter_good = 0, counter_bad = 0;

        for(int a : listOfSquares){

            for(BaseSingleEntry en : baseInitialSamples){

                if(a == en.square_x1_x2){

                    if(en.state == BaseSingleEntry.State.BAD){
                        counter_bad++;
                    }
                    else{
                        counter_good++;
                    }
                }
            }
        }
        if(counter_bad > counter_good){
            System.out.println("Input is in state BAD");
        }else{
            System.out.println("Input is in state GOOD");

        }
    }

}
