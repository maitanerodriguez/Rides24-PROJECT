package exceptions;
public class carAlreadyExistsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public carAlreadyExistsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public carAlreadyExistsException(String s)
  {
    super(s);
  }
}
