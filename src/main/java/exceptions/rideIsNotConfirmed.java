package exceptions;
public class rideIsNotConfirmed extends Exception {
 private static final long serialVersionUID = 1L;
 
 public rideIsNotConfirmed()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public rideIsNotConfirmed(String s)
  {
    super(s);
  }
}
