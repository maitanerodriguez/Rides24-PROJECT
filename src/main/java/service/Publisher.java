package service;

import javax.xml.ws.Endpoint;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;

public class Publisher {

	public static void main(String[] args) {
		Endpoint.publish("http://0.0.0.0:1099/ws", new BLFacadeImplementation());
		 System.out.println("Service published");

	}

}
