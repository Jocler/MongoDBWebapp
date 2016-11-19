package com.journaldev.mongodb.converter;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.Person;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class PersonConverter {

	// convert Person Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Person p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", p.getName()).append("country", p.getCountry())
				.append("senha", p.getSenha()).append("email", p.getEmail());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static Person toPerson(DBObject doc) {
		Person p = new Person();
		p.setName((String) doc.get("name"));
		p.setCountry((String) doc.get("country"));
		p.setEmail((String) doc.get("email"));
		p.setSenha((String) doc.get("senha"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;

	}
	
}
