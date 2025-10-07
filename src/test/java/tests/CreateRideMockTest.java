package tests;

import static org.junit.Assert.assertEquals;
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
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.moreThanCarSeatsException;

public class CreateRideMockTest {
	
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
	
	
	Driver driver;

	@Test
	//sut.createRide:  The Driver("iker driver", "driver1@gmail.com") HAS one ride "from" "to" in that "date". 
	public void test1()  {

		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998-11-23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";

		//String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver
		
		String matrikula= "1234GHG";
		String marka="Audi";
		String modeloa="a4";
		int eserlekuKopurua=5;
		
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
			
			 driver=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
			
			 Kotxe k1= new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
			 driver.addRide(rideFrom, rideTo, rideDate, 0, 0, k1);
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(rideFrom, rideTo, rideDate, 0, 0, NAN, matrikula);
			sut.close();
			
			fail();
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
				fail();
			} catch (moreThanCarSeatsException e) {
				// TODO Auto-generated catch block
				fail();
			} 
	} 
	@Test
	//sut.createRide:  The Driver("Aitor Fernandez", "driver1@gmail.com") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		//define parameters
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998-11-23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String matrikula= "1234GHG";
		
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
			Driver  driver1=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);

			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver1.getEmail())).thenReturn(driver1);
					
			//invoke System Under Test (sut)  
			sut.open();
			Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, NAN, matrikula);
			sut.close();
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			//ride is in DB. The persist operation has been invoked.
			//boolean existRide=testDA.existRide(driverEmail,ride.getFrom(), ride.getTo(), ride.getDate());
				
			//assertTrue(existRide);
			//testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			   fail();
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

				fail();
			//redone state of the system (create object in the database)
			
			} catch (moreThanCarSeatsException e) {
				fail();
			} 
	} 
	@Test
	//sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test3() {
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String NAN=null;
				String matrikula=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;
				try {
					rideDate = sdf.parse("05/10/2025");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Mockito.when(db.find(Driver.class, null)).thenThrow(IllegalArgumentException.class);
				
				
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, NAN, matrikula);
				System.out.println("ride "+ride);
				
				//verify the results
				assertNull(ride);
				
				
			   } catch (RideAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();

				} catch (RideMustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
					fail();

				} catch (Exception e) {
				// TODO Auto-generated catch block
					fail();

				} finally {
					sut.close();
				}
			
			   } 
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.
	
	public void test4() {
		String NAN = "98765432X";
		String log = "ander_eguna";
		String password = "Katu@2025!";
		String email = "ander.eguna@fikmail.com";
		String izena = "Ander";
		String abizena = "Eguna";
		String jaiotzeData = "1998-11-23";
		int telefonoZenbakia = 688123456;
		String sexua = "m";
		
		String matrikula= "1234GHG";
		
		String rideFrom=null;
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		try {
			//configure the state through mocks 

			driver=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
	        Mockito.when(db.find(Driver.class, driver.getEmail())).thenReturn(driver);
			
	        //invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, NAN, matrikula);
			sut.close();			
			//verify the results
			System.out.println("Ride: "+ride);
			assertNull(ride);
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
   }

}
