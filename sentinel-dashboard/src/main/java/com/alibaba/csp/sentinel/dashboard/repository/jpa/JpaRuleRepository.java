package com.alibaba.csp.sentinel.dashboard.repository.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.FlowRulePO;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.dashboard.repository.rule.RuleRepository;
import com.alibaba.csp.sentinel.dashboard.repository.jpa.base.FlowRulePORepository;
import com.alibaba.csp.sentinel.dashboard.repository.jpa.specfication.FlowRulePOSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Repository("jpaRuleRepository")
public class JpaRuleRepository <T extends RuleEntity> implements RuleRepository<T, Long> {
    @Autowired
    private FlowRulePORepository flowRuleRepository;
    @Override
    public T save(T entity) {
        if (entity.getId() == null) {
            entity.setId(nextId());
        }else{
            Optional<FlowRulePO> flowRulePO=findFlowRulePOById(entity.getId());
            if(flowRulePO.isPresent()){
                return (T)flowRulePO.get().getRule();
            }
        }
        FlowRulePO processedEntity = preProcess(entity);
        if (processedEntity != null) {
            flowRuleRepository.save(processedEntity);
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
        return flowRuleRepository.findById(id).map(it->{
            flowRuleRepository.deleteById(id);
            return (T)it.getRule();
        }).orElse(null);
    }

    public Optional<FlowRulePO> findFlowRulePOById(Long id){
        return flowRuleRepository.findById(id).map(it->{
            ((T)it.getRule()).setId(it.getId());
            return it;
        });
    }
    @Override
    public T findById(Long id) {
        return findFlowRulePOById(id).map(it->(T)it.getRule()).orElse(null);
    }

    @Override
    public List<T> findAllByMachine(MachineInfo machineInfo) {
        return findAllFlowRulePO(machineInfo.getApp(),machineInfo.getIp(),machineInfo.getPort(),null).stream().map(it->{
            T t=(T)it.getRule();
            t.setId(it.getId());
            return t;
        }).collect(Collectors.toList());
    }

    public List<FlowRulePO> findAllFlowRulePO(String app,String ip,Integer port,String resource) {
        return flowRuleRepository.findAll(FlowRulePOSpecification.toFlowRulePOSpecification(app,ip,port,resource));
    }

    @Override
    public List<T> findAllByApp(String appName) {
        return findAllFlowRulePO(appName,null,null,null).stream().map(it->{
            T t=(T)it.getRule();
            t.setId(it.getId());
            return t;
        }).collect(Collectors.toList());
    }

    public void clearAll() {
    }

    protected FlowRulePO preProcess(T entity) {

        FlowRulePO flowRulePO=new FlowRulePO();
        BeanUtils.copyProperties(entity, flowRulePO);
        flowRulePO.setRule(entity);
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
