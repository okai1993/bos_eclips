package com.tencent.bos.dao.base;

import com.tencent.bos.beans.base.Standard;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//第一个参数：实体类
//第二个参数：实体类的主键的包装数据类型
public interface StandardRepository extends JpaRepository<Standard,Long> {
    @Query("from Standard where name=?")
    List<Standard> findByName(String name);

    @Query("from Standard where name=? and maxLength=?")
    List<Standard>findByNameAndMaxlrngth(String name,Integer max);

    //自定义删除方法
    /*更新，删除数据库的方法必须还要加上事务管理*/
    @Transactional
    /*更改数据库的方法必须添加modifying注解*/
    @Modifying
    @Query("delete from Standard where name=?")
    void deleteByName(String name);
}