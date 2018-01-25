package com.tencent.bos.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.tencent.bos.beans.base.Area;
import com.tencent.bos.beans.base.Standard;
import com.tencent.bos.service.base.AreaService;
import com.tencent.bos.web.action.CommonAction;
import com.tencent.utils.PinYin4jUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
public class AreaAction extends CommonAction{
    private static final long serialVersionUID = 5059676695054900556L;
    private Area area=new Area();

    @Autowired
    private AreaService areaService;


    //文件上传功能
    private File file;

    private String fileFileName;

    public void setFileFileName(String fileFileName) {
       // System.out.println("文件的名字是==================："+fileFileName);
        this.fileFileName = fileFileName;
    }

    public AreaAction() {
        super(Area.class);
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Action(value = "Area_ImportDocument", results= {
            @Result(name = "success",location = "/pages/base/area.html",type = "redirect")
    })
    public String importDocument() throws IOException {

        List<Area> list=new ArrayList<>();
        if(fileFileName.endsWith(".xls")){

            try {
                HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
                HSSFSheet sheetAt = wb.getSheetAt(0);
                for (Row row:sheetAt) {
                    if(row.getRowNum()==1){
                        continue;
                    }
                    area = writeExcel(row);
                    //每行就是一个包装area，将其放在一个集合里面
                    list.add(this.area);
                    areaService.save(list);
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(fileFileName.endsWith(".xlsx")){
            try {
                XSSFWorkbook wb=new XSSFWorkbook(new FileInputStream(file));
                XSSFSheet sheetAt = wb.getSheetAt(0);
                for (Row row:sheetAt
                        ) {
                    if(row.getRowNum()==0){
                        continue;
                    }
                    Area area = writeExcel(row);
                    //每行就是一个包装area，将其放在一个集合里面
                    list.add(this.area);
                    areaService.save(list);
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
        }
        return SUCCESS;
    }

    public Area writeExcel(Row row){
        String province = row.getCell(1).getStringCellValue();
        String city = row.getCell(2).getStringCellValue();
        String district = row.getCell(3).getStringCellValue();
        String postcode = row.getCell(4).getStringCellValue();
        province = province.substring(0, province.length() - 1);
        city =city.substring(0, city.length() - 1);
        district = district.substring(0, district.length() - 1);
        String citycode = PinYin4jUtils.hanziToPinyin(province, "");
        String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
        System.out.println("headByString:"+headByString);
        String shortcode= PinYin4jUtils.stringArrayToString(headByString);
        Area area=new Area();
        area.setProvince(province);
        area.setCity(city);
        area.setCitycode(citycode);
        area.setDistrict(district);
        area.setPostcode(postcode);
        area.setShortcode(shortcode);
        return area;

    }

    //分页

    @Action(value = "Area_findByPage")
    public String findByPage() throws IOException {
        Pageable pageable=new PageRequest(page-1,rows);
        Page<Area> pages= areaService.findByPage(pageable);
        Map<String,Object>map=new HashMap<>();
        /*long total = pages.getTotalElements();
        List<Area> content = pages.getContent();
        map.put("total",total);
        map.put("rows",content);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        JSONObject jsonObject = JSONObject.fromObject(map,jsonConfig);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonObject.toString());*/
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        page2Json(pages,jsonConfig);
        return NONE;
    }

    //查询所有分区
    //得到所有的area
    private String q;//多选框搜索选项

    public void setQ(String q) {
        this.q = q;
    }

    @Action(value = "area_findAll")
    public String findAll() throws IOException {
        List<Area> list;
        if(!StringUtils.isEmpty(q)){
           list= areaService.findByQ(q);
        }else{
        list = areaService.findByPage(null).getContent();}
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        list2Json(list,jsonConfig);
        return NONE;
    }

    /*@Override
    public Area getModel() {
        return area;
    }*/
}
