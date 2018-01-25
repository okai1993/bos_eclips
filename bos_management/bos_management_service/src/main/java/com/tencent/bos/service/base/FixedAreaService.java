package com.tencent.bos.service.base;

import com.tencent.bos.beans.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FixedAreaService {
    void save(FixedArea model);

    Page<FixedArea> findByPage(Pageable pageable);
}
