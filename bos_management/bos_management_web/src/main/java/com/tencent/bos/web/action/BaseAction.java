package com.tencent.bos.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
    private T model;

    public BaseAction(){
        
        //得到子类的字节码文件
        Class<? extends BaseAction> aClass = this.getClass();
        //获取BaseAction<Standard>
        Type genericSuperclass = aClass.getGenericSuperclass();

        ParameterizedType parameterizedType= (ParameterizedType) genericSuperclass;
        //获取<Standard>,<Area>...
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Class<T> classes= (Class<T>) actualTypeArguments[0];
        try {
            model= classes.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public T getModel() {
        return null;
    }
}
