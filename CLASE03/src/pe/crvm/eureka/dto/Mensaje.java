package pe.crvm.eureka.dto;
/**
 * 
 * @author Alumno
 *
 */
public class Mensaje {
	
	
	
	private int codigo;
	private String texto;
	
	public Mensaje() {
		
	}
	
	public Mensaje(int codigo, String texto) {
		this.codigo = codigo;
		this.texto = texto;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
}
