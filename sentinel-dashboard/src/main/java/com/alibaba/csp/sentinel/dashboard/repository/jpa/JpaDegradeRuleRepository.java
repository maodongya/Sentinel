package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import org.springframework.stereotype.Repository;

@Repository("jpaDegradeRuleRepository")
public class JpaDegradeRuleRepository extends JpaRuleRepository<DegradeRuleEntity>{
    public String getRuleType(){
        return DegradeRuleEntity.class.getSimpleName();
    }
}
