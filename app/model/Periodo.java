package model;

import java.util.ArrayList;
import java.util.List;

public class Periodo {

	private List<Disciplina> periodo;
	public final int MAXCREDITOS = 28;
	private int creditosAtuais;
	
	
	public Periodo() {
		this.setCreditosAtuais(0);
		this.periodo = new ArrayList<Disciplina>();
	}
	

	public List<Disciplina> getPeriodo() {
		return periodo;
	}
	
	//Creator -> Cada periodo é formado por n disciplinas
	//Information experto -> A classe periodo tem as informações necessarias para adicionar uma disciplina
	//Diminuição do acoplamento, aumento da coesão -> Os créditos de cada periodo são calculados dentro da classe periodo
	public boolean addDisciplina(Disciplina disc) {
		if (getCreditosAtuais() + disc.getCreditos() <= MAXCREDITOS) {
			setCreditosAtuais(getCreditosAtuais() + disc.getCreditos());
			this.periodo.add(disc);
			return true;
		}
		return false;
	}

	//Information experto -> A classe periodo tem as informações necessarias para remover uma disciplina
	public boolean removeDisciplina(Disciplina disc) {
		if (getCreditosAtuais() >= 0) {
			setCreditosAtuais(getCreditosAtuais() - disc.getCreditos());
			this.periodo.remove(disc);
			return true;
		}
		return false;
	}
	

	public boolean contem(Disciplina disciplina){
		return periodo.contains(disciplina);
	}
	
	public List<Disciplina> getDisciplinas(){
		return periodo;
	}

	public int getCreditosAtuais() {
		return creditosAtuais;
	}
	
	
	public void setCreditosAtuais(int creditosAtuais) {
		this.creditosAtuais = creditosAtuais;
	}
}
