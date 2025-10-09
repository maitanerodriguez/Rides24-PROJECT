package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Balorazio;
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
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
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
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

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
		
		
		public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
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
		
		public boolean existTraveler(String NAN) {
			 return  db.find(Driver.class, NAN)!=null;
			 

		}
		
		public Traveler createTraveler(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua) {
			System.out.println(">> TestDataAccess: createTraveler");
			Traveler traveler=null;
				db.getTransaction().begin();
				try {
				    traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
					db.persist(traveler);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return traveler;
	    }

		public boolean removeTraveler(String travelerNAN) {
			System.out.println(">> TestDataAccess: removeTraveler");
			Traveler t = db.find(Traveler.class, travelerNAN);
			if (t!=null) {
				db.getTransaction().begin();
				db.remove(t);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public Traveler addTravelerWithAlert(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, String from, String to,  Date date) {
			System.out.println(">> TestDataAccess: addTravelerWhitAlert");
				Traveler traveler=null;
				db.getTransaction().begin();
				try {
					 traveler = db.find(Traveler.class, email);
					 					 
					if (traveler==null)
						traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
				    traveler.addAlert(from, to, date);
				    db.persist(traveler);
				    System.out.println("Stored: "+traveler);
					db.getTransaction().commit();
					return traveler;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return traveler;
	    }
		
		public boolean existAlert(String NAN, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existAlert");
			Traveler t = db.find(Traveler.class, NAN);
			if (t!=null) {
				return t.doesAlertExist(from, to, date);
			} else 
			return false;
		}
		
		public Integer addRideNumberWithBalorazio(Integer idBalorazioa, int puntuazioa, String komentarioa, String data, Integer rideNumber, String NAN) {
			Traveler t = null;
			Balorazio b = null;

			try {
				db.getTransaction().begin();
				t = db.find(Traveler.class, NAN);
				Ride r = db.find(Ride.class, rideNumber);

				if (t == null || r == null) {
					db.getTransaction().commit();
					return null;
				}

				if (t.balorazioExist(rideNumber)) {
					db.getTransaction().commit();
					return null; 
				}

				b = t.addBalorazio(idBalorazioa, puntuazioa, komentarioa, data, r);
				r.addBalorazio(b);

				db.persist(t);
				db.getTransaction().commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return rideNumber;
		}
		
		public Balorazio createBalorazio(Integer idBalorazioa, int puntuazioa, String komentarioa, String data, Ride ride, Traveler traveler) {
			System.out.println(">> TestDataAccess: createBalorazio");
			Balorazio balorazio = null;
				db.getTransaction().begin();
				try {
				    balorazio = new Balorazio(idBalorazioa, puntuazioa, komentarioa, data, ride, traveler);
					db.persist(balorazio);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return balorazio;
	    }
	
		public boolean removeBalorazio(Integer idBalorazioa) {
			System.out.println(">> TestDataAccess: removeBalorazio");
			Balorazio b = db.find(Balorazio.class, idBalorazioa);
			if (b!=null) {
				db.getTransaction().begin();
				db.remove(b);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public Ride createRide(Integer rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver, Kotxe k) {
			System.out.println(">> TestDataAccess: createRide");
			Ride ride = null;
				db.getTransaction().begin();
				try {
				    ride = new Ride(rideNumber, from, to, date, nPlaces, price, driver, k);
					db.persist(ride);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return ride;
	    }
	
		public boolean removeRide(Integer rideNumber) {
			System.out.println(">> TestDataAccess: removeRide");
			Ride r = db.find(Ride.class, rideNumber);
			if (r!=null) {
				db.getTransaction().begin();
				r.getBalorazioak().clear();
				db.remove(r);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public Kotxe createKotxe(String matrikula, String marka, String modeloa, int eserlekuKopurua, Driver driver) {
			System.out.println(">> TestDataAccess: createKotxe");
			Kotxe kotxe= null;
				db.getTransaction().begin();
				try {
				    kotxe= new Kotxe(matrikula, marka, modeloa, eserlekuKopurua, driver);
					db.persist(kotxe);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return kotxe;
	    }
		
		public boolean removeKotxe(String matrikula) {
			System.out.println(">> TestDataAccess: removeKotxe");
			Kotxe k = db.find(Kotxe.class, matrikula);
			if (k!=null) {
				db.getTransaction().begin();
				db.remove(k);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
		public boolean existBalorazio(Integer idBalorazio, int puntuazioa, String komentarioa, String data, String NAN, Integer rideNumber) {
			System.out.println(">> TestDataAccess: existBalorazio");
			Traveler t = db.find(Traveler.class, NAN);
			if (t!=null) {
				return t.balorazioExist(rideNumber);
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
		public boolean existBook(String NAN, Ride r) {
			System.out.println(">> TestDataAccess: existBook");
			Traveler t= db.find(Traveler.class, NAN);
			if(t!=null) {
				return t.existBook(r);
			}else {
				return false;
			}
		}
		public Traveler addTravelerWithRide(String NAN,String log, String password, String email, String izena,String abizena, String jaiotzeData, int telefonoZenbakia, String sexua, int nSeats, Integer rideNumber,String from, String to,  Date date, int nPlaces, float price, Kotxe k, Driver d) {
			System.out.println(">> TestDataAccess: addTravelerWithRide");
			Traveler traveler=null;
			Ride ride=null;
	
			try {
				db.getTransaction().begin();
				traveler=db.find(Traveler.class, NAN);
				ride=db.find(Ride.class,rideNumber);
				if(traveler==null) {
					traveler=new Traveler(NAN, log, password, email, izena, abizena, jaiotzeData, telefonoZenbakia, sexua);
				}
				if(ride==null) {
					ride = new Ride(from, to, date, nPlaces, price, d, k);
				}
				traveler.addBook(ride, nSeats);
				db.persist(ride);
				db.persist(traveler);
				System.out.println("Stored: "+traveler);
				db.getTransaction().commit();
				return traveler;
			}catch(Exception e) {
				e.printStackTrace();
			}
			return traveler;
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
		public boolean removeRideFromDB(Integer rideNumber) {
			System.out.println(">> TestDataAccess: removeRideFromDB");
			Ride r = db.find(Ride.class, rideNumber);
			if (r!=null) {
				db.getTransaction().begin();
				r.getBookList().clear();
				db.remove(r);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }
		
}