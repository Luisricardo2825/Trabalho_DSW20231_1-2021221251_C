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


@WebServlet("/ControllerJogo")
public class JogoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public JogoServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Double numMaior = null;
		Double numMenor = null;
		
		for (int numberoInformado = 1; numberoInformado <= 5; numberoInformado++) {
			Double valorInformadoDoNumero = Double.parseDouble( request.getParameter("num"+numberoInformado) );
			numMenor = numMenor == null || numMenor >= valorInformadoDoNumero ? valorInformadoDoNumero : numMenor;
			numMaior = numMaior == null || numMaior <= valorInformadoDoNumero ? valorInformadoDoNumero : numMaior;
		}
		
		
		sendResponseDoPost(request, response, numMaior, numMenor);
	}
	
	private void sendResponseDoPost(HttpServletRequest request, HttpServletResponse response, Double numMaior, Double numMenor)  throws ServletException, IOException{
		final Path pahtTemplateResultadoJogo = Paths.get( request.getServletContext().getRealPath("/templates/ResultadoJogoTemplate.html") );
		String templateResultadoJogo = Files.readString( pahtTemplateResultadoJogo, StandardCharsets.ISO_8859_1 );
		
		templateResultadoJogo = templateResultadoJogo.replace("$menorNum", numMenor+"");
		templateResultadoJogo = templateResultadoJogo.replace("$maiorNum", numMaior+"");


		response.getWriter().write(templateResultadoJogo);
	}

}
