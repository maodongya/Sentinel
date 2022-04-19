package com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.typedescriptor;

import com.vladmihalcea.hibernate.type.util.ObjectMapperWrapper;
import com.vladmihalcea.hibernate.util.LogUtils;
import com.vladmihalcea.hibernate.util.ReflectionUtils;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.*;
import org.hibernate.usertype.DynamicParameterizedType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;

public class ObjectValuePoDescriptor
        extends AbstractTypeDescriptor<ObjectValuePo> implements DynamicParameterizedType {
    private Type propertyType;

    private Class propertyClass;

    private ObjectMapperWrapper objectMapperWrapper;

    public ObjectValuePoDescriptor() {
        this(ObjectMapperWrapper.INSTANCE);
    }

    public ObjectValuePoDescriptor(Type type) {
        this();
        setPropertyClass(type);
    }

    public ObjectValuePoDescriptor(final ObjectMapperWrapper objectMapperWrapper) {
        super(ObjectValuePo.class, new MutableMutabilityPlan<ObjectValuePo>() {
            @Override
            protected ObjectValuePo deepCopyNotNull(ObjectValuePo value) {
                return value.deepCopyNotNull(value);
            }
        });
        this.objectMapperWrapper = objectMapperWrapper;
    }

    public ObjectValuePoDescriptor(final ObjectMapperWrapper objectMapperWrapper, Type type) {
        this(objectMapperWrapper);
        setPropertyClass(type);
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final XProperty xProperty = (XProperty) parameters.get(DynamicParameterizedType.XPROPERTY);
        Type type = (xProperty instanceof JavaXMember) ?
                ReflectionUtils.invokeGetter(xProperty, "javaType") :
                ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
        setPropertyClass(type);
    }

    @Override
    public boolean areEqual(ObjectValuePo one, ObjectValuePo another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        if (one instanceof Collection && another instanceof Collection) {
            return Objects.equals(one, another);
        }
        if (one.getClass().equals(another.getClass()) &&
                ReflectionUtils.getDeclaredMethodOrNull(one.getClass(), "equals", Object.class) != null) {
            return one.equals(another);
        }
        return objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(one)).equals(
                objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(another))
        );
    }

    @Override
    public String toString(ObjectValuePo value) {
        return objectMapperWrapper.toString(value);
    }

    @Override
    public ObjectValuePo fromString(String string) {
//        return objectMapperWrapper.fromString(string, propertyType);
        if(ObjectValuePo.class.isAssignableFrom(propertyClass)){
          return  ObjectValuePo.fromString(string);
        }else{
            return objectMapperWrapper.fromString(string,ObjectValuePo.class);
        }
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(ObjectValuePo value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> ObjectValuePo wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        String stringValue = value.toString();
        return fromString(stringValue);
    }

    private void setPropertyClass(Type type) {
        this.propertyType = type;
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable) {
            type = ((TypeVariable) type).getGenericDeclaration().getClass();
        }
        this.propertyClass = (Class) type;
        validatePropertyType();
    }

    private void validatePropertyType() {
        if(Collection.class.isAssignableFrom(propertyClass)) {
            if (propertyType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) propertyType;

                for(Class genericType : ReflectionUtils.getGenericTypes(parameterizedType)) {
                    if(validatedTypes.contains(genericType)) {
                        continue;
                    }
                    validatedTypes.add(genericType);
                    Method equalsMethod = ReflectionUtils.getMethodOrNull(genericType, "equals", Object.class);
                    Method hashCodeMethod = ReflectionUtils.getMethodOrNull(genericType, "hashCode");

                    if(equalsMethod == null ||
                            hashCodeMethod == null ||
                            Object.class.equals(equalsMethod.getDeclaringClass()) ||
                            Object.class.equals(hashCodeMethod.getDeclaringClass())) {
                        LogUtils.LOGGER.warn("The {} class should override both the equals and hashCode methods based on the JSON object value it represents!", genericType);
                    }
                }
            }
        }
    }

    private static List<Class> validatedTypes = new ArrayList<>();
}
