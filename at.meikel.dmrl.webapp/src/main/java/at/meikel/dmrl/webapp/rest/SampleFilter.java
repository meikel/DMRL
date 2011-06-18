package at.meikel.dmrl.webapp.rest;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

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
		PrintWriter out = response.getWriter();
		CharResponseWrapper wrapper = new CharResponseWrapper(response);
		chain.doFilter(request, wrapper);
		CharArrayWriter caw = new CharArrayWriter();
		caw.write("jsonp(");
		caw.write(wrapper.toString());
		caw.write(")");
		response.setContentLength(caw.toString().length());
		out.write(caw.toString());
		out.close();
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

class CharResponseWrapper extends ServletResponseWrapper {
	private CharArrayWriter output;
	private PrintWriter writer;

	public String toString() {
		return output.toString();
	}

	public CharResponseWrapper(ServletResponse response) {
		super(response);
		output = new CharArrayWriter();
		writer = new PrintWriter(output);
	}

	public PrintWriter getWriter() {
		return writer;
	}
}