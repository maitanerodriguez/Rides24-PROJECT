package tests;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Alerta;
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;
import testOperations.TestDataAccess;

public class createAlertWhiteTestBD {
	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();



    @Test
    public void test1() throws ParseException  {
    	String nondik = "Madrid";
    	String nora ="Bilbo";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse("06/10/2026");
		java.sql.Date rideDate = new java.sql.Date(parsed.getTime());	
		
		try {
			
			//define parameters
			//invoke System Under Test (sut)  
			sut.open();
		    Alerta a = sut.createAlert(nondik, nora, rideDate, "12345678A");
			sut.close();
			
			assertNull(a);
			
		   } catch (alertAlreadyExists e) {
			 //verify the results
				sut.close();
				fail();
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			sut.close();
			fail();
		} 
	} 
   

    @Test
    public void test2() throws ParseException {
    	String travelerNAN="12345678A";
    	String travelerLog = "traveler1";
    	String travelerpass = "123";
    	String travelerEmail = "traveler1@gmail.com";
    	String travelerIzena = "Ibai";
    	String travelerAbizena = "Martin";
    	String travelerjaiotze = "01/07/1997";
    	int travelertelefono = 612332456;
    	String travelersexu = "male";
    	
    	String nondik = "Madrid";
    	String nora ="Bilbo";
		
		
		boolean travelerCreated=false;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse("01/07/1997");
		java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
		
		try {
			
			//define parameters
			testDA.open();
			if (!testDA.existTraveler(travelerNAN)) {
				testDA.createTraveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);
			    travelerCreated=true;
			}
			testDA.close();		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createAlert(nondik, nora, rideDate, travelerNAN);
			sut.close();
			
			fail();
			
		   } catch (RideMustBeLaterThanTodayException  e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (alertAlreadyExists e) {
				sut.close();
				fail();
		} finally {
				  //Remove the created objects in the database (cascade removing)   
				testDA.open();
				  if (travelerCreated) 
					  testDA.removeTraveler(travelerNAN);
		          testDA.close();
		        }
		   } 

    
    @Test
    public void test3() throws ParseException {
    	//define paramaters
    	String travelerNAN="12345678A";
    	String travelerLog = "traveler1";
    	String travelerpass = "123";
    	String travelerEmail = "traveler1@gmail.com";
    	String travelerIzena = "Ibai";
    	String travelerAbizena = "Martin";
    	String travelerjaiotze = "01/07/1997";
    	int travelertelefono = 612332456;
    	String travelersexu = "male";
    	
    	String nondik = "Madrid";
    	String nora ="Bilbo";

    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	java.util.Date parsed = sdf.parse("06/10/2026");
    	java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
    			
    	try {
    				//Create a driver and her ride
    				
    			testDA.open();
    			testDA.addTravelerWithAlert(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu, nondik, nora, rideDate);
   				testDA.close();
   				
   				
    				//invoke System Under Test (sut)  
   				sut.open();
   				Alerta alerta = sut.createAlert(nondik, nora, rideDate, travelerNAN);
    				
    				//if the program goes to this point fail
    			fail();
    			
    				
    		   } catch (alertAlreadyExists e) {
    				// if the program goes to this point fail  
   					assertTrue(true);
    				
    				
   				} catch (RideMustBeLaterThanTodayException e) {
   					fail();
    					// if the program goes to this point fail  
    				
    			} finally {
    				sut.close();
    				testDA.open();
    				//reestablish the state of the system (remove the driver and her rides in the database)

    				testDA.removeTraveler(travelerNAN);

    				testDA.close();	      
    			 }
    	 } 


    @Test
    public void test4() throws ParseException {
    	String travelerNAN="12345678A";
    	String travelerLog = "traveler1";
    	String travelerpass = "123";
    	String travelerEmail = "traveler1@gmail.com";
    	String travelerIzena = "Ibai";
    	String travelerAbizena = "Martin";
    	String travelerjaiotze = "01/07/1997";
    	int travelertelefono = 612332456;
    	String travelersexu = "male";
    	
    	String nondik = "Madrid";
    	String nora ="Bilbo";

    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	java.util.Date parsed = sdf.parse("06/10/2026");
    	java.sql.Date rideDate = new java.sql.Date(parsed.getTime());	
		
		Alerta alerta = null;
		
		testDA.open();
			testDA.createTraveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);
		testDA.close();
		
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 alerta = sut.createAlert(nondik, nora, rideDate, travelerNAN);
			sut.close();			
			
			//verify the results
			assertNotNull(alerta);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existAlert(travelerNAN, nondik, nora, rideDate);
				
			assertTrue(exist);
			testDA.close();
			
		   } catch (alertAlreadyExists e) {
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
		
		
		finally {   

			testDA.open();
				testDA.removeTraveler(travelerNAN);
			testDA.close();
			
		        }
		   }
    
   

}
