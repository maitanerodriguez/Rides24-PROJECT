package businessLogic;

import java.util.Date;

import java.util.List;

//import domain.Booking;

import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Alerta;
import domain.Balorazio;
import domain.Book;
import domain.Driver;
import domain.Erreklamazio;
import domain.Kotxe;
import domain.Mugimendua;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;
import exceptions.bookAlreadyExistException;
import exceptions.carAlreadyExistsException;
import exceptions.erreklamazioAlreadyExistsException;
import exceptions.moreThanCarSeatsException;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;
import exceptions.rideIsNotConfirmed;
import exceptions.MoneyDatabaseException;
import exceptions.NoCashException;
import exceptions.NotEnoughSeatsException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String NAN, String matrikula) throws RideMustBeLaterThanTodayException, RideAlreadyExistException,moreThanCarSeatsException;
	
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod
	public User login (String log, String pass);
	
	@WebMethod
	public void register (String mota,String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua);
	
	@WebMethod
	public boolean existitu (String NAN, String email, String log, int telefonoZenbakia);
	
	@WebMethod
	public void diruaSartu(int kant, String NAN);
	
	@WebMethod
	public Book createBook(String NAN, Integer rideNumber, int seats)throws bookAlreadyExistException, NoCashException,NotEnoughSeatsException;
	
	@WebMethod
	public void denyBook(Integer bookNumber, Integer rideNumber, String NAN);
	
	@WebMethod
	public List<Book> getBooks(String NAN);
	
	@WebMethod
	public List<Book> getBooksFromRide(Integer rideNumber);
	
	@WebMethod
	public List<Ride> getRidesFromDriver(String NAN);
	
	@WebMethod
	public Kotxe createCar(String marka, String modeloa, int eserlekuKopurua, String matrikula, String NAN) throws carAlreadyExistsException;
	
	@WebMethod
	public List<Kotxe> getCars(String NAN);
	
	@WebMethod
	public List<Mugimendua> getMugimenduak(String NAN);
	
	@WebMethod
	public float eskuratuDirua(String NAN);
	
	@WebMethod
	public void confirmBook(Integer bookNumber, String NAN)throws rideIsNotConfirmed;
	
	@WebMethod
	public void cancelRide(Integer rideNumber, String NAN);
	
	@WebMethod
	public void confirmRide(Integer rideNumber);
	
	@WebMethod
	public Alerta createAlert(String nondik, String nora, Date data, String NAN)throws alertAlreadyExists,RideMustBeLaterThanTodayException ;
	
	@WebMethod
	public List<Ride> getRidesRelatedToAlerts(String NAN);
	
	@WebMethod
	public void updateLastLogged(String NAN, String lastLogged);
	
	@WebMethod
	public Balorazio createBalorazio(Integer idBalorazio, int puntuazio, String komentario, String data, String NAN, Integer rideNumber) throws reviewAlreadyExistsException, ratingMoreThanFiveException;
	
	@WebMethod
	public List<Balorazio> getBalorazioak(String NAN);
	
	@WebMethod
	public List<Balorazio> getBalorazioakFromRide(Integer rideNumber);
	
	@WebMethod
	public Erreklamazio createErreklamazio (String deskribapena, String data,String erreklamatzaileNAN, String erreklamatuNAN, Integer rideNumber)throws erreklamazioAlreadyExistsException;
	
	@WebMethod
	public List<Erreklamazio> getReceivedReports(String NAN);
	
	@WebMethod
	public List<Erreklamazio> getCreatedReports(String NAN);
	
	@WebMethod
	public List<Erreklamazio> getReportsFromRide(Integer rideNumber);
	
	@WebMethod
	public void deleteUser(String NAN);
	
	@WebMethod
	public List <Erreklamazio> getReports();
	
	@WebMethod
	public void acceptReport(Integer idReport);
	
	@WebMethod
	public void denyReport(Integer idReport);
	}
