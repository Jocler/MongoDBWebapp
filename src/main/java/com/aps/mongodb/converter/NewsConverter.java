package com.aps.mongodb.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.aps.mongodb.model.Curtida;
import com.aps.mongodb.model.News;
import com.aps.mongodb.model.Person;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class NewsConverter {

	// convert News Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(News n) {

		
		BasicDBList db_curtidas_list = new BasicDBList();
		if(n.getCurtidas() != null && n.getCurtidas().size() > 0){
			for(Curtida curtida: n.getCurtidas()){
				BasicDBObject db_curtida = new BasicDBObject();
				db_curtida.append("id_person", curtida.getId_person());
				db_curtida.append("id_news", curtida.getId_news());
				db_curtida.append("like", curtida.getLike());
				db_curtidas_list.add(db_curtida);
			}
		}
		
		BasicDBObject person = new BasicDBObject();
		person.append("_id", new ObjectId(n.getPerson().getId()));
		person.append("name", n.getPerson().getName());
		person.append("photo", n.getPerson().getPhoto());
		
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("person", person)
				.append("data_publicacao", n.getDataPublicacao())
				.append("tipo", n.getTipo())
				.append("titulo", n.getTitulo())
				.append("descricao", n.getDescricao())
				.append("curtidas", db_curtidas_list);
		
		if (n.getId() != null)
			builder = builder.append("_id", new ObjectId(n.getId()));
		return builder.get();
	}

	// convert DBObject Object to News
	// take special note of converting ObjectId to String
	public static News toNews(DBObject doc) {
		News n = new News();
		n.setDataPublicacao((String) doc.get("data_publicacao"));
		n.setTipo((String) doc.get("tipo"));
		n.setTitulo((String) doc.get("titulo"));
		n.setDescricao((String) doc.get("descricao"));
		
		List<Curtida> curtidas_list = new ArrayList<>();
		BasicDBList db_curtidas_list = (BasicDBList) doc.get("curtidas");
		
		if(db_curtidas_list != null && db_curtidas_list.size() > 0){
		
			for (int i = 0; i < db_curtidas_list.size(); i ++){
				BasicDBObject curtida = (BasicDBObject)db_curtidas_list.get(i);
				Curtida c = new Curtida();
				c.setId_person(curtida.getString("id_person"));
				c.setId_news(curtida.getString("id_news"));
				c.setLike((Boolean)curtida.getBoolean("like"));
				curtidas_list.add(c);
			}
		}
		n.setCurtidas(curtidas_list);
		
		BasicDBObject db_person = (BasicDBObject)doc.get("person");
		Person person = new Person();
		ObjectId id_p = (ObjectId) db_person.get("_id");
		person.setId(id_p.toString());
		person.setName(db_person.getString("name"));
		person.setPhoto(db_person.getString("photo"));
		n.setPerson(person);
		
		ObjectId id = (ObjectId) doc.get("_id");
		n.setId(id.toString());
		return n;

	}
	
}
