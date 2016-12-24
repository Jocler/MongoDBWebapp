package com.aps.mongodb.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bson.types.ObjectId;

import com.aps.mongodb.converter.NewsConverter;
import com.aps.mongodb.model.News;
import com.aps.mongodb.model.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBNewsDAO {

	private DBCollection col;

	public MongoDBNewsDAO(MongoClient mongo) {
		this.col = mongo.getDB("aps-noticias").getCollection("News");
	}

	public News createNews(News n) {
		
		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		n.setDataPublicacao(df.format(cal.getTime()));
		
		DBObject doc = NewsConverter.toDBObject(n);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		n.setId(id.toString());
	
		return readNews(n);
	}

	public News updateNews(News n) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(n.getId())).get();
		this.col.update(query, NewsConverter.toDBObject(n));
		
		return readNews(n);
	}

	public List<News> readAllNews() {
		List<News> data = new ArrayList<News>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			News n = NewsConverter.toNews(doc);
			data.add(n);
		}
		return data;
	}
	
	public List<News> readNewsByType(String type) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("tipo", type).get();
		
		List<News> data = new ArrayList<News>();
		DBCursor cursor = col.find(query);
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			News n = NewsConverter.toNews(doc);
			data.add(n);
		}
		return data;
	}
	
	public List<News> readAllNewsPerson(String id_person) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("person._id", new ObjectId(id_person)).get();
		
		List<News> data = new ArrayList<News>();
		DBCursor cursor = col.find(query);
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			News n = NewsConverter.toNews(doc);
			data.add(n);
		}
		return data;
	}
	
	public void updateInfoPersonInNews(Person person) {
		
		BasicDBObject newsUpdate = new BasicDBObject();
		newsUpdate.append("$set", new BasicDBObject().append("person.name", person.getName()).append("person.photo", person.getPhoto()));
		
		DBObject query = BasicDBObjectBuilder.start()
				.append("person._id", new ObjectId(person.getId())).get();
		
		this.col.updateMulti(query, newsUpdate);
	
	}

	public void deleteNews(News n) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(n.getId())).get();
		this.col.remove(query);
	}

	public News readNews(News n) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(n.getId())).get();
		DBObject data = this.col.findOne(query);
		return NewsConverter.toNews(data);
	}
	

}
