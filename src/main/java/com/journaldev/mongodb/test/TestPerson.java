package com.journaldev.mongodb.test;

import com.google.gson.Gson;
import com.journaldev.mongodb.dao.MongoDBPersonDAO;
import com.journaldev.mongodb.model.Person;
import com.mongodb.MongoClient;

public class TestPerson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO mongo = new MongoDBPersonDAO(mongoClient);
			
			Person p = new Person();
			p.setName("Ricarlo");
			p.setEmail("ricarlo@email.com");
			p.setSenha("1243");
			
			mongo.createPerson(p);
			
			System.out.println(new Gson().toJson(mongo.readPerson(p)));
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
