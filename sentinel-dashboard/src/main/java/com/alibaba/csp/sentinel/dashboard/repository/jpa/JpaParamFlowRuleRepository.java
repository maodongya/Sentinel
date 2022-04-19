package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import org.springframework.stereotype.Repository;

@Repository("jpaParamFlowRuleRepository")
public class JpaParamFlowRuleRepository extends JpaRuleRepository<ParamFlowRuleEntity>{
    public String getRuleType(){
        return ParamFlowRuleEntity.class.getSimpleName();
    }
}
