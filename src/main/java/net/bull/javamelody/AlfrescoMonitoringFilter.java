/*
 * Copyright 2008-2019 by Emeric Vernat
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

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.security.AuthorityService;

/**
 * Filter of monitoring JavaMelody for Alfresco.
 *
 * @author Emeric Vernat
 */
// This filter is not used because it is before the point which makes
// authorityService and AuthenticationUtil work.
// So the authorized-users parameter must be used manually instead of this
// filter.

// @WebFilter(filterName = "javamelody-auth", urlPatterns = "/monitoring",
// asyncSupported = true, dispatcherTypes = {
// DispatcherType.REQUEST, DispatcherType.ASYNC })
public class AlfrescoMonitoringFilter implements Filter {

	private static AuthorityService authorityService;

	/** {@inheritDoc} */
	@Override
	public void init(FilterConfig config) throws ServletException {
		// nothing
	}

	/** {@inheritDoc} */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (!isAdmin(httpRequest)) {
			PluginMonitoringFilter.logForDebug("Forbidden access to monitoring from " + request.getRemoteAddr());
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden access");
			httpResponse.flushBuffer();
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isAdmin(HttpServletRequest httpRequest) {
		return authorityService.hasAdminAuthority() || AuthenticationUtil.isRunAsUserTheSystemUser();
	}

	public static void setAuthorityService(AuthorityService authorityService) {
		AlfrescoMonitoringFilter.authorityService = authorityService;
	}

	@Override
	public void destroy() {
		// nothing
	}
}
