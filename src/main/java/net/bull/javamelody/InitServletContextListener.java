/*
 * Copyright 2008-2017 by Emeric Vernat
 *
 *     This file is part of the Alfresco JavaMelody plugin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
