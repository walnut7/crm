package com.kpleasing.crm.ejb.eao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.kpleasing.crm.ejb.eao.local.BaseEaoLocal;

/**
 * Session Bean implementation class BaseEAOLocal
 * 
 * @param <T>
 */
public abstract class BaseEao<T, PK extends Serializable> implements BaseEaoLocal<T, PK> {

	protected Class<T> entityClass;

	@PersistenceContext(unitName = "CRMPersistenceUnit")
	public EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public BaseEao() {
	}

	public BaseEao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void save(T entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(T entity) {
		entityManager.merge(entity);
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public void delete(PK id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
	}

	@Override
	public T findById(PK id) {
		return (T) entityManager.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return entityManager.createQuery("select o from " + entityClass.getSimpleName() + " o").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperty(String propertyName, Object propertyValue) {
		Query query = entityManager.createQuery(
				"select o from " + entityClass.getSimpleName() + " o where o." + propertyName + "= :propertyValue");
		query.setParameter("propertyValue", propertyValue).getResultList();
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperty(Object[] fields, Object... args) {
		Query query = entityManager
				.createQuery("select o from " + entityClass.getSimpleName() + " as o " + buildWhereJpql(fields));
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return (List<T>) query.getResultList();
	}

	/**
	 * 查询条件组合
	 * 
	 * @param fields
	 * @return
	 */
	private String buildWhereJpql(Object[] fields) {
		StringBuffer out = new StringBuffer("");
		if (fields != null && fields.length > 0) {
			out.append(" where ");
			for (int index = 0; index < fields.length; index++) {
				out.append(" o." + fields[index] + "=?" + (index + 1) + " ");
				out.append("and");
			}
			out.delete(out.length() - 3, out.length());
		}
		return out.toString();
	}

	/**
	 * Object转Map
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public Map<String, Object> ConvertObjToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					if (o == null || o == "") {
						continue;
					}
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}
		
	/**
	 * 通过多个属性统计数量
	 * @param propertyNames 属性名称数组
	 * @param values 对应的属性值数组 return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int countByPropertys(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from " + entityClass.getSimpleName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append(" = ");
			strBuffer.append("? ");
		}

		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i+1, values[i]);
		}

		List list = query.getResultList();
		Long result = (Long) list.get(0);
		return result.intValue();
	}
	
	/**
	 * 通过多个属性统计数量（模糊查询）
	 * @param propertyNames 属性名称数组
	 * @param values 对应的属性值数组 return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int countByPropertysLike(String[] propertyNames, Object[] values) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from " + entityClass.getSimpleName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append(" like ");
			strBuffer.append("? ");
		}
		
		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i+1, "%"+values[i]+"%");
		}
		
		List list = query.getResultList();
		Long result = (Long) list.get(0);
		return result.intValue();
	}
	
	
	/**
	 * 统计数量
	 * @return
	 */
	@Override
	public int countAll() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("select count(*) from " + entityClass.getSimpleName());
		strBuffer.append(" as model ");

		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);

		List list = query.getResultList();
		Long result = (Long) list.get(0);
		return result.intValue();
	}
	
	/**
	 * 分页查找所有的记录
	 * @param page 要返回的页数
	 * @param pageSize 没有记录数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(int page, int pageSize) {
		return findAll(page, pageSize, "");
	}
	
	
	public List<T> findAll(int page, int pageSize, String sort) {
		String queryString = "from " + entityClass.getSimpleName() + " " + sort;
		Query query = entityManager.createQuery(queryString);
		int firstResult = (page - 1) * pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.getResultList();  
	}
	
	
	/**
	 * 通过属性查找并分页
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param page 页数
	 * @param pageSize 每页显示条数
	 */
	@Override
	public List<T> findByProperty(String propertyName, Object value, int page, int pageSize) {
		return this.findByPropertys(new String[] { propertyName }, new Object[] { value }, page, pageSize);
	}
	
	
	
	@Override
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize) {
		return findByPropertys(propertyNames, values, page, pageSize, "");
	}
	
	
	/**
	 * 通过多个属性组合查询
	 * @param propertyNames 属性名称数组
	 * @param values 对应于propertyNames的值
	 * @param page 页数
	 * @param pageSize 每页显示数 return 匹配的结果 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize, String sort) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + entityClass.getSimpleName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append(" = ");
			strBuffer.append("? ");
		}
		strBuffer.append(sort);
		String queryString = strBuffer.toString();

		int firstResult = (page - 1) * pageSize;

		Query query = entityManager.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i+1, values[i]);
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);

		return query.getResultList();  
	}
	
	/**
	 * 通过多个属性组合查询（模糊查询）
	 * @param propertyNames 属性名称数组
	 * @param values 对应于propertyNames的值
	 * @param page 页数
	 * @param pageSize 每页显示数 return 匹配的结果 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPropertysLike(String[] propertyNames, Object[] values,
			int page, int pageSize, String sort) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + entityClass.getSimpleName());
		strBuffer.append(" as model where ");
		for (int i = 0; i < propertyNames.length; i++) {
			if (i != 0)
				strBuffer.append(" and");
			strBuffer.append(" model.");
			strBuffer.append(propertyNames[i]);
			strBuffer.append(" like ");
			strBuffer.append("? ");
		}
		strBuffer.append(sort);
		String queryString = strBuffer.toString();
		
		int firstResult = (page - 1) * pageSize;
		
		Query query = entityManager.createQuery(queryString);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i+1, "%"+values[i]+"%");
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		
		return query.getResultList();  
	}
	
	/**
	 * 通过多个属性组合查询(Map)
	 * @param propertyNames 属性名称数组
	 * @param values 对应于propertyNames的值
	 * @param page 页数
	 * @param pageSize 每页显示数 return 匹配的结果 return 匹配的结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPropertyMap(Map<String, Object> map,int page, int pageSize) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + entityClass.getSimpleName());
		if(map.size()>0){
			strBuffer.append(" as model where ");
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object value = map.get(key);
	
				strBuffer.append(" model." + key + "= '" + value + "'");
				strBuffer.append(" and");
			}
			strBuffer.delete(strBuffer.length() - 4, strBuffer.length());
		}
		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);
		int firstResult = (page - 1) * pageSize;
		query.setFirstResult(firstResult);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByPropertyMap(Map<String, Object> map) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("from " + entityClass.getSimpleName());
		if(map.size()>0){
			strBuffer.append(" as model where ");
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object value = map.get(key);
	
				strBuffer.append(" model." + key + "= '" + value + "'");
				strBuffer.append(" and");
			}
			strBuffer.delete(strBuffer.length() - 4, strBuffer.length());
		}
		String queryString = strBuffer.toString();
		Query query = entityManager.createQuery(queryString);
		
		return query.getResultList();
	}
	

}
