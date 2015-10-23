package control_2OO.Start_OO_week05.example;

import java.util.Comparator;

public class ComparadoDeAlunosPorNome implements Comparator<Aluno> {

	@Override
	public int compare(Aluno a0, Aluno a1) {		
		return a0.getNome().compareTo(a1.getNome());
	}

}
