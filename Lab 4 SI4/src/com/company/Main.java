package com.company;

import java.util.Random;

public class Main {
    //big theta (n)
    public static double successProbes(double Load){// method returns the average of the successful probes for linear and quadratic hash tables
        double calcProbe = 0.5*(1+1/(1-Load)); //based on the load, the calculated search probe
        System.out.println("Calculated Probing: " + calcProbe);

        double avgLinHundred = 0, avgQuadHundred = 0;

        for(int i=0; i<100; i++){ //to get a good average iterating 100 times
            Random rnd = new Random();
            //creates hash tables storing a max num of keys of 100000 (both linear and quadratic tables)
            HashTableLin C1 = new HashTableLin(100000, Load);
           // HashTableQuad C2 = new HashTableQuad(100000, Load);

            double avgLin = 0, avgQuad =0;

            for(int j=0; j<100000; j++){//assigning values in to the hash using random number generation
                int num = rnd.nextInt(Integer.MAX_VALUE);
                if(C1.isIn(num)){ // if number is not found in the table, then insert it in both class
                    j--;  //C2.isIn(num)
                    continue;
                }
                else{ //otherwise, repeat the process by decrementing the counter
                    avgLin += C1.insertCount(num);
                   // avgQuad += C2.successfulProbe(num);
                }
            }
            //calculates the avg successful search for 1 iteration
            avgLinHundred += (avgLin/C1.getNumKeys());
            //avgQuadHundred += (avgQuad/C2.getNumOfKeys());
        }
        //avgQuadHundred /=100;//calculates the final avg for successful probes
        //System.out.println("Quadratic Average: " + avgQuadHundred);
        return (avgLinHundred/100+1);
    }
    //big theta(n)
    public static double unsuccessProbe(double Load){//calculates the avg for unsuccessful probe search
        double calcProbe = 0.5*(1+ (1/((1-Load)*(1-Load)))); //calculated unsuccessful probe search value based on load
        System.out.println("Calculated searches that are unsuccessful: " + calcProbe);

        HashTableLin C1 = new HashTableLin(100000, Load);

        double avgUnSuccLin = 0;

        for(int i=0; i<100000; i++){//for loop to insert random values in the hash
            Random rnd = new Random();
            int num = rnd.nextInt(Integer.MAX_VALUE);
            if(!C1.isIn(num)){
                C1.insert(num);
            }
            else{
                i--;
            }
        }
        for(int i=0; i<100000; i++){ //calls the unsuccProbe method in the HashTableLin class to search for an item and store the number of unsuccessful searches
            Random rnd = new Random();
            int num = rnd.nextInt(Integer.MAX_VALUE);
            avgUnSuccLin += C1.unsucProbing(num);
        }
        return avgUnSuccLin/100000; //returns the average unsuccessful probes
    }
    public static void main(String[] args) {
        //Test for Constructor if it created the correct size
        System.out.println("Test - Linear Probing Hash table");
        System.out.println("Test for Constructor (appropriate table size check): ]n");
        HashTableLin a = new HashTableLin(5,0.9);
        a.printKeysAndIndexes(); // should be 7 values



        //checking isIn of Linear


        //Testing insert method of linear

        System.out.println("\n\nChecking insert\n\n");
        a.insert(19);
        a.insert(1);
        a.insert(21);
        a.insert(15);
        a.insert(13);
        a.insert(33);
        a.insert(22);
        a.printKeysAndIndexes();
        //
        /*System.out.println("\n\nChecking rehash");
        HashTableLin b = new HashTableLin(1,0.5);
        b.insert(2);
        b.insert(12);
        b.insert(13);
        b.insert(1);
        b.printKeysAndIndexes();
        System.out.println(b.getSize());
        //checking successful probes method
        //comparing theoretical and measured values


*/

//        System.out.println("\n\nTESTING THE UNSUCCESSFUL PROBE: ");
        /*double[] LoadsArr = {0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9};
        for(int i=0;i<LoadsArr.length;i++){
            System.out.println("\nLoad Factor: " + LoadsArr[i]);
            System.out.println("UnsuccessFul Probe: " + unsuccessProbe(LoadsArr[i]));
        }

        System.out.println("\n\n");
        System.out.println("\nTesting successful probes Static method:  ");
        //System.out.println("\nLoad Factor: " + LoadsArr[0] + ", " + "Linear Average: " + successProbes(LoadsArr[0]));
        for(int i=0; i<LoadsArr.length; i++){
            System.out.println("Load Factor: " + LoadsArr[i] + "\n" + "Linear Average: " + successProbes(LoadsArr[i]) + "\n");
        }*/
        System.out.println("\n\nChecking quadratic");
        HashTableQuad c = new HashTableQuad(5,0.9);
        c.insert(9);
        c.insert(19);
        c.insert(1);
        c.insert(21);
        c.insert(15);
        c.insert(13);
        c.insert(33);
        c.insert(22);
        c.printKeysAndIndexes();
        System.out.println(c.getSize());

        //Testing Quadratic Probing
    }

}