package com.tencent.bos.dao.base;


import com.tencent.bos.beans.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface CourierRepository extends JpaRepository<Courier,Long>,JpaSpecificationExecutor<Courier> {
    @Modifying
    @Query("update Courier set deltag=1 where id= ? ")
    void updateById(long l);
}
