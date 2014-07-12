// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-linked-data-server
// Responsible: ALIADA Consortium
package eu.aliada.linkedDataServerSetup.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import eu.aliada.linkedDataServerSetup.log.MessageCatalog;
import eu.aliada.shared.log.Log;
import eu.aliada.linkedDataServerSetup.rdbms.DBConnectionManager;

import javax.servlet.ServletContext;

/**
 * Linked Data Server startup and shutdown listener.
 * 
 * @author Idoia Murua
 * @since 1.0
 */
public class ApplicationLifecycleListener implements ServletContextListener {
	private Log logger = new Log(ApplicationLifecycleListener.class);
	
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		logger.info(MessageCatalog._00001_STARTING);
		ServletContext sc = event.getServletContext();
		//Get DDBB connection
		DBConnectionManager db = new DBConnectionManager();
		//Save DDBB connection in Servlet Context attribute
		sc.setAttribute("db", db);
	}

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
		//Close DDBB connection
		DBConnectionManager db = (DBConnectionManager) event.getServletContext().getAttribute("db");
		db.closeConnection();
		logger.info(MessageCatalog._00012_STOPPED);	
	}
}
