package com.tencent.bos.service.base.impl;

import com.tencent.bos.beans.base.Area;
import com.tencent.bos.dao.base.AreaRepository;
import com.tencent.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Controller
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void save(List<Area> list) {
        areaRepository.save(list);
    }

    @Override
    public Page<Area> findByPage(Pageable pageable) {
        Page<Area> all = areaRepository.findAll(pageable);
        return all;
    }

    @Override
    public List<Area> findByQ(String q) {
        q="%"+q.toUpperCase()+"%";

        return areaRepository.findByQ(q);
    }

    @Override
    public Area findByID(Long id) {
        return areaRepository.findOne(id);
    }

    @Override
    public List<Area> findByPCD(Specification<Area> specification) {

        return areaRepository.findAll(specification);
    }


}
