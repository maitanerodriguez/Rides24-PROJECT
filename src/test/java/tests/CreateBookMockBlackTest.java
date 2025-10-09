<<<<<<< HEAD
package tests;

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
import domain.Book;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;
import exceptions.NoCashException;
import exceptions.NotEnoughSeatsException;
import exceptions.bookAlreadyExistException;

public class CreateBookMockBlackTest {
static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

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
	
	/*Driver atributuak*/
	String NAN = "12345678Z";
	String log = "maite_izarra";
	String password = "Txakur@2025!";
	String email = "maite.izarra@fikmail.com";
	String izena = "Maite";
	String abizena = "Izarra";
	String jaiotzeData = "1995/06/14";
	int telefonoZenbakia = 699987654;
	String sexua = "f";
	
	/*Kotxe atributuak*/
	String matrikula= "1234GHG";
	String marka="Audi";
	String modeloa="a4";
	int eserlekuKopurua=5;
	
	Driver driver= new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
	Kotxe kotxe= new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
	
	@Test
	//sut.createBook: The Traveler () HAS NOT one book "from" "tp" in that "date"
	public void test1() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The Traveller id is NULL. The test must return null. If an exception is returned createBook method is not well implemented
	public void test2() {
String NAN=null;
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, null)).thenThrow(IllegalArgumentException.class);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			Book b=sut.createBook(NAN, 1, 1);
			sut.close();
			System.out.println(b);
			
			assertNull(b);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}catch(Exception e){
			fail();
		}finally {
			sut.close();
		}
	}
	@Test
	//sut.createBook: Ride number is negative
	public void test3() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(-1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, -1, 1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.creteBook: The seats number is negative
	public void test4() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, -1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The traveller already booked that ride
	public void test5() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			traveler.addBook(r,1);
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			sut.close();
			assertTrue(true);
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The ride doensn't have enough seats
	public void test6() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 6);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			sut.close();
			assertTrue(true);
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The traveller doesn't have enough cash
	public void test7() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 2, driver, kotxe);
			traveler.diruaSartu(1);
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			sut.close();
			assertTrue(true);
		}
	}
}
=======
package tests;

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
import domain.Book;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;
import exceptions.NoCashException;
import exceptions.NotEnoughSeatsException;
import exceptions.bookAlreadyExistException;

public class CreateBookMockBlackTest {
static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

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
	
	/*Driver atributuak*/
	String NAN = "12345678Z";
	String log = "maite_izarra";
	String password = "Txakur@2025!";
	String email = "maite.izarra@fikmail.com";
	String izena = "Maite";
	String abizena = "Izarra";
	String jaiotzeData = "1995/06/14";
	int telefonoZenbakia = 699987654;
	String sexua = "f";
	
	/*Kotxe atributuak*/
	String matrikula= "1234GHG";
	String marka="Audi";
	String modeloa="a4";
	int eserlekuKopurua=5;
	
	Driver driver= new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
	Kotxe kotxe= new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
	
	@Test
	//sut.createBook: The Traveler () HAS NOT one book "from" "tp" in that "date"
	public void test1() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The Traveller id is NULL. The test must return null. If an exception is returned createBook method is not well implemented
	public void test2() {
String NAN=null;
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, null)).thenThrow(IllegalArgumentException.class);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			Book b=sut.createBook(NAN, 1, 1);
			sut.close();
			System.out.println(b);
			
			assertNull(b);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}catch(Exception e){
			fail();
		}finally {
			sut.close();
		}
	}
	@Test
	//sut.createBook: Ride number is negative
	public void test3() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(-1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, -1, 1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.creteBook: The seats number is negative
	public void test4() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, -1);
			sut.close();
			
			assertTrue(true);
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The traveller already booked that ride
	public void test5() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			traveler.addBook(r,1);
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			sut.close();
			assertTrue(true);
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The ride doensn't have enough seats
	public void test6() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 0, driver, kotxe);
			
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 6);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			sut.close();
			assertTrue(true);
		}catch(NoCashException e) {
			fail();
		}
	}
	@Test
	//sut.createBook: The traveller doesn't have enough cash
	public void test7() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998/11/23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
			Traveler traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			Ride r=new Ride(1, rideFrom, rideTo, rideDate, 5, 2, driver, kotxe);
			traveler.diruaSartu(1);
			Mockito.when(db.find(Traveler.class, traveler.getNan())).thenReturn(traveler);
			Mockito.when(db.find(Ride.class, r.getRideNumber())).thenReturn(r);
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			sut.createBook(NAN, 1, 1);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			sut.close();
			assertTrue(true);
		}
	}
}
>>>>>>> 86c3915c58355aa4f81d424c98af32d64bc5300c
