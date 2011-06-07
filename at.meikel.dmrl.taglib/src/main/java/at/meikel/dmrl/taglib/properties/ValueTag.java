package at.meikel.dmrl.taglib.properties;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class ValueTag extends TagSupport {
	private String path = null;
	private String key = null;

	public void setPath(final String path) {
		this.path = path;
	}

	public void setKey(final String key) {
		this.key = key.trim();
	}

	@Override
	public int doStartTag() throws JspException {
		final JspWriter out = pageContext.getOut();
		try {
			value(out);
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

	private void value(final JspWriter out) throws IOException {
		final Properties prop = new Properties();
		prop.load(pageContext.getServletContext().getResourceAsStream(path));
		final String value = prop.getProperty(key);
		out.print(value);
	}

}
