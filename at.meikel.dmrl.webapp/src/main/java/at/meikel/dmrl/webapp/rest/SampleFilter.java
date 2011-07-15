package at.meikel.dmrl.webapp.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class SampleFilter
 */
public class SampleFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(SampleFilter.class);

	/**
	 * Default constructor.
	 */
	public SampleFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// String url = request.getScheme() + request.getServerName()
		// + request.getServerPort();
		String reqUrl = "unknown";
		try {
			reqUrl = ((HttpServletRequest) request).getRequestURL().toString();
			String queryString = ((HttpServletRequest) request)
					.getQueryString();
			if (queryString != null) {
				reqUrl += "?" + queryString;
			}
		} catch (Exception e) {
			// ignore
		}

		LOGGER.info("Executing servlet for URL " + reqUrl + " at "
				+ new java.util.Date());
		LOGGER.info("    request-content-type=" + request.getContentType());
		String callback = request.getParameter("callback");
		LOGGER.info("    callback=" + callback);
		ServletOutputStream out = response.getOutputStream();
		if (callback != null) {
			out.write((callback + "(").getBytes());
		}
		chain.doFilter(request, response);
		if (callback != null) {
			out.write(")".getBytes());
		}
		LOGGER.info("    response-content-type=" + response.getContentType());
		out.close();
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
