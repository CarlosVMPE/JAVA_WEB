package pe.crvm.eureka.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pe.crvm.eureka.dto.Mensaje;
import pe.crvm.eureka.model.CuentaModel;
import pe.egcc.eureka.domain.Empleado;

/**
 * Servlet implementation class CuentaController
 */
@WebServlet({"/CuentaDeposito","/CuentaRetiro","/CuentaConMovimientos"})
public class CuentaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private CuentaModel cuentaModel;
	private ResponseClient responseClient;
	
	
	@Override
	public void init() throws ServletException {
		cuentaModel = new CuentaModel();
		responseClient = new ResponseClient();
	}
	
    @Override
    protected void service(HttpServletRequest request, 
    					HttpServletResponse response) throws ServletException, IOException {
    	
    	String path;
    	
    	// Control de sesiones
    	if(request.getSession().getAttribute("usuario") == null){
    		Mensaje mensaje = new Mensaje(-100,"Debe iniciar sesi�n");
    		responseClient.response(request, response, mensaje);
    		return;
    	} else{
    		path = request.getServletPath();
    		
    		if(path.equals("/CuentaDeposito")){
    			cuentaDeposito(request, response);
    		}else if(path.equals("/CuentaConMovimientos")){
    			cuentaConMovimientos(request, response);
    		}
    	}
    	
 
        if(path.equals("/CuentaDeposito")){
        	cuentaDeposito(request,response);
        }
    	
    }

	private void cuentaConMovimientos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Mensaje mensaje = new Mensaje(1,"Ya estamos");
		try {
			String cuenta = request.getParameter("cuenta");
			List<Map<String, ?>> lista = cuentaModel.traerMovimientos(cuenta);
			if(lista.isEmpty()){
				mensaje = new Mensaje(-1, "Cuenta no existe");
			}else{
				Gson gson = new Gson();
				mensaje = new Mensaje(1, gson.toJson(lista));

			}
			
		} catch (Exception e) {
			mensaje = new Mensaje(-1, e.getMessage());
		}
		
		
		
		responseClient.response(request, response, mensaje);
		
	}

	private void cuentaDeposito(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		Mensaje mensaje;
		try {
			// Datos
			String cuenta = request.getParameter("cuenta");
			double importe = Double.parseDouble(request.getParameter("importe"));
			
			// Empleado
			Empleado bean = (Empleado) request.getSession().getAttribute("usuario");
			
			// Proceso
			cuentaModel.registrarDeposito(cuenta, importe, bean.getCodigo());
			mensaje = new Mensaje(1,"La operaci�n matem�tica se realiz� con exito");
			
			
		} catch (Exception e) {
			mensaje = new Mensaje(-1,e.getMessage());
		}
		
		
		
	}

}
