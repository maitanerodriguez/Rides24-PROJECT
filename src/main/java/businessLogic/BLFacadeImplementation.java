package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new DataAccess();
		    
		//dbManager.close();

		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    @WebMethod public List<String> getDepartCities(){
    	dbManager.open();	
		
		 List<String> departLocations=dbManager.getDepartCities();		

		dbManager.close();
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	@WebMethod public List<String> getDestinationCities(String from){
		dbManager.open();	
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		dbManager.close();
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String NAN, String matrikula ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException,moreThanCarSeatsException{
	   
		dbManager.open();
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, NAN, matrikula);		
		dbManager.close();
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Ride> getRides(String from, String to, Date date){
		dbManager.open();
		List<Ride>  rides=dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	@WebMethod 
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		dbManager.open();
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}
	
	
	public void close() {
		DataAccess dB4oManager=new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    
    @WebMethod
    public User login (String log, String pass) {
    	dbManager.open();
    	User u= dbManager.login(log, pass);
    	//dbManager.close();
    	return u;
    }
    @WebMethod
    public void register (String mota, String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
    	dbManager.open();
        dbManager.register(mota, NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
        dbManager.close();
    }
    @WebMethod
    public boolean existitu(String NAN, String email, String log, int telefonoZenbakia) {
    	dbManager.open();
    	return dbManager.existitu(NAN,email,log,telefonoZenbakia);
    }
    @WebMethod
    public void diruaSartu(int kant, String NAN){
    	dbManager.open();
    	dbManager.diruaSartu(kant, NAN);
    	dbManager.close();
    }
    @WebMethod
    public Book createBook(String NAN, Integer rideNumber, int seats) throws bookAlreadyExistException, NoCashException, NotEnoughSeatsException {
    	dbManager.open();
    	Book book=dbManager.createBook(NAN, rideNumber, seats);
    	dbManager.close();
    	return book;
    }
    @WebMethod
    public void denyBook(Integer bookNumber, Integer rideNumber, String NAN) {
    	dbManager.open();
    	dbManager.denyBook(bookNumber,rideNumber, NAN);
    	dbManager.close();
    }
    @WebMethod
    public List<Book> getBooks(String NAN){
    	dbManager.open();
    	List<Book> books=dbManager.getBooks(NAN);
    	return books;
    }
    @WebMethod
    public List<Ride> getRidesFromDriver(String NAN){
    	dbManager.open();
    	List<Ride> rides=dbManager.getRidesFromDriver(NAN);
    	dbManager.close();
    	return rides;
    }
    @WebMethod
    public List<Book> getBooksFromRide(Integer rideNumber){
    	dbManager.open();
    	List<Book> books=dbManager.getBooksFromRide(rideNumber);
    	dbManager.close();
    	return books;
    }
    @WebMethod
    public Kotxe createCar(String marka, String modeloa, int eserlekuKopurua, String matrikula, String NAN) throws carAlreadyExistsException {
    	dbManager.open();
    	Kotxe kotxe=dbManager.createCar(marka, modeloa ,eserlekuKopurua, matrikula, NAN);
    	dbManager.close();
    	return kotxe;
    }
    @WebMethod
	public List<Kotxe> getCars(String NAN){
		dbManager.open();
    	List<Kotxe> kotxeak=dbManager.getCars(NAN);
    	dbManager.close();
    	return kotxeak;
	}
    @WebMethod
	public List<Mugimendua> getMugimenduak(String NAN){
		dbManager.open();
    	List<Mugimendua> mugimenduak=dbManager.getMugimenduak(NAN);
    	dbManager.close();
    	return mugimenduak;
	}
	
    @WebMethod
	public float eskuratuDirua(String NAN) {
		dbManager.open();
    	float dirua=dbManager.eskuratuDirua(NAN);
    	dbManager.close();
    	return dirua;
	}
    @WebMethod
	public void confirmBook(Integer bookNumber, String NAN)throws rideIsNotConfirmed {
		dbManager.open();
		dbManager.confirmBook(bookNumber, NAN);
		dbManager.close();
	}
    @WebMethod
	public void cancelRide(Integer rideNumber, String NAN) {
		dbManager.open();
		dbManager.cancelRide(rideNumber, NAN);
		dbManager.close();
	}
    @WebMethod
	public void confirmRide(Integer rideNumber) {
		dbManager.open();
		dbManager.confirmRide(rideNumber);
		dbManager.close();
	}
    @WebMethod
	public Alerta createAlert(String nondik, String nora,Date data, String NAN)throws alertAlreadyExists, RideMustBeLaterThanTodayException {
		dbManager.open();
    	Alerta alert=dbManager.createAlert(nondik, nora, data, NAN);
    	dbManager.close();
    	return alert;
	}
    @WebMethod
	public List<Ride> getRidesRelatedToAlerts(String NAN){
		dbManager.open();
    	List<Ride> rides=dbManager.getRidesRelatedToAlerts(NAN);
    	dbManager.close();
    	return rides;
	}
    @WebMethod
	public void updateLastLogged(String NAN, String lastLogged) {
		
	dbManager.open();
	dbManager.updateLastLogged(NAN, lastLogged);
	dbManager.close();
	}
    @WebMethod
	public Balorazio createBalorazio(int puntuazio, String komentario, String data,String NAN, Integer rideNumber) throws reviewAlreadyExistsException, ratingMoreThanFiveException {
		dbManager.open();
    	Balorazio b=dbManager.createBalorazio(puntuazio, komentario, data,NAN, rideNumber);
    	dbManager.close();
    	return b;
	}
    @WebMethod
	public List<Balorazio> getBalorazioak(String NAN){
		dbManager.open();
    	List<Balorazio> balorazioak=dbManager.getBalorazioak(NAN);
    	dbManager.close();
    	return balorazioak;
	}
    @WebMethod
	public List<Balorazio> getBalorazioakFromRide(Integer rideNumber){
		dbManager.open();
		List<Balorazio> balorazioak=dbManager.getBalorazioakFromRide(rideNumber);
		dbManager.close();
		return balorazioak;
	}
    @WebMethod
	public Erreklamazio createErreklamazio (String deskribapena, String data,String erreklamatzaileNAN, String erreklamatuNAN, Integer rideNumber)throws erreklamazioAlreadyExistsException {
		dbManager.open();
		Erreklamazio erreklamazioa=dbManager.createErreklamazio(deskribapena, data, erreklamatzaileNAN, erreklamatuNAN, rideNumber);
		dbManager.close();
		return erreklamazioa;
	}
    @WebMethod
	public List<Erreklamazio> getReceivedReports(String NAN){
		dbManager.open();
		List<Erreklamazio> jasotakoErreklamazioak=dbManager.getReceivedReports(NAN);
		dbManager.close();
		return jasotakoErreklamazioak;
	}
    @WebMethod
	public List<Erreklamazio> getCreatedReports(String NAN){
		dbManager.open();
		List<Erreklamazio> sortutakoErreklamazioak=dbManager.getCreatedReports(NAN);
		dbManager.close();
		return sortutakoErreklamazioak;
	}
    @WebMethod
	public List<Erreklamazio> getReportsFromRide(Integer rideNumber){
		dbManager.open();
		List<Erreklamazio> erreklamazioak=dbManager.getReportsFromRide(rideNumber);
		dbManager.close();
		return erreklamazioak;
	}
    @WebMethod
	public void deleteUser(String NAN) {
		dbManager.open();
		dbManager.deleteUser(NAN);
		dbManager.close();
	}
    @WebMethod
	public List <Erreklamazio> getReports(){
		dbManager.open();
		List <Erreklamazio> erreklamazioak=dbManager.getReports();
		dbManager.close();
		return erreklamazioak;
	}
    @WebMethod
	public void acceptReport(Integer idReport) {
		dbManager.open();
		dbManager.acceptReport(idReport);
		dbManager.close();
	}
    @WebMethod
	public void denyReport(Integer idReport) {
		dbManager.open();
		dbManager.denyReport(idReport);
		dbManager.close();
	}
}

