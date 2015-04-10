package com.yida.core.common.easyui;

import java.util.List;

public class EasyUITreeData {
	private String id;
	private String text;
	private String code;
	private String pid;
	private String icon;
	private String iconSkin;
	private List<EasyUITreeData> children;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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
	public List<EasyUITreeData> getChildren() {
		return children;
	}
	public void setChildren(List<EasyUITreeData> children) {
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
	public Boolean getNocheck() {
		return nocheck;
	}
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
}
