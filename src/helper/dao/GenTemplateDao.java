package helper.dao;

import helper.entity.GenTemplate;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.yida.core.base.dao.BaseDao;

@Repository
@Scope("singleton")
@Lazy(false)
public class GenTemplateDao extends BaseDao<GenTemplate, String> {

}
