package pe.crvm.eureka.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.crvm.eureka.dto.Mensaje;

public class ResponseClient {
	
	public void response(HttpServletRequest request, HttpServletResponse response, Mensaje mensaje) throws IOException{
		// Retornando JSON
	    Gson gson = new Gson();
	    String textoJson = gson.toJson(mensaje);
	    //response.setContentType("text/plain;charset=UTF-8");
	    response.setContentType("application/json;charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    out.println(textoJson);
	    out.flush();
	    out.close();
	}
}
