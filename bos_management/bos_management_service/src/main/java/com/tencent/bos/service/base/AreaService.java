package com.tencent.bos.service.base;

import com.tencent.bos.beans.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    void save(List<Area> list);

    Page<Area> findByPage(Pageable pageable);

    List<Area> findByQ(String q);

    Area findByID(Long id);

    List<Area> findByPCD(Specification<Area> specification);
}
