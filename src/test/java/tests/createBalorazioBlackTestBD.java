package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Balorazio;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;
import testOperations.TestDataAccess;

public class createBalorazioBlackTestBD {

	static DataAccess sut=new DataAccess();
	static TestDataAccess testDA=new TestDataAccess();
	
	 @Test
	 public void test1() {	
		 Integer idBalorazioa = 345;
	     int puntuazioa = 5;
	     String komentarioa = "Ondo";
	     String data = "02/01/2025";

	     String NAN = "12345678A";
	     Integer rideNumber = 12;
	       	
	     SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	     Date rideDate = null;
	    try {
	   		rideDate = sdf.parse("05/06/2025");
	   	} catch (ParseException e) {
	   		e.printStackTrace();
	    }
	       	
	    Balorazio b = null;
	      	
	  	testDA.open();
	    Driver d = testDA.createDriver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = testDA.createKotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = testDA.createTraveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = testDA.createRide(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	    testDA.close();

	    try {
	    	sut.open();
	       	b = sut.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, NAN, rideNumber);
	       	sut.close();

	       	assertNotNull(b);

	    } catch (reviewAlreadyExistsException e) {
	       	fail();
	    } catch (ratingMoreThanFiveException e) {
	       	 fail();
	    } catch (Exception e) {
	       	 e.printStackTrace();
	       	 fail();
	    } finally {
	       	 testDA.open();
	       	 testDA.removeBalorazio(idBalorazioa);
	         testDA.close();
	    }
	 }
  
    @Test
	public void test2() {	
    	Integer idBalorazioa = 345;
    	int puntuazioa = 5;
    	String komentarioa = "Ondo";
    	String data = "02/01/2025";

    	String NAN = null;
    	Integer rideNumber = 12;
    	Balorazio b = null;

    	try {
    		sut.open();
    	    b = sut.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, NAN, rideNumber);
    	    sut.close();

    	    assertNull(b);

    	    } catch (reviewAlreadyExistsException e) {
    	        fail();
    	    } catch (ratingMoreThanFiveException e) {
    	        fail();
    	    } catch (Exception e) {
    	       e.printStackTrace();
    	       fail();
    	    } finally {
    	        testDA.open();
    	        testDA.removeBalorazio(idBalorazioa);
    	        testDA.close();
    	    }
    	}
    
    @Test
	public void test3() {	
    	Integer idBalorazioa = 345;
    	int puntuazioa = 5;
    	String komentarioa = "Ondo";
    	String data = "02/01/2025";

    	String NAN = "12345678A";
    	Integer rideNumber = -1;
    	Balorazio b = null;

    	try {
    		sut.open();
    	    b = sut.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, NAN, rideNumber);
    	    sut.close();

    	    assertNull(b);

    	    } catch (reviewAlreadyExistsException e) {
    	        fail();
    	    } catch (ratingMoreThanFiveException e) {
    	        fail();
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	        fail();
    	    } finally {
    	        testDA.open();
    	        testDA.removeBalorazio(idBalorazioa);
    	        testDA.close();
    	    }
    	}
    
    @Test
   	public void test4() {	
    	Integer idBalorazioa = 345;
       	int puntuazioa = 5;
       	String komentarioa = "Ondo";
       	String data = "02/01/2025";

       	String NAN = "12345678A";
       	Integer rideNumber = 12;
       	
       	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	Date rideDate = null;
    	try {
    		rideDate = sdf.parse("05/06/2025");
    	} catch (ParseException e) {
    		
    	}
       	
       	Balorazio b = null;
       	
       	testDA.open();
        Driver d = testDA.createDriver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
        Kotxe k = testDA.createKotxe("1234ABC", "Toyota", "Corolla", 5, d);
        Traveler traveler = testDA.createTraveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
        Ride ride = testDA.createRide(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
        Balorazio balorazio = testDA.addBalorazioWith(idBalorazioa, puntuazioa, komentarioa, data, rideNumber, NAN);
        testDA.close();
       	
       	try {
       		sut.open();
       	    b = sut.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, NAN, rideNumber);
       	    sut.close();

       	    } catch (reviewAlreadyExistsException e) {
       	    	assertTrue(true);
       	    } catch (ratingMoreThanFiveException e) {
       	        fail();
       	    } catch (Exception e) {
       	        e.printStackTrace();
       	        fail();
       	    } finally {
       	        testDA.open();
       	        testDA.removeBalorazio(idBalorazioa);
       	        testDA.close();
       	    }
       	}
    
    @Test
   	public void test5() {	
       	Integer idBalorazioa = 345;
       	int puntuazioa = 8;
       	String komentarioa = "Ondo";
       	String data = "02/01/2025";

       	String NAN = "12345678A";
       	Integer rideNumber = 12;
       	
       	Balorazio b = null;
       
       	try {
       		sut.open();
       	    b = sut.createBalorazio(idBalorazioa, puntuazioa, komentarioa, data, NAN, rideNumber);
       	    sut.close();

       	    } catch (reviewAlreadyExistsException e) {
       	    	fail();
       	    } catch (ratingMoreThanFiveException e) {
       	    	assertTrue(true);
       	    } catch (Exception e) {
       	        e.printStackTrace();
       	        fail();
       	    } finally {
       	        testDA.open();
       	        testDA.removeBalorazio(idBalorazioa);
       	        testDA.close();
       	    }
       	}
    
}