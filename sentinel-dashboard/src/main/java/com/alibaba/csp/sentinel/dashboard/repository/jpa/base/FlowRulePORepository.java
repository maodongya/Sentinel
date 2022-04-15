package com.alibaba.csp.sentinel.dashboard.repository.jpa.base;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.FlowRulePO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlowRulePORepository
        extends JpaRepository<FlowRulePO, Long>, JpaSpecificationExecutor<FlowRulePO> {
}
