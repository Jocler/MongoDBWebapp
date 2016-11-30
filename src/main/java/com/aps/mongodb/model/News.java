package com.aps.mongodb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class News {
	
	private String id;
	private Person person;
	private String data_publicacao;
	private String tipo;
	private String titulo;
	private String descricao;
	private boolean votou;
	private List<Curtida> curtidas;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String getDataPublicacao() {
		return data_publicacao;
	}
	public void setDataPublicacao(String data_publicacao) {
		this.data_publicacao = data_publicacao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isVotou() {
		return votou;
	}
	public void setVotou(boolean votou) {
		this.votou = votou;
	}
	public List<Curtida> getCurtidas() {
		return curtidas;
	}
	public void setCurtidas(List<Curtida> curtidas) {
		this.curtidas = curtidas;
	}

}
