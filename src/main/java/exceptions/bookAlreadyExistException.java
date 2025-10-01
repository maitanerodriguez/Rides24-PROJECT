
package exceptions;
public class bookAlreadyExistException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public bookAlreadyExistException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public bookAlreadyExistException(String s)
  {
    super(s);
  }
}