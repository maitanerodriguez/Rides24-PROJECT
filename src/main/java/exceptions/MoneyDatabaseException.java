package exceptions;
public class MoneyDatabaseException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public MoneyDatabaseException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public MoneyDatabaseException(String s)
  {
    super(s);
  }
}
