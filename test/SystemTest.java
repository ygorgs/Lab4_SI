import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.Disciplina;
import model.ListaDeDisciplinas;
import model.Periodo;
import model.PlanoDeCurso;

import org.junit.Before;
import org.junit.Test;



public class SystemTest {

	private ListaDeDisciplinas grade;
	
	@Before
	public void inicializar(){
		grade = ListaDeDisciplinas.getInstance();
	}
	
	/*
	 * visito o sistema e ele me exibe uma lista das disciplinas do 1o período já alocadas
	 */
	@Test
	public void deveExibirListaDasDisciplinasDoPrimeiroPeriodo() {
		PlanoDeCurso meuPlano = new PlanoDeCurso();
		Periodo primeiroPeriodo = meuPlano.getPeriodo(1);
		Periodo primeiroPeriodoPadrao = meuPlano.getPeriodoPadrao(1);
		
		for (Disciplina disciplina : primeiroPeriodoPadrao.getDisciplinas()) {
			assertTrue(primeiroPeriodo.getDisciplinas().contains(disciplina));
		}
	}
	
	/*
	 * Uma disciplina tem pré-requisitos, e ela só pode ser incluída se seus prérequisitos 
	 * já foram incluídos no 1o período. 
	 */
	@Test
	public void deveAdicionarDisciplinasComPreRequisitos(){
		PlanoDeCurso meuPlano = new PlanoDeCurso();
		assertTrue(meuPlano.addDisciplina(grade.getDisciplina("Programacao 2"),2));
		assertFalse(meuPlano.addDisciplina(grade.getDisciplina("Estrutura de Dados e Algoritmos"),2));
	}
	
	/*
	 *  Não é possível incluir disciplinas de forma a ter mais de 28 créditos alocados no período.
	 */
	@Test
	public void naoDeveTerMaisDe28CreditosEmUmPeriodo(){
		PlanoDeCurso meuPlano = new PlanoDeCurso();
		meuPlano.addDisciplina(grade.getDisciplina("Programacao 2"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Laboratório de Programacao 2"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Calculo Diferencial e Integral 2"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Matemática Discreta"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Fundamentos de Física Clássica"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Metodologia Científica"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Opitativa"),2);
		assertFalse(meuPlano.addDisciplina(grade.getDisciplina("Opitativa"),2));
	}

	/*
	 * retirar disciplinas alocadas para os períodos a partir do 2o, 
	 */
	@Test
	public void deveRetirarDisciplinasAlocadas(){
		PlanoDeCurso meuPlano = new PlanoDeCurso();
		meuPlano.addDisciplina(grade.getDisciplina("Programacao 2"),2);
		//disciplina do 1º periodo
		assertFalse(meuPlano.removerDisciplina(grade.getDisciplina("Programacao 1")));
		//disciplina do 2º periodo
		assertTrue(meuPlano.removerDisciplina(grade.getDisciplina("Programacao 2")));
		//disciplina que não está na grade
		assertFalse(meuPlano.removerDisciplina(grade.getDisciplina("Metodologia Científica")));
	}
	
	/*
	 * Ao retirar uma disciplina que é prerequisito de outras, o sistema deve remover 
	 * também todos aquelas que fiquem sem seus prérequisitos satisfeitos.
	 */
	@Test
	public void deveRetirarTodasAsDisciplinasSemPrerequisitosSatisfeitos(){
		PlanoDeCurso meuPlano = new PlanoDeCurso();
		meuPlano.addDisciplina(grade.getDisciplina("Programacao 2"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Laboratório de Programacao 2"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Teoria dos Grafos"),2);
		meuPlano.addDisciplina(grade.getDisciplina("Estrutura de Dados e Algoritmos"),3);
		
		meuPlano.removerDisciplina((grade.getDisciplina("Teoria dos Grafos")));
		
		assertFalse(meuPlano.removerDisciplina((grade.getDisciplina("Estrutura de Dados e Algoritmos"))));
	}
	
}
