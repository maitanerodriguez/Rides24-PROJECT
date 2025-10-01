package exceptions;
public class NotEnoughSeatsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public NotEnoughSeatsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public NotEnoughSeatsException(String s)
  {
    super(s);
  }
}

