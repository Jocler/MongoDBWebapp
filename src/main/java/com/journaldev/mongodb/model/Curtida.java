package com.journaldev.mongodb.model;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Curtida {

	private String id;
    private String id_person;
    private String id_news;
    private boolean like;


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getId_person() {
		return id_person;
	}

	public void setId_person(String id_person) {
		this.id_person = id_person;
	}

	public String getId_news() {
		return id_news;
	}

	public void setId_news(String id_news) {
		this.id_news = id_news;
	}

	public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    
}
