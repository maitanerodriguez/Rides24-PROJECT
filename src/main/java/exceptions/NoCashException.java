package exceptions;
public class NoCashException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NoCashException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public NoCashException(String s)
  {
    super(s);
  }
}