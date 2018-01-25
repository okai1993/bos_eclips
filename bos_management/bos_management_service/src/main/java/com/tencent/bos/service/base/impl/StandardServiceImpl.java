package com.tencent.bos.service.base.impl;

import com.tencent.bos.beans.base.Standard;
import com.tencent.bos.dao.base.StandardRepository;
import com.tencent.bos.service.base.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "standardService")
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);

    }

    @Override
    public Page<Standard> findByPage(Pageable pageable){
        Page<Standard> page = standardRepository.findAll(pageable);

        return page;
    }
}
