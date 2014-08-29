// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-links-discovery
// Responsible: ALIADA Consortium
package eu.aliada.gui.rdbms;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import eu.aliada.gui.log.MessageCatalog;
import eu.aliada.shared.log.Log;

/**
 * DDBB connection manager. It contains all the related functions with the DDBB:
 * open, close connection.
 * 
 * @author Elena
 * @since 1.0
 */
public class DBConnectionManager {
	private final Log logger = new Log(DBConnectionManager.class);

	private Connection conn = null;
	
	@Resource(name = "jdbc/aliada")
	private DataSource ds;

	/**
	 * Constructor. Creates the DDBB connection.
	 * 
	 * @since 1.0
	 */
	public DBConnectionManager() {
		InitialContext ic;
		DataSource ds;
		try {
			ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/aliada");
			conn = ds.getConnection();
		} catch (NamingException exception) {
			logger.error(MessageCatalog._00005_DATA_ACCESS_FAILURE, exception);
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00005_DATA_ACCESS_FAILURE, exception);
		}
	}

	/**
	 * Closes the DDBB connection.
	 * 
	 * @since 1.0
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException exception) {
			logger.error(MessageCatalog._00005_DATA_ACCESS_FAILURE, exception);
		}
	}

	/**
	 * Returns the DDBB connection.
	 * 
	 * @return the DDBB connection.
	 * @since 1.0
	 */
	public Connection getConnection() {
		return this.conn;
	}

}
