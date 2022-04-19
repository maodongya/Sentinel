package com.alibaba.csp.sentinel.dashboard.repository.jpa.specfication;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.SentinelRulePO;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.SentinelRulePO_;
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
public class SentinelRulePOSpecification extends BaseSpecification<SentinelRulePO> {

  private String ruleType;
  private String app;
  private String ip;
  private String resource;
  private Integer port;

  @Override
  public Predicate toPredicate(
      Root<SentinelRulePO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
    return equal(SentinelRulePO_.APP, app)
        .and(equal(SentinelRulePO_.RULE_TYPE, ruleType))
        .and(equal(SentinelRulePO_.IP, ip))
        .and(equal(SentinelRulePO_.PORT, port))
        .and(equal(SentinelRulePO_.RESOURCE, resource))
        .toPredicate(root, query, criteriaBuilder);
  }
  public static SentinelRulePOSpecification toSpecification(String app, String ruleType,String ip, Integer port, String resource){
    return SentinelRulePOSpecification.builder().app(app).ruleType(ruleType).ip(ip).resource(resource).port(port).build();
  }
}
