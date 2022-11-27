package web.UI.automation.helper;

public class ThreadSleep {

    static long millis;
    /*
    * This method should be used only for debugging purpose
    */
    public static void For(double seconds) {

        millis = (long) seconds * 1000;

       try{
           Thread.sleep(millis);
       } catch (InterruptedException e) {
           System.out.println("ThreadSleep Interrupted");
       }
    }
}
