package com.yida.core.common.ztree;

import java.util.List;

public class ZtreeData {
	private String id;
	private String name;
	private String pid;
	private String icon;
	private String iconSkin;
	private Integer isOrg;
	private List<ZtreeData> children;
	private Boolean open = Boolean.FALSE;
	private Boolean checked = Boolean.FALSE;
	private Boolean isParent = Boolean.FALSE;
	private Boolean nocheck = Boolean.FALSE;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public List<ZtreeData> getChildren() {
		return children;
	}
	public void setChildren(List<ZtreeData> children) {
		this.children = children;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	
	public Boolean getNocheck() {
		return nocheck;
	}
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
	@Override
	public String toString() {
		return name;
	}
	public Integer getIsOrg() {
		return isOrg;
	}
	public void setIsOrg(Integer isOrg) {
		this.isOrg = isOrg;
	}
}
