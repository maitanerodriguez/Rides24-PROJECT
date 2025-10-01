package exceptions;
public class ratingMoreThanFiveException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public ratingMoreThanFiveException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public ratingMoreThanFiveException(String s)
  {
    super(s);
  }
}
