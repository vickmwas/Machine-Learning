import java.util.ArrayList;


 class BaseSingleEntry {

     int val_x1;
     int val_x2;
     int square_x1_x2;

     enum State{
        BAD,
        GOOD
    }

     State state;

    BaseSingleEntry(int x1, int x2, int square, State state){

        this.val_x1 = x1;
        this.val_x2 = x2;
        this.square_x1_x2 = square;
        this.state = state;
    }
}
