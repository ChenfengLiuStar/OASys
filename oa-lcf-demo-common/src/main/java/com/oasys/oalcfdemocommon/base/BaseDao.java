package com.oasys.oalcfdemocommon.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;



/**
 * 
* <p>Title: BaseDao.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2019</p>  
* @author chenfengLiu
* @date 2019年1月27日  
* @version 1.0
 */
public interface BaseDao<T extends Serializable> {
	/**
	 * 带条件查询
	 * @param t 实体类
	 * @return
	 */
	//type 生成sql语句的类的类对象
	//method 生成sql语句的类的方法名
	@SelectProvider(type=MyBatisUtil.class,method="findAll")
	public List<T> findAll(T t, Map<String, Object> map);
	@InsertProvider(type=MyBatisUtil.class,method="findCount")
	public void findCount(T t, Map<String, Object> map);
	@UpdateProvider(type=MyBatisUtil.class,method="update")
	public void update(T t);
	@DeleteProvider(type=MyBatisUtil.class,method="delete")
	public void delete(T t);
}
