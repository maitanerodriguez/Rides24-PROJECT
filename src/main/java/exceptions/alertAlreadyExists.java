package exceptions;
public class alertAlreadyExists extends Exception {
 private static final long serialVersionUID = 1L;
 
 public alertAlreadyExists()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public alertAlreadyExists(String s)
  {
    super(s);
  }
}
