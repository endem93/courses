package deques;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        int counter = 0;
        //String fullString = StdIn.readString();

//        Scanner fileIn = new Scanner(new File("resources/distinct.txt"));

        while(!StdIn.isEmpty()){
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }

//        while(fileIn.hasNext()){
//            String s = fileIn.next();
//            randomizedQueue.enqueue(s);
//        }

        for(String s : randomizedQueue){
            StdOut.println(s);
            counter++;
            if(k==counter) break;
        }

    }

}
