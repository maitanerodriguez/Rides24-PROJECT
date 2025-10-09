package testOperations;
//
import java.util.Date;

import configuration.ConfigXML;
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
		public boolean existTraveler(String NAN) {
			dbManagerTest.open();
			boolean existTraveler=dbManagerTest.existTraveler(NAN);
			dbManagerTest.close();
			return existTraveler;
		}
		
		public Driver addDriverWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, String from, String to,  Date date, int nPlaces, float price, String matrikula) {
			dbManagerTest.open();
			Driver driver=dbManagerTest.addDriverWithRide(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua, from, to, date, nPlaces, price, matrikula);
			dbManagerTest.close();
			return driver;

		}
		public Traveler addTravelerWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, int nSeats, Integer rideNumber,String from, String to,  Date date, int nPlaces, float price, Kotxe k, Driver d) {
			dbManagerTest.open();
			Traveler traveler=dbManagerTest.addTravelerWithRide(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua, nSeats, rideNumber, from, to, date, nPlaces, price, k, d);
			dbManagerTest.close();
			return traveler;
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
		


}
