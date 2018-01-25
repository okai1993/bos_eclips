package com.tencent.bos.service.base;

import com.tencent.bos.beans.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StandardService {
    void save(Standard standard);

    Page<Standard> findByPage(Pageable pageable);
}
