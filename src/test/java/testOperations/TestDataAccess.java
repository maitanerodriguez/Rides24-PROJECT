package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Book;
import domain.Driver;
import domain.Kotxe;
import domain.Ride;
import domain.Traveler;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public boolean removeTraveler(String NANT) {
		System.out.println(">> TestDataAccess: removeTraveler");
		Traveler t = db.find(Traveler.class, NANT);
		if (t!=null) {
			db.getTransaction().begin();
			db.remove(t); 
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public boolean removeRideFromDB(Integer rideNumber) {
		System.out.println(">> TestDataAccess: removeRideFromDB");
		Ride r = db.find(Ride.class, rideNumber);
		if (r!=null) {
			db.getTransaction().begin();
			db.remove(r);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public boolean removeCarFromDB(String matrikula) {
		System.out.println(">> TestDataAccess: removeCarFromDB");
		Kotxe k = db.find(Kotxe.class, matrikula);
		if (k!=null) {
			db.getTransaction().begin();
			db.remove(k);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver getDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: getDriver "+driverEmail);
		Driver d = db.find(Driver.class, driverEmail);
		
		return d;
    }
	
	public Driver createDriver(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
		System.out.println(">> TestDataAccess: createDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public Kotxe createKotxe(String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver) {
		System.out.println(">> TestDataAccess: createKotxe");
		Kotxe k=null;
		db.getTransaction().begin();
		try {
		    k=new Kotxe(matrikula,marka,modeloa,eserlekuKopurua, driver);
			db.persist(k);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return k;
	}
	public Ride createRide(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k) {
		System.out.println(">> TestDataAccess: createRide");
		Ride r=null;
		db.getTransaction().begin();
		try {
		    r=new Ride(rideNumber, from, to, date, nPlaces, price, driver, k);
		    db.persist(driver);
		    db.persist(k);
			db.persist(r);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return r;
	}
	public Traveler createTraveler(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
		System.out.println(">> TestDataAccess: createTraveler");
		Traveler traveler=null;
		db.getTransaction().begin();
		try {
			traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData,telefonoZenbakia, sexua);
			db.persist(traveler);
			db.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return traveler;
	}
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
	public boolean existTraveler(String NAN) {
		 return  db.find(Driver.class, NAN)!=null;
		 

	}
		
		public Driver addDriverWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, String from, String to,  Date date, int nPlaces, float price, String matrikula) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				Kotxe kotxe=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, email);
					 kotxe=db.find(Kotxe.class, matrikula);
					 
					if (driver==null)
						driver=new Driver(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
				    driver.addRide(from, to, date, nPlaces, price, kotxe);
				    db.persist(driver);
				    System.out.println("Stored: "+driver);
					db.getTransaction().commit();
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return driver;
	    }
		
		public Traveler addTravelerWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, int nSeats, Integer rideNumber,String from, String to,  Date date, int nPlaces, float price, Kotxe k, Driver d) {
			System.out.println(">> TestDataAccess: addTravelerWithRide");
			Traveler traveler=null;
			Ride ride=null;
			db.getTransaction().begin();
			try {
				traveler=db.find(Traveler.class, NAN);
				ride=db.find(Ride.class,rideNumber);
				if(traveler==null) {
					traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
				}
				if(ride==null) {
					ride = new Ride(from, to, date, nPlaces, price, d, k);
				}
				traveler.addBook(ride, nSeats);
				db.persist(traveler);
				System.out.println("Stored: "+traveler);
				db.getTransaction().commit();
				return traveler;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return traveler;
		}
		
		public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
		}
		public boolean existBook(String NAN, Ride r) {
			System.out.println(">> TestDataAccess: existBook");
			Traveler t= db.find(Traveler.class, NAN);
			if(t!=null) {
				return t.existBook(r);
			}else {
				return false;
			}
		}
		public Ride removeRide(String email, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				return r;

			} else 
			return null;

		}
		public Ride removeRideFromDriver(String NAN, Integer rideNumber) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, NAN);
			Ride r= db.find(Ride.class, rideNumber);
			if (d!=null) {
				db.getTransaction().begin();
				d.removeRide(r);
				db.getTransaction().commit();
				return r;

			} else 
			return null;

		}
		public Book removeBookFromTraveler(String NAN, Integer rideNumber) {
			System.out.println(">> TestDataAccess: removeRideFromTraveler");
			Traveler t= db.find(Traveler.class, NAN);
			if(t!=null) {
				db.getTransaction().begin();
				Book b=t.removeBookWithRideID(rideNumber);
				db.getTransaction().commit();
				return b;
			}else {
				return null;
			}
		}


		
}
