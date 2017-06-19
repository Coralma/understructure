package com.coral.base.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 */
public final class GenericUtils {

    private GenericUtils() {
    }

    /**
     * 通过反射,获得指定类的父类的泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>.
     *
     * @param clazz clazz 需要反射的类,该类必须继承范型父类
     * @param index 泛型参数所在索引,从0开始.
     * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (genType == null) {
            for (final Type type : clazz.getGenericInterfaces()) {
                if (type != null) {
                    genType = type;
                }
            }
        }
        return getSuperClassGenericType(genType, index);
    }

    /**
     * 获取指定定类的父类的泛型参数的实际类型
     *
     * @param genType 反射类型
     * @param index   输入的索引
     * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */
    public static Class<?> getSuperClassGenericType(final Type genType, final int index) {
        // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class
        final Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("你输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
        }
        final Type param = params[index];
        if (!(param instanceof Class)) {
            if (param instanceof ParameterizedType) {
                final Type rawType = ((ParameterizedType) param).getRawType();
                if (rawType instanceof Class) {
                    return (Class<?>) rawType;
                }
            }
            return Object.class;
        }
        return (Class<?>) param;
    }

    /**
     * 获取指定定类的父类的泛型参数的实际类型
     *
     * @param genType 反射类型
     * @return 范型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */
    public static Class<?> getSuperClassGenericType(final Type genType) {
        return getSuperClassGenericType(genType, 0);
    }

    /**
     * 通过反射,获得指定类的父类的第一个泛型参数的实际类型. 如BuyerServiceBean extends DaoSupport<Buyer>.
     *
     * @param clazz clazz 需要反射的类,该类必须继承泛型父类
     * @return 泛型参数的实际类型, 如果没有实现ParameterizedType接口，即不支持泛型，所以直接返回<code>Object.class</code>
     */
    public static Class getSuperClassGenericType(final Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

}
