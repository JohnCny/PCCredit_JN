/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.wicresoft.jrad.base.database.model;

import java.util.List;

public class QueryResult<V> {
	private Integer totalCount = null;
	private List<V> items = null;

	public QueryResult(Integer totalCount, List<V> items) {
		this.items = items;
		this.totalCount = totalCount;
	}

	public List<V> getItems() {
		return this.items;
	}

	public void setItems(List<V> items) {
		this.items = items;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}