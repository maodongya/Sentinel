package com.alibaba.csp.sentinel.dashboard.datasource.entity.jpa.typedescriptor;

import com.vladmihalcea.hibernate.type.json.internal.AbstractJsonSqlTypeDescriptor;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ObjectValuePoSqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {
    public static final ObjectValuePoSqlTypeDescriptor INSTANCE = new ObjectValuePoSqlTypeDescriptor();
    @Override
    public int getSqlType() {
        return Types.JAVA_OBJECT;
    }

    @Override
    public <X> ValueBinder<X> getBinder(JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setString(index, javaTypeDescriptor.unwrap(value, String.class, options));
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options)
                    throws SQLException {
                st.setString(name, javaTypeDescriptor.unwrap(value, String.class, options));
            }
        };
    }
}
