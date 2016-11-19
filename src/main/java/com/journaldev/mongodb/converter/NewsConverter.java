package com.journaldev.mongodb.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.News;
import com.journaldev.mongodb.model.Person;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class NewsConverter {

	// convert News Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(News n) {

		//BasicDBList ids_person_voto = new BasicDBList();
		//ids_person_voto.addAll(n.getIdPersonVoto());
		
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("id_person", n.getIdPerson()).append("data_publicacao", n.getDataPublicacao())
				.append("tipo", n.getTipo()).append("titulo", n.getTitulo())
				.append("descricao", n.getDescricao()).append("ids_person_voto", "");
		
		if (n.getId() != null)
			builder = builder.append("_id", new ObjectId(n.getId()));
		return builder.get();
	}

	// convert DBObject Object to News
	// take special note of converting ObjectId to String
	public static News toNews(DBObject doc) {
		News n = new News();
		n.setIdPerson((String) doc.get("id_person"));
		n.setDataPublicacao((String) doc.get("data_publicacao"));
		n.setTipo((String) doc.get("tipo"));
		n.setTitulo((String) doc.get("titulo"));
		n.setDescricao((String) doc.get("descricao"));
		
	/*	List<String> ids_persons = new ArrayList<>();
		BasicDBList ids_person_voto = (BasicDBList) doc.get("ids_person_voto");
		
		for(int i = 0; i< ids_person_voto.size(); i ++){
			BasicDBObject id_person = (BasicDBObject)ids_person_voto.get(i);
			//ids_persons.add(id_person.getString("id_person"));
			ids_persons.add(id_person.toString());
		}
		n.setIdPersonVoto(ids_persons);*/
		
		ObjectId id = (ObjectId) doc.get("_id");
		n.setId(id.toString());
		return n;

	}
	
}
