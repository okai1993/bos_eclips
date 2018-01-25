package com.tencent.bos.service;

import com.tencent.bos.beans.base.Customer;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import javax.jws.WebService;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

public class CRMTest {
    @Test
    public void findAll(){
        List<Customer> list = (List<Customer>) WebClient.create("http://localhost:8180/webService/customerService/findAll")
                .type(MediaType.APPLICATION_JSON)// 指定本客户端传递给服务器的数据格式
                .accept(MediaType.APPLICATION_JSON)//指定服务器传递回来的数据格式,本客户端能处理的数据格式
                .getCollection(Customer.class);
        for (Customer customer : list) {
            System.out.println(customer.getAddress());

        }
    }
}
