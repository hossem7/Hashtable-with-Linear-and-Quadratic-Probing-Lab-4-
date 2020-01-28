package com.company;

public class HashTableQuad {
    private Integer[] table;
    private int size;
    private int keys;
    private double loadFact;


    public HashTableQuad(int maxNum, double load){ //creates an empty hash table
        loadFact = load;
        size = (int) (maxNum/loadFact) + 1;
        size = this.smallestPrimeNum(size);
        keys = 0;
        table = new Integer[size];
    }

    private int smallestPrimeNum(int num){
        int primeCounter;
        while (true){
            primeCounter = 0;
            for (int i = 2; i <= Math.sqrt(num); i++){
                if (num%i != 0)
                    continue;
                primeCounter ++;
            }
            if (primeCounter == 0)
                break;
            num++;
        }
        return num;
    }

    public void insert(int n){
        double tempLoadFact = (keys+1)/size;
        if(tempLoadFact > loadFact){
            this.rehash();//rehash if the table is full
        }
        if(!this.isIn(n)){
            int index= n%size;
            if(table[index] == null){//if there is no number in the calculated place --> insert the number
                table[index] = n;
                keys++;
                return;
            }
            if(table[index] != n){//if there is a number already in the calculated position, perform quadratic probing to find a new place
                int i=1;
                while(i<size-1){
                    int tempIndex = (index+i*i)% size;
                    if(table[tempIndex] == null){//if a new place is found such that there are no elements in it --> insert the targetted element here
                        table[tempIndex] = n;
                        keys++;
                        return;
                    }
                    i++;
                }

            }
        }

    }

    public int insertCount (int n){
        double tempLoadFact = (keys + 1)/size;
        int numProbes = 0;
        if (n <= 0)
            return numProbes;
        if (tempLoadFact > loadFact)
            this.rehash();

        int position = n%size;
        int tempIndex = position;
        int counter = 1;
        if(this.isIn(n)!=true){
            while(table[position] != null){
                if (counter == size - 1)
                    tempIndex = 0;
                else{
                    tempIndex = (position + counter*counter);
                    counter++;
                    numProbes++;
                }
            }
            keys++;
            table[position] = n;
        }
        return numProbes;
    }

    public void rehash(){
        int position;
        size = this.smallestPrimeNum(2*size);
        Integer[] doubleTable = new Integer [size];
        int i=0;
        while(i<table.length){
            if (table[i] != null){ //
                position = table[i] % size;
                if(doubleTable[position] == null){
                    doubleTable[position] = table[i];
                    continue;
                }
                else if(doubleTable[position] != table[i]){
                    int tempIndex;
                    for (int j = 1; j<size - 1; j++){
                        tempIndex = (position + j*j)%size;
                        if (doubleTable[tempIndex] == null){
                            doubleTable[tempIndex] = table[i];
                            break;
                        }
                    }
                }
            }
            i++;
        }

        table = doubleTable;
    }

    public boolean isIn (int n){
        int position = n%size;
        if (n <= 0)
            return false;
        if(table[position]==null){ //if the calculated position is empty, that means n doesn't exist in the hash table
            return false;
        }
        if(table[position]==n){ //vice versa
            return true;
        }
        else{
            int i = position;
            while(table[i] ==null){
                if(i*i>=size){ //not sure if this is needed
                    return false;
                }
                if(table[i*i] == n){ //if found return true
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public void printKeys(){
        int numKeys = 0;
        for (numKeys = 0; numKeys < keys; numKeys++)
            System.out.println("The element is: " + table[numKeys]);
    }

    public void printKeysAndIndexes(){
        int position = 0;
        for (int i = 0; i < size; i++){
            System.out.println("The element " + table[position] + ", is at " + position + ",");
            position++;
        }
    }

    public int getSize(){
        return this.size;
    }

    public Integer [] getTable(){
        return this.table;
    }

    public double getLoad(){
        return this.loadFact;
    }

    public int getNumKeys(){
        return this.keys;
    }


}

/*public class HashTableQuad {
    //instance fields are the same as HashTableLin
    private Integer[] table;
    private int tableSize;
    private int numOfKeys;
    private double maxLoad;

    //big theta (1)
    public HashTableQuad(int maxNum, double load){ // initializes an empty hash table
        maxLoad = load;
        numOfKeys = 0;
        int size = (int)(numOfKeys/maxLoad)+1; //same process as HasTableLin
        size = smallestPrimeNum(size);
        tableSize = size;
        table = new Integer[tableSize];
    }
    //worst case: big theta(square root of n)
    //best case: big theta(1)
    private static boolean isPrime(int n){ //same process as HasTableLin
        if(n%2 == 0){
            return false;
        }
        else{
            for(int i=3; i*i<=n; i+=2){
                if(n%i==0){
                    return false;
                }
            }
        }
        return true;
    }
    //worst case: big theta(square root of n)
    //best case: big theta(1)
    private int smallestPrimeNum(int n){ // same process as HasTableLin
        while(!isPrime(n)){
            n++;
        }
        return n;
    }
    //Worst case: big theta(n)
    //Best case: big theta(1)
    public boolean isIn(int n){ //same process as HasTableLin but with quadratic probing
        int pos = n%tableSize;
        if(pos >= tableSize){
            return false;
        }
        if(table[pos] == null){
            return false;
        }
        if(table[pos]==n){
            return true;
        }
        else{
            for(int i=pos; table[i*i]==null; i++){ //checks if the value n is in the hash table using linear probing
                if(i>tableSize){ //not sure if this is needed
                    return false;
                }
                if(table[i*i] == n){ //if found return true
                    return true;
                }
            }
        }
        return false;
    }
    //big theta(n) since we have to copy over the elements
    public void rehash(){ //same process as HashTableLin but with quadratic probing
        tableSize = this.smallestPrimeNum(tableSize*2);
        Integer[] temp = table;
        for(int i=0; i<table.length; i++){
            if(temp[i]!=null){
                int position = temp[i]%tableSize;

                if(table[position] == null){
                    table[position] = table[i];
                    numOfKeys ++;
                    continue;
                }
                if(table[position] != temp[i]){ //collision is resolved by quadratic probing (j*j)
                    for(int j=1; j<tableSize-1; j++){
                        int currentPos = (position+j*j)%tableSize;
                        if(table[currentPos] == null){
                            table[currentPos]= temp[i];
                            break;
                        }
                    }
                }
            }
        }
    }
    //worst case: big theta(n)
    //best case: big theta(1)
    public void insert(int n){ //inserts the value n in the correct position
        if(((numOfKeys+1)/tableSize) > maxLoad){ // same as HashTableLin
            this.rehash();
        }
        if(isIn(n)!=true){//same as HashTableLin
            int position = n%tableSize;
            if(table[position] == null){ //same as HashTableLin
                table[position]=n;
                numOfKeys++;
            }
            else if(table[position] != n){ //collision is resolved by quadratic probing (i*i)
                for(int i=1; i<tableSize-1; i++){
                    int currentPos = (position+i*i)%tableSize;

                    if(table[currentPos] == null){ //if that position is empty, then insert the value
                        table[currentPos] = n;
                        numOfKeys++;
                        break;
                    }
                }
            }
        }
    }
    //worst case: big theta(n)
    //best case: big theta(1)
    public int successfulProbe(int n){ //same process as HasTableLin but with quadratic probing
        int numOfProbes=0;
        if(((numOfKeys+1)/tableSize) > maxLoad){
            this.rehash();
        }
        if(isIn(n)!=true){
            int position = n%tableSize;
            if(table[position] == null){
                table[position] = n;
                numOfKeys++;
                return 1;
            }
            if(table[position] != n){ //collision is resolved by quadratic probing (i*i)
                numOfProbes = 1;
                for(int i=1; i<tableSize-1; i++){
                    int currentPos = (position+i*i)%tableSize;
                    numOfProbes++;
                    if(table[currentPos] == null){//if that position is empty, then insert the value
                        table[currentPos] = n;
                        numOfKeys++;
                        break;
                    }
                }
            }
        }
        return numOfProbes;
    }

    //big theta (n)
    public void printkeys(){ // prints the values in the hash table
        System.out.println("Keys stored in the table: ");
        for(int i=0; i<tableSize; i++){
            if(table[i]!=null){
                System.out.print(table[i] + ", ");
            }
        }
    }
    //big theta(n)
    public void printKeysAndIndexes(){ //prints the values in the hash table with their indexes
        System.out.println("Value, Index");
        for(int j=0; j<table.length; j++){
            System.out.println(table[j] + ", " + j);
        }
    }
    //all three of them are big theta(1)
    public int getTableSize(){
        return tableSize;
    } //returns size of the hash table
    public double getLoadFactor() { return maxLoad; } //returns the max load factor of the hash
    public int getNumOfKeys() { return numOfKeys; } //returns the number of elements in the hash
}
*/