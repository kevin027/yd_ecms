package helper.controller;


import com.tools.sys.SysConstant;
import com.tools.utils.StringUtils;
import com.yida.core.base.controller.BaseController;
import helper.entity.GenTemplate;
import helper.service.GenTemplateService;
import helper.vo.ListGenTemplateForm;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/gen")
public class GenTemplateController extends BaseController {
    private @Resource
    GenTemplateService genTemplateService;
    private GenTemplate genTemplate;
    private ListGenTemplateForm query;

    @RequestMapping("main")
    public String main() {
        return "main";
    }

    @RequestMapping("listGenTemplate")
    public String listGenTemplate() {
        try {
            List<GenTemplate> list = this.genTemplateService.listGenTemplate(query, pageInfo);
            List<String> includePropertys = Arrays.asList("id", "name", "invalid", "remark");
            jsonText = "{\"total\": " + pageInfo.getTotalResult() + ", \"rows\":" + StringUtils.toJsonArrayIncludeProperty(list, includePropertys) + "}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysConstant.JSON_RESULT_PAGE;
    }

    @RequestMapping("addGenTemplate")
    public String addGenTemplate() {
        return "addGenTemplate";
    }

    @RequestMapping("saveGenTemplate")
    public String saveGenTemplate() {
        JSONObject result = new JSONObject();
        try {
            GenTemplate saveEntity = this.genTemplateService.saveGenTemplate(genTemplate);
            result.put(SysConstant.AJAX_SUCCESS, "新增成功。");
            result.put("saveId", saveEntity.getId());
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "新增失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return SysConstant.JSON_RESULT_PAGE;
    }

    @RequestMapping("modGenTemplate")
    public String modGenTemplate() {
        genTemplate = genTemplateService.getGenTemplateById(genTemplate.getId());
        return "modGenTemplate";
    }

    @RequestMapping("updateGenTemplate")
    public String updateGenTemplate() {
        JSONObject result = new JSONObject();
        try {
            this.genTemplateService.updateGenTemplate(genTemplate);
            result.put(SysConstant.AJAX_SUCCESS, "修改成功。");
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "修改失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return SysConstant.JSON_RESULT_PAGE;
    }

    private String genTemplateId;

    @RequestMapping("delGenTemplate")
    public String delGenTemplate() {
        JSONObject result = new JSONObject();
        try {
            this.genTemplateService.delGenTemplateById(genTemplateId);
            result.put(SysConstant.AJAX_SUCCESS, "删除成功。");
        } catch (Exception e) {
            result.put(SysConstant.AJAX_ERROR, "删除失败：" + e.getMessage());
        }
        jsonText = result.toString();
        return SysConstant.JSON_RESULT_PAGE;
    }

    public GenTemplate getGenTemplate() {
        return genTemplate;
    }

    public void setGenTemplate(GenTemplate genTemplate) {
        this.genTemplate = genTemplate;
    }

    public ListGenTemplateForm getQuery() {
        return query;
    }

    public void setQuery(ListGenTemplateForm query) {
        this.query = query;
    }

    public String getGenTemplateId() {
        return genTemplateId;
    }

    public void setGenTemplateId(String genTemplateId) {
        this.genTemplateId = genTemplateId;
    }

}
