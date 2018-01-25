package com.tencent.bos.service.base.impl;

import com.tencent.bos.beans.base.Courier;
import com.tencent.bos.dao.base.CourierRepository;
import com.tencent.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    @Override
    public Page<Courier> findByPage(Specification<Courier> specification, Pageable pageable) {

        Page<Courier> all = courierRepository.findAll(specification,pageable);

        return all;
    }

    @Override
    public Page<Courier> queryPage(Courier courier,Pageable pageable){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("company", ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))
                .withMatcher("standard.name",ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING));
        Example<Courier> example = Example.of(courier, matcher);
        Page<Courier> all = courierRepository.findAll(example, pageable);
        return all;

    }

    @Override
    public void delete(String ids) {
        if (!StringUtils.isEmpty("ids")) {
            String[] split = ids.split(",");

            for (String str : split
                    ) {
                System.out.println("要删除的id："+str);
                courierRepository.updateById(Long.parseLong(str));
            }
        }
    }
}
