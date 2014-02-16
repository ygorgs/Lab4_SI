package controllers;

import java.util.ArrayList;

import play.db.ebean.Model;

public class Disciplina extends Model {

	private String nome;
	private int creditos;
	private ArrayList<Disciplina> preRequisitos;
	private boolean status;
	private int dificuldade;
	private int id;

	// Information Expert -> Cada disciplina tem um nome, numero de creditos,
	// lista de pre-requisitos e uma dificuldade
	public Disciplina(String id, String nome, String creditos, ArrayList<Disciplina> preReq, String dificuldade) {
		this.setId(Integer.parseInt(id));
		this.setCreditos(Integer.parseInt(creditos));
		this.setNome(nome);
		this.setPreRequisitos(preReq);
		this.status = false;
		this.setDificuldade(Integer.parseInt(dificuldade));
	}

	/**
	 * 
	 * @return lista de pre-requisitos da disciplina
	 */
	public ArrayList<Disciplina> getPreRequisitos() {
		return preRequisitos;
	}

	/**
	 * altera a lista de pre-requisitos da disciplina
	 * 
	 * @param preReq
	 */
	public void setPreRequisitos(ArrayList<Disciplina> preReq) {
		if (preReq == null) {
			this.preRequisitos = new ArrayList<Disciplina>();
		} else {
			this.preRequisitos = preReq;
		}
	}

	/**
	 * verifica se uma disciplina é Co-Requisito
	 * 
	 * @param preRequisito
	 * @return true ou false
	 */
	public boolean isCoRequisito(Disciplina preRequisito) {
		for (Disciplina disciplina : this.preRequisitos) {
			if (disciplina.equals(preRequisito))
				return true;
		}
		return false;
	}

	public boolean isConcluida() {
		return status;
	}

	public void concluirDisciplina() {
		this.status = true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public int getDificuldade() {
		return dificuldade;
	}

	public void setDificuldade(int dificuldade) {
		if (dificuldade <= 1) {
			this.dificuldade = 1;
		} else if (dificuldade >= 5) {
			this.dificuldade = 5;
		} else {
			this.dificuldade = dificuldade;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disciplina other = (Disciplina) obj;
		if (creditos != other.creditos)
			return false;
		if (dificuldade != other.dificuldade)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (!preRequisitos.equals(other.preRequisitos))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

}
