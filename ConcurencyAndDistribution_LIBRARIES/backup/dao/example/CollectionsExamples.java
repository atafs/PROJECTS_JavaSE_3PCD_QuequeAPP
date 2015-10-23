package control_2OO.Start_OO_week05.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

@SuppressWarnings("unused")
public class CollectionsExamples {

	public static void main(String[] args) {
		
		List<Aluno> alunos = new ArrayList<Aluno>(); // ordered, possible repeat
//		List<Aluno> alunos = new LinkedList<Aluno>(); // ordered, possible repeat
//		Queue<Aluno> alunos = new LinkedList<Aluno>(); // FIFO
//		Deque<Aluno > alunos = new LinkedList<Aluno>(); // FIFO or LIFO
//		Stack<Aluno> alunos = new Stack<Aluno>(); // LIFO
//		Set<Aluno> alunos = new HashSet<Aluno>();    // un-ordered, no repeat
//		Map<String, Aluno> alunos = new HashMap<String, Aluno>(); // Mapped with key

		//List, Set, Queue, Deque, Stack
		alunos.add(new Aluno(223, "José"));
		alunos.add(new Aluno(123, "Joana"));
		alunos.add(new Aluno(423, "Ana"));
		alunos.add(new Aluno(623, "Manuel"));
		alunos.add(new Aluno(323, "Beatriz"));
		
		//Map
//		alunos.put("José", new Aluno(223, "José"));
//		alunos.put("Joana", new Aluno(123, "Joana"));
//		alunos.put("Ana", new Aluno(423, "Ana"));
//		alunos.put("Manuel", new Aluno(623, "Manuel"));
//		alunos.put("Beatriz", new Aluno(323, "Beatriz"));

		//List
//		Comparator<Aluno> comparador = new ComparadoDeAlunosPorNome();
//		Comparator<Aluno> comparador = new ComparadoDeAlunosPorNumero();		
//		Collections.sort(alunos, comparador); // Only for lists

		Iterator<Aluno> it = alunos.iterator();
		
		while(it.hasNext()) {
			Aluno a = it.next();
			System.out.println(a);
		}
		
		// List & Set
//		for(Aluno a: alunos) {
//			System.out.println(a);
//		}		
		
		// Queue
//		while(!alunos.isEmpty()) {
//			System.out.println(alunos.poll());
//		}		

		// Stack
//		while(!alunos.empty()) {
//			System.out.println(alunos.pop());
//		}		

		// Deque as Queue
//		while(!alunos.isEmpty()) {
//			System.out.println(alunos.poll());
//		}		

		// Deque as Stack
//		while(!alunos.isEmpty()) {
//			System.out.println(alunos.pop());
//		}		
	
	}

}
