package com.tencent.bos.web.action.base;

import com.tencent.bos.beans.base.Customer;
import com.tencent.bos.beans.base.FixedArea;
import com.tencent.bos.beans.base.SubArea;
import com.tencent.bos.service.base.FixedAreaService;
import com.tencent.bos.service.base.SubAreaService;
import com.tencent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
public class FixedAreaAction extends CommonAction<FixedArea> {
    @Autowired
    private FixedAreaService fixedAreaService;
    @Autowired
    private SubAreaService subAreaService;

    public FixedAreaAction() {
        super(FixedArea.class);
    }

    //添加数据的功能
    @Action(value = "fixedAreaAction_save",results = {
            @Result(name="success", location="/pages/base/fixed_area.html", type="redirect")
    })
    public String save(){
        System.out.println(getModel());
        fixedAreaService.save(getModel());
        return SUCCESS;
    }

    //分页
    @Action(value = "fixedAreaAction_findByPage")
    public String findByPage() throws IOException {
        Pageable pageable=new PageRequest(page-1,rows);
        Page<FixedArea>page= fixedAreaService.findByPage(pageable);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        page2Json(page,jsonConfig);
        return NONE;
    }

    //CRM:找到未关联的客户
    @Action(value = "fixedAreaAction_findUnRelativeCustomer")
    public String findUnRelativeCustomer() throws IOException {
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/webService/customerService/findUnRelativeCustomer")
                .type(MediaType.APPLICATION_JSON)// 指定本客户端传递给服务器的数据格式
                .accept(MediaType.APPLICATION_JSON)//指定服务器传递回来的数据格式,本客户端能处理的数据格式
                .getCollection(Customer.class);
        list2Json(list,null);
        return NONE;
    }

    //CRM:找到关联的客户
    @Action(value = "fixedAreaAction_findRelativeCustomer")
    public String findRelativeCustomer() throws IOException {
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/webService/customerService/findRelativeCustomer")
                .type(MediaType.APPLICATION_JSON)// 指定本客户端传递给服务器的数据格式
                .accept(MediaType.APPLICATION_JSON)//指定服务器传递回来的数据格式,本客户端能处理的数据格式
                .query("fixedAreaId",getModel().getId())
                .getCollection(Customer.class);
        list2Json(list,null);
        return NONE;
    }

    //CRM:关联客户
    private Long[] customerIds;

    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String assignCustomers2FixedArea(){
        System.out.println("得到的数组长度："+customerIds.length+":"+getModel().getId());
        WebClient.create("http://localhost:8180/webService/customerService/assignCustomers2FixedArea")
                .type(MediaType.APPLICATION_JSON)// 指定本客户端传递给服务器的数据格式
                .accept(MediaType.APPLICATION_JSON)//指定服务器传递回来的数据格式,本客户端能处理的数据格式
                .query("fixedAreaId",getModel().getId())
                .query("customerIds",customerIds)
                .put(null);
        return SUCCESS;
    }

    //SubArea:找到为关联的分区
    @Action(value = "fixedAreaAction_findUnRelativeSubArea")
    public String findUnRelativeSubArea() throws IOException {
        List<SubArea>list=subAreaService.findUnRelativeSubArea();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"area","fixedArea"});
        list2Json(list,jsonConfig);
        return NONE;
    }

    //SubArea:找到关联的分区
    @Action(value = "fixedAreaAction_findRelativeSubArea")
    public String findRelativeSubArea() throws IOException {
        List<SubArea> list =subAreaService.findRelativeSubArea(getModel().getId());
        System.out.println(list+"===[[[[[=====");
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"area","fixedArea"});
        list2Json(list,jsonConfig);
        return NONE;
    }

    //SubArea:关联分区
    private Long[] subAreaIds;

    public void setSubAreaIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    @Action(value = "fixedAreaAction_assignSubArea2FixedArea",results = {
            @Result(name = "success",location = "/pages/base/fixed_area.html",type = "redirect")
    })
    public String assignSubArea2FixedArea(){
        subAreaService.assignSubArea2FixedArea(getModel().getId(),customerIds);
        return SUCCESS;
    }
}
