package com.tencent.bos.service.base.impl;

import com.tencent.bos.beans.base.FixedArea;
import com.tencent.bos.beans.base.SubArea;
import com.tencent.bos.dao.base.FixedAreaRepository;
import com.tencent.bos.dao.base.SubAreaRepository;
import com.tencent.bos.service.base.AreaService;
import com.tencent.bos.service.base.SubAreaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    @Autowired
    private SubAreaRepository subAreaRepository;

    @Autowired
    private AreaService areaService;
    @Override
    public void save(SubArea model) {

        subAreaRepository.save(model);
    }

    @Override
    public Page<SubArea> findByPage(Specification<SubArea> specification1, Pageable pageable) {

        Page<SubArea> all = subAreaRepository.findAll(specification1,pageable);

        return all;
    }

    @Override
    public List<SubArea> findUnRelativeSubArea() {
        return subAreaRepository.findByFixedAreaIsNull();
    }

    @Override
    public List<SubArea> findRelativeSubArea(Long id) {
       // System.out.println(id+"得到的id是");

        return subAreaRepository.findByFixedAreaId(id);

    }

    @Override
    public void assignSubArea2FixedArea(Long id, Long[] customerIds) {
        if(id!=null){
            //先清除所有与定区相关的客户
           //  System.out.println(id+"===");
            subAreaRepository.clearByFixedAreaId(id);
        }
        //再重新将该定区关联客户
        if(customerIds!=null && customerIds.length>0){
            for (Long customerId : customerIds) {
             //     System.out.println(customerId+"+++");
                subAreaRepository.refreshByFixedAreaId(id,customerId);
            }

        }
    }
}
