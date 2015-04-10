package com.yida.core.common.tag;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 金额格式化标签
 */
public class NumberFormatTag extends TagSupport {
	
	private static final long serialVersionUID = 7875194978242202535L;
	
	public static final Map<String, DecimalFormat> formats = new ConcurrentHashMap<String, DecimalFormat>();
	
	private String value;
	private String format;

	@Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		DecimalFormat ft = formats.get(format);
		if (null == ft) {
			ft = new DecimalFormat(format);
			try {
				pageContext.getOut().println(ft.format(value));
				formats.put(format, ft);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				pageContext.getOut().println(ft.format(value));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.SKIP_BODY;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
