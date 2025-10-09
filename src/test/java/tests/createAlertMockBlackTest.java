package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import domain.Alerta;
import domain.Traveler;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;

public class createAlertMockBlackTest {

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
	    public void test1() throws ParseException {
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
			String nora = "Bilbo";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse("06/10/2026");
			java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
			
			try {
				Traveler traveler1=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);

				//configure the state through mocks 
		        Mockito.when(db.find(Traveler.class, traveler1.getNAN())).thenReturn(traveler1);
						
				//invoke System Under Test (sut)  
				sut.open();
				Alerta alerta=sut.createAlert(nondik, nora, rideDate, travelerNAN);
				sut.close();
				//verify the results
				assertNotNull(alerta);
				assertEquals(alerta.getNondik(),nondik);
				assertEquals(alerta.getNora(),nora);
				assertEquals(alerta.getData(),rideDate);
				
				
			   } catch (alertAlreadyExists e) {
				// if the program goes to this point fail  
				fail();
				
				} catch (RideMustBeLaterThanTodayException e) {
					// if the program goes to this point fail  

				fail();
				//redone state of the system (create object in the database)
				
			} 
		} 
	   

	    @Test
	    public void test2() {
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
			String nora = "Bilbo";
			
			Date rideDate=null;
			
			Alerta alerta=null;
			
			Traveler traveler1=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);

			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getNAN())).thenReturn(traveler1);
	        
			try {
				//invoke System Under Test (sut)  
				sut.open();
				 alerta=sut.createAlert(nondik, nora, rideDate, travelerNAN);
				sut.close();			
				
				//verify the results
				assertNull(alerta);
				
				
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
			
			   }
	    
	    @Test
	    public void test3() throws ParseException {
	    	String travelerNAN="12345678A";
	    	String travelerLog = "traveler1";
	    	String travelerpass = "123";
	    	String travelerEmail = "traveler1@gmail.com";
	    	String travelerIzena = "Ibai";
	    	String travelerAbizena = "Martin";
	    	String travelerjaiotze = "01/07/1997";
	    	int travelertelefono = 612332456;
	    	String travelersexu = "male";
	    	
			String nondik  =null;
			String nora = "Bilbo";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse("06/10/2026");
			java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
			
			Alerta alerta=null;
			
			Traveler traveler1=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);

			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getNAN())).thenReturn(traveler1);
			try {
				//invoke System Under Test (sut)  
				sut.open();
				 alerta=sut.createAlert(nondik, nora, rideDate, travelerNAN);
				sut.close();			
				
				//verify the results
				assertNull(alerta);
				
				//q is in DB
				//Mirar con Mockito que no se ha invocado a su creación
				
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
			String nora = null;
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse("06/10/2026");
			java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
			
			Alerta alerta=null;
			
			Traveler traveler1=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);

			//configure the state through mocks 
	        Mockito.when(db.find(Traveler.class, traveler1.getNAN())).thenReturn(traveler1);
			try {
				//invoke System Under Test (sut)  
				sut.open();
				 alerta=sut.createAlert(nondik, nora, rideDate, travelerNAN);
				sut.close();			
				
				//verify the results
				assertNull(alerta);
				
				//q is in DB
				//Mirar con Mockito que no se ha invocado a su creación
				
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
		   }

	    @Test
	    public void test5() throws ParseException {
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
			String nora = "Bilbo";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse("01/07/1997");
			java.sql.Date rideDate = new java.sql.Date(parsed.getTime());
			
			try {
				
				Traveler traveler1=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);

				//configure the state through mocks 
		        Mockito.when(db.find(Traveler.class, traveler1.getNAN())).thenReturn(traveler1);	
				
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
				}
			   } 
	    
	    @Test
	    public void test6() throws ParseException{
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
			String nora = "Bilbo";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse("06/10/2026");
			java.sql.Date rideDate = new java.sql.Date(parsed.getTime());	
			
			try {
						
				 Traveler traveler=new Traveler(travelerNAN, travelerLog, travelerpass, travelerEmail, travelerIzena, travelerAbizena, travelerjaiotze, travelertelefono, travelersexu);
				 traveler.addAlert(nondik, nora, rideDate);
				//configure the state through mocks 
		        Mockito.when(db.find(Traveler.class, traveler.getNAN())).thenReturn(traveler);
			
				
				//invoke System Under Test (sut)  
				sut.open();
			    sut.createAlert(nondik, nora, rideDate, travelerNAN);
				sut.close();
				
				fail();
				
			   } catch (alertAlreadyExists e) {
				 //verify the results
					sut.close();
					assertTrue(true);
				} catch (RideMustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
				fail();
			} 
		} 
	    
	   
	    

}
