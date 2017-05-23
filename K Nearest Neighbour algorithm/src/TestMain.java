import java.io.File;
import java.util.*;
import static java.lang.System.exit;

/*
*
* K-Nearest Neighbour algorithm implementation
* Created by Mark Kilonzi
* Copyright 2017
*
* */
public class TestMain {

    private static int k;                                                               // Number of nearest neighbours to be considered
    private static ArrayList<String> list = new ArrayList<>();                          // List of String created from file
    private static ArrayList<SingleEntry> listObject = new ArrayList<>();               // List of Objects to store each line (dataset)
    private static ArrayList<Double> randomList = new ArrayList<>();                    // List used to store random input dataset

    public static void main(String[] args) throws Exception {

        TestMain test1 = new TestMain();                                                //Create a new object of TestMain and call respective methods
        test1.importData();
        test1.convertToDouble();
        test1.getvalueofk();
        test1.randomizeInputSet();
        test1.findQueryInstances();
        test1.selectKNeighbours();
        test1.conductVoting();
    }

    /*
    * importData reads the chosen file
    * reads line by line
    * (delimited by '\n')
    * and adds the file data to a list of type String
    *
    */
    private void importData() throws Exception {

        System.out.println("\nChoose text file:\n");
        System.out.println("1 - Heart Data");
        System.out.println("2 - Diabetes Data\n");

        Scanner reader = new Scanner(System.in);
        int q = reader.nextInt();

        String FILE_NAME;

        if(q == 1){
             FILE_NAME = "heart_data.txt";
        }
        else if(q == 2){
             FILE_NAME = "diabetes_data.txt";
        }
        else {
            throw new Exception("Invalid Input! Enter 1 or 2");
        }

        System.out.println("\n\n\n<===================Initial List from file==================>\n\n");

        Scanner scan = new Scanner(new File(FILE_NAME));
        scan.useDelimiter(System.getProperty("line.separator"));

        while (scan.hasNext()){
            list.add(scan.next());
        }
        scan.close();

        printStrings();

    }
/*
*
* getvalueofk gets the number k from the user
*
* */
    private void getvalueofk(){

        System.out.printf("\n\n\n Enter K (Number of nearest neighbours to be considered):\n");
        Scanner reader = new Scanner(System.in);
        int q = reader.nextInt();
        if(q % 2 != 0){
            k = q;
        }else{
            System.out.println("K must be odd!");
            exit(0);
        }
    }
/*
* convertToDouble loops through list,
* gets a line at a time and splits it with a space " " delimiter
* each element of each line is cast to a double
*
*A new list of double is formed which is passed as an object of type SingleEntry
 * an arraylist of SingleEntries is then formed
*/

    private void convertToDouble() {

        System.out.println("\n\n\n<===================Text data imported to ArrayList============>\n\n");

        int i,j;
        for(i=0; i<list.size(); i++){

            ArrayList<Double> arD = new ArrayList<>();
            String [] splited = list.get(i).split(" ");

          for (j = 0; j<splited.length; j++){
              arD.add(Double.parseDouble(splited[j]));
             }
            //convertedList.add(arD);
            listObject.add(new SingleEntry(arD));
        }
        printConvertedList();

    }

    private void randomizeInputSet(){

        int i,j;

        for(i =0; i < listObject.get(i).originalValues.size(); i++ ){

            ArrayList<Double> temp = new ArrayList<>();

            for (j=0; j<listObject.size(); j++){

                double d = listObject.get(j).originalValues.get(i);
                temp.add(d);
            }

            Collections.shuffle(temp);
            randomList.add(temp.get(0));
        }

        randomList.remove(randomList.size()-1);
        printInputSet();
    }

    private void findQueryInstances(){

        int i,j;

        for(i = 0; i < listObject.size(); i++){

            for(j = 0; j < listObject.get(i).originalValues.size() - 1; j++){

                listObject.get(i).sum += Math.pow(randomList.get(j) - listObject.get(i).originalValues.get(j), 2);
            }
        }
        System.out.println("<===================Square Differences========================>\n");

        printQueryDistances();
    }

    private void selectKNeighbours(){

        listObject.sort(SingleEntry.singleEntryComparator);

        System.out.println("\n\n<===================After Sorting========================>\n");

        printQueryDistances();

        listObject.subList(k, listObject.size()).clear();

        System.out.println("\n\n<===================After taking first k elements========================>\n");

        printQueryWithDiseaseState();

    }

    private void conductVoting(){

        int positive_counter = 0;
        int negative_counter = 0;

       for(SingleEntry se : listObject){

           if(se.diseaseState == SingleEntry.DiseaseState.NEGATIVE){

               negative_counter++;
           }
           else if(se.diseaseState == SingleEntry.DiseaseState.POSITIVE){

               positive_counter++;
           }
       }

       if(Math.max(positive_counter, negative_counter) == positive_counter){

           System.out.println("\n\n<===================PATIENT IS POSITIVE!========================>\n");

       }else if(Math.max(positive_counter, negative_counter) == negative_counter){

           System.out.println("\n\n<===================PATIENT IS NEGATIVE!========================>\n");

       }

    }

    private  void printQueryWithDiseaseState(){

        for(SingleEntry se : listObject){

            System.out.println(se.sum +"    State:  " +se.diseaseState.toString());
        }
    }
    private void printQueryDistances(){


        for(SingleEntry se : listObject){

            System.out.println(se.sum);
        }
    }


    private void printInputSet(){

        System.out.println("\n<===================Random Input Set=========================>\n");
        System.out.println(randomList.toString());
        System.out.println("\n\n");
    }


    private void printStrings(){

        for(String s : list){

            System.out.println(s);
        }
    }

    private  void printConvertedList() {

       for(SingleEntry en : listObject){

           System.out.println(en.originalValues);
       }

    }

}
