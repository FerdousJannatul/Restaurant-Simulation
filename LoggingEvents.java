
/**
 * 
 *This Class writes into the Logging.txt file the sequence of events happening
 * @author Jannatul Ferdous
 * @version 10/11/2019
 */

import java.io.File;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LoggingEvents
{
   String fileContent;
    
   public LoggingEvents()
    {
        
    }

    public void run()
    {
        
      fileContent=Restaurant.fileContent;
      File resultFile=new File("Logger.txt");
        try{
         resultFile.createNewFile();
      } catch(Exception e1){ //exception handling
         System.out.println(e1);
       }
        
   //Using BufferedWriter and FileWriter class to write into the output file
    try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/jannatulferdous/Documents/BlueJ/Project-1/Logger.txt"))) {
     bufferedWriter.write(fileContent);
   } catch (IOException e) {
    // exception handling
   }
 }
}
