package com.tools.sys;

import java.util.Collections;
import java.util.List;

public class Page<T> {
	
	private Integer total;
	private List<T> rows;
	private List<T> footer;
	
	private Page(List<T> rows,List<T> footer, Integer total) {
		if (null == rows) {
			rows = Collections.emptyList();
		} else {
			this.rows = rows;
		}
		if (null == footer) {
			footer = Collections.emptyList();
		} else {
			this.footer = footer;
		}
		if (null == total) {
			total = 0;
		} else {
			this.total = total;
		}
	}
	
	public static <E> Page<E> create(List<E> rows, Integer total) {
		return new Page<E>(rows,null, total);
	}
	public static <E> Page<E> create(List<E> rows,List<E> footer, Integer total) {
		return new Page<E>(rows,footer, total);
	}

	public Integer getTotal() {
		return total;
	}

	public List<T> getRows() {
		return rows;
	}
	public List<T> getFooter(){
		return footer;
	}
}
