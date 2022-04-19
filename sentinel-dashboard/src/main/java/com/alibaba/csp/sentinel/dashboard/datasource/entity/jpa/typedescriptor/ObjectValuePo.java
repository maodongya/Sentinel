package com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.typedescriptor;

import com.alibaba.fastjson.JSON;
import com.vladmihalcea.hibernate.type.util.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ObjectValuePo<T> implements Serializable {
    Class clz;
    T value;

    @Override
    public String toString() {
        return Configuration.INSTANCE.getObjectMapperWrapper().toString(this);
    }
    public static ObjectValuePo fromString(String value){
        ObjectValuePo<String> objectValuePo= JSON.parseObject(value,ObjectValuePo.class);
        return new ObjectValuePo(objectValuePo.getClz(),JSON.parseObject(JSON.toJSONString(objectValuePo.getValue()),objectValuePo.getClz()));
    }
    public ObjectValuePo deepCopyNotNull(ObjectValuePo<?> value){
        return new ObjectValuePo(value.getClz(),JSON.parseObject(JSON.toJSONString(value.getValue()),value.getClz()));
    }
}
