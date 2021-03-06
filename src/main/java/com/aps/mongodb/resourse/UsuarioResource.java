package com.aps.mongodb.resourse;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.aps.mongodb.dao.MongoDBNewsDAO;
import com.aps.mongodb.dao.MongoDBPersonDAO;
import com.aps.mongodb.model.Person;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;


@Path("/usuario")
public class UsuarioResource {
	
	private JsonElement parsePersonToJsonObject(Object person){
		String s = new Gson().toJson(person);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(s);	
	}
	
	private String createResponse(boolean error, Object data, String msg){
		JsonObject j = new JsonObject();
		j.addProperty("error", error);
		
		if(data != null)
			j.add("data", parsePersonToJsonObject(data));
		else
			j.addProperty("data", "");
		
		j.addProperty("message", msg );
		
		return j.toString();
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inserir(Person person) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);
			person = personDAO.createPerson(person);
			
			if(person != null){
				String response = createResponse(false, person, "Usu�rio criado com sucesso.");
				mongoClient.close();
				return Response.status(200).entity(response).build();
				
			}else{
				mongoClient.close();
				String response = createResponse(true, null, "Error ao criar usu�rio.");
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
	public Response alterar(Person person) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);
			MongoDBNewsDAO newsDAO = new MongoDBNewsDAO(mongoClient);
			person = personDAO.updatePerson(person);
			
			if(person != null){
				newsDAO.updateInfoPersonInNews(person);
				String response = createResponse(false, person, "Usu�rio atualizado com sucesso.");
				mongoClient.close();
				return Response.status(200).entity(response).build();
			}else{
				mongoClient.close();
				String response = createResponse(true, null, "Error ao atualizar usu�rio.");
				return Response.status(500).entity(response).build();
			}	
		
		} catch (Exception e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
	}
	
	@POST
	@Path("/login")
	@Consumes("application/json")
	@Produces("application/json")
	public Response login(Person person) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);			
			person = personDAO.loginPerson(person);
			
			if(person != null){
				String response = createResponse(false, person, "Login com sucesso.");
				mongoClient.close();
				return Response.status(200).entity(response).build();		
			}else{
				mongoClient.close();
				String response = createResponse(true, null, "Usu�rio ou senha incorretos.");
				return Response.status(500).entity(response).build();
			}	
		
		} catch (Exception e) {
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
			MongoDBPersonDAO mongo = new MongoDBPersonDAO(mongoClient);
			ArrayList<Person> person = (ArrayList<Person>) mongo.readAllPerson();
		
			String response = createResponse(false, person, "Lista de todos os usuarios");
			mongoClient.close();
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
}
