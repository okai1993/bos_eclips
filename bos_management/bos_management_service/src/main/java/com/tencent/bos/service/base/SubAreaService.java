package com.tencent.bos.service.base;

import com.tencent.bos.beans.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SubAreaService {

    void save(SubArea model);


    Page<SubArea> findByPage(Specification<SubArea> specification1, Pageable pageable);



    List<SubArea> findUnRelativeSubArea();

    List<SubArea> findRelativeSubArea(Long id);

    void assignSubArea2FixedArea(Long id, Long[] customerIds);
}
