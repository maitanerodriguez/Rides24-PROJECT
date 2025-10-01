package exceptions;
public class erreklamazioAlreadyExistsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public erreklamazioAlreadyExistsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public erreklamazioAlreadyExistsException(String s)
  {
    super(s);
  }
}
