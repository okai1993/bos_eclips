package com.tencent.bos.web.action.base;

import com.tencent.bos.beans.base.Area;
import com.tencent.bos.beans.base.Courier;
import com.tencent.bos.beans.base.SubArea;
import com.tencent.bos.service.base.AreaService;
import com.tencent.bos.service.base.SubAreaService;
import com.tencent.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.annotations.LazyCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class SubAreaAction extends CommonAction<SubArea> {
    @Autowired
    private SubAreaService subAreaService;
    @Autowired
    private AreaService areaService;

    public SubAreaAction() {
        super(SubArea.class);
    }

    //添加数据的功能
    @Action(value = "subarea_save",results = {
            @Result(name="success", location="/pages/base/sub_area.html", type="redirect")
    })
    public String save(){
        System.out.println("得到的是："+ getModel().getArea());
        subAreaService.save(getModel());
        return SUCCESS;
    }

    //分页查询方法一：
    /*private String province;
    private String city;
    private String district;

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Action(value = "subarea_findByPage")
    public String findByPage() throws IOException {
        Specification<Area> specification=new Specification<Area>(){
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(province)) {
                    System.out.println("查询到的省为："+province);
                    Predicate courierNum1 = cb.equal(root.get("province").as(String.class), province);
                    list.add(courierNum1);
                }
                if (!StringUtils.isEmpty(city)) {
                    Predicate courierNum1 = cb.equal(root.get("city").as(String.class), city);
                    list.add(courierNum1);
                }
                if (!StringUtils.isEmpty(district)) {
                    Predicate courierNum1 = cb.equal(root.get("district").as(String.class), district);
                    list.add(courierNum1);
                }
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] array = new Predicate[list.size()];
                list.toArray(array);
                // 构造查询条件
                return cb.and(array);
            }
        };
        Pageable pageable=new PageRequest(page-1,rows);
        final List<Area>areas=areaService.findByPCD(specification);
        final String keyWords = getModel().getKeyWords();
        Specification<SubArea> specification1 = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                List<Predicate> list2 = new ArrayList<>();
                if (!StringUtils.isEmpty(areas)) {
                    for (Area area : areas) {
                        System.out.println("查询到的area："+area);
                        Predicate courierNum1 = cb.equal(root.get("area").as(Area.class), area);
                        list.add(courierNum1);
                    }
                }
                if (!StringUtils.isEmpty(keyWords)) {
                    Predicate courierNum1 = cb.equal(root.get("keyWords").as(String.class), keyWords);
                    list2.add(courierNum1);
                }
                Predicate[] array = new Predicate[list.size()];
                list.toArray(array);
                // 构造查询条件
                if(!StringUtils.isEmpty(list)){
                    Predicate and = cb.or(array);
                    list2.add(and);
                }
                if (list2.size() == 0) {
                    return null;
                }
                Predicate[] array2 = new Predicate[list2.size()];
                list2.toArray(array2);
                return cb.and(array2);
            }
        };
        Page<SubArea>page= subAreaService.findByPage(specification1,pageable);
        System.out.println("得到的数据时："+page);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        page2Json(page,jsonConfig);
        return NONE;
    }*/

//分页查询方法二：
    @Action(value = "subarea_findByPage")
    public String findByPage() throws IOException {
        SubArea subArea = getModel();
        final Area area=subArea.getArea();
        final String keyWords = getModel().getKeyWords();
        Specification<SubArea> specification = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (area != null && !StringUtils.isEmpty(area.getProvince())) {
                    // 根据收派标准的名字进行级联查询
                    Join<Object, Object> join = root.join("area");
                    Predicate province = cb.equal(join.get("province").as(String.class), area.getProvince());
                    list.add(province);
                }
                if (area != null && !StringUtils.isEmpty(area.getCity())) {
                    // 根据收派标准的名字进行级联查询
                    Join<Object, Object> join = root.join("area");
                    Predicate city = cb.equal(join.get("city").as(String.class), area.getCity());
                    list.add(city);
                }
                if (area != null && !StringUtils.isEmpty(area.getDistrict())) {
                    // 根据收派标准的名字进行级联查询
                    Join<Object, Object> join = root.join("area");
                    Predicate district = cb.equal(join.get("district").as(String.class), area.getDistrict());
                    list.add(district);
                }
                if (!StringUtils.isEmpty(keyWords)) {
                    Predicate courierNum1 = cb.equal(root.get("keyWords").as(String.class), keyWords);
                    list.add(courierNum1);
                }
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] array = new Predicate[list.size()];
                list.toArray(array);
                // 构造查询条件
                return cb.and(array);
            }
        };
        Pageable pageable=new PageRequest(page-1,rows);
        Page<SubArea>page= subAreaService.findByPage(specification,pageable);
        System.out.println("得到的数据时："+page);
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        page2Json(page,jsonConfig);
        return NONE;
    }


}
