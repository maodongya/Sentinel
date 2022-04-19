package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.SentinelRulePO;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.typedescriptor.ObjectValuePo;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.repository.jpa.specfication.SentinelRulePOSpecification;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.repository.jpa.base.SentinelRulePORepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Repository("jpaRuleRepository")
public class JpaRuleRepository <T extends RuleEntity> implements RuleRepository<T, Long> {
    @Autowired
    private SentinelRulePORepository sentinelRulePORepository;
    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(nextId());
        }
        SentinelRulePO processedEntity = findOrCreate(entity);
        if (processedEntity != null) {
            sentinelRulePORepository.save(processedEntity);
            entity.setId(processedEntity.getId());
        }
        return entity;
    }

    @Override
    public List<T> saveAll(List<T> rules) {
        if (rules == null) {
            return null;
        }
        for (T rule : rules) {
            save(rule);
        }
        return rules;
    }

    @Override
    public T delete(Long id) {
        return sentinelRulePORepository.findById(id).map(it->{
            sentinelRulePORepository.deleteById(id);
            return (T)it.getRule().getValue();
        }).orElse(null);
    }

    public Optional<SentinelRulePO> findFlowRulePOById(Long id){
        if(!Optional.ofNullable(id).isPresent()){
            return Optional.ofNullable(null);
        }
        return sentinelRulePORepository.findById(id).map(it->{
            ((T)it.getRule().getValue()).setId(it.getId());
            return it;
        });
    }
    @Override
    public T findById(Long id) {
        return findFlowRulePOById(id).map(it->(T)it.getRule().getValue()).orElse(null);
    }
    public String getRuleType(){
        return null;
    }
    @Override
    public List<T> findAllByMachine(MachineInfo machineInfo) {
        return findAllFlowRulePO(machineInfo.getApp(),getRuleType(),machineInfo.getIp(),machineInfo.getPort(),null).stream().map(it->{
            T t=(T)it.getRule().getValue();
            t.setId(it.getId());
            return t;
        }).collect(Collectors.toList());
    }

    public List<SentinelRulePO> findAllFlowRulePO(String app,String ruleType,String ip,Integer port,String resource) {
        return sentinelRulePORepository.findAll(SentinelRulePOSpecification.toSpecification(app,ruleType,ip,port,resource));
    }

    @Override
    public List<T> findAllByApp(String appName) {
        return findAllFlowRulePO(appName,getRuleType(),null,null,null).stream().map(it->{
            T t=(T)it.getRule().getValue();
            t.setId(it.getId());
            return t;
        }).collect(Collectors.toList());
    }

    protected SentinelRulePO findOrCreate(T entity){
        SentinelRulePO sentinelRulePO= generateSentinelRulePo(entity);
        Optional<SentinelRulePO> existBean=findFlowRulePOById(entity.getId());
        if(existBean.isPresent()){
            sentinelRulePO.setId(existBean.get().getId());
            return sentinelRulePO;
        }

        List<SentinelRulePO> existBeans=findAllFlowRulePO(entity.getApp(),getRuleType(),entity.getIp(),entity.getPort(),entity.getResource());
        if(!CollectionUtils.isEmpty(existBeans)){
            sentinelRulePO.setId(existBeans.get(0).getId());
        }
        return sentinelRulePO;
    }

    protected SentinelRulePO generateSentinelRulePo(T entity) {
        SentinelRulePO flowRulePO=new SentinelRulePO();
        BeanUtils.copyProperties(entity, flowRulePO);
        flowRulePO.setRule(new ObjectValuePo(entity.getClass(),entity));
        flowRulePO.setRuleType(entity.getClass().getSimpleName());
        Date date = new Date();
        flowRulePO.setGmtCreate(date);
        flowRulePO.setGmtModified(date);
        return flowRulePO;
    }

    /**
     * Get next unused id.
     *
     * @return next unused id
     */
    protected Long nextId(){
        return null;
    }
}
