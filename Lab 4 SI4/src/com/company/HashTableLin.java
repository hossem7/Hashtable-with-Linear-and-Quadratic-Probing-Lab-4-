package com.company;

public class HashTableLin {
    private Integer[] table;
    private int size;
    private int keys;
    private double loadFact;

    public HashTableLin(int maxNum, double load){ //creates an empty hash table
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
        double tempLoadFact = (keys + 1)/size;

        if (tempLoadFact > loadFact)
            this.rehash();

        int position = n%size;
        while (table[position]!= null){
            if (this.isIn(n) == true)
                return;
            if (position == size-1)
                position = 0;
            else
                position++;
        }
        table[position] = n;
        keys++;

    }


    public int insertCount (int n){
        double tempLoadFact = (double)(keys + 1)/size;
        int numProbes = 0;

        if (tempLoadFact > loadFact)
            this.rehash();

        int position = n%size;
        while(table[position] != null){
            if (this.isIn(n) == true)
                return numProbes;
            if (position == size - 1)
                position = 0;
            else{
                position++;
                numProbes++;
            }
        }
        keys++;
        table[position] = n;

        return numProbes;
    }

    public int unsucProbing(int n){
        int numUnsucProbes = 0;
        int position = n%size;
        if(table[position] == null){
            numUnsucProbes = 1;
            return numUnsucProbes;
        }
        else if(table[position].equals(n)){
            return numUnsucProbes;
        }
        numUnsucProbes = 1;
        int i=1;
        while(i<size-1){
            int currentIndex = (i + position) % size;
            numUnsucProbes++;
            if(table[currentIndex] == null){
                return numUnsucProbes;
            }
            else if(table[currentIndex] == n){
                return numUnsucProbes;
            }
            i++;
        }
        return numUnsucProbes;
    }

    public void rehash(){
        int position;
        size = this.smallestPrimeNum(2*size);
        Integer[] doubleTable = new Integer [size];
        for (int i = 0; i<table.length; i++){
            if (table[i] != null){ //
                position = table[i] % size;
                if(doubleTable[position] == null){
                    doubleTable[position] = table[i];
                    continue;
                }
                else if(doubleTable[position] != table[i]){
                    int tempIndex;
                    for (int j = 1; j<size - 1; j++){
                        tempIndex = (position + j)%size;
                        if (doubleTable[tempIndex] == null){
                            doubleTable[tempIndex] = table[i];
                            break;
                        }
                    }
                }
            }
        }
        table = doubleTable;
    }

    public boolean isIn (int n){
        int pos = n%size;
        if(table[pos]==null){ //if the calculated position is empty, that means n doesn't exist in the hash table
            return false;
        }
        if(table[pos]==n){ //vice versa
            return true;
        }
        else{
            int i = pos;
            while(table[i] ==null){
                if(i>size){ //not sure if this is needed
                    return false;
                }
                if(table[i] == n){ //if found return true
                    return true;
                }
                i++;
            }

        }
        return false;

    }

    public void printKeys(){
        int numKeys = 0;
        System.out.println("The elements are: ");
        for (numKeys = 0; numKeys < size; numKeys++){
            if(table[numKeys]!=null){
                System.out.println(table[numKeys]);
            }
        }

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


} // end class





/*public class HashTableLin {
    private Integer[] table; //stores integer values in the array
    private int tableSize; //holds the size of the hash array
    private int numOfKeys; //holds the number of elements in the hash table
    private double maxLoad; //holds the load of the hash table


    //Average Case: Big Theta(1)
    public HashTableLin(int maxNum, double load){
        maxLoad = load;
        numOfKeys = 0;

        int size = (int)(maxNum/maxLoad)+1; //gets the size of the table
        size = smallestPrimeNum(size); // sets it to the next prime number
        tableSize = size; //sets the hash table size
        table = new Integer[tableSize];  //initializes the hash table
    }
    //worst case: big theta(square root (n))
    //best case: big theta(1)
    private static boolean isPrime(int n){ //checks if the number n is a prime number
        if(n%2 == 0){ //even numbers will never be a prime number
            return false;
        }
        else{
            for(int i=3; i*i<=n; i+=2){ //checks if the square of i is less than the number n. If so increments by +2 since we want to keep i an odd number
                if(n%i==0){ //if the division returns no remainder, then it's not a prime number
                    return false;
                }
            }
        }
        return true; //prime number if nothing above executes
    }
    //worst case: big theta(square root of (n))
    //best case: big theta(1)
    private int smallestPrimeNum(int n){
        while(!isPrime(n)){ //while n is not a prime number
            n++;//iterate the value of n
        }
        return n; //return n when it becomes a prime number
    }
    //Best case: big theta(1)
    //Worst Case: big theta(n)
    public void insert(int n){ //inserts the value n in the appropriate position
        //if statement checks if the current load factor is greater than max load, then rehash should be done
        if(((numOfKeys+1)/tableSize) > maxLoad){
            this.rehash();
        }
        //if the value n is not in the hash table
        if(isIn(n)!=true){
            //n%tableSize is the calculated position where the value n should go
            if(table[n%tableSize] == null){//checks if there is no value in the calculated position then we insert n in that location
                table[n%tableSize] = n;
                numOfKeys++;
                return;
            }
            else if((table[n%tableSize]!=n)){//if there is collision and a number exists in the calculated position
                for(int i=1; i<tableSize-1;i++){ // for loop for linear probing
                    int currentPos = (n%tableSize+i)%tableSize; //checks for the next available position
                    if(table[currentPos] == null){//if the next available position is empty, enter the value to the hash table
                        table[currentPos] = n;
                        numOfKeys++;
                        return;
                    }
                }
            }
        }

    }
    //Big theta n because have to copy over the new values to the new hash table
    public void rehash(){ //allocates the hash table to a new one of the double the size
        this.tableSize *=2;
        this.tableSize = smallestPrimeNum(tableSize);//uses the prime number method
        Integer[] temp = table;
        table = new Integer[tableSize]; // new hash table of double the size

        for(int i=0; i<temp.length;i++){
            if(temp[i] != null){
                int position = (temp[i]%tableSize);

                if(table[position] == null){ //if position is empty, number goes in that position
                    table[position] = temp[i];
                }
                else if(table[position] != temp[i]){ //if there is a collision, linear probing is performed
                    for(int j=1; j<tableSize-1; j++){
                        int currentPos = (position+j)%tableSize;
                        if(table[currentPos] == null){
                            table[currentPos] = temp[i];
                            break;
                        }
                    }
                }
            }
        }
    }
    //Worst case: big theta(n)
    //Best case: big theta(1)
    public boolean isIn(int n){//checks if the number n is in the hash table
        int pos = n%tableSize;
        if(table[pos]==null){ //if the calculated position is empty, that means n doesn't exist in the hash table
            return false;
        } 
        if(table[pos]==n){ //vice versa
            return true;
        }
        else{
            for(int i=pos; table[i]==null; i++){ //checks if the value n is in the hash table using linear probing
                if(i>tableSize){ //not sure if this is needed
                    return false;
                }
                if(table[i] == n){ //if found return true
                    return true;
                }
            }
        }
        return false; //if n is not found
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
    //big theta(1) - best case
    //Big theta(n) - worst case
    public int successfulProbe(int n){//method is used for the successful probe search for the static method in the test class
        int numOfProbes = 0; //hold the value of successful probes
        if(((numOfKeys+1)/tableSize) > maxLoad){ //if current load factor is greater than the max load factor, do a rehash
            this.rehash();
        }
        if(!isIn(n)){//if key not found
            int position = n%tableSize;
            if(table[position] == null){
                table[position] = n;
                numOfKeys++;
                return 1; //returns 1 because an element has been inserted which means 1 successful probe
            }
            else if(table[position] != n){ //if there is collision
                numOfProbes++;//increments the successful probe by 1 since we checked one position

                for(int i=1; i<tableSize-1; i++){
                    int currentPos = (position+i)%tableSize;
                    if(table[currentPos] != null){
                        numOfProbes++; //number of successful probe is incremented until the number is inserted
                    }
                    else{
                        table[currentPos] = n; //number is inserted
                        numOfKeys++;
                        break;
                    }
                }
            }
        }
        return numOfProbes;
    }


    //(BONUS)
    //worst case: Big theta(n)
    //best case: big theta(1)
    public int unsuccProbe(int n){ //method used to measure the number of unsuccessful probes
        //Unsuccessful probes: number of probes needed to find an element
        int position = n%tableSize;
        if(table[position]==null){
            return 1;
        }
        else if(table[position] == n){
            return 0;
        }
        else{//exact process as finding an element like the isIn method but with an addition of incrementing the number of probes
            int NumOfUnSuccProbe = 1;
            for(int i=1; i<tableSize-1; i++){
                int currentPos = (position + i)%tableSize;
                NumOfUnSuccProbe++;
                if(table[currentPos] == null || table[currentPos] == n){
                    return NumOfUnSuccProbe;
                }
            }
            return NumOfUnSuccProbe;
        }
    }

    //all three of them are big theta(1)
    public int getHashTableSize(){ return tableSize; } //returns size of the hash table
    public double getMaxLoad() { return maxLoad; } //returns the max load factor of the hash
    public int getNumOfKeys(){ return numOfKeys; } //returns the number of elements in the hash
}
*/