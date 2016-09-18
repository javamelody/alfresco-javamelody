/*
 * Copyright 2008-2016 by Emeric Vernat
 *
 *     This file is part of Java Melody.
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

import org.alfresco.service.cmr.security.AuthorityService;

/**
 * Initialisation JavaMelody add-on.
 * 
 * @author Emeric Vernat
 */
public class JavaMelodyInit {
	public void setAuthorityService(AuthorityService authorityService) {
		AlfrescoMonitoringFilter.setAuthorityService(authorityService);
	}

	public void init() {
		if (Parameters.getParameter(Parameter.SQL_TRANSFORM_PATTERN) == null) {
			// regexp pour agréger les paramètres bindés dans les critères
			// de requêtes SQL tels que "in (?, ?, ?, ?)" et ainsi pour éviter
			// que ces requêtes ayant un nombre variable de paramètres soient
			// considérées comme différentes ;
			// de fait cela agrège aussi les values des inserts
			System.setProperty(Parameters.PARAMETER_SYSTEM_PREFIX + Parameter.SQL_TRANSFORM_PATTERN, "\\([\\?, ]+\\)");
		}
		
		LOG.info("JavaMelody is monitoring Alfresco");
	}
}
