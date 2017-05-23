import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class SingleEntry {

    ArrayList<Double> originalValues = new ArrayList<>();

    enum DiseaseState{

        POSITIVE,
        NEGATIVE
    };

    DiseaseState diseaseState;

    double sum = 0;

    private double getSum(){

        return sum;
    }

    SingleEntry(ArrayList<Double> list){

        originalValues = list;

        if(list.get(list.size() -1 ) == 2.0){

            diseaseState = DiseaseState.POSITIVE;
        }
        else if(list.get(list.size() -1 ) == 1.0){

            diseaseState = DiseaseState.NEGATIVE;
        }

    }

    static Comparator<SingleEntry> singleEntryComparator = (o1, o2) -> (int) (o1.getSum() - o2.getSum());
}
