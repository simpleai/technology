package com.xiaoruiit.knowledge.point.mybatis.typehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    protected final TypeReference<T> reference;

    public JsonTypeHandler(TypeReference<T> reference) {
        this.reference = reference;
    }

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.read(rs.getString(columnName));
    }

    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.read(rs.getString(columnIndex));
    }

    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.read(cs.getString(columnIndex));
    }

    protected abstract T read(String var1);

    protected abstract String toJson(T var1);
}
