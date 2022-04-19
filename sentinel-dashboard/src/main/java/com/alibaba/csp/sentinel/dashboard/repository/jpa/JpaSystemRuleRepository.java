package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import org.springframework.stereotype.Repository;

@Repository("japSystemRuleRepository")
public class JpaSystemRuleRepository extends JpaRuleRepository<SystemRuleEntity>{
    @Override
    public String getRuleType() {
        return SystemRuleEntity.class.getSimpleName();
    }
}
