package com.kpleasing.crm.ejb.eao.local;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseEaoLocal<T, PK extends Serializable> {

	/**
	 * 更新
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 保存
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(PK id);
	
	/**
	 * 刷新
	 * @param id
	 */
	public void flush();
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public T findById(PK id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	
	
	/**
	 * 根据条件查询
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object propertyValue);
	
	
	/**
	 * 根据条件查询
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	public List<T> findByProperty(Object[] fields, Object... args);
	
	/**
	 * 分页查询	
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<T> findAll(int page, int pageSize);
	
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	public List<T> findAll(int page, int pageSize, String sort);
	
	/**
	 * 根据条件(Map)查询所有记录数
	 * @param map
	 * @return
	 */
	public List<T> findByPropertyMap(Map<String, Object> map);


	/**
	 * Object转Map
	 * @param obj
	 * @return
	 */
	public Map<String, Object> ConvertObjToMap(Object obj);
	
	/**
	 * 
	 * @param propertyNames
	 * @param values
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize);
	
	
	/**
	 * 
	 * @param propertyNames
	 * @param values
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	public List<T> findByPropertys(String[] propertyNames, Object[] values,
			int page, int pageSize, String sort);
	
	
	/**
	 * 
	 * @param propertyNames
	 * @param values
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	public List<T> findByPropertysLike(String[] propertyNames, Object[] values,
			int page, int pageSize, String sort);
	
	
	/**
	 * 
	 * @return
	 */
	public int countAll();
	
	
	/**
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	public int countByPropertys(String[] propertyNames, Object[] values);
	
	
	/**
	 * 
	 * @param propertyNames
	 * @param values
	 * @return
	 */
	public int countByPropertysLike(String[] propertyNames, Object[] values);
	
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<T> findByProperty(String propertyName, Object value, int page, int pageSize);
	
	/**
	 * 
	 * @param map
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<T> findByPropertyMap(Map<String,Object> map,int page, int pageSize);
	
}
