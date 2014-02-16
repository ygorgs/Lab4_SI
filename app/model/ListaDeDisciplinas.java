package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class ListaDeDisciplinas {

	private static ListaDeDisciplinas listaDeDisciplinas;
	private ArrayList<Disciplina> listaDisc;

	// Armazena todas as disciplinas oferecidas pelo curso
	public ListaDeDisciplinas() {
		listaDisc = new ArrayList<Disciplina>();
		try {
			preencherDisciplinas();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ListaDeDisciplinas getInstance() {
		if (listaDeDisciplinas == null) {
			listaDeDisciplinas = new ListaDeDisciplinas();
		}
		return listaDeDisciplinas;
	}

	/**
	 * 
	 * @param disciplina
	 * @return uma determinada disciplina
	 */
	public Disciplina getDisciplina(int index) {
		return listaDisc.get(index);
	}

	public Disciplina getDisciplina(String nome) {
		for (int i = 0; i < listaDisc.size(); i++) {
			if (listaDisc.get(i).getNome().equals(nome))
				return listaDisc.get(i);
		}
		return null;
	}

	private void preencherDisciplinas() throws JDOMException, IOException {

		File f = new File("disciplinas-do-curso.xml");
		SAXBuilder sb = new SAXBuilder();

		Document d = sb.build(f);
		Element mural = d.getRootElement();
		List<?> elements = mural.getChildren();
		Iterator<?> i = elements.iterator();

		while (i.hasNext()) {
			Element element = (Element) i.next();

			ArrayList<Disciplina> preReq = new ArrayList<Disciplina>();
			for (Object e1 : element.getChild("requisitos").getChildren()) {
				preReq.add(listaDisc.get(Integer.parseInt(((Element) e1).getValue())));
			}

			listaDisc.add(new Disciplina(element.getAttributeValue("id"), element.getAttributeValue("nome"), element
					.getChildText("creditos"), preReq, element.getChildText("dificuldade")));

		}

	}
}