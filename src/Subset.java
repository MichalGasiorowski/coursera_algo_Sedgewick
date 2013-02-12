
import java.util.NoSuchElementException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */
public class Subset {

    public static void main(String[] args) {
        //StdOut.println(args[0]);
        int k = Integer.parseInt(args[0]);   
        if (k == 0) { return; } 
        RandomizedQueue<String> rdq = new RandomizedQueue<String>();
        String ss = "";
        //StdOut.println("-------"); 
        try {
            while ((ss = StdIn.readString()) != "") {
                rdq.enqueue(ss);
                //StdOut.print(ss + " ");                        
            }  
        }
        catch (NoSuchElementException ex) {
            
        }
        finally {
            //StdOut.println("-------");
            int i = 1;
            for (String str : rdq) {
                StdOut.println(str);
                if (++i > k) {
                    break;
                }
            }
        }
    }
}
