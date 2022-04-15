package com.alibaba.csp.sentinel.dashboard.repository.jpa.specfication;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.FlowRulePO;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.FlowRulePO_;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @description
 * @auther Mao DongYa
 * @create 2022/3/17 4:43 下午
 */
@SuperBuilder
@NoArgsConstructor
public class FlowRulePOSpecification extends BaseSpecification<FlowRulePO> {

  private String app;
  private String ip;
  private String resource;
  private Integer port;

  @Override
  public Predicate toPredicate(
      Root<FlowRulePO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return equal(FlowRulePO_.APP, app)
        .and(equal(FlowRulePO_.IP, ip))
        .and(equal(FlowRulePO_.PORT, port))
        .and(equal(FlowRulePO_.RESOURCE, resource))
        .toPredicate(root, query, criteriaBuilder);
  }
  public static FlowRulePOSpecification toFlowRulePOSpecification(String app,String ip,Integer port,String resource){
    return FlowRulePOSpecification.builder().app(app).ip(ip).resource(resource).port(port).build();
  }
}
