package tests;

import static org.junit.Assert.assertNotNull;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
