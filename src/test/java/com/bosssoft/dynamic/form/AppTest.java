 package com.bosssoft.dynamic.form;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.bosssoft.dynamic.form.model.DataSourceElement;
import com.bosssoft.dynamic.form.util.DynamicFormUtil;
import com.bosssoft.dynamic.form.util.SupportDataSourceHandler;

public class AppTest {

     @Test
     public void supportDataSourceTest(){
         Map<String, DataSourceElement> map=   SupportDataSourceHandler.getSupportDataSources();
        for (Entry<String, DataSourceElement> entry : map.entrySet()) {
            System.out.println(entry.getKey()+"---"+entry.getValue());
        }
         
     }
     
     @Test
     public void generateIdTest(){
         System.out.println(DynamicFormUtil.generateId());
     }
}
