package com.tencent.crm.dao;

import com.tencent.crm.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    // 查找未关联定区的客户
    List<Customer> findByFixedAreaIdIsNull();


    // 查找关联到指定定区的客户
    List<Customer> findByFixedAreaId(String fixedAreaId);

    //由fixedAreaId清空与之对应的客户，即将客户的fixedAreaId置于null
    @Modifying
    @Query("update Customer set fixedAreaId=null where fixedAreaId=?1 ")
    void clearByFixedAreaId(String fixedAreaId);

    //再重新将该定区关联客户
    @Modifying
    @Query("update Customer set fixedAreaId=?1 where id=?2")
    void refreshByFixedAreaId(String fixedAreaId, Long customerId);
}
