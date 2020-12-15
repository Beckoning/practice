package com.pay.payment.center.springtest;

import com.pay.payment.center.resourcemap.AbstractDkHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class ResourceMapTest {

//    @Resource
//    private Map<String, AbstractDkHandler> abstractDkHandlerMap = new HashMap<>();
    @Value("${server.port}")
    private Integer port;
    @Autowired
    private Y y;

    @Autowired
    private X x;

    @Autowired
    private AbstractDkHandler abstractDkHandler;


    public void testMap(){
//        abstractDkHandlerMap.get("singleCardChain").invoke("");
//        System.out.println(applicationName);
        abstractDkHandler.invoke("");
    }



    ResourceMapTest(){
        System.out.println("ResourceMapTest");
    }

    @PostConstruct
    public void testName(){
        System.out.println("PostConstruct");
    }


    @Transactional
    public void testTransaction(){

    }




}
