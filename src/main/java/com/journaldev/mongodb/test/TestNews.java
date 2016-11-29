package com.journaldev.mongodb.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.journaldev.mongodb.dao.MongoDBNewsDAO;
import com.journaldev.mongodb.dao.MongoDBPersonDAO;
import com.journaldev.mongodb.model.Curtida;
import com.journaldev.mongodb.model.News;
import com.journaldev.mongodb.model.Person;
import com.mongodb.MongoClient;

public class TestNews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			MongoClient mongoClient = new MongoClient();
			
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);
			
			System.out.println(new Gson().toJson(newsDAO.readAllNewsPerson("583ba032ca85013f7044a3c5")));
			
			
			// ##### Create news  #####
			/*News news = new News();
			Calendar cal = Calendar.getInstance();
	
			news.setTipo("3");
			news.setTitulo("titulo " + cal.getTimeInMillis());
			news.setDescricao("descrição " + cal.getTimeInMillis());
			news.setCurtidas(new ArrayList<Curtida>());
			
			Person person = new Person();
			person.setId("583ba032ca85013f7044a3c5");
			person = personDAO.readPerson(person);
	
			news.setPerson(person);
			
			System.out.println(new Gson().toJson(newsDAO.createNews(news)));*/
			
			/*Curtida c = new Curtida();
			c.setIdNews(news.getId());
			c.setIdPerson("58293694b7afa858c1623c30");
			c.setLike(true);
			List<Curtida> curtidas = new ArrayList<>();
			curtidas.add(c);
			news.setCurtidas(curtidas);
			
			System.out.println(new Gson().toJson(newsDAO.updateNews(news)));*/
			
			// #########################  		

			
			
			
			/*// ##### update news  #####
			News news = new News();
			news.setId("583890e85ca49d923b34dcfa");
			news = newsDAO.readNews(news);
			
			news.setIdPerson("583899ce5ca4b71ced15fe53");
			newsDAO.updateNews(news);
			
			System.out.println(new Gson().toJson(news));
			
			// ######################### */
			
		
			/*// ##### delete #####
			News news = new News();
			news.setId("5834e1e590ee83f1b99abee8");
			newsDAO.deleteNews(news);
			
			
			System.out.println("");
			
			// ######################### */		
			
			/*// ##### update news  #####
			News news = new News();
			news.setId("583a5c78bd720657daca1fe6");
			news = newsDAO.readNews(news);
			news.setTipo("1");
				
			System.out.println(new Gson().toJson(newsDAO.updateNews(news)));
				
			// ######################### */ 		
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
