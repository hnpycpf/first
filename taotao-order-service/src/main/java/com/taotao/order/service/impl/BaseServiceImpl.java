/*package com.taotao.manager.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.container.page.PageHandler;
import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.pojo.BasePojo;
import com.taotao.manager.service.BaseService;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T> {
	@Autowired
	private Mapper<T> mapper;
	
	private Class<T>  t;
	
	public BaseServiceImpl() {
		Type superclass = this.getClass().getGenericSuperclass();
		ParameterizedType param=(ParameterizedType) superclass;
		Type[] types = param.getActualTypeArguments();
		this.t=(Class<T>) types[0];
	}

	@Override
	public T queryById(Long id) {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public List<T> queryAll() {
		// TODO Auto-generated method stub
		return mapper.select(null);
	}

	@Override
	public int queryCountByWhere(T t) {
		// TODO Auto-generated method stub
		return mapper.selectCount(t);
	}

	@Override
	public List<T> queryListByWhere(T t) {
		// TODO Auto-generated method stub
		
		return mapper.selectByExample(t);
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		return mapper.select(null);
	}

	@Override
	public T queryOne(T t) {
		// TODO Auto-generated method stub
		return mapper.selectOne(t);
	}

	@Override
	public void save(T t) {
		// TODO Auto-generated method stub
		if(t.getCreated()==null){
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		}
		else{
			t.setUpdated(t.getCreated());
		}
		mapper.insert(t);
		
		
	}

	@Override
	public void saveSelective(T t) {
		// TODO Auto-generated method stub
		if(t.getCreated()==null){
			t.setCreated(new Date());
			t.setUpdated(t.getCreated());
		}
		else{
			t.setUpdated(t.getCreated());
		}
		mapper.insertSelective(t);
		
	}

	@Override
	public void updateById(T t) {
		// TODO Auto-generated method stub
		t.setUpdated(new Date());
		mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		// TODO Auto-generated method stub
		t.setUpdated(new Date());
		mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		mapper.deleteByPrimaryKey(id);		
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// TODO Auto-generated method stub
		Example example=new Example(this.t);
		example.createCriteria().andIn("id", ids);
		mapper.deleteByExample(example);
	}

}
*/

package com.taotao.order.service.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.pojo.BasePojo;

//@Service // 这里是不对的,因为在此类的构造方法中要获取父，但是他没有父，
public class BaseServiceImpl<T> implements com.taotao.order.service.BaseService<T> {

	// 泛型注入，spring4和以上版本才可以使用
	@Autowired
	private Mapper<T> mapper;

	// 声明泛型的class
	private Class<T> clazz;

	// 在构造方法中获取泛型的class
	public BaseServiceImpl() {
		// 获取父类的参数的type
		Type type = this.getClass().getGenericSuperclass();

		// 强转type为其子类，目的能够使用子类的方法，获取泛型的class
		ParameterizedType pType = (ParameterizedType) type;

		// 调用方法获取父类的class
		this.clazz = (Class<T>) pType.getActualTypeArguments()[0];
	}

	@Override
	public T queryById(Long id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}
	@Override
	public T queryById(String id) {
		T t = this.mapper.selectByPrimaryKey(id);
		return t;
	}

	@Override
	public List<T> queryAll() {
		List<T> list = this.mapper.select(null);
		return list;
	}

	@Override
	public int queryCountByWhere(T t) {
		int count = this.mapper.selectCount(t);
		return count;
	}

	@Override
	public List<T> queryListByWhere(T t) {
		List<T> list = this.mapper.select(t);
		return list;
	}

	@Override
	public List<T> queryByPage(Integer page, Integer rows) {
		// 设置分页参数，第一个参数是当前页码数，第二个参数是每页显示的数据条数
		PageHelper.startPage(page, rows);

		// 执行查询
		List<T> list = this.mapper.select(null);

		return list;
	}

	@Override
	public T queryOne(T t) {
		T result = this.mapper.selectOne(t);
		return result;
	}

	@Override
	public void save(T t) {
		// 判断保存的数据是否已经设置时间，
		

		this.mapper.insert(t);
	}

	@Override
	public void saveSelective(T t) {
		// 判断保存的数据是否已经设置时间，
		

		// if (t.getCreated() == null) {
		// // 如果没有设置新增时间，在这里设置新增时间
		// t.setCreated(new Date());
		// }
		// if (t.getUpdated() == null) {
		// // 判断是否设置更新时间，如果没有设置，在这里设置
		// t.setUpdated(t.getCreated());
		// }

		this.mapper.insertSelective(t);
	}

	@Override
	public void updateById(T t) {
		// 设置更新时间
	
		this.mapper.updateByPrimaryKey(t);
	}

	@Override
	public void updateByIdSelective(T t) {
		// 设置更新时间
		
		this.mapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public void deleteById(Long id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByIds(List<Object> ids) {
		// 创建example
		Example example = new Example(this.clazz);

		// 设置删除条件
		example.createCriteria().andIn("id", ids);

		// 执行批量删除
		this.mapper.deleteByExample(example);

	}

}
