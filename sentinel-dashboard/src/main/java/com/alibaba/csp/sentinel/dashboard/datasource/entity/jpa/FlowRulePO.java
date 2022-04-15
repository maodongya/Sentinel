package com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sentinel_rule")
@Data
@TypeDefs({
        @TypeDef(
                name = "rule-json",
                typeClass = JsonType.class,
                defaultForType = RuleEntity.class
        )
})
public class FlowRulePO<T>{
    /**id，主键*/
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    /**创建时间*/
    @Column(name = "gmt_create")
    private Date gmtCreate;
    /**修改时间*/
    @Column(name = "gmt_modified")
    private Date gmtModified;
    /**应用名称*/
    @Column(name = "app")
    private String app;
    @Column(name = "ip")
    private String ip;
    @Column(name = "port")
    private Integer port;
    /**资源名称*/
    @Column(name = "resource")
    private String resource;

    @Column(name = "rule")
    @Type(type = "rule-json")
    private T rule;
}
