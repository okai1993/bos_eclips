package com.tencent.bos.service.base;

import com.tencent.bos.beans.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {
    void save(Courier courier);

    Page<Courier> findByPage(Specification<Courier> specification, Pageable pageable);

    Page<Courier> queryPage(Courier courier, Pageable pageable);

    void delete(String ids);
}
