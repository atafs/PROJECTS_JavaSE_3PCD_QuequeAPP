package control_2OO.Start_OO_week05.example;

import java.util.Comparator;

public class ComparadoDeAlunosPorNumero implements Comparator<Aluno> {

	@Override
	public int compare(Aluno o1, Aluno o2) {
		return (int)(o1.getNumero() - o2.getNumero());
	}

}
