package com.tencent.bos.service.base.impl;

import com.tencent.bos.beans.base.FixedArea;
import com.tencent.bos.dao.base.FixedAreaRepository;
import com.tencent.bos.service.base.FixedAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
    @Autowired
    private FixedAreaRepository fixedAreaRepository;


    @Override
    public void save(FixedArea model) {
        fixedAreaRepository.save(model);
    }

    @Override
    public Page<FixedArea> findByPage(Pageable pageable) {
        Page<FixedArea> all = fixedAreaRepository.findAll(pageable);
        return all;
    }
}
