
/**
 * This Class stores data associated with each customer and has methods to access them
 *
 * @author Jannatul Ferdous
 * @version 10/11/2019
 */

public class Customer 
{
    int arrivalTime;
    int index;
    int depatureTime;
    boolean isServed;
    int waitTime; 
    int timeServed; //time the customer starts getting served
    int numOrder; //order volume

    /**Constructor for Customer class*/ 
    public Customer(int t, int index,int numOrder ){//index keeps track of the customer number 
        this.arrivalTime = t;
        this.index = index;
        this.isServed = false;
        this.numOrder=numOrder;
    }

    /**Setter for depature time*/ 
    public void setDepature(int x){
        this.depatureTime = x;
    }

    /**Setter for time served*/ 
    public void setTimeServed(int x){
        this.timeServed = x;
    }

    /**Calculates and returns wait time*/ 
    public int waitTime(){
        return waitTime = timeServed - arrivalTime ;
    }

    /**Returns customerIndex*/ 
    public int getIndex(){
        return index;
    }

    /**Returns all customer info as a string.*/ 
    public String toString(){//was used for testing purposes
        return index + " Arrival Time: " + arrivalTime + " Depature Time: " + depatureTime+ " is Served: " + isServed+ " time served: "+ timeServed ;
    } 

}
