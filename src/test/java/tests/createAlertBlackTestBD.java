package tests;

import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;


import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Alerta;
import domain.Traveler;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.alertAlreadyExists;

public class createAlertBlackTestBD {
	
	private EntityManager db;
    private DataAccess sut; 
	
	@Before
	public void setUp() {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb-test");
	    db = emf.createEntityManager();
	    sut = new DataAccess(db);
	    
	    db.getTransaction().begin();
        db.createQuery("DELETE FROM Alerta").executeUpdate();
        db.createQuery("DELETE FROM Traveler").executeUpdate();
        db.getTransaction().commit();
	}
	


    @Test
    public void test1() throws Exception {
    	 Traveler t = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "06/10/2025", 612332456, "male" );
    	 db.getTransaction().begin();
    	 db.persist(t);
         db.getTransaction().commit();

         Alerta  a= sut.createAlert("Madrid", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025"), "12345678A");
         
         assertNotNull(a);
    }
   

    @Test
    public void test2() throws Exception {
        Alerta result = sut.createAlert("Madrid", "Bilbo", null, "12345678A");
        assertNull(result);
    }
    
    @Test
    public void test3() throws Exception {
     	Alerta result = sut.createAlert(null, "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025"), "12345678A");
       assertNull(result);
    	
    }


    @Test
    public void test4() throws Exception {
    	Alerta result = sut.createAlert("Madrid", null, new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025"), "12345678A");
    	assertNull(result);
    }

    @Test
    public void test5() throws Exception {
    	Traveler t = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "06/10/2025", 612332456, "male" );
    	db.getTransaction().begin();
   	 	db.persist(t);
        db.getTransaction().commit();
        

        assertThrows(RideMustBeLaterThanTodayException.class, () -> {
        	   sut.createAlert("Madrid", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("01/07/1997"), "12345678A");

        });
    }
    
    @Test
    public void test6() throws Exception {
    	Traveler t = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "06/10/2025", 612332456, "male" );
    	db.getTransaction().begin();
    	db.persist(t);
        db.getTransaction().commit();

  
        	Alerta a1 = t.addAlert("Madrid", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025"));
   
        	db.getTransaction().begin();
        	db.persist(a1);
        	db.getTransaction().commit();

       assertThrows(alertAlreadyExists.class, () -> {
        	   sut.createAlert("Madrid", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("06/10/2025"), "12345678A");
       });
    }
	
	

}
