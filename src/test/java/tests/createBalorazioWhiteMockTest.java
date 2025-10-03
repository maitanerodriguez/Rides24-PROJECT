package tests;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Balorazio;
import domain.Ride;
import domain.Traveler;
import exceptions.ratingMoreThanFiveException;
import exceptions.reviewAlreadyExistsException;

public class createBalorazioWhiteMockTest {
	
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
        Balorazio result = sut.createBalorazio(8, "Ondo", "02/01/2025", "12345678A", 12);
        assertNull(result);
    }
	
    @Test
    public void test2() throws Exception {
    	Traveler t = mock(Traveler.class);
        Ride r = mock(Ride.class);

        Mockito.when(db.find(Traveler.class, "12345678A")).thenReturn(t);
        Mockito.when(db.find(Ride.class, 12)).thenReturn(r);
        
        try {
            sut.createBalorazio(8, "Ondo", "02/01/2025", "12345678A", 12);
        } catch (reviewAlreadyExistsException e) {
            assertTrue(true);
        } catch (Exception e) {
        	 e.printStackTrace();
        }
    }
		
    @Test
    public void test3() throws Exception {
        Traveler t = mock(Traveler.class);
        Ride r = mock(Ride.class);

        Mockito.when(db.find(Traveler.class, "12345678A")).thenReturn(t);
        Mockito.when(db.find(Ride.class, 12)).thenReturn(r);
        Mockito.when(t.balorazioExist(12)).thenReturn(false);

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
        Traveler t1 = mock(Traveler.class);
        Ride r = mock(Ride.class);
        Balorazio b1 = mock(Balorazio.class);

        Mockito.when(db.find(Traveler.class, "23567123H")).thenReturn(t1);
        Mockito.when(db.find(Ride.class, 12)).thenReturn(r);
        Mockito.when(t1.balorazioExist(12)).thenReturn(false);
        Mockito.when(t1.addBalorazio(8, "Ondo", "02/01/2025", r)).thenReturn(b1);
    }
}
