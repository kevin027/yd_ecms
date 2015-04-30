package helper.controller;


import com.tools.sys.PageInfo;
import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import helper.entity.GenTemplate;
import helper.service.GenTemplateService;
import helper.vo.ListGenTemplateForm;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/gen")
public class GenTemplateController extends BaseController {

    public @Resource GenTemplateService genTemplateService;

    @RequestMapping("main")
    public String main() {
        return "main";
    }

    @ResponseBody
    @RequestMapping("listGenTemplate")
    public String listGenTemplate(ListGenTemplateForm query,PageInfo pageInfo) {
        try {
            List<GenTemplate> list = this.genTemplateService.listGenTemplate(query, pageInfo);
            List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
            jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonText;
    }

    @RequestMapping("addGenTemplate")
    public String addGenTemplate() {
        return "addGenTemplate";
    }

    @ResponseBody
    @RequestMapping("saveGenTemplate")
    public String saveGenTemplate(GenTemplate genTemplate) {
        JSONObject result = new JSONObject();
        try {
            GenTemplate saveEntity = this.genTemplateService.saveGenTemplate(genTemplate);
            result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
            result.put("saveId", saveEntity.getId());
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return jsonText;
    }

    @RequestMapping("modGenTemplate")
    public String modGenTemplate(HttpServletRequest request,String id) {
        GenTemplate genTemplate = genTemplateService.getGenTemplateById(id);
        request.setAttribute("form",genTemplate);
        return "modGenTemplate";
    }

    @ResponseBody
    @RequestMapping("updateGenTemplate")
    public String updateGenTemplate(GenTemplate genTemplate) {
        JSONObject result = new JSONObject();
        try {
            this.genTemplateService.updateGenTemplate(genTemplate);
            result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return jsonText;
    }

    @ResponseBody
    @RequestMapping("delGenTemplate")
    public String delGenTemplate(String genTemplateId) {
        JSONObject result = new JSONObject();
        try {
            this.genTemplateService.delGenTemplateById(genTemplateId);
            result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return jsonText;
    }

}
