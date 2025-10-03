package tests;

import static org.junit.Assert.assertNotNull;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Alerta;
import domain.Traveler;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;

public class createAlertMockBlackTest {

	private EntityManager db;
	private EntityTransaction m;
	private DataAccess sut;
	
	
	@Before 
	public void setUp() { 
		db = mock(EntityManager.class);
		m = mock(EntityTransaction.class);
		MockitoAnnotations.openMocks(this);
		when(db.getTransaction()).thenReturn(m);
		sut = new DataAccess(db);
		sut.open();
		
    
	}  
	
	@After  
	public void tearDown() {  
		sut.close();    
	}
	
	 @Test
	    public void test1() throws Exception {
		 	Traveler t = mock(Traveler.class);
	    	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025");
	    	
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);
	    	when(t.doesAlertExist("Madrid", "Bilbo", date)).thenReturn(false);
	    	
	    	Alerta a = mock(Alerta.class);
	    	
	    	when(t.addAlert("Madrid", "Bilbo", date)).thenReturn(a);
	    	
	    	Alerta result = sut.createAlert("Madrid", "Bilbo", date, "12345678A");
	    	
	    	assertNotNull(result);
	    }
	   

	    @Test
	    public void test2() throws Exception {
	    	Traveler t = mock(Traveler.class);
	    	
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);
	        Alerta result = sut.createAlert("Madrid", "Bilbo", null, "12345678A");
	        assertNull(result);
	    }
	    
	    @Test
	    public void test3() throws Exception {
	    	Traveler t = mock(Traveler.class);
	    	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025");
	    	
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);

	    	Alerta result = sut.createAlert(null, "Bilbo", date, "12345678A");

	    	assertNull(result);
	    	
	    }


	    @Test
	    public void test4() throws Exception {
	    	Traveler t = mock(Traveler.class);
	    	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025");
	    	
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);


	    	Alerta result =  sut.createAlert("Madrid", null, date, "12345678A");

	    	assertNull(result);
	    }

	    @Test
	    public void test5() throws Exception {
	    	java.util.Date date = new SimpleDateFormat("dd/MM/yyyy").parse("08/09/2024");
	    	Traveler t = mock(Traveler.class);
	    	
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);
	    	
	        assertThrows(RideMustBeLaterThanTodayException.class, () -> {
	        	   sut.createAlert("Madrid", "Bilbo", date, "12345678A");

	        });
	    }
	    
	    @Test
	    public void test6() throws Exception {
	    	java.util.Date  date = new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025");
	    	Traveler t = mock(Traveler.class);
	    	when(db.find(Traveler.class, "12345678A")).thenReturn(t);
	        when(t.doesAlertExist("Madrid", "Bilbo", date)).thenReturn(true);

	       assertThrows(alertAlreadyExists.class, () -> {
	        	   sut.createAlert("Madrid", "Bilbo", date, "12345678A");
	        
	        	
	       });
	    }
	    

}
