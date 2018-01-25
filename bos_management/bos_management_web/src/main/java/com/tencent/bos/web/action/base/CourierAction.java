package com.tencent.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tencent.bos.beans.base.Courier;

import com.tencent.bos.beans.base.Standard;
import com.tencent.bos.service.base.CourierService;
import com.tencent.bos.web.action.CommonAction;
import com.tencent.utils.PropertyOperator;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Namespace("/")
@ParentPackage("struts-default")
@Scope(value = "prototype")
@Controller
public class CourierAction extends CommonAction<Courier> {
    private Courier courier;

    @Autowired
    private CourierService courierService;

    public CourierAction() {
        super(Courier.class);
    }

    //添加数据的功能
    @Action(value = "courier_save",results = {
            @Result(name="success", location="/pages/base/courier.html", type="redirect")
    })
    public String save(){
        System.out.println("得到的courier："+courier);
        courierService.save(getModel());
        return SUCCESS;
    }

    //分页


   /* private String sort;
    private String sortfield;
    private String sortMethod;
    public void setSort(String sort) {
        System.out.println("sort======:"+sort);
        this.sort = sort;
        String[] split = sort.split(".");
        sortfield=split[0];
        sortMethod=split[1];

    }*/


    @Action(value = "courier_findByPage")
    public String findByPage() throws IOException {
        //courierNum=1&standard.name=2&company=3&type=4
        Courier courier =getModel();
        final String courierNum = courier.getCourierNum();
        final Standard standard = courier.getStandard();
        final String company = courier.getCompany();
        final String type = courier.getType();
        System.out.println(courierNum+"::"+company+"::"+type+";:");
        Specification<Courier> specification=new Specification<Courier>() {
            /* @param root : 通常指实体类
         @param query : 查询对象
          @param cb : 构造查询条件的对象
          @return 组合查询条件*/
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               List<Predicate>list=new ArrayList<>();
               if(!StringUtils.isEmpty(courierNum)){
                   Predicate courierNum1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                   list.add(courierNum1);
               }
               if(!StringUtils.isEmpty(company)){
                   Predicate company1 = cb.like(root.get("company").as(String.class), company);
                   list.add(company1);
               }
               if(standard!=null&&!StringUtils.isEmpty(standard.getName())){
                   // 根据收派标准的名字进行级联查询
                   Join<Object, Object> join = root.join("standard");
                   Predicate name = cb.equal(join.get("name").as(String.class), standard.getName());
                   list.add(name);
               }
               if(!StringUtils.isEmpty(type)){
                   Predicate type1 = cb.equal(root.get("type").as(String.class), type);
                   list.add(type1);

               }
               if(list.size()==0){
                   return null;
               }
                Predicate[] array = new Predicate[list.size()];
                list.toArray(array);
                // 构造查询条件
                return cb.and(array);
            }
        };
        Pageable pageable=new PageRequest(page-1,rows);//@param page zero-based page index.
        Page<Courier> page = courierService.findByPage(specification, pageable);
         System.out.println("查询到的结果："+page);
        /*long totalElements = page.getTotalElements();
        List<Courier> content = page.getContent();
        Map<String,Object> map=new HashMap<>();
        map.put("total",totalElements);
        map.put("rows",content);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        String s = JSONObject.fromObject(map,jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);*/
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        page2Json(page,jsonConfig);
        return NONE;
    }

    /*@Action(value = "courier_findByPage")
    public String findByPage() throws IOException {
        //courierNum=1&standard.name=2&company=3&type=4
        Courier courier = (Courier) getModel();
        PropertyOperator propertyOperator = new PropertyOperator();
        courier = propertyOperator.replacePropertyFromEmptyToNull(courier);
        Pageable pageable=new PageRequest(page-1,rows);//@param page zero-based page index.
        Page<Courier> page = courierService.queryPage(courier,pageable);
        System.out.println("查询到的结果："+page);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        page2Json(page,jsonConfig);
        return NONE;
    }
*/
    //删除
    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courier_delete",results = {
            @Result(name="success",type = "redirect",location = "/pages/base/courier.html")
    })
    public String delete(){
        courierService.delete(ids);
        return SUCCESS;
    }

   /* @Override
    public Courier getModel() {

        return courier;
    }*/
}
