import java.util.*;
import java.io.*;

/**
 * The Restaurant Class is what controls the simulation.It has Customer queues,priority queues and methods to add to them and remove from them when served. It also calculates all the necessary data.
 *
 * @author Jannatul Ferdous
 * @version 10/11/2019
 */

public class Restaurant
{
    //Instance Variables
    
    Scanner sc;
    int numCook; //number of Cooks 
    int [] randomNumbers; //array to stored the random times generated for customer arrival
    PriorityQueue<Event> pQue; //priority queue that adds events according to time
    ArrayList<Customer> arrayList; //arrayList to store all Customers
    ArrayDeque<Event> deque; //Customer queue
    int max; //the max number for order Volume 
    int min; //the min number for order Volume 
    int orderVolume; //the quantity ordered by a customer
    int numCustomers; //the number of Customers during the 12-hour period
    int serviceTime; //the time that the cashier takes to process order
    int t; //cooking time for each type of Food
    String food; //the type of food (Pizza/Bagel/Hoagie)
    int price;
    public static String fileContent; //variable to store contents for the logging file
    

   /**Constructor for objects of class Restaurant.*/
     public Restaurant(){
       serviceTime=Cashier.serviceTime; 
       fileContent="";
    }
    
    /**Method to generate random arrival times, store in an array and sort in increasing order*/
    public int[] generateIncreasingRandoms() {
        
      sc = new Scanner(System.in);
      System.out.println("Enter the number of Customers for the simulation");
      numCustomers = sc.nextInt();
      int max=720;
      randomNumbers = new int[numCustomers];
      Random random = new Random(0);
      
       for (int i = 0; i < randomNumbers.length; i++) {
        randomNumbers[i] = random.nextInt(max);
     }
     Arrays.sort(randomNumbers);
     //for(int x:randomNumbers) System.out.println(x);
     return randomNumbers;
   }
   
   /**Method to get the specific cooking time and range of order volume for each type of food.*/
   public void getData(){
       
        sc = new Scanner(System.in);
        System.out.println("Input the Type of Food you want to test");
        food = sc.next();
        t=0;
        price=0;
        
        switch(food){
            case("Pizza"):
            t=Pizza.cookTime;
            max=Pizza.max;
            min=Pizza.min;
            price=Pizza.price;
            break;
            case("Bagel"):
            t=Bagel.cookTime;
            max=Bagel.max;
            min=Bagel.min;
            price=Bagel.price;
            break;
            case("Hoagie"):
            t=Hoagie.cookTime;
            max=Hoagie.max;
            min=Hoagie.min;
            price=Hoagie.price;
            break;
            default:
            System.out.println("Choose between Pizza, Bagel and Hoagie");
        }
   }

    /**Receives time from arrival time array and adds the times and customers to the priority queue and arrayList respectively*/
    public void customerArrival(){
     
        //initialize queue and arrayList 
        pQue = new PriorityQueue<Event>();
        arrayList = new ArrayList<Customer>();
        deque = new ArrayDeque<Event>();

        //add all customer entry ts to the priority queue of events
        int customerIndex = 1;//keeps track of the customer
        
        for(int i=0; i<randomNumbers.length; i++){
             
            int time1=randomNumbers[i]; //arrival time
            int time=randomNumbers[i]+serviceTime; //arrival time+ ordering time
            
            Random rand=new Random(0); //generate a random number in the range of order volume
            orderVolume=rand.nextInt(max) + min;
            System.out.println(orderVolume);
            Customer c = new Customer(time, customerIndex, 5);
            Event e = new Event(c, "Arrival");
            arrayList.add(c);// adds customer to the array list of customers
            pQue.add(e);// adds event to the priority queue 
            
            fileContent+="Customer "+customerIndex+" has arrived at time "+time1+"\n";
            fileContent+="Customer "+customerIndex+" has placed order for "+orderVolume+" "+ food+" at time "+time+"\n";
            
            customerIndex++;
        }
    }

    /**Runs the simulation */
    public void simulation(){
        //Ask for number of cashiers
        sc = new Scanner(System.in);
        System.out.println("Input Number of Cooks");
        numCook = sc.nextInt();

        customerArrival();//gets info from input file and adds the ts and customers to the priority queue and arrayList respectively 

        Event e = new Event();
        
        int cooksFree = numCook;

        while (pQue.peek() != null){//while the Priority Queue is not empty
            
           e = pQue.poll();//remove the head of the priority Queue

           if(isOpen(e)){// if event is an arrival when restaurant is open
                if (cooksFree > 0){//if a cook is available
                    cooksFree--; //reduce the number of cooks by 1
                    serve(e);//serve the customer;
                    fileContent+="Customer "+e.CustomerIndex()+" has been served \n";
                }
                else if(cooksFree == 0){//if there are no cooks available
                    addToQueue(e);//add event to queue 
                    fileContent+="Customer " + e.customerIndex + " has been added to queue at time \n";
        
                }
            }
            
             else if(e.type == "Departure"){//if event is a depature
                if(cooksFree < numCook)cooksFree++;//increase number of cook available only when cooks available is less that the total number of cooks
                int newTime = e.time;//new time is the time the depature occured 
                if (deque.peek() != null){//if queue is not empty
                    e = deque.poll();//remove the head of the Queue
                    e.setTime(newTime);//change the time of the event to the time the last customer departed
                    cooksFree--;
                    serve(e);//serves the customer in the queue
                    fileContent+="Customer "+e.CustomerIndex()+" has been served at time "+newTime+"\n";
                }
            }
        }
    }

    /**Runs the simulation*/
    public void addToQueue(Event e){
         deque.add(e);
      }

    /**This method handles the serving of a customer */
    public void serve(Event e){
        
        int cookingTime=t*orderVolume;
        
        e.setTime(e.time + serviceTime+ cookingTime);
        e.setType("Departure");//change event type to depature  
        pQue.add(e);//adds the new depature event back to priority Queue

        //set depature time of the  customer and time served
        Customer c = arrayList.get(e.customerIndex -1);
        c.setDepature(e.time);
        c.setTimeServed(e.time);
        c.isServed = true;
        arrayList.set(e.customerIndex-1, c);
    }

    /**This method handles the serving of a customer*/
    public boolean isOpen(Event e){
        int open = 0; 
        int close = 720; //(12 Hours)
        //Checks if Customer arrives when Store is close
        if (e.time < open || e.time > close){
           return false;
        }
        else if (e.type == "Departure")return false;
        else return true;
    }

    /** Returns total daily profit after cost deductions */
    public double totalRevenue(){
        float total = 0;
        
        for (int i=0; i < arrayList.size();i++){
            Customer c = arrayList.get(i);
            if(c.isServed){
                total +=(price*orderVolume);
            }//if customer is served add profit per customer to total profit
        }
        
        double minWage=7.5; //minimum wage of Pennsylvania
        
        double profit=total-((numCook+1)*minWage);//sales-wage of number of cooks+1 cashier
        return profit;
    }
    

    /** Returns the average wait time of the customers */
    public int avgWaitTime(){
        int waitTime = 0;
        int avgWT= 0;
        int notServed = 0;//Number of customers not served 
        int numCustomers = arrayList.size();//total number of customers

        for (int i=0; i < arrayList.size();i++){
            Customer c = arrayList.get(i);
            if(c.isServed){
                waitTime += c.waitTime();
            }//if customer is served add the customer's wait time to wait time
            else notServed++;
        }        
        avgWT = waitTime/(numCustomers - notServed);//Average Wait time
        return avgWT;
    }

    /** Returns the maximum wait time of the customers */
    public int maxWaitTime(){
        int maxWait = 0;
        for (int i=0; i < arrayList.size();i++){
            Customer c = arrayList.get(i);
            if(c.isServed && c.waitTime() > maxWait)maxWait = c.waitTime;
        }
        return maxWait;
    }
    
}
