package com.tencent.crm.service;

import com.tencent.crm.beans.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {
    @GET
    @Path("/findAll")
    public List<Customer> findAll();

    //查找未关联的用户
    @GET
    @Path("/findUnRelativeCustomer")
    public List<Customer> findUnRelativeCustomer();

    //查找关联的用户
    @GET
    @Path("/findRelativeCustomer")
    public List<Customer> findRelativeCustomer(@QueryParam("fixedAreaId") String fixedAreaId);

    //将与某定区关联的用户全部清空并且重新刷新数据
    @PUT
    @Path("/assignCustomers2FixedArea")
    public void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,
                                   @QueryParam("customerIds") Long[] customerIds);

    //
}
