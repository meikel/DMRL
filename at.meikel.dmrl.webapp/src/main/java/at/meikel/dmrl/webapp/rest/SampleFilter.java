package at.meikel.dmrl.webapp.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class SampleFilter
 */
public class SampleFilter implements Filter {

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
		System.out
				.println("--------------------------------------------------");
		System.out.println("timestamp=" + new java.util.Date());
		System.out.println("request-content-type=" + request.getContentType());
		String callback = request.getParameter("callback");
		System.out.println("callback=" + callback);
		ServletOutputStream out = response.getOutputStream();
		if (callback != null) {
			out.write((callback + "(").getBytes());
		}
		chain.doFilter(request, response);
		if (callback != null) {
			out.write(")".getBytes());
		}
		System.out.println("response-content-type=" + response.getContentType());
		out.close();
		System.out
				.println("--------------------------------------------------");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
