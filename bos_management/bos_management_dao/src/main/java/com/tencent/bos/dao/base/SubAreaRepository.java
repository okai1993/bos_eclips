package com.tencent.bos.dao.base;

import com.tencent.bos.beans.base.Customer;
import com.tencent.bos.beans.base.FixedArea;
import com.tencent.bos.beans.base.SubArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SubAreaRepository extends JpaRepository<SubArea,Long>,JpaSpecificationExecutor<SubArea> {
    // 查找未关联定区的分区
    List<SubArea> findByFixedAreaIsNull();


    // 查找关联到指定定区的分区
    List<SubArea> findByFixedAreaId(Long fixedArea);

    //由fixedAreaId清空与之对应的分区，即将客户的fixedAreaId置于null
    @Modifying
    @Query("update SubArea set fixedArea.id=null where fixedArea.id=?1 ")
    void clearByFixedAreaId(Long fixedAreaId);

    //再重新将该定区关联分区
    @Modifying
    @Query("update SubArea set fixedArea.id=?1 where id=?2")
    void refreshByFixedAreaId(Long fixedAreaId, Long customerId);

}
