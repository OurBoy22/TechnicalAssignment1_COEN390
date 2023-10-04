package com.example.test;

//this class is used to simplify all the checking of numbers, to see if increments are possible based on the max count set and current count
public class Count {
   public int value = 0;

   //constructor
    public Count(){
        this.value = 0;
    }
    public Count(int value){
        this.value = value;
    }

    public void incrementCount(){
        this.value++;
    } //increments count

    public boolean tryIncrement(Count currentCount, Count currentMax){ // function to try to increment the count
        if (checkCanIncrement(currentCount.value, currentMax.value)){ // if the current count will not exceed the max count
            incrementCount();
            return true;
        }
        return false;
    }

    public boolean checkCanIncrement(int currentCount, int currentMax){
        if (currentCount >= currentMax){// if the current count will not exceed the max count
            return false;
        }
        return true;
    }

    public static String checkZeroMaxCount(int num_in){
        if (num_in == 0){
            return "";
        }
        else{
            return Integer.toString(num_in);
        }
    }

    //setter and getter
    public void setValue(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
