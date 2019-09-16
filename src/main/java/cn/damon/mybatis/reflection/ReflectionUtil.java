package cn.damon.mybatis.reflection;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 反射工具类
 * 
 * @author Damon
 *
 */
public class ReflectionUtil {
	
	/**
	 *  为指定的bean的propName属性的值设为value
	 *  
	 * @param bean 目标对象
	 * @param propName 对象的属性名
	 * @param value  值
	 */
	private static void setPropToBean(Object bean, String propName, Object value){
		Field f;
		try {
			f = bean.getClass().getDeclaredField(propName);//获得对象指定的属性
	        f.setAccessible(true);//将字段设置为可通过反射进行访问
	        f.set(bean, value);//为属性设值
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 
	 * 从resultSet中读取一行数据，并填充至指定的实体bean
	 * 
	 * @param entity  待填充的实体bean
	 * @param resultSet  从数据库加载的数据
	 * @throws SQLException sqlexception
	 */
	public static void setPropToBeanFromResultSet(Object entity,ResultSet resultSet) throws SQLException {
		//通过反射获取对象的所有字段
			Field[] declaredFields = entity.getClass().getDeclaredFields();
		//遍历所有的字段，从resultSet中读取相应的数据，并填充至对象的属性中
		for (Field declaredField : declaredFields) {
			//如果是字符串类型数据
			if ("String".equals(declaredField.getType().getSimpleName())) {
				setPropToBean(entity, declaredField.getName(), resultSet.getString(declaredField.getName()));
			} else if ("Integer".equals(declaredField.getType().getSimpleName())) {
				//如果是int类型数据
				setPropToBean(entity, declaredField.getName(), resultSet.getInt(declaredField.getName()));
			} else if ("Long".equals(declaredField.getType().getSimpleName())) {
				//如果是long类型数据
				setPropToBean(entity, declaredField.getName(), resultSet.getLong(declaredField.getName()));
			}
		}
		
	}
	
	
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class clazz = Class.forName("cn.damon.mybatis.entity.TUser");
		Object user = clazz.newInstance();
		ReflectionUtil.setPropToBean(user, "userName", "lison");
		System.out.println(user);
	}




}
