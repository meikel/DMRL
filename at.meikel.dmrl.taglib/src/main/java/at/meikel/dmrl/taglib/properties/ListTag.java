package at.meikel.dmrl.taglib.properties;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class ListTag extends TagSupport {
	private String path = null;
	private HashSet<Object> keys = null;

	public void setPath(final String path) {
		this.path = path;
	}

	public void setKeys(final String keys) {
		this.keys = new HashSet<Object>();
		if (keys.trim().length() == 0) {
			// default = *
			this.keys.add("*");
		} else {
			StringTokenizer st = new StringTokenizer(keys, ",");
			while (st.hasMoreTokens()) {
				String key = st.nextToken().trim();
				if (key.length() > 0) {
					this.keys.add(key);
				}
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		final JspWriter out = pageContext.getOut();
		try {
			if (keys == null) {
				keys = new HashSet<Object>();
				keys.add("*");
			}
			list(out);
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

	private void list(final JspWriter out) throws IOException {
		final Properties prop = new Properties();
		prop.load(pageContext.getServletContext().getResourceAsStream(path));
		if (keys.remove("*")) {
			keys.addAll(prop.keySet());
		}
		for (final Object key : keys) {
			final String value = prop.getProperty(key.toString());
			out.println("<p>" + key + " = " + value + "</p>");
		}
	}

}
