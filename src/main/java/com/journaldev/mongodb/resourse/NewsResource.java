package com.journaldev.mongodb.resourse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.journaldev.mongodb.dao.MongoDBNewsDAO;
import com.journaldev.mongodb.dao.MongoDBPersonDAO;
import com.journaldev.mongodb.model.News;
import com.journaldev.mongodb.model.Person;
import com.mongodb.MongoClient;


@Path("/noticia")
public class NewsResource {
	
	private JsonElement parseNewsToJsonObject(News news){
		String s = new Gson().toJson(news);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(s);
	}
	
	
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inserir(News news) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			newsDAO.createNews(news);
			
			JsonObject j = new JsonObject();
			
			if(newsDAO.readNews(news) != null){
				
				j.addProperty("error", false);
				j.add("data", parseNewsToJsonObject(news));
				j.addProperty("message", "Noticia criadoa com sucesso." );
				return Response.status(200).entity(j.toString()).build();
				
			}else{
				j.addProperty("error", true);
				j.addProperty("data", "");
				j.addProperty("message", "Error ao criar noticia." );
				return Response.status(500).entity(j.toString()).build();
			}	
		
			
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}
	
	@POST
	@Path("/alterar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response alterar(News news) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			
		
			return Response.status(200).entity(news).build();
			
		} catch (Exception e) {
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<News> getAll(){
		
	
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			ArrayList<News> n = (ArrayList<News>) mongo.readAllNews();
			return n;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
		
	}
	
	@GET
	@Path("/getNewsByType/{type}")
	@Produces("application/json")
	public List<News> getNewsByType(@PathParam("type") String type){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			ArrayList<News> n = (ArrayList<News>) mongo.readNewsByType(type);
			return n;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
		
	}
	
}
