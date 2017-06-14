package com.cccis.base.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanCopier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean工具类
 */
public final class BeanUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BeanUtils.class);

    /*
     * 用来缓存BeanCopier的缓存
     */
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap();

    private BeanUtils() {

    }

    private static BeanCopier getBeanCopier(final Class<?> sourceClass, final Class<?> destClass) {
        final String key = sourceClass.getCanonicalName() + ":" + destClass.getCanonicalName();
        BeanCopier beanCopier = BEAN_COPIER_CACHE.get(key);
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, destClass, false);
            BEAN_COPIER_CACHE.putIfAbsent(key, beanCopier);
        }
        return beanCopier;
    }

    /**
     * 复制某个对象为目标对象类型的对象 当source与target对象属性名相同, 但数据类型不一致时，source的属性值不会复制到target对象
     *
     * @param <T>      目标对象类型参数
     * @param source   源对象
     * @param destType 目标对象类型
     * @return 复制后的结果对象
     */
    public static <T> T copyAs(final Object source, final Class<T> destType) {
        if (source == null || destType == null) {
            return null;
        }
        try {
            final BeanCopier beanCopier = getBeanCopier(source.getClass(), destType);
            final T dest = destType.newInstance();
            beanCopier.copy(source, dest, null);
            return dest;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制源对象集合到目标对象列表
     *
     * @param source   源对象
     * @param destType 目标对象
     * @param <T>      源对象类型参数
     * @param <K>      目标对象类型参数
     * @return 结果集合, 一个list
     */
    public static <T, K> List<K> copyAs(final Collection<T> source, final Class<K> destType) {
        if (CollectionUtils.isNullOrEmpty(source) || destType == null) {
            return Collections.EMPTY_LIST;
        }

        final List<K> result = Lists.newArrayList();
        if (source.isEmpty()) {
            return result;
        }
        try {
            for (final Object object : source) {
                final BeanCopier beanCopier = getBeanCopier(object.getClass(), destType);
                final K dest = destType.newInstance();
                beanCopier.copy(object, dest, null);
                result.add(dest);
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 复制属性：从源对象复制和目标对象相同的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copy(final Object source, final Object target) {
        if (source == null || target == null) {
            return;
        }
        final BeanCopier beanCopier = getBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    /**
     * 复制属性：从源对象复制和目标对象相同的属性，除了忽略的属性之外 如果属性名相同，但数据类型不同，会抛出运行时异常FatalBeanException
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 忽略属性
     */
    public static void copyProperties(final Object source, final Object target, final String... ignoreProperties) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }

    /**
     * 将Map对象拷贝到Bean，Map中的key对应Bean的属性名，value对应属性值
     *
     * @param source 源对象，map
     * @param target 目标对象
     */
    public static void copyMapToObject(final Map<?, ?> source, final Object target) {
        try {
            org.apache.commons.beanutils.BeanUtils.populate(target, source);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把对象当作Map用
     *
     * @param obj 对象
     * @return Map
     */
    /*@SuppressWarnings("unchecked")
    public static Map<String, Object> getProperties(Object obj) {
        return new CCCBeanMap(obj);
    }*/

    /**
     * 设置属性
     *
     * @param bean  目标对象
     * @param name  属性名
     * @param value 属性值
     */
    public static void setProperty(final Object bean, final String name, final Object value) {
        try {
            org.apache.commons.beanutils.BeanUtils.setProperty(bean, name, value);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取属性的值
     *
     * @param bean 目标对象
     * @param name 属性名
     * @return 属性的值，其实是String类型
     */
    public static Object getProperty(final Object bean, final String name) {
        try {
            return org.apache.commons.beanutils.BeanUtils.getProperty(bean, name);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置Field值
     *
     * @param bean      要设置对象
     * @param fieldName 字段名
     * @param value     值
     */
    public static void setFieldValue(final Object bean, final String fieldName, final Object value) {
        try {
            final Field field = findField(bean.getClass(), fieldName);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取得指定名称的Field, 子类找不到, 去父类里找
     *
     * @param clz       类
     * @param fieldName 指定名称
     * @return 找不到返回null
     */
    public static Field findField(final Class<?> clz, final String fieldName) {
        Field f = null;
        try {
            f = clz.getDeclaredField(fieldName);
        } catch (final NoSuchFieldException e) {
            if (clz.getSuperclass() != null) {
                f = findField(clz.getSuperclass(), fieldName);
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace(e.getMessage(), e);
            }
        }
        return f;
    }

    /**
     * 取得指定的属性设置器
     *
     * @param clz          类
     * @param propertyName 属性名
     * @return 属性设置器
     */
    /*public static PropertySetter getSetter(Class<?> clz, String propertyName) {
        PropertySetter propertySetter = PropertySetter.EMPTY;
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, clz);
            Method setter = pd.getWriteMethod();
            propertySetter = new PropertySetterImpl(setter, propertyName);
        }
        catch (IntrospectionException e) {
            LOG.trace(clz.getName() + ".class," + e.getMessage(), e);
        }
        return propertySetter;
    }*/

    /**
     * 用于model修改时的对象复制,把srcModel复制到destModel,srcModel中为null的字段不复制，同名且类型相同的属性才复制
     *
     * @param srcModel  表单提交的源对象
     * @param destModel 数据库中的目标对象
     */
    public static void copyNotNullProperties(final Object srcModel, final Object destModel) {
        if (srcModel == null || destModel == null) {
            return;
        }

        try {
            final PropertyDescriptor[] srcDescriptors = Introspector.getBeanInfo(srcModel.getClass()).getPropertyDescriptors();
            final PropertyDescriptor[] destDescriptors = Introspector.getBeanInfo(destModel.getClass()).getPropertyDescriptors();
            final Map<String, PropertyDescriptor> destPropertyNameDescriptorMap = Maps.newHashMap();
            for (final PropertyDescriptor destPropertyDescriptor : destDescriptors) {
                destPropertyNameDescriptorMap.put(destPropertyDescriptor.getName(), destPropertyDescriptor);
            }
            for (final PropertyDescriptor srcDescriptor : srcDescriptors) {
                final PropertyDescriptor destDescriptor = destPropertyNameDescriptorMap.get(srcDescriptor.getName());
                if (destDescriptor != null && destDescriptor.getPropertyType() == srcDescriptor.getPropertyType() && destDescriptor.getPropertyType() != Class.class) {// 类型相同的属性才复制
                    if (srcDescriptor.getReadMethod() != null) {
                        final Object val = srcDescriptor.getReadMethod().invoke(srcModel);
                        if (val != null && destDescriptor.getWriteMethod() != null) {// not null
                            destDescriptor.getWriteMethod().invoke(destModel, val);
                        }
                    }
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 适用于copy非集合类的属性，主要适用场景，源对象和目标对象有相同名称且为集合类的属性 使用该方法存在的问题：对于集合类的属性，即使类型相同，也没有办法copy成功 添加该方法的原因：对于集合类，在运行期间会丢失类型信息
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyNonCollectionProperties(final Object source, final Object target) {
        final List<String> ignoreProperties = new ArrayList<String>();
        final Field[] declaredFields = target.getClass().getDeclaredFields();
        for (final Field field : declaredFields) {
            if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                ignoreProperties.add(field.getName());
            }
        }
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties.toArray(new String[ignoreProperties.size()]));
    }
}
