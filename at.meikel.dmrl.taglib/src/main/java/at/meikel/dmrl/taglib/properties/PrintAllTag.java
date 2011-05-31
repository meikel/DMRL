package at.meikel.dmrl.taglib.properties;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class PrintAllTag extends TagSupport {
	private String var = null;
	private String maxDate = null;
	private String minDate = null;
	private String numberOfMonths = null;

	public void setVar(String var) {
		this.var = var;
	}
	
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public void setNumberOfMonths(String numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			printAll(out);
		} catch (IOException e) {
			// TODO ::MBE:: Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	private void calendar(JspWriter out) throws IOException {
		// http://jqueryui.com/demos/datepicker/#multiple-calendars

		String tooltip = var + " = ";
		
		Integer i = null;
		try {
			i = (Integer) pageContext.getAttribute("xxx", PageContext.PAGE_SCOPE);
		} catch (Exception e) {
			// ignore
		}
		
		if (i == null) {
			i = Integer.valueOf(0);
		}
		
		String value = "1.1.2011";
		tooltip += value;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
	        Date date = sdf.parse(value);
	        sdf = new SimpleDateFormat("dd.MM.yyyy");
	        value = sdf.format(date);
        } catch (ParseException e) {
        	// TODO ::MBE::
        }
		out.println("<input type=\"text\" size=\"10\" maxlength=\"10\" value=\"" + value + "\" id=\"datepicker_" + i + "\"></input>");
		out.println("<input type=\"hidden\" name=\"" + var + "\" id=\"date_" + i + "\"></input>");
		out.println("<script>");
		out.println("$(function() {");
		out.println("	$( \"#datepicker_" + i + "\" ).datepicker({");
		out.println("			altField:			'#date_" + i + "',");
		if (maxDate != null) {
			out.println("			maxDate:			'" + maxDate + "',");
		}
		if (minDate != null) {
			out.println("			minDate:			'" + minDate + "',");
		}
		out.println("			altFormat:			'yy-mm-dd',");
		out.println("			appendText:			'&nbsp; (tt.mm.jjjj oder ttmmjjjj)',");
		out.println("			showOn: 			'button',");
		out.println("			buttonImage: 		'pics/calendar.gif',");
		out.println("			buttonImageOnly:	true,");
		out.println("			buttonText:			'" + tooltip + "',");
		out.println("			changeMonth:		true,");
		out.println("			changeYear:			true,");
		if (numberOfMonths != null) {
			out.println("			numberOfMonths:		" + numberOfMonths + ",");
		}
		out.println("			showButtonPanel:	true,");
		out.println("			showWeek:			true");
		out.println("	});");
		out.println("});");
		out.println("</script>");
		
		if (i.intValue() == Integer.MAX_VALUE) {
			i = Integer.valueOf(0);
		} else {
			i = Integer.valueOf(i.intValue() + 1);
		}
		
		pageContext.setAttribute("xxx", i, PageContext.PAGE_SCOPE);
	}
	
	private void printAll(JspWriter out) throws IOException {
		java.util.Properties prop = new java.util.Properties();
		prop.load(pageContext.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
		// prop.list((java.io.PrintWriter) out);
		@SuppressWarnings("unchecked")
		java.util.Enumeration e = prop.propertyNames(); 
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			out.println("<p>" + key + " = " + value + "</p>");
		}
	}

}
