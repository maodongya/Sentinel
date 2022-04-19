package com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.typedescriptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladmihalcea.hibernate.type.AbstractHibernateType;
import com.vladmihalcea.hibernate.type.util.Configuration;
import com.vladmihalcea.hibernate.type.util.ObjectMapperWrapper;
import org.hibernate.usertype.DynamicParameterizedType;


import java.lang.reflect.Type;
import java.util.Properties;

public class ObjectValuePoStringType extends AbstractHibernateType<ObjectValuePo> implements DynamicParameterizedType {
    public static final ObjectValuePoStringType INSTANCE = new ObjectValuePoStringType();

    public ObjectValuePoStringType() {
        super(ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(Configuration.INSTANCE.getObjectMapperWrapper()));
    }

    public ObjectValuePoStringType(Type javaType) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(Configuration.INSTANCE.getObjectMapperWrapper(), javaType)
        );
    }

    public ObjectValuePoStringType(Configuration configuration) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(configuration.getObjectMapperWrapper()),
                configuration
        );
    }

    public ObjectValuePoStringType(ObjectMapper objectMapper) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(new ObjectMapperWrapper(objectMapper))
        );
    }

    public ObjectValuePoStringType(ObjectMapperWrapper objectMapperWrapper) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(objectMapperWrapper)
        );
    }

    public ObjectValuePoStringType(ObjectMapper objectMapper, Type javaType) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(new ObjectMapperWrapper(objectMapper), javaType)
        );
    }

    public ObjectValuePoStringType(ObjectMapperWrapper objectMapperWrapper, Type javaType) {
        super(
                ObjectValuePoSqlTypeDescriptor.INSTANCE,
                new ObjectValuePoDescriptor(objectMapperWrapper, javaType)
        );
    }

    @Override
    public String getName() {
        return "rule-entity";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((ObjectValuePoDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }
}
