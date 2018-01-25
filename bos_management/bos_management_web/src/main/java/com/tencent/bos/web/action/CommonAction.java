package com.tencent.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tencent.bos.beans.base.Courier;
import com.tencent.bos.service.base.StandardService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.ss.formula.functions.T;
import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    //分页
    protected int page;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    protected int rows;
    private Class<T> clazz;
    private T model;
    public CommonAction(Class<T> clazz){
        this.clazz=clazz;
        try {
            model=clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将page对象转为json数据类型
    public void page2Json(Page<T> page,JsonConfig jsonConfig) throws IOException {
        long totalElements = page.getTotalElements();
        List<T> content = page.getContent();
        Map<String,Object> map=new HashMap<>();
        map.put("total",totalElements);
        map.put("rows",content);
        String s ;
        if(jsonConfig!=null){
            s= JSONObject.fromObject(map,jsonConfig).toString();
        }else{
            s= JSONObject.fromObject(map).toString();
        }
        System.out.println("得到的json数据时："+s);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }

    //将list集合类型的数据转为json数据类型
    public void list2Json(List list,JsonConfig jsonConfig) throws IOException {
        JSONArray jsonArray;
        if(jsonConfig!=null){
            jsonArray = JSONArray.fromObject(list,jsonConfig);
        }else{
            jsonArray = JSONArray.fromObject(list);
        }

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        // System.out.println("得到所有的standard"+jsonArray);
        response.getWriter().write(jsonArray.toString());
    }


    @Override
    public T getModel() {
        return model;
    }
}
