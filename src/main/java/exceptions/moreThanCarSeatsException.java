package exceptions;
public class moreThanCarSeatsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public moreThanCarSeatsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public moreThanCarSeatsException(String s)
  {
    super(s);
  }
}
