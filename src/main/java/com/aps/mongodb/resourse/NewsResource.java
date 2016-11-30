package com.aps.mongodb.resourse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.aps.mongodb.dao.MongoDBNewsDAO;
import com.aps.mongodb.model.Curtida;
import com.aps.mongodb.model.News;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;


@Path("/noticia")
public class NewsResource {
	
	private JsonElement parseNewsToJsonObject(Object news){
		String s = new Gson().toJson(news);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(s);
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

	private int findPersonCurtida(List<Curtida> curtidas, String id_person_find ){
		int index = -1;
		for (Curtida curtida: curtidas ){
			if(curtida.getId_person().equals(id_person_find)){
				return curtidas.indexOf(curtida);
			}
		}
		return index;
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

	@POST
	@Path("/deletar")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deletar(News news) {
		
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			
			newsDAO.deleteNews(news);

			String response = createResponse(false, null, "Notícia deletada com sucesso.");
			return Response.status(200).entity(response).build();
			
		} catch (Exception e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
	}

	
	@GET
	@Path("/getNewsById/{id_news}")
	@Produces("application/json")
	public Response getNewsById(@PathParam("id_news") String id_news){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongoNews = new MongoDBNewsDAO(mongoClient);
			
			// read news
			News news = new News();
			news.setId(id_news);
			news = mongoNews.readNews(news);
			if(news != null){
				String response = createResponse(false, news, "Noticia");
				return Response.status(200).entity(response).build();		
			}else{
				String response = createResponse(true, null, "Noticia não exite.");
				return Response.status(200).entity(response).build();			
			}
			
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
			
			String response = createResponse(false, news, "Toda a lista de notÃ­cias");
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
			
			String response = createResponse(false, news, "Lista de notÃ­cias por tipo");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	
	@GET
	@Path("/getAllNewsPerson/{id_person}")
	@Produces("application/json")
	public Response getAllNewsPerson(@PathParam("id_person") String id_person){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			// read news by type
			ArrayList<News> news = (ArrayList<News>) mongo.readAllNewsPerson(id_person);
			
			String response = createResponse(false, news, "Lista de notÃ­cias do usuário");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	@POST
	@Path("/curtir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response curtir(String curtid){
		
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBNewsDAO mongo = new MongoDBNewsDAO(mongoClient);
			
			Curtida curtida = new Gson().fromJson(curtid, Curtida.class);
			News news = new News();
			// read news 
			news.setId(curtida.getId_news());
			news = mongo.readNews(news);
			
			
			int index = findPersonCurtida(news.getCurtidas(), curtida.getId_person());
			// se já exite só atualiza
			if(index != -1){
				news.getCurtidas().get(index).setLike(curtida.getLike());
			}else{
				news.getCurtidas().add(curtida);
			}
			// update news
			news = mongo.updateNews(news);
			
			String msg = curtida.getLike() ? "Você curtiu!": "Você não curtiu!";
			
			String response = createResponse(false, news, msg);
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
}
