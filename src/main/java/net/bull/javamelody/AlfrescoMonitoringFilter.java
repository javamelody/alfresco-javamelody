/*
 * Copyright 2008-2016 by Emeric Vernat
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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			LOG.debug("Forbidden access to monitoring from " + request.getRemoteAddr());
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
