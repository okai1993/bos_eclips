package com.tencent.crm.service.impl;

import com.tencent.crm.beans.Customer;
import com.tencent.crm.dao.CustomerRepository;
import com.tencent.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findUnRelativeCustomer() {
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findRelativeCustomer(String fixedAreaId) {
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
        if(StringUtils.isNotEmpty(fixedAreaId)){
            //先清除所有与定区相关的客户
           // System.out.println(fixedAreaId+"===");
            customerRepository.clearByFixedAreaId(fixedAreaId);
        }
        //再重新将该定区关联客户
        if(customerIds!=null && customerIds.length>0){
            for (Long customerId : customerIds) {
              //  System.out.println(customerId+"+++");
                customerRepository.refreshByFixedAreaId(fixedAreaId,customerId);
            }

        }
    }


}
