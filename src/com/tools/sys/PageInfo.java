package com.tools.sys;

public class PageInfo {
	
	//每页记录数
	private Integer maxResult;
	
	//总记录数，根据任务逻辑查询获得，设置时同时和size计算出totalPate总页数的大小。
	private Integer totalResult;
	
	//当前页数，页面传入，根据totalResult（总记录数）可能影响其值的改变。
	private Integer currentPage;
	
	//合计页数，初始无值，在totalResult被设置时同时计算得到。
	private Integer totalPage;

    private Integer page;

    private Integer rows;

	public PageInfo() {
		this.currentPage = 1;
	}

	public Integer getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(Integer maxResult) {
		this.maxResult = maxResult;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(Integer total) {
		this.totalResult = total;
		this.totalPage = (totalResult - 1) / maxResult + 1 ;
		this.currentPage = currentPage > totalPage ? totalPage : currentPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public Integer getFirstResult() {
		return (currentPage-1) * maxResult;
	}

    public void setPage(Integer page) {
        this.page = page;
        if (null != page && !"".equals(page)) {
            this.setCurrentPage(page);
        }
    }

    public void setRows(Integer rows) {
        this.rows = rows;
        if (null != rows && !"".equals(rows)) {
            this.setMaxResult(rows);
        }
    }

    public Integer getPage() {
        return page;
    }

    public Integer getRows() {
        return rows;
    }
}