package at.meikel.dmrl.taglib.properties;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class PrintAllTag extends TagSupport {
	private String var = null;
	private String maxDate = null;
	private String minDate = null;
	private String numberOfMonths = null;

	public void setVar(final String var) {
		this.var = var;
	}

	public void setMaxDate(final String maxDate) {
		this.maxDate = maxDate;
	}

	public void setMinDate(final String minDate) {
		this.minDate = minDate;
	}

	public void setNumberOfMonths(final String numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

	@Override
	public int doStartTag() throws JspException {
		final JspWriter out = pageContext.getOut();
		try {
			printAll(out);
		} catch (final IOException e) {
			// TODO ::MBE:: Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	private void printAll(final JspWriter out) throws IOException {
		final java.util.Properties prop = new java.util.Properties();
		prop.load(pageContext.getServletContext().getResourceAsStream(
				"/META-INF/MANIFEST.MF"));
		// prop.list((java.io.PrintWriter) out);
		@SuppressWarnings("unchecked")
		final java.util.Enumeration e = prop.propertyNames();
		while (e.hasMoreElements()) {
			final String key = (String) e.nextElement();
			final String value = prop.getProperty(key);
			out.println("<p>" + key + " = " + value + "</p>");
		}
	}

}
