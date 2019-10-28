
/**
 * 
 *This Class keeps track of the Customer index, event time and event type
 * @author Jannatul Ferdous
 * @version 10/11/2019
 */
public class Event implements Comparable<Event>
{
    
    int time;
    int customerIndex;
    String type;
    /** Constructor for objects of class Event */
    public Event(){
    }

    public Event(Customer c, String type)
    {
        this.time = c.arrivalTime;
        this.customerIndex = c.getIndex();
        this.type = type; 
    }
    
    public int CustomerIndex(){
        return customerIndex;
    }
    /**Setter for time event occurs */
    void setTime(int t){
        this.time = t;
    }

    /**Setter for event type */
    void setType(String type){
        this.type = type; 
    }

    /**comparator for events */
    public int compareTo(Event e){
        if (time > e.time)return 1;
        if (time < e.time) return -1;
        else return 0;

    }

    /**Returns event details as a String */
    public String toString(){//was used for testing purposes
        return "Customer Index: " +customerIndex + " event time: " + time + " event type: " +type;
    } 
}