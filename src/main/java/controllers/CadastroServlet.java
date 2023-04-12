package controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DB_PESSOA;
import models.Pessoa;


@WebServlet("/ControllerPessoa")
public class CadastroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public CadastroServlet() { }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Pessoa novaPessoa = new Pessoa( request.getParameter("nome"), request.getParameter("idade"));
		DB_PESSOA.add( novaPessoa );
		
		sendPostResponse(request, response, novaPessoa);
	}
	
	private void sendPostResponse(HttpServletRequest request, HttpServletResponse response, Pessoa ultimaPesosaCriada) throws ServletException, IOException {		
		final Path tempateTabelaPathInWeb = Paths.get( request.getServletContext().getRealPath("/templates/TabelaPessoasTemplate.html") );
		final Path templateLinasPathInWeb = Paths.get( request.getServletContext().getRealPath("/templates/LinhaTabelaPessoasTemplate.html") );

		
		String templatePaginaTabelaPessoas = Files.readString( tempateTabelaPathInWeb, StandardCharsets.ISO_8859_1 );
		String templateLinhaTabelaPessoas = Files.readString( templateLinasPathInWeb, StandardCharsets.ISO_8859_1 );
		
		templatePaginaTabelaPessoas = templatePaginaTabelaPessoas.replace("$Nome_Pessoa" , ultimaPesosaCriada.getNome());
		templatePaginaTabelaPessoas = templatePaginaTabelaPessoas.replace("$Idade_Pessoa" , ultimaPesosaCriada.getIdade().toString());

		String linhasDaTabela = "";
		
		int ids = 0;
		for (Pessoa p : DB_PESSOA.getAll()) {			
			linhasDaTabela += templateLinhaTabelaPessoas
					.replace("$ID", ids+"")
					.replace("$Nome", p.getNome())
					.replace("$Idade", p.getIdade()+"");
			
			ids++;
		}
		
		templatePaginaTabelaPessoas = templatePaginaTabelaPessoas.replace("$rows", linhasDaTabela);

		response.getWriter().write(templatePaginaTabelaPessoas);
	}

}
