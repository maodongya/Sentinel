package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import org.springframework.stereotype.Repository;


@Repository("japFlowRuleRepository")
public class JpaFlowRuleRepository extends JpaRuleRepository<FlowRuleEntity>{
    public String getRuleType(){
        return FlowRuleEntity.class.getSimpleName();
    }

}
