import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;


public class TestMain {

    private static int k;   // Number of nearest neighbours to be considered
    private static ArrayList<String> list = new ArrayList<>();
    private static ArrayList<ArrayList<Double>> convertedList = new ArrayList<>() ;
    private static ArrayList<Double> randomList = new ArrayList<>();
    private static List<Double> sortedList;
    private static HashMap<Integer, Double> results = new HashMap<>();

    public static void main(String[] args) throws IOException {

        importData();

        convertToDouble();

        getvalueofk();

        randomizeInputSet();

        findQueryDistances();

        printSortedSquares();

        selectKNeighbours();

    }

    private static void importData() throws FileNotFoundException {

        String FILE_NAME = "heart_data.txt";

        System.out.println("\n\n\n<===================Initial List from file==================>\n\n");

        Scanner scan = new Scanner(new File(FILE_NAME));
        scan.useDelimiter(System.getProperty("line.separator"));

        while (scan.hasNext()){
            list.add(scan.next());
        }
        scan.close();

        printStrings();

    }

    private static void getvalueofk(){

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


    private static void convertToDouble() {

        System.out.println("\n\n\n<===================Text data imported to ArrayList============>\n\n");

        int i,j;
        for(i=0; i<list.size(); i++){

            ArrayList<Double> arD = new ArrayList<>();
            String [] splited = list.get(i).split(" ");

          for (j = 0; j<splited.length; j++){
              arD.add(Double.parseDouble(splited[j]));
             }
            convertedList.add(arD);
        }
        printConvertedList();

    }

    private static void randomizeInputSet(){

        int i,j;

        for(i=0; i<convertedList.get(i).size(); i++){

            ArrayList<Double> temp = new ArrayList<>();

            for(j=0; j<convertedList.size(); j++){

                double d = convertedList.get(j).get(i);
                temp.add(d);
            }
            Collections.shuffle(temp);
            randomList.add(temp.get(0));
        }
        randomList.remove(randomList.size()-1);
        printInputSet();
    }

    private static void findQueryDistances(){

        int i,j;

        for(i=0; i<convertedList.size(); i++){

            double sum = 0;

            for(j=0; j<convertedList.get(i).size()-1; j++){

               sum += Math.pow(randomList.get(j) - convertedList.get(i).get(j), 2);
            }

            results.put(i, sum );
            // squares.add(sum);
        }

        printQueryDistances();
    }

    private static List<Double> sortHashMapByValue(HashMap <Integer, Double> hm){

        List<Double> temp = new ArrayList<Double>( hm.values());

        temp.sort(Double::compareTo);

        return temp;
    }

    private static void  selectKNeighbours(){

        sortedList.subList(k, sortedList.size()).clear();

        System.out.println("\n\n<===================Nearest K Squares========================>\n");

        for(Double value : sortedList)
            System.out.println(value);
    }

    private static void conductVoting(){

        for(Double d : sortedList){

        }
    }

    private static void printQueryDistances(){

        System.out.println("\n<===================Square Differences========================>\n");

        for(Map.Entry me : results.entrySet()){

            System.out.println(me.getValue());
        }
    }

    private static void printSortedSquares(){

        System.out.println("\n<===================Sorted Square Differences========================>\n");


        sortedList = sortHashMapByValue(results);

        for(Double value : sortedList)
            System.out.println(value);
    }

    private static void printInputSet(){

        System.out.println("\n<===================Random Input Set=========================>\n");
        System.out.println(randomList.toString());
    }


    private static void printStrings(){

        for(String s : list){

            System.out.println(s);
        }
    }

    private static void printConvertedList() {

        for(ArrayList<Double> arD : convertedList){

            for(Double d : arD){
                System.out.print(d + " ");
            }
            System.out.println("");
        }

    }

}
