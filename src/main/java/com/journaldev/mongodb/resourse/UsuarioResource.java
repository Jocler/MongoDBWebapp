package com.journaldev.mongodb.resourse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
	
	private JsonElement parsePersonToJsonObject(Person person){
		String s = new Gson().toJson(person);
		JsonParser jsonParser = new JsonParser();
		return jsonParser.parse(s);	
	}
	
	
	@POST
	@Path("/inserir")
	@Consumes("application/json")
	@Produces("application/json")
	public Response inserir(Person person) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);
			personDAO.createPerson(person);
		
			JsonObject j = new JsonObject();
			
			if(personDAO.readPerson(person) != null){
				j.addProperty("error", false);
				j.add("data", parsePersonToJsonObject(person));
				j.addProperty("message", "Usuário criado com sucesso." );
				return Response.status(200).entity(j.toString()).build();
				
			}else{
				j.addProperty("error", true);
				j.addProperty("data", "");
				j.addProperty("message", "Error ao criar usuário." );
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
	public Response alterar(Person person) {
		try {
	
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongoClient);
			personDAO.updatePerson(person);
			
			JsonObject j = new JsonObject();
			
			if(personDAO.readPerson(person) != null){
				j.addProperty("error", false);
				j.add("data", parsePersonToJsonObject(person));
				j.addProperty("message", "Usuário atualizado com sucesso." );
				return Response.status(200).entity(j.toString()).build();	
			}else{
				j.addProperty("error", true);
				j.addProperty("data", "");
				j.addProperty("message", "Error ao atualizar usuário." );
				return Response.status(500).entity(j.toString()).build();
			}	
		
			
		} catch (Exception e) {
			throw new WebApplicationException(500);
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
			
			JsonObject j = new JsonObject();
			Person p = personDAO.loginPerson(person);
			if(p != null){
				j.addProperty("error", false);
				j.add("data", parsePersonToJsonObject(p));
				j.addProperty("message", "Login com sucesso." );
				return Response.status(200).entity(j.toString()).build();		
			}else{
				j.addProperty("error", true);
				j.addProperty("data", "");
				j.addProperty("message", "Usuário ou senha incorretos." );
				return Response.status(500).entity(j.toString()).build();
			}	
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
	}
	
	@GET
	@Path("/getAll")
	@Produces("application/json")
	public List<Person> getAll(){
		
	
		try {
			MongoClient mongoClient = new MongoClient();
			MongoDBPersonDAO mongo = new MongoDBPersonDAO(mongoClient);
			
			ArrayList<Person> p = (ArrayList<Person>) mongo.readAllPerson();
			return p;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebApplicationException(500);
		}
		
	}
	
}
