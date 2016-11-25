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
	
	private JsonElement parseNewsToJsonObject(Object news){
		String s = new Gson().toJson(news);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(s);
	}
	
	private boolean findPersonVoto(List<String> ids_person, String id_find ){
		for (String id: ids_person ){
			if(id_find.equals(id))
				return true;
		}
		return false;
	}
	
	private String createResponse(boolean error, Object data, String msg){
		JsonObject j = new JsonObject();
		j.addProperty("error", error);
		
		if(data != null)
			j.add("data", parseNewsToJsonObject(data));
		else
			j.addProperty("data", "");
		
		j.addProperty("message", msg );
		
		return j.toString();
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inserir(News news) {
		
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			
			news = newsDAO.createNews(news);
			if(news != null){
				String response = createResponse(false, news, "Noticia criada com sucesso.");
				return Response.status(200).entity(response).build();
				
			}else{
				String response = createResponse(true, null, "Error ao criar noticia.");
				return Response.status(500).entity(response).build();
			}	
		
			
		} catch (Exception e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
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
			
			if(newsDAO.updateNews(news) != null){
				String response = createResponse(false, news, "Noticia alterada com sucesso.");
				return Response.status(200).entity(response).build();
				
			}else{
				String response = createResponse(true, null, "Error ao alterar noticia.");
				return Response.status(500).entity(response).build();
			}
			
		} catch (Exception e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
	}

	@GET
	@Path("/getNewsById/{id_news}/{id_person}")
	@Produces("application/json")
	public Response getNewsById(@PathParam("id_news") String id_news, @PathParam("id_person") String id_person){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongoNews = new MongoDBNewsDAO(mongoClient);
			MongoDBPersonDAO mongoPerson = new MongoDBPersonDAO(mongoClient);
			
			// read news
			News news = new News();
			news.setId(id_news);
			news = mongoNews.readNews(news);
			
			// read person
			Person p = new Person();
			p.setId(id_person);
			p = mongoPerson.readPerson(p);
			
			// verify voto
			news.setVotou(findPersonVoto(news.getIdPersonVoto(), id_person));
			
			String response = createResponse(false, news, "Notícia");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	@GET
	@Path("/getAll")
	@Produces("application/json")
	public Response getAll(){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			// read news all
			ArrayList<News> news = (ArrayList<News>) mongo.readAllNews();
			
			String response = createResponse(false, news, "Toda a lista de notícias");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	@GET
	@Path("/getNewsByType/{type}")
	@Produces("application/json")
	public Response getNewsByType(@PathParam("type") String type){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			// read news by type
			ArrayList<News> news = (ArrayList<News>) mongo.readNewsByType(type);
			
			String response = createResponse(false, news, "Lista de notícias por tipo");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	@POST
	@Path("/createVoto/{id_news}/{id_person}")
	@Produces("application/json")
	public Response createVoto(@PathParam("id_news") String id_news, @PathParam("id_person") String id_person){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			News news = new News();
			// read news 
			news.setId(id_news);
			news = mongo.readNews(news);
			
			// update news
			// se não tiver voto adiciona
			String msg = "";
			if(!findPersonVoto(news.getIdPersonVoto(), id_person)){
				news.getIdPersonVoto().add(id_person);
				news = mongo.updateNews(news);
				news.setVotou(true);
				msg = "Voto criado com sucesso.";
			}else{
				news.setVotou(true);
				msg = "Você já voltou neste post.";
			}
			
			String response = createResponse(false, news, msg);
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	
	@POST
	@Path("/removeVoto/{id_news}/{id_person}")
	@Produces("application/json")
	public Response removeVoto(@PathParam("id_news") String id_news, @PathParam("id_person") String id_person){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			News news = new News();
			// read news 
			news.setId(id_news);
			news = mongo.readNews(news);
			
			// update news
			String msg = "Você não pussui voto neste post.";
			for (int i = 0; i < news.getIdPersonVoto().size(); i++){
				if(news.getIdPersonVoto().get(i).equals(id_person)){
					news.getIdPersonVoto().remove(i);
					msg = "Voto removido com sucesso.";
					news.setVotou(false);
				}
			}
			news = mongo.updateNews(news);
			
			String response = createResponse(false, news, msg);
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
}
