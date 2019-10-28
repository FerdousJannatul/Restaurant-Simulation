
/**
 * This Class runs the simulations
 *
 * @author Jannatul Ferdous
 * @version 10/11/2019
 */

public class ExperimentController
{
   
    public static void main(String [] args)
    {
        ExperimentController exc=new ExperimentController();
        exc.run();
    }
       
       public void run(){
           
        Restaurant r= new Restaurant();
        r.generateIncreasingRandoms();
        r.getData();
        r.simulation();
        System.out.println("Average Seving Time: "+r.avgWaitTime());
        
        System.out.println("Maximum Wait Time among all the customers "+r.maxWaitTime());
        
        System.out.println("Total profit generated "+r.totalRevenue());
        
        LoggingEvents l=new LoggingEvents();
        l.run();
        
    }
}
