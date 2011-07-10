package at.meikel.dmrl.webapp.rest;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

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
		System.out.println(request.getContentType());
		// PrintWriter out = response.getWriter();
		ServletOutputStream out = response.getOutputStream();
		CharResponseWrapper wrapper = new CharResponseWrapper(
				(HttpServletResponse) response);
		System.out.println("callback=" + request.getParameter("callback"));
		chain.doFilter(request, wrapper);
		// CharArrayWriter caw = new CharArrayWriter();
		String s = "jsonp(" + wrapper.toString() + ")";
		if (s.length() < 100) {
			System.out.println("s=" + s);
		} else {
			System.out.println("s.length()=" + s.length());
		}
		// caw.write(s);
		response.setContentLength(s.length());
		// out.write(caw.toString());
		out.print(s);
		out.close();
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

class CharResponseWrapper extends HttpServletResponseWrapper {
	private CharArrayWriter output;
	private StringBuilder builder;

	@Override
	public String toString() {
		// if (output != null) {
		// System.out.println("output = " + output.toString());
		// }
		// if (builder != null) {
		// System.out.println("builder = " + builder.toString());
		// }

		if (output != null) {
			return output.toString();
		} else if (builder != null) {
			return builder.toString();
		} else {
			return "";
		}
	}

	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
	}

	@Override
	public PrintWriter getWriter() {
		output = new CharArrayWriter();
		return new PrintWriter(output);
	}

	@Override
	public ServletOutputStream getOutputStream() {
		// builder = new StringBuilder();
		output = new CharArrayWriter();
		final PrintWriter pw = new PrintWriter(output);
		ServletOutputStream stream = new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				// builder.append((char) b);
				// output.append((char) b);
				pw.write(b);
			}
		};
		return stream;
	}

}