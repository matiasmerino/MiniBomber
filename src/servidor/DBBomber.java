package servidor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;

public class DBBomber {
	
	private Connection conn;
	
	public DBBomber() {
		conn = MySQLConnection.getConnection();
	}
	
	public Object puntuacionesMax() {
		Statement stmt = null;
		ResultSet rs = null;
		int cont = 0;
		Object[][] datos = new Object[20][5];
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM usuarios ORDER BY puntosacum DESC LIMIT 20");
			while (rs.next()) {
				datos[cont][0] = cont+1;
				datos[cont][1] = rs.getString(2);
				datos[cont][2] = rs.getInt(7);
				datos[cont][3] = rs.getInt(4);
				if(rs.getInt(8) != 0)
					datos[cont][4] = String.format("%.2f",(double)rs.getInt(4)/rs.getInt(8));
				else datos[cont][4] = 0;
				cont++;
			}			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return datos;
	}
	

	public boolean agregaUsuario(String usuario, String pass, String nick, String pregunta, String respuesta) {
		CallableStatement cStmt = null;
		try {
			cStmt = conn.prepareCall("{call INSERTA_USUARIO(?, ?, ?, ?, ?)}");
			cStmt.setString(1, usuario);
			cStmt.setString(2, pass);
			cStmt.setString(3, nick);
			cStmt.setString(4, pregunta);
			cStmt.setString(5, respuesta);
			cStmt.execute();	
			return true;
		} catch(SQLException sqle) {
			System.out.println("Usuario duplicado");
		} finally {
			try {
				cStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;		
	}
	
	public boolean validarLogin(String usuario, String contrasena) {
		Statement stmt = null;
		ResultSet rs = null;
		String sentencia = "Select * from usuarios where usuario like '"+ usuario +"' and pass = '"+contrasena+"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sentencia);
			return rs.next();

		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public void actualizaContrasena(String nuevaPass, String usuario) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("Update usuarios set pass = ? where usuario = ?");
			pstmt.setString(1, nuevaPass);
			pstmt.setString(2, usuario);
			pstmt.executeUpdate();			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public void actualizaNick(String nuevoNick, String usuario) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("Update usuarios set nick = ? where usuario = ?");
			pstmt.setString(1, nuevoNick);
			pstmt.setString(2, usuario);
			pstmt.executeUpdate();			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public String getDato(String usuario, String campo) {
		Statement stmt = null;
		ResultSet rs = null;
		String sentencia = "Select "+campo+" from usuarios where usuario like '"+usuario+"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sentencia);
			if(rs.next())
				return rs.getString(1);
		
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}
	
	public String getPuntosPorJuego(String usuario) {
		Statement stmt = null;
		ResultSet rs = null;
		float div = 0;
		String sentencia = "SELECT puntosacum,juegosjugados FROM usuarios WHERE usuario LIKE '"+usuario+"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sentencia);
			rs.next();
			int a = rs.getInt(1);
			int b = rs.getInt(2);
			div = (float)a/b;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return String.format("%.2f",div);
	}
	
	public String getRank(String usuario) {
		Statement stmt = null;
		ResultSet rs = null;
		String sentencia = "SELECT 1+(SELECT count(*) FROM usuarios a WHERE a.puntosacum > b.puntosacum) AS Ranking "
							+ "FROM usuarios b WHERE usuario LIKE '"+usuario+"'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sentencia);
			rs.next();
			return rs.getString(1);			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return usuario;
	}
	
	
	public void desconectar() {
		MySQLConnection.close();
	}

	public static void main(String[] args) {
		
		DBBomber prueba = new DBBomber();
		prueba.desconectar();
	}

	public void actualizaPuntuacion(String usuario, int puntos) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Update usuarios set puntosacum = puntosacum + ?, juegosjugados = juegosjugados+1 where usuario = ?");
			pstmt.setLong(1, puntos);
			pstmt.setString(2, usuario);
			pstmt.executeUpdate();			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	public void actualizaJuegosGanados(String usuario) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("Update usuarios set juegosganados = juegosjugados+1 where usuario = ?");
			pstmt.setString(1, usuario);
			pstmt.executeUpdate();			
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}			
	}
	
}