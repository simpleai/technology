package com.xiaoruiit.knowledge.point.mybatis.typehandler;

/**
 * @author hanxiaorui
 * @date 2024/10/17
 */
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.Assert;

public class CodeEnumTypeHandler<T extends Enum<T> & CodeEnum> extends BaseTypeHandler<T> {
    private final Method codeOf;

    public CodeEnumTypeHandler(Class<T> clazz) {
        Assert.isTrue(clazz.isEnum(), clazz + "必须是枚举类型");
        Assert.isTrue(CodeEnum.class.isAssignableFrom(clazz), clazz + "必须实现CodeEnum接口");
        Method codeOf = this.getPublicMethod(clazz, "codeOf", Integer.TYPE);
        if (codeOf == null) {
            codeOf = this.getPublicMethod(clazz, "codeOf", Integer.class);
        }

        Assert.notNull(codeOf, clazz + "类没有找到codeOf方法");
        Assert.isTrue(Modifier.isStatic(codeOf.getModifiers()), clazz + "必须为静态方法");
        this.codeOf = codeOf;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, ((CodeEnum)parameter).code());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : this.invokeMethod(this.codeOf, (Object)null, rs.getInt(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : this.invokeMethod(this.codeOf, (Object)null, rs.getInt(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : this.invokeMethod(this.codeOf, (Object)null, cs.getInt(columnIndex));
    }

    private Method getPublicMethod(Class<T> clazz, String name, Class<?> paramType) {
        Method[] var4 = clazz.getDeclaredMethods();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(name) && Arrays.equals(method.getParameterTypes(), new Class[]{paramType}) && Modifier.isPublic(method.getModifiers())) {
                return method;
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private T invokeMethod(Method method, Object target, Object... args) {
        try {
            return (T)method.invoke(target, args);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }
}
