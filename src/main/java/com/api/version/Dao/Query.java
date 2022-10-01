package com.api.version.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.api.version.Model.Objeto;


public class Query {
    private volatile static Connection con;
	private volatile static boolean open = false;

	public Query() {
		con = ConnectionFactory.getConnection();
	}

	public static void isOpen(boolean isopen){
		open = isopen;
		if(isopen){
			con = ConnectionFactory.getConnection();
		}else{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    public static List<Object> query(Object[] parametros) {
		List<Object> lista = new ArrayList<>();
		String sql = (String)parametros[0];
		parametros[1] = ((String)parametros[1]).toLowerCase();
		if(parametros.length>2){
			parametros[2] = ((String)parametros[2]).toLowerCase();
		}
		try {
			if(open){
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
			 

			

			if (parametros[1].equals("objeto")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();
				List<Object> columname = new ArrayList<>();

				for (int i = 1; i <= meta.getColumnCount(); i++) {

					columname.add(meta.getColumnLabel(i));
					
				}
				while (rs.next()) {
					List<Object> l = new ArrayList<>();
					obj = new Objeto();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						l.add(rs.getString(i));
					}
					
					obj.l.add(columname);
					obj.l.add(l);
					lista.add(obj);
				}

			} else if (parametros[1].equals("objeto combobox")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();
				List<Object> columname = new ArrayList<>();

				for (int i = 1; i <= meta.getColumnCount(); i++) {

					columname.add(meta.getColumnLabel(i));
					
				}
				
			
				while (rs.next()) {
					List<Object> l = new ArrayList<>();
					obj = new Objeto();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						l.add(rs.getString(i));
					}
					obj.l.add(columname);
					obj.l.add(l);
					obj.toString = (String)obj.getFirst((String)parametros[2]);
					for (int i = 3; i < parametros.length; i++) {
						obj.valuestosearch.add((String)parametros[i]);
					}

					lista.add(obj);
				}

			} else if (parametros[1].equals("relatorio") || parametros[1].equals("objetos")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();
				List<Object> l = new ArrayList<>();

				for (int i = 1; i <= meta.getColumnCount(); i++) {

					l.add(meta.getColumnLabel(i));

				}
				obj.l.add(l);

				while (rs.next()) {
					l = new ArrayList<>();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						l.add(rs.getString(i));
						
					}

					obj.l.add(l);
				}
				lista = new ArrayList<>();
				lista.add(obj);
			}

		
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return lista;

	}

	public static List<Objeto> query(String[] parametros) {

		try {
			if(open){
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Objeto> lista = new ArrayList<>();
		String sql = (String)parametros[0];
		parametros[1] = ((String)parametros[1]).toLowerCase();
		if(parametros.length>2){
			parametros[2] = ((String)parametros[2]).toLowerCase();
		}
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery(); 

			if (parametros[1].equals("objeto")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();
				List<Object> columname = new ArrayList<>();

				for (int i = 1; i <= meta.getColumnCount(); i++) {

					columname.add(meta.getColumnLabel(i));
					
				}
				
				
				while (rs.next()) {
					List<Object> l = new ArrayList<>();
					obj = new Objeto();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						l.add(rs.getString(i));
					}
				
					obj.l.add(columname);
					obj.l.add(l);
					lista.add(obj);
				}
				
				

            } else if (parametros[1].equals("objeto combobox")) {
				Objeto obj = new Objeto();
				ResultSetMetaData meta = rs.getMetaData();
				List<Object> columname = new ArrayList<>();

				for (int i = 1; i <= meta.getColumnCount(); i++) {

					columname.add(meta.getColumnLabel(i));
					
				}
				
			
				while (rs.next()) {
					List<Object> l = new ArrayList<>();
					obj = new Objeto();
					for (int i = 1; i <= meta.getColumnCount(); i++) {
						l.add(rs.getString(i));
					}
					obj.l.add(columname);
					obj.l.add(l);
					obj.toString = (String)obj.getFirst((String)parametros[2]);
					for (int i = 3; i < parametros.length; i++) {
						obj.valuestosearch.add((String)parametros[i]);
					}

					lista.add(obj);
				}

			}else if (parametros[1].equals("relatorio") || parametros[1].equals("objetos")) {
                Objeto obj = new Objeto();
                ResultSetMetaData meta = rs.getMetaData();
                List<Object> l = new ArrayList<>();

                for (int i = 1; i <= meta.getColumnCount(); i++) {

                    l.add(meta.getColumnLabel(i));

                }
                obj.l.add(l);

                while (rs.next()) {
                    l = new ArrayList<>();
                    for (int i = 1; i <= meta.getColumnCount(); i++) {
                        l.add(rs.getString(i));
                        
                    }

                    obj.l.add(l);
                }
                lista = new ArrayList<>();
                lista.add(obj);
            }
			rs.close();
			
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}
	
	/***
	 * Recebe
	 * 
	 * @category Sem retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public static boolean CED(String sql) {
		boolean value = false;
		if (sql == null) {
			return value;
		}
		try {
			if(open){
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			boolean r  = ps.execute();
			
			value = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			value = false;
		}
		return value;
	}

/***
	 * Recebe uma consulta e retorna uma lista de valores;
	 * 
	 * @return List<String>
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public static List<String> search(String sql) {

		List<String> dados = new ArrayList<>();
		try {
			if(open){
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try(PreparedStatement ps = con.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();

			while (rs.next() && rs.getString(1) != null) {
				dados.add(rs.getString(1));
			}
			rs.close();
			if (dados.isEmpty()) {
				dados.add("");
			} /*
				 * else if(Functions.isNull(dados.get(0))){
				 * dados.set(0, "");
				 * }
				 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao executar o comando no banco de dados!\r\nFunção: Query.Search()");
		}

		return dados;
	}

	/***
	 * 
	 * @return String
	 * @category Com retorno, ligação com banco de dados.
	 * @exception SQLException
	 * @author Kauan t.i
	 ***/
	public static String Count(String sql) {
		String valor = "0";
		if (sql == null) {
			return valor;
		}

		try {
			if(open){
				if(con == null || con.isClosed()){
					con = ConnectionFactory.getConnection();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try(PreparedStatement ps = con.prepareStatement(sql)){
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getString(1) != null) {
				valor = rs.getString(1);
				try {
					Integer.parseInt(valor);
				} catch (NumberFormatException e) {
					System.out.println("Count só pode receber números\r\n Função: Count");
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro ao executar o comando no banco de dados!\r\nFunção: Query.Count()");
		}

		return valor;
	}

}
