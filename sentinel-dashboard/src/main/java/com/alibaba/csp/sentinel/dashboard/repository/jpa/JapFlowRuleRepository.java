package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.FlowRulePO;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository("japFlowRuleRepository")
public class JapFlowRuleRepository extends JpaRuleRepository<FlowRuleEntity>{
    @Override
    protected FlowRulePO preProcess(FlowRuleEntity entity) {
        List<FlowRulePO> exists=findAllFlowRulePO(entity.getApp(),entity.getIp(),entity.getPort(),entity.getResource());
        if(CollectionUtils.isEmpty(exists)){
            return super.preProcess(entity);
        }
        return exists.get(0);
    }
}
