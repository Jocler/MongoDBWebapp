package com.journaldev.mongodb.test;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;
import com.journaldev.mongodb.dao.MongoDBNewsDAO;
import com.journaldev.mongodb.model.News;
import com.mongodb.MongoClient;

public class TestNews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			MongoClient mongoClient = new MongoClient();
			
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			
			
			// ##### Create news  #####
			News news = new News();
			Calendar cal = Calendar.getInstance();
			news.setIdPerson("583899ce5ca4b71ced15fe53");
			news.setTipo("t1");
			news.setTitulo("titulo " + cal.getTimeInMillis());
			news.setDescricao("descrição " + cal.getTimeInMillis());
			news.setIdPersonVoto(new ArrayList<String>());
			
			System.out.println(new Gson().toJson(newsDAO.createNews(news)));
			
			// ######################### 		
			
			
			
			
			/*// ##### update news  #####
			News news = new News();
			news.setId("583890e85ca49d923b34dcfa");
			news = newsDAO.readNews(news);
			
			news.setIdPerson("583899ce5ca4b71ced15fe53");
			newsDAO.updateNews(news);
			
			System.out.println(new Gson().toJson(news));
			
			// ######################### */
			
			
			
			/*// ##### create voto #####
			News news = new News();
			news.setId("583890e85ca49d923b34dcfa");
			news = newsDAO.readNews(news);
			
			news.getIdPersonVoto().add("583899ce5ca4b71ced15fe53");
			newsDAO.updateNews(news);
			
			System.out.println(new Gson().toJson(news));
			
			// ######################### */
			
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
