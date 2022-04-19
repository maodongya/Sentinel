package com.alibaba.csp.sentinel.dashboard.repository.jpa.base;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.SentinelRulePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SentinelRulePORepository
        extends JpaRepository<SentinelRulePO, Long>, JpaSpecificationExecutor<SentinelRulePO> {
}
