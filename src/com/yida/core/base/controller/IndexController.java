package com.yida.core.base.controller;

import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.entity.Account;
import com.yida.core.base.entity.AuditOrg;
import com.yida.core.base.entity.Function;
import com.yida.core.base.entity.Staff;
import com.yida.core.interfaces.IFunctionTreeFilter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

	/**
	 * 进入登录页面
	 */
	@RequestMapping("toLogin")
	public String toLogin() {
        return "login";
    }

    @RequestMapping("signin")
    public String signin(HttpServletRequest request,String accounts,String password) {
        try {
            // 检查账号登录的合法性
            Account loginAccount = accountService.handleLogin(accounts,password);
            // 检查账号的有效性
            if (loginAccount.getInvalid()) {
                request.setAttribute("error_msg", "账号被挂起，暂不能登录。");
                return "login";
            }
            if (null == loginAccount.getType()) {
                request.setAttribute("error_msg", "不能确定账号类型。");
                return "login";
            }
            // 内部用户登录
            if (Account.Type.STAFF == loginAccount.getType()) {
                Staff loginStaff = loginAccount.getStaff();
                // 判断该账号是否已经关联人员。
                if (null == loginStaff || !StringUtils.isMeaningFul(loginStaff.getId())) {
                    request.setAttribute("error_msg", "账号没有关联相关人员，暂不能登录。");
                    return "login";
                }
                // 判断该账号关联的人员是否有效
                if (loginStaff.getInvalid()) {
                    request.setAttribute("error_msg", "账号关联的人员记录被标记为无效状态，暂不能登录。");
                    return "login";
                }
                // 确定该账号所属的机构
                AuditOrg auditOrg = staffService.getAuditOrgForStaff(loginStaff);
                if (null == auditOrg) {
                    request.setAttribute("error_msg", "不能确定该账号所属的机构。");
                    return "login";
                }
                // 设置账号的默认机构为人员所在的机构
                loginAccount.setDefaultAuditOrgId(auditOrg.getId());
                // 设置账号所处的机构（用于确定拥有的权限），默认为账号关联人员所在的机构
                loginAccount.setCurrentAuditOrgId(loginAccount.getDefaultAuditOrgId());
            }
            // 管理员登录
            else if (Account.Type.ADMIN == loginAccount.getType()) {
                AuditOrg headAuditOrg = auditOrgService.getHeadAuditOrg();
                String auditOrgId = (null != headAuditOrg) ? headAuditOrg.getId() : null;
                loginAccount.setDefaultAuditOrgId(auditOrgId);
                loginAccount.setCurrentAuditOrgId(auditOrgId);
            }
            // 获取账号拥有的功能权限
            loginAccount.setFunctions(functionService.findAccountFunctions(loginAccount));
            // 登录信息加入到session
            getCurSession().setAttribute(SysConstant.LOGIN_ACCOUNT, loginAccount);
        } catch (Exception e) {
            request.setAttribute("error_msg", "帐号或密码错误。");
            return "login";
        }
        return "index";
    }

	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		// 判断是否登录，如果没有登录则返回空
		HttpSession session = getCurSession();

		if (null != session) {
			// 获取登录账号的菜单权限
			Account loginAccount = (Account) session.getAttribute(SysConstant.LOGIN_ACCOUNT);

			// 机构数据可见选择
			List<AuditOrg> auditOrgs = this.auditOrgService.findAssociateAuditOrgByAccountId(loginAccount.getId());
			request.setAttribute("auditOrgs", auditOrgs);

			// 判断是否切换机构权限
			if (StringUtils.isMeaningFul(selAuditOrgId) && !selAuditOrgId.equals(super.getCurrentAuditOrgId())) {
				if (auditOrgs.contains(new AuditOrg(selAuditOrgId))) {
					super.getCurrentAccount().setCurrentAuditOrgId(selAuditOrgId);

					// 添加账号的访问权限
					List<Function> funs = this.functionService.findAccountFunctions(loginAccount);
					loginAccount.setFunctions(funs);
				}
			}

			// 通过账号已有的权限来初始化首页左边菜单的数据
			List<Function> menus = functionService.listToFunctionTree(loginAccount.getFunctions(), new IFunctionTreeFilter(){
				@Override
				public boolean accpet(Function f) {
					return (f.getHierarchy() == 1 || f.getHierarchy() == 2) && f.getType() == Function.Type.MENU;
				};
			});
			request.setAttribute("menus", menus);
		} else {
			request.setAttribute("auditOrgs", Collections.EMPTY_LIST);
			request.setAttribute("menus", Collections.EMPTY_LIST);
		}
		return "index";
	}

	/**
	 * 退出登录
	 */
	@RequestMapping("logout")
	public String logout() {
		HttpSession session = BaseController.getCurRequest().getSession(false);
		if (null != session) {
			session.invalidate();
		}
		return "login";
	}
	
	/**
	 * 锁定账号
	 */
	@RequestMapping("lockAccount")
	public String lockAccount() {
		JSONObject result = new JSONObject();
		try {
			Account loginAccount = super.getCurrentAccount();
			if (null != loginAccount) {
				loginAccount.setLock(true);
			}
			result.put(SysConstant.AJAX_SUCCESS, "success");
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}
	
	/**
	 * 解锁账号
	 */
	@RequestMapping("unlockAccount")
	public String unlockAccount(String password) {
		JSONObject result = new JSONObject();
		try {
			Account loginAccount = super.getCurrentAccount();
			if (null != password) {
				if (!loginAccount.getPassword().equals(password)) {
					throw new IllegalStateException("密码不正确。");
				}
				if (null != loginAccount) {
					loginAccount.setLock(false);
				}
				result.put(SysConstant.AJAX_SUCCESS, "success");
			} else {
				throw new IllegalStateException("没有输入账号和密码。");
			}
		} catch (Exception e) {
			result.put(SysConstant.AJAX_ERROR, "解锁失败：" + e.getMessage());
		}
		jsonText = result.toString();
		return SysConstant.JSON_RESULT_PAGE;
	}

}
