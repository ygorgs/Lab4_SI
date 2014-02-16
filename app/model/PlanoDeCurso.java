package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.JDOMException;

public class PlanoDeCurso {

	private ListaDeDisciplinas listaDeDisciplinas;
	private List<Periodo> meuPlanoDeCurso;
	private List<Periodo> fluxogramaPadrao;

	// Creator -> Um plano de Curso é formado por n Periodos
	public PlanoDeCurso() {
		listaDeDisciplinas = ListaDeDisciplinas.getInstance();
		meuPlanoDeCurso = new ArrayList<Periodo>();
		try {
			geraFluxogramaPadrao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Periodo> getPlanoDeCurso() {
		return meuPlanoDeCurso;
	}

	// auemnto da coesão -> testa as condições necessarias para a disciplina ser
	// adicionada num determinado periodo
	public boolean addDisciplina(Disciplina disciplina, int periodo) {
		while (meuPlanoDeCurso.size() < periodo) {
			meuPlanoDeCurso.add(new Periodo());
		}
		if (checarPreRequisitos(disciplina, periodo)) { return meuPlanoDeCurso.get(periodo - 1).addDisciplina(
				disciplina); }
		return false;
	}

	// auemnto da coesão -> testa as condições necessarias para a disciplina ser
	// removida de um determinado periodo
	public boolean removerDisciplina(Disciplina disciplina) {
		for (int i = 1; i < meuPlanoDeCurso.size(); i++) {
			if (meuPlanoDeCurso.get(i).contem(disciplina)) {
				meuPlanoDeCurso.get(i).removeDisciplina(disciplina);
				removerCoRequisitos(disciplina, i);
				return true;
			}
		}
		return false;
	}

	// retorna meu plano de curso
	public Periodo getPeriodo(int periodo) {
		return meuPlanoDeCurso.get(periodo - 1);

	}

	// retorna plano de curso padrão
	public Periodo getPeriodoPadrao(int periodo) {
		return fluxogramaPadrao.get(periodo - 1);
	}

	private void removerCoRequisitos(Disciplina preRequisito, int periodo) {
		for (int i = periodo + 1; i < meuPlanoDeCurso.size(); i++) {
			for (int j = (meuPlanoDeCurso.get(i).getDisciplinas().size()) - 1; j >= 0; j--) {
				if (meuPlanoDeCurso.get(i).getDisciplinas().get(j).isCoRequisito(preRequisito))
					removerDisciplina(meuPlanoDeCurso.get(i).getDisciplinas().get(j));
			}
		}
	}

	private boolean checarPreRequisitos(Disciplina disciplina, int periodo) {
		for (Disciplina preRequisito : disciplina.getPreRequisitos()) {
			boolean contem = false;
			for (int i = periodo - 1; i >= 0; i--) {
				if (meuPlanoDeCurso.get(i).getDisciplinas().contains(preRequisito)) {
					contem = true;
					break;
				}
			}
			if (!contem)
				return false;
		}
		return true;
	}

	private void geraFluxogramaPadrao() throws JDOMException, IOException {
		this.fluxogramaPadrao = new ArrayList<Periodo>();

	}
}
