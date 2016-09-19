package net.bull.javamelody;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitServletContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (Parameters.getParameter(Parameter.STORAGE_DIRECTORY) == null
				&& System.getProperty("alfresco.home") != null) {
			System.setProperty(Parameters.PARAMETER_SYSTEM_PREFIX + Parameter.STORAGE_DIRECTORY.getCode(),
					System.getProperty("alfresco.home") + "/alf_data/javamelody");
		}
		if (Parameters.getParameter(Parameter.SQL_TRANSFORM_PATTERN) == null) {
			// regexp pour agréger les paramètres bindés dans les critères
			// de requêtes SQL tels que "in (?, ?, ?, ?)" et ainsi pour éviter
			// que ces requêtes ayant un nombre variable de paramètres soient
			// considérées comme différentes ;
			// de fait cela agrège aussi les values des inserts
			System.setProperty(Parameters.PARAMETER_SYSTEM_PREFIX + Parameter.SQL_TRANSFORM_PATTERN.getCode(),
					"\\([\\?, \n\r]+\\)");
		}

		LOG.debug("JavaMelody is monitoring Alfresco");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// nothing
	}
}
