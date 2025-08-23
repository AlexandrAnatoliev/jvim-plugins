/**
* JvimEcho - Simple Java echo program for Vim-Java integration testing
* 
* This program receives commant line arguments and echoes them back
* to standart output. Used by Vim for testing Java integration.
*
* @version 0.1.0
* @autor AlexandrAnatoliev
*/
public class JvimEcho {

  /**
  * Main method - entry port for echo program
  *
  * @param args Command line arguments to be echoed back
  *             If no arguments provided, displays usage message
  */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("No input provided");
      System.out.println("Usage: java JvimEcho <text to echo>");
      return;
    }
        
    // Просто возвращаем то, что получили
    System.out.println(String.join(" ", args));
  }
}
