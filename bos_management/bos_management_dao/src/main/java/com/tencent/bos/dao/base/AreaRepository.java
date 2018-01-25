package com.tencent.bos.dao.base;

import com.tencent.bos.beans.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AreaRepository extends JpaRepository<Area,Long>,JpaSpecificationExecutor<Area> {
    @Query(value = "from Area where province like ?1 or city like ?1 or district like ?1 or shortcode like ?1")
    List<Area> findByQ(String q);
}
