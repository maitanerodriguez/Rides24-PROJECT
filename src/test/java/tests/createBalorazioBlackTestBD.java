package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Balorazio;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;

public class createBalorazioBlackTestBD {

	private EntityManagerFactory emf;
	private EntityManager db;
	private DataAccess sut;

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:rides.odb");
        db = emf.createEntityManager();
        sut = new DataAccess(db); 
    }

    @After
    public void tearDown() {
        sut.close();
        emf.close();
    }

    @Test
    public void test1() throws Exception {
        Balorazio result = sut.createBalorazio(8, "Ondo", "02/01/2025", null, 12);
        assertNull(result);
    }

    @Test
    public void test2() throws Exception {
        Balorazio result = sut.createBalorazio(8, "Ondo", "02/01/2025", "12345678A", -1);
        assertNull(result);
    }
    
    @Test
    public void test3() throws Exception {
    	Traveler t = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
        Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
    	Kotxe kotxe = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
    	Ride r = null;
    	try {
    	    r = new Ride(12, "Donostia", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("05/06/2025"), 4, 30.00f, d, kotxe);
    	} catch (ParseException e) {
    	    e.printStackTrace();
    	}
        db.persist(t);
        db.persist(r);
        db.flush();

        Balorazio balorazioa = t.addBalorazio(8, "Ondo", "02/01/2025", r);
        r.addBalorazio(balorazioa);
        db.persist(balorazioa);
        db.flush();

        try {
            sut.createBalorazio(8, "Ondo", "02/01/2025", "12345678A", 12);
        } catch (reviewAlreadyExistsException e) {
            assertTrue(true);
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }


    @Test
    public void test4() throws Exception {
    	Traveler t = new Traveler("12345678A", "traveler1", "123", "traveler1@gmail.com", "Ibai", "Martin", "01/02/1997", 612332456, "male" );
        Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
    	Kotxe kotxe = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
    	Ride r = null;
    	try {
    	    r = new Ride(12, "Donostia", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("05/06/2025"), 4, 30.00f, d, kotxe);
    	} catch (ParseException e) {
    	    e.printStackTrace();
    	}
        db.persist(t);
        db.persist(r);
        db.flush();
        
        try {
            sut.createBalorazio(2, "Ondo", "02/01/2025", "12345678A", 12);
        } catch (ratingMoreThanFiveException e) {
            assertTrue(true);
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }

    @Test
    public void test5() throws Exception {
        Traveler t1 = new Traveler("23567123H", "traveler1", "123", "traveler1@gmail.com", "June", "Sanchez", "01/02/1997", 612332456, "female");
        db.persist(t1);

        Driver d = new Driver("Z9988776K", "driver1", "789", "driver1@gmail.com", "Jon", "Arrieta", "10/11/1985", 634567890, "male");
        Kotxe kotxe = new Kotxe("1234ABC", "Toyota", "Corolla", 5, d);
        Ride r = new Ride(12, "Donostia", "Bilbo", new SimpleDateFormat("dd/MM/yyyy").parse("05/06/2025"), 4, 30.00f, d, kotxe);
        db.persist(r);
        db.flush();
        
        Balorazio b1 = new Balorazio(8, "Ondo", "02/01/2025", r, t1);
        db.persist(b1);
        //assertNotNull(b);
        
    }
}

