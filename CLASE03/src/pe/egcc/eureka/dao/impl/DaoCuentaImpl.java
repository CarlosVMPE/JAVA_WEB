package pe.egcc.eureka.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import pe.egcc.eureka.dao.espec.DaoCuentaEspec;
import pe.egcc.eureka.db.AccesoDB;

/**
 *
 * @author Gustavo Coronel
 */
public class DaoCuentaImpl implements DaoCuentaEspec {

  @Override
  public List<Map<String, ?>> conMovimientos(String cuenta) {
    List<Map<String, ?>> lista;
    Connection cn = null;
    try {
      cn = AccesoDB.getConnection();
      String sql = "select CUENCODIGO,MONENOMBRE,CUENSALDO, "
              + "CUENESTADO,MOVINUMERO,MOVIFECHA, "
              + "MOVIIMPORTE,TIPOCODIGO,TIPONOMBRE "
              + "from v_movimiento "
              + "where CUENCODIGO = ?";
      PreparedStatement pstm = cn.prepareStatement(sql);
      pstm.setString(1, cuenta);
      ResultSet rs = pstm.executeQuery();
      lista = JdbcUtil.rsToList(rs);
      rs.close();
      pstm.close();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage());
    } catch (Exception e) {
      String mensaje = "Se ha producido un error, intentelo mas tarde.";
      if (e.getMessage() != null && !e.getMessage().isEmpty()) {
        mensaje += (" " + e.getMessage());
      }
      throw new RuntimeException(mensaje);
    } finally {
      try {
        cn.close();
      } catch (Exception e) {
      }
    }
    return lista;
  }
  
  @Override
  public void registrarDeposito(String cuenta, double importe, String codEmp){
	  Connection cn = null;
	  try {
		//*** Iniciar Tx  
		cn = AccesoDB.getConnection();
		cn.setAutoCommit(false);
		
		//*** Actualizando la Tabla Cuenta
		String sql = "update cuenta "
				+ "set DEC_CUENSALDO = DEC_CUENSALDO + ?, "
				+ "INT_CUENCONTMOV = INT_CUENCONTMOV + 1 "
				+ "where CHR_CUENCODIGO = ?";
		
		PreparedStatement pstm = cn.prepareStatement(sql);
		pstm.setDouble(1, importe);
		pstm.setString(2, cuenta);
		int filas = pstm.executeUpdate();
		pstm.close();
		
		if(filas == 0){ 	// el codigo no existe
			throw new SQLException("¡No existe la cuenta!"); 	
		}
		
		//*** Leer Nro. de Movimiento
		
		sql = "select INT_CUENCONTMOV from cuenta "
			+ "where CHR_CUENCODIGO = ?";
		pstm = cn.prepareStatement(sql);
		pstm.setString(1, cuenta);
		ResultSet rs = pstm.executeQuery();
		
		rs.next();
		
		int nroMov = rs.getInt("INT_CUENCONTMOV");
		
		rs.close();
		pstm.close();
		
		//*** Insertar el Movimiento
		
		sql = "insert into movimiento (CHR_CUENCODIGO, INT_MOVINUMERO, "
					+"DTT_MOVIFECHA, CHR_EMPLCODIGO, CHR_TIPOCODIGO, "
					+"DEC_MOVIIMPORTE) "
					+"values(?,?,SYSDATE,?,'003',?)"; //   CHR_TIPOCODIGO = 003 porque sera un deposito
		
		pstm = cn.prepareStatement(sql);
		pstm.setString(1, cuenta);
		pstm.setInt(2, nroMov);
		pstm.setString(3, codEmp);
		pstm.setDouble(4, importe);
		
		pstm.executeUpdate();
		pstm.close();
		
		
		//*** Confirmar Tx
		cn.commit();
		
		  
		} catch (Exception e) {
			// Aquí si hay un error, se hará un rollback
			try {
				cn.rollback();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
			String  mensaje = "Error en el proceso!";
			
			if(e.getMessage() != null && !e.getMessage().isEmpty()){
				mensaje += " " + e.getMessage();
			}
			
			throw new RuntimeException(mensaje);
			
		} finally {
			try {
				cn.close();

			} catch (Exception e2) {

			}
		}
  }

}
