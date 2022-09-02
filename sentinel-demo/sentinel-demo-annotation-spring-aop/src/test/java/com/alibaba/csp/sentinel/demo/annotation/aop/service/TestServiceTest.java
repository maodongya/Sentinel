package com.alibaba.csp.sentinel.demo.annotation.aop.service;


import com.alibaba.csp.sentinel.demo.annotation.aop.ApplicationTest;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestServiceTest extends ApplicationTest {
    public static final List<FlowRule> STATIC_RULES_1 = new ArrayList<FlowRule>();
    public static final List<FlowRule> STATIC_RULES_2 = new ArrayList<FlowRule>();
    static {
        FlowRule first = new FlowRule();
        first.setResource("test");
        first.setCount(4);
        STATIC_RULES_1.add(first);

        FlowRule second = new FlowRule();
        second.setResource("hello");
        second.setCount(4);
        STATIC_RULES_2.add(second);


    }
    @Autowired
    private TestService testService;

    @org.junit.Test
    public void test1() {
        FlowRuleManager.loadRules(STATIC_RULES_1);

        for(int i=0;i<1000000;i++){
            testService.test();
        }

    }
    @org.junit.Test
    public void manyThreads() throws InterruptedException {
        FlowRuleManager.loadRules(STATIC_RULES_2);
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(2,2,10, TimeUnit.MINUTES,new LinkedBlockingDeque(100));
        threadPoolExecutor.submit(()-> {
            try {
                hello();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPoolExecutor.submit(()-> {
            try {
                helloAnother();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(1000*60*2);
    }

    @org.junit.Test
    public void hello() throws InterruptedException {
        for(int i=0;i<1000000;i++){
          String result= testService.hello(i);
          log.info("i={}",result);

          if(!result.startsWith("Hello at")){
            Thread.sleep(1000);
          }
        }
    }

    @org.junit.Test
    public void helloAnother() throws InterruptedException {
        for(int i=0;i<1000000;i++){
            String result= testService.helloAnother(String.valueOf(i));
            log.info("i={}",result);

            if(!result.startsWith("Hello at")){
                Thread.sleep(1000);
            }
        }
    }
}