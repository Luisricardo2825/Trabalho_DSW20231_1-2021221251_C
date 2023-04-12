package db;

import java.util.ArrayList;
import java.util.List;

import models.Pessoa;

public class DB_PESSOA {
	private static List<Pessoa> pessoas = new ArrayList<>();
	
	public static void add(Pessoa pessoa) {
		pessoas.add(pessoa);
	}
	
	public static List<Pessoa> getAll() {
		return pessoas;
	}
	
}
