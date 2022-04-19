package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import org.springframework.stereotype.Repository;

@Repository("japAuthorityRuleRepository")
public class JapAuthorityRuleRepository extends JpaRuleRepository<AuthorityRuleEntity> {
    public String getRuleType(){
        return AuthorityRuleEntity.class.getSimpleName();
    }
}
