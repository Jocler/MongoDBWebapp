package com.journaldev.mongodb.resourse;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.journaldev.mongodb.dao.MongoDBPersonDAO;
import com.journaldev.mongodb.model.Person;
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
				String response = createResponse(false, person, "Usuário criado com sucesso.");
				return Response.status(200).entity(response).build();
				
			}else{
				String response = createResponse(true, null, "Error ao criar usuário.");
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
			person = personDAO.updatePerson(person);
			
			if(person != null){
				String response = createResponse(false, person, "Usuário atualizado com sucesso.");
				return Response.status(200).entity(response).build();	
			}else{
				String response = createResponse(true, null, "Error ao atualizar usuário.");
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
				return Response.status(200).entity(response).build();		
			}else{
				String response = createResponse(true, null, "Usuário ou senha incorretos.");
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
		
			String response = createResponse(false, person, "");
			return Response.status(200).entity(response).build();
			
		} catch (UnknownHostException e) {
			String response = createResponse(true, null, e.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
}
