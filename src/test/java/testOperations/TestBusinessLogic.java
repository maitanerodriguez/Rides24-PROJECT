package testOperations;
//
import java.util.Date;

import configuration.ConfigXML;
import domain.Balorazio;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;

public class TestBusinessLogic {
	TestDataAccess dbManagerTest;
 	
    
	   public TestBusinessLogic()  {
			
			System.out.println("Creating TestBusinessLogic instance");
			ConfigXML c=ConfigXML.getInstance();
			dbManagerTest=new TestDataAccess(); 
			dbManagerTest.close();
		}
		
		 
		public boolean removeDriver(String driverEmail) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeDriver(driverEmail);
			dbManagerTest.close();
			return b;

		}
		
		public Driver createDriver(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
			dbManagerTest.open();
			Driver driver=dbManagerTest.createDriver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			dbManagerTest.close();
			return driver;

		}
		
		public boolean existDriver(String email) {
			dbManagerTest.open();
			boolean existDriver=dbManagerTest.existDriver(email);
			dbManagerTest.close();
			return existDriver;

		}
		
		public Driver addDriverWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, String from, String to,  Date date, int nPlaces, float price, String matrikula) {
			dbManagerTest.open();
			Driver driver=dbManagerTest.addDriverWithRide(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua, from, to, date, nPlaces, price, matrikula);
			dbManagerTest.close();
			return driver;

		}
		public boolean existRide(String email, String from, String to, Date date) {
			dbManagerTest.open();
			boolean b=dbManagerTest.existRide(email, from, to, date);
			dbManagerTest.close();
			return b;
		}
		public Ride removeRide(String email,String from, String to, Date date ) {
			dbManagerTest.open();
			Ride r=dbManagerTest.removeRide( email, from,  to,  date );
			dbManagerTest.close();
			return r;
		}
		public boolean existTraveler(String NAN) {
			dbManagerTest.open();
			boolean b=dbManagerTest.existTraveler(NAN);
			dbManagerTest.close();
			return b;
		}
		public Traveler createTraveler(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
			dbManagerTest.open();
			Traveler traveler=dbManagerTest.createTraveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			dbManagerTest.close();
			return traveler;
		}
		public boolean removeTraveler(String travelerNAN) {
			dbManagerTest.open();
			boolean b=dbManagerTest.removeTraveler(travelerNAN);
			dbManagerTest.close();
			return b;
	    }
		public Traveler addTravelerWithAlert(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, String from, String to,  Date date) {
			dbManagerTest.open();
			Traveler traveler=dbManagerTest.addTravelerWithAlert(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua, from, to, date);
			dbManagerTest.close();
			return traveler;
		}
		public boolean existAlert(String NAN, String from, String to, Date date) {
			dbManagerTest.open();
			boolean b=dbManagerTest.existAlert(NAN, from, to, date);
			dbManagerTest.close();
			return b;
		}
		
		public Balorazio createBalorazio(Integer idBalorazioa, int puntuazioa, String komentarioa, String data, Ride ride, Traveler traveler) {
			dbManagerTest.open();
			Balorazio balorazio = dbManagerTest.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, ride, traveler);
			dbManagerTest.close();
			return balorazio;
		}
		
		public boolean removeBalorazio(Integer idBalorazioa) {
		    dbManagerTest.open();
		    boolean removed = dbManagerTest.removeBalorazio(idBalorazioa);
		    dbManagerTest.close();
		    return removed;
		}
		
		public Integer addRideNumberWithBalorazio(Integer idBalorazioa, int puntuazioa, String komentarioa, String data, Integer rideNumber, String NAN) {
			dbManagerTest.open();
			Integer rNumber = dbManagerTest.addRideNumberWithBalorazio(idBalorazioa, puntuazioa, komentarioa, data, rideNumber, NAN);
			dbManagerTest.close();
			return rNumber;
		}
		
		
		public Ride createRide(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k) {
			dbManagerTest.open();
			Ride ride = dbManagerTest.createRide(rideNumber, from, to, date, nPlaces, price, driver, k);
			dbManagerTest.close();
			return ride;
	    }
	
		public boolean removeRide(Integer rideNumber) {
			dbManagerTest.open();
		    boolean removed = dbManagerTest.removeBalorazio(rideNumber);
		    dbManagerTest.close();
		    return removed;
	    }
		
		public Kotxe createKotxe(String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver) {
			dbManagerTest.open();
			Kotxe kotxe = dbManagerTest.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
			dbManagerTest.close();
			return kotxe;
	    }
		
		public boolean removeKotxe(String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver) {
			dbManagerTest.open();
			boolean removed = dbManagerTest.removeKotxe(matrikula);
			dbManagerTest.close();
			return removed;
	    }
		
		
		public boolean existBalorazio(Integer idBalorazio, int puntuazioa, String komentarioa, String data, String NAN, Integer rideNumber) {
			dbManagerTest.open();
			boolean exist = dbManagerTest.existBalorazio(idBalorazio, puntuazioa, komentarioa, data, NAN, rideNumber);
			dbManagerTest.close();
			return exist;
		}
}
