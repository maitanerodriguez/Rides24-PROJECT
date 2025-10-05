package tests;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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

public class createBalorazioBlackMockTest {
	
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
    }
	
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
	    
	    Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = new Ride(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	       	
	    Balorazio b = null;
	    
	    Mockito.when(db.find(Driver.class, d.getNAN())).thenReturn(d);
	    Mockito.when(db.find(Kotxe.class, k.getMatrikula())).thenReturn(k);
	    Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date rideDate = null;
	    try {
	   		rideDate = sdf.parse("05/06/2025");
	   	} catch (ParseException e) {
	   		e.printStackTrace();
	    }
	     
	    Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = new Traveler(NAN, "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = new Ride(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	       	
	    Balorazio b = null;
	    
	    Mockito.when(db.find(Driver.class, d.getNAN())).thenReturn(d);
	    Mockito.when(db.find(Kotxe.class, k.getMatrikula())).thenReturn(k);
	    Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date rideDate = null;
	    try {
	   		rideDate = sdf.parse("05/06/2025");
	   	} catch (ParseException e) {
	   		e.printStackTrace();
	    }
	     
	    Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = new Traveler(NAN, "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = new Ride(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	       	
	    Balorazio b = null;
	    
	    Mockito.when(db.find(Driver.class, d.getNAN())).thenReturn(d);
	    Mockito.when(db.find(Kotxe.class, k.getMatrikula())).thenReturn(k);
	    Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
	   		e.printStackTrace();
	    }
	     
	    Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = new Traveler(NAN, "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = new Ride(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	       	
	    traveler.addBalorazio(idBalorazioa, puntuazioa, komentarioa, data, ride);
	    
	    Balorazio b = null;
	    
	    Mockito.when(db.find(Driver.class, d.getNAN())).thenReturn(d);
	    Mockito.when(db.find(Kotxe.class, k.getMatrikula())).thenReturn(k);
	    Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date rideDate = null;
	    try {
	   		rideDate = sdf.parse("05/06/2025");
	   	} catch (ParseException e) {
	   		e.printStackTrace();
	    }
	     
	    Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
	    Kotxe k = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
	    Traveler traveler = new Traveler(NAN, "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
	    Ride ride = new Ride(rideNumber, "Donostia", "Bilbo", rideDate, 4, 30.00f, d, k);
	       	
	    
	    Balorazio b = null;
	    
	    Mockito.when(db.find(Driver.class, d.getNAN())).thenReturn(d);
	    Mockito.when(db.find(Kotxe.class, k.getMatrikula())).thenReturn(k);
	    Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
	    Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
	    } 
	 }
    
    
    
}
