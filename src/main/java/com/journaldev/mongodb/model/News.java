package com.journaldev.mongodb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class News {
	
	private String id;
	private String id_person;
	private String data_publicacao;
	private String tipo;
	private String titulo;
	private String descricao;
	private List<String> ids_person_voto;
	private boolean votou;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPerson() {
		return id_person;
	}
	public void setIdPerson(String id_person) {
		this.id_person = id_person;
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
	public List<String> getIdPersonVoto() {
		return ids_person_voto;
	}
	public void setIdPersonVoto(List<String> ids_person_voto) {
		this.ids_person_voto = ids_person_voto;
	}
	public boolean isVotou() {
		return votou;
	}
	public void setVotou(boolean votou) {
		this.votou = votou;
	}

}
