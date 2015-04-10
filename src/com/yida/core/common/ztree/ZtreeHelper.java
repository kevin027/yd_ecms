package com.yida.core.common.ztree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.Org;
import com.yida.core.base.entity.Staff;
import com.yida.core.common.easyui.EasyUITreeData;
import com.yida.core.common.enums.Sex;

public class ZtreeHelper {

	public static List<ZtreeData> toZtreeData(String parentId, List<? extends IZtreeData<?>> zTreeDataList, Set<String> checkIds) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(zTreeDataList.size());
		for (IZtreeData<? extends IZtreeData<?>> obj : zTreeDataList) {
			ZtreeData z = new ZtreeData();
			
			// 节点标识、节点名称、父节点标识
			z.setId(obj.getId());
			z.setName(obj.getName());
			z.setPid(parentId); 
			
			// 是否选中状态，并且如果选中就展开
			z.setChecked(null != checkIds && checkIds.contains(obj.getId()));
			z.setOpen(z.getChecked());
			
			List<? extends IZtreeData<?>> children = obj.getChildren();
			if (null != children && 0 < children.size()) {
				z.setChildren(toZtreeData(z.getId(), children, checkIds));
				z.setIsParent(true);
			} else {
				z.setIsParent(false);
			}
			datas.add(z);
		}
		return datas;
	}

	public static List<ZtreeData> staffToZtreeData(String parentId, List<Staff> staffs) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(staffs.size());
		for (Staff obj : staffs) {
			ZtreeData z = new ZtreeData();
			z.setId(obj.getId());
			z.setName(obj.getName());
			z.setPid(parentId); 
			z.setOpen(false);
			z.setIsParent(false);
			if (null != obj.getSex() && Sex.FEMALE == obj.getSex()) {
				z.setIconSkin("female");
			} else {
				z.setIconSkin("male");
			}
			datas.add(z);
		}
		return datas;
	}

	public static List<ZtreeData> orgToZtreeData(String parentId, List<Org> orgs) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(orgs.size());
		for (Org obj : orgs) {
			ZtreeData z = new ZtreeData();
			z.setId(obj.getId());
			z.setName(obj.getName());
			z.setPid(parentId); 
			z.setOpen(false);
			z.setIsParent(true);
			z.setIconSkin(1 == obj.getHierarchy() ? "company" : "department");
			datas.add(z);
		}
		return datas;
	}

	public static List<ZtreeData> orgTreeForStaffSelect(String parentId, List<Org> orgs, Set<String> checkIds) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(orgs.size());
		for (Org obj : orgs) {
			ZtreeData curData = new ZtreeData();
			curData.setId(obj.getId());
			curData.setName(obj.getName());
			curData.setPid(parentId); 
			curData.setOpen(true);
			curData.setIsParent(true);
			curData.setNocheck(true);
			curData.setIconSkin(1 == obj.getHierarchy() ? "company" : "department");
			
			List<ZtreeData> czds = new ArrayList<ZtreeData>();
			curData.setChildren(czds);
			
			// 加入机构
			if (null != obj.getChildren() && 0 < obj.getChildren().size()) {
				czds.addAll(orgTreeForStaffSelect(curData.getId(), obj.getChildren(), checkIds));
			}
			
			// 加入人员
			if (null != obj.getStaffs() && 0 < obj.getStaffs().size()) {
				List<ZtreeData> szs = new ArrayList<ZtreeData>(obj.getStaffs().size());
				for (Staff staff : obj.getStaffs()) {
					ZtreeData sz = new ZtreeData();
					sz.setId(staff.getId());
					sz.setName(staff.getName());
					sz.setPid(parentId); 
					sz.setOpen(false);
					sz.setIsParent(false);
					sz.setIconSkin(Sex.FEMALE == staff.getSex() ? "female" : "male");
					sz.setChecked(null != checkIds && checkIds.contains(staff.getId()));
					szs.add(sz);
				}
				czds.addAll(szs);
			}
			datas.add(curData);
		}
		return datas;
	}

	public static List<ZtreeData> orgTreeWithoutCheckbox(String parentId, List<Org> orgs, boolean withStaff) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(orgs.size());
		for (Org obj : orgs) {
			ZtreeData curData = new ZtreeData();
			curData.setId(obj.getId());
			curData.setName(obj.getName());
			curData.setPid(parentId); 
			curData.setIsParent(true);
			curData.setNocheck(true);
			curData.setOpen(1 == obj.getHierarchy());
			curData.setIconSkin(1 == obj.getHierarchy() ? "company" : "department");
			
			List<ZtreeData> czds = new ArrayList<ZtreeData>();
			curData.setChildren(czds);
			
			// 加入机构
			if (null != obj.getChildren() && 0 < obj.getChildren().size()) {
				czds.addAll(orgTreeWithoutCheckbox(curData.getId(), obj.getChildren(), withStaff));
			}
			
			// 加入人员
			if (withStaff) {
				if (null != obj.getStaffs() && 0 < obj.getStaffs().size()) {
					List<ZtreeData> szs = new ArrayList<ZtreeData>(obj.getStaffs().size());
					for (Staff staff : obj.getStaffs()) {
						ZtreeData sz = new ZtreeData();
						sz.setId(staff.getId());
						sz.setName(staff.getName());
						sz.setPid(parentId); 
						sz.setOpen(false);
						sz.setIsParent(false);
						sz.setIconSkin(Sex.FEMALE == staff.getSex() ? "female" : "male");
						sz.setNocheck(true);
						szs.add(sz);
					}
					czds.addAll(szs);
				}
			}
			datas.add(curData);
		}
		return datas;
	}

	public static List<ZtreeData> orgTreeForAccountSelect(String parentId, List<Org> orgs, Set<String> checkIds) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(orgs.size());
		for (Org obj : orgs) {
			ZtreeData curData = new ZtreeData();
			curData.setId(obj.getId());
			curData.setName(obj.getName());
			curData.setPid(parentId); 
			curData.setOpen(true);
			curData.setIsParent(true);
			curData.setNocheck(true);
			curData.setIconSkin("company");
			
			List<ZtreeData> szs = new ArrayList<ZtreeData>();
			Stack<Org> stack = new Stack<Org>();
			stack.push(obj);
			while(!stack.isEmpty()) {
				Org o = stack.pop();
				stack.addAll(o.getChildren());
				for (Staff staff : o.getStaffs()) {
					for (Account a : staff.getAccounts()) {
						ZtreeData sz = new ZtreeData();
						sz.setId(a.getId());
						sz.setName(a.getAccounts());
						sz.setPid(parentId); 
						sz.setOpen(false);
						sz.setIsParent(false);
						sz.setIconSkin("male");
						sz.setChecked(null != checkIds && checkIds.contains(a.getId()));
						szs.add(sz);
					}
				}
			}
			curData.setChildren(szs);
			datas.add(curData);
		}
		return datas;
	}

	public static List<ZtreeData> orgTreeForOrgSelect(String parentId, List<Org> orgs, Set<String> checkOrgNodeIds) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(orgs.size());
		for (Org obj : orgs) {
			ZtreeData curData = new ZtreeData();
			curData.setId(obj.getId());
			curData.setName(obj.getName());
			curData.setPid(parentId); 
			curData.setOpen(true);
			curData.setIsParent(true);
			curData.setNocheck(false);
			curData.setChecked(checkOrgNodeIds.contains(obj.getId()));
			curData.setIconSkin(1 == obj.getHierarchy() ? "company" : "department");
			
			List<ZtreeData> czds = new ArrayList<ZtreeData>();
			curData.setChildren(czds);
			
			// 加入机构
			if (null != obj.getChildren() && 0 < obj.getChildren().size()) {
				czds.addAll(orgTreeForOrgSelect(curData.getId(), obj.getChildren(), checkOrgNodeIds));
			}
			
			datas.add(curData);
		}
		return datas;
	}

	/**
	 * easyui的tree数据
	 * @param parentId
	 * @param zTreeDataList
	 * @param checkIds
	 * @return
	 */
	public static List<EasyUITreeData> toEasyUITreeData(String parentId, List<Org> orgs, Set<String> checkOrgNodeIds) {
		List<EasyUITreeData> datas = new ArrayList<EasyUITreeData>(orgs.size());
		for (Org obj : orgs) {
			EasyUITreeData curData = new EasyUITreeData();
			curData.setId(obj.getId());
			curData.setText(obj.getName());
			curData.setPid(parentId); 
			curData.setOpen(true);
			curData.setIsParent(true);
			curData.setNocheck(false);
			curData.setChecked(checkOrgNodeIds.contains(obj.getId()));
			curData.setIconSkin(1 == obj.getHierarchy() ? "company" : "department");
			
			List<EasyUITreeData> czds = new ArrayList<EasyUITreeData>();
			curData.setChildren(czds);
			
			// 加入机构
			if (null != obj.getChildren() && 0 < obj.getChildren().size()) {
				czds.addAll(toEasyUITreeData(curData.getId(), obj.getChildren(), checkOrgNodeIds));
			}
			
			datas.add(curData);
		}
		return datas;
	}

	public static List<ZtreeData> toZtreeData(List<? extends IZtreeData<?>> zTreeDataList) {
		List<ZtreeData> datas = new ArrayList<ZtreeData>(zTreeDataList.size());
		for (IZtreeData<? extends IZtreeData<?>> obj : zTreeDataList) {
			ZtreeData z = new ZtreeData();
			z.setId(obj.getId());
			z.setName(obj.getName());
			z.setChecked(false);
			z.setOpen(false);
			
			List<? extends IZtreeData<?>> children = obj.getChildren();
			if (null != children && 0 < children.size()) {
				z.setChildren(toZtreeData(children));
				z.setIsParent(true);
			} else {
				z.setIsParent(false);
			}
			datas.add(z);
		}
		return datas;
	}
}
