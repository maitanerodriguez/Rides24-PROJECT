
package tests;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

import dataAccess.DataAccess;
import domain.Book;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;
import exceptions.NoCashException;
import exceptions.NotEnoughSeatsException;
import exceptions.bookAlreadyExistException;
import testOperations.TestDataAccess;

public class CreateBookBlackTestBD {
	static DataAccess sut=new DataAccess();
	 
	//additional operations needed to execute the test 
	static TestDataAccess testDA=new TestDataAccess();
	
	String NAN = "12345678Z";
	String log = "maite_izarra";
	String password = "Txakur@2025!";
	String email = "maite.izarra@fikmail.com";
	String izena = "Maite";
	String abizena = "Izarra";
	String jaiotzeData = "1995/06/14";
	int telefonoZenbakia = 699987654;
	String sexua = "f";
	
	String matrikula= "1234GHG";
	String marka="Audi";
	String modeloa="a4";
	int eserlekuKopurua=5;
	
	private Driver driver=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
	private Kotxe kotxe= new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
	
	@Test
	//sut.createBook: The Traveler () HAS NOT one book "from" "tp" in that "date"
	public void test1() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT = "98765432X";
		String log1 = "ander_eguna";
		String password1 = "Katu@2025!";
		String email1 = "ander.eguna@fikmail.com";
		String izena1 = "Ander";
		String abizena1 = "Eguna";
		String jaiotzeData1 = "1998-11-23";
		int telefonoZenbakia1 = 688123456;
		String sexua1 = "m";
		
		int rideNumber=17;
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
			testDA.open();
			testDA.createTraveler(NANT, log1, password1, email1, izena1, abizena1, jaiotzeData1, telefonoZenbakia1, sexua1);
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,0 , d, k);
			testDA.close();
			
			sut.open();
			Book b= sut.createBook(NANT, rideNumber, 1);
			sut.close();
			System.out.println(b);
			assertNotNull(b);
			//book datubasean dago
			testDA.open();
			boolean existBook=testDA.existBook(NANT, b.getRide());
			assertTrue(existBook);
			testDA.close();
			
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			System.out.println("2");
			fail();
		}catch(NoCashException e) {
			System.out.println("3");
			fail();
		}finally {
			testDA.open();
			testDA.removeTraveler(NANT); //erreserba eta traveler kentzen dira
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();
			
		}
	}
	@Test
	//sut.createBook: The Traveller id is NULL. The test must return null. If an exception is returned createBook method is not well implemented
	public void test2() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT=null;
		
		Integer rideNumber=17;
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
			//Driver d= new Driver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Kotxe k= new Kotxe(matrikula,marka,modeloa,eserlekuKopurua, d);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			testDA.open();
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,0 , d, k);
			testDA.close();
		
			//String NAN, Integer rideNumber, int seats
			sut.open();
			Book b=sut.createBook(NANT, rideNumber, 1);
			//System.out.println(b);
			
			
		}catch(bookAlreadyExistException e) {
			System.out.println("1");
			fail();
		}catch(NotEnoughSeatsException e) {
			System.out.println("2");
			fail();
		}catch(NoCashException e) {
			System.out.println("3");
			fail();
		}catch(Exception e){
			assertTrue(true);
		}finally {
			sut.close();
			testDA.open();
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();   
			
		}
	}
	@Test
	//sut.createBook: Ride number is negative
	public void test3() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT = "98765432X";
		String log1 = "ander_eguna";
		String password1 = "Katu@2025!";
		String email1 = "ander.eguna@fikmail.com";
		String izena1 = "Ander";
		String abizena1 = "Eguna";
		String jaiotzeData1 = "1998-11-23";
		int telefonoZenbakia1 = 688123456;
		String sexua1 = "m";
		
		int rideNumber=-1;
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
			testDA.open();
			testDA.createTraveler(NANT, log1, password1, email1, izena1, abizena1, jaiotzeData1, telefonoZenbakia1, sexua1);
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,0 , d, k);
			testDA.close();
			
			sut.open();
			Book b= sut.createBook(NANT, rideNumber, 1);
			sut.close();
			System.out.println(b);
			assertNotNull(b);
			//book datubasean dago
			testDA.open();
			boolean existBook=testDA.existBook(NANT, b.getRide());
			assertTrue(existBook);
			testDA.close();
			
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			System.out.println("2");
			fail();
		}catch(NoCashException e) {
			System.out.println("3");
			fail();
		}finally {
			testDA.open();
			testDA.removeTraveler(NANT); //erreserba eta traveler kentzen dira
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();
			
		}
	}
	@Test
	//sut.creteBook: The seats number is negative
	public void test4() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT = "98765432X";
		String log1 = "ander_eguna";
		String password1 = "Katu@2025!";
		String email1 = "ander.eguna@fikmail.com";
		String izena1 = "Ander";
		String abizena1 = "Eguna";
		String jaiotzeData1 = "1998-11-23";
		int telefonoZenbakia1 = 688123456;
		String sexua1 = "m";
		
		int rideNumber=17;
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
			testDA.open();
			testDA.createTraveler(NANT, log1, password1, email1, izena1, abizena1, jaiotzeData1, telefonoZenbakia1, sexua1);
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,0 , d, k);
			testDA.close();
			
			sut.open();
			Book b= sut.createBook(NANT, rideNumber,-1);
			sut.close();
			System.out.println(b);
			assertNotNull(b);
			//book datubasean dago
			testDA.open();
			boolean existBook=testDA.existBook(NANT, b.getRide());
			assertTrue(existBook);
			testDA.close();
			
		}catch(bookAlreadyExistException e) {
			fail();
		}catch(NotEnoughSeatsException e) {
			System.out.println("2");
			fail();
		}catch(NoCashException e) {
			System.out.println("3");
			fail();
		}finally {
			testDA.open();
			testDA.removeTraveler(NANT); //erreserba eta traveler kentzen dira
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();
			
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
		String jaiotzeData = "1998-11-23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		int rideNumber=1;
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
		boolean existTraveler=false;
		try {
			testDA.open();
			existTraveler=testDA.existTraveler(NAN);
			testDA.addTravelerWithRide(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua, 1, rideNumber, rideFrom, rideTo, rideDate,5,0, kotxe, driver);
			testDA.close();
			
			sut.open();
			Book b= sut.createBook(NAN, rideNumber, 1);
			sut.close();
			
			fail();
		}catch(bookAlreadyExistException e) {
			sut.close();
			assertTrue(true);
		}catch(NotEnoughSeatsException e) {
			fail();
		}catch(NoCashException e) {
			fail();
		}finally {
			testDA.open();
			if(existTraveler) {
				testDA.removeBookFromTraveler(NAN, rideNumber);
			}else {
				testDA.removeTraveler(NAN);
			}
		}
	}
	@Test
	//sut.createBook: The ride doensn't have enough seats
	public void test6() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT = "98765432X";
		String log1 = "ander_eguna";
		String password1 = "Katu@2025!";
		String email1 = "ander.eguna@fikmail.com";
		String izena1 = "Ander";
		String abizena1 = "Eguna";
		String jaiotzeData1 = "1998-11-23";
		int telefonoZenbakia1 = 688123456;
		String sexua1 = "m";
		
		Integer rideNumber=17;
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
			//Driver d= new Driver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Kotxe k= new Kotxe(matrikula,marka,modeloa,eserlekuKopurua, d);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			testDA.open();
			Traveler t= testDA.createTraveler(NANT, log1, password1, email1, izena1, abizena1, jaiotzeData1,telefonoZenbakia1, sexua1);
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,0 , d, k);
			testDA.close();
			
			sut.open();
			Book b=sut.createBook(NANT, rideNumber, 6);
			//System.out.println(b);
		}catch(bookAlreadyExistException e) {
			
			fail();
		}catch(NotEnoughSeatsException e) {
			
			assertTrue(true);
		}catch(NoCashException e) {
			fail();
		}catch(Exception e){
			fail();
		}finally {
			sut.close();
			testDA.open();
			testDA.removeTraveler(NANT); //erreserba eta traveler kentzen dira
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();   
			
		}
	}
	@Test
	//sut.createBook: The traveller doesn't have enough cash
	public void test7() {
		String NAND = "12345678Z";
		String log = "maite_izarra";
		String password = "Txakur@2025!";
		String email = "maite.izarra@fikmail.com";
		String izena = "Maite";
		String abizena = "Izarra";
		String jaiotzeData = "1995/06/14";
		int telefonoZenbakia = 699987654;
		String sexua = "f";
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
		String NANT = "98765432X";
		String log1 = "ander_eguna";
		String password1 = "Katu@2025!";
		String email1 = "ander.eguna@fikmail.com";
		String izena1 = "Ander";
		String abizena1 = "Eguna";
		String jaiotzeData1 = "1998-11-23";
		int telefonoZenbakia1 = 688123456;
		String sexua1 = "m";
		
		Integer rideNumber=17;
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
			//Driver d= new Driver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			//Kotxe k= new Kotxe(matrikula,marka,modeloa,eserlekuKopurua, d);
			//Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k
			testDA.open();
			Traveler t= testDA.createTraveler(NANT, log1, password1, email1, izena1, abizena1, jaiotzeData1, telefonoZenbakia1, sexua1);
			t.diruaSartu(1);
			Driver d=testDA.createDriver(NAND, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			Kotxe k=testDA.createKotxe(matrikula, marka, modeloa, eserlekuKopurua, d);
			Ride r=testDA.createRide(rideNumber, rideFrom, rideTo, rideDate, 5 ,2 , d, k);
			testDA.close();
			
			sut.open();
			Book b=sut.createBook(NANT, rideNumber, 1);
			//System.out.println(b);
		}catch(bookAlreadyExistException e) {
			
			fail();
		}catch(NotEnoughSeatsException e) {
			
			fail();
		}catch(NoCashException e) {
			assertTrue(true);
		}catch(Exception e){
			fail();
		}finally {
			sut.close();
			testDA.open();
			testDA.removeTraveler(NANT); //erreserba eta traveler kentzen dira
			testDA.removeRideFromDB(rideNumber);
			testDA.removeDriver(NAND);
			testDA.removeCarFromDB(matrikula);
			testDA.close();   
			
		}
	}
		
}



