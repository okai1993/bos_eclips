package com.tencent.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tencent.bos.beans.base.Standard;
import com.tencent.bos.service.base.StandardService;
import com.tencent.bos.web.action.CommonAction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Namespace("/")
@Scope(value = "prototype")
@Action("standard") // actually that is not necessary as it is added by convention
@ParentPackage("struts-default")
public class StandardAction extends CommonAction{
    private Standard standard;

    public StandardAction() {
        super(Standard.class);
    }

    @Resource(name = "standardService")
    private StandardService standardService;

    //添加数据的功能
    @Action(value = "standard_save",results = {
            @Result(name="success", location="/pages/base/standard.html", type="redirect")
    })
    public String save(StandardService standardService){
        standardService.save((Standard) getModel());
        return SUCCESS;
    }


    @Action(value = "standard_findByPage")
    public String findByPage() throws IOException {
        Pageable pageable=new PageRequest(page-1,rows);//@param page zero-based page index.
        Page<Standard> page = standardService.findByPage(pageable);
       // System.out.println(page);
        page2Json(page,null);
        return NONE;
    }

    //得到所有的standard
    @Action(value = "standard_findAll")
    public String findAll() throws IOException {
        Page<Standard> all = standardService.findByPage(null);

        list2Json(all.getContent(),null);
        return NONE;
    }


}
