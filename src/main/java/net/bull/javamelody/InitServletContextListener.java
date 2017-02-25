/*
 * Copyright 2008-2017 by Emeric Vernat
 *
 *     This file is part of Java Melody.
 *
 * Java Melody is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java Melody is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java Melody.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.bull.javamelody;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initialisation des paramètres.
 * @author Emeric Vernat
 */
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
