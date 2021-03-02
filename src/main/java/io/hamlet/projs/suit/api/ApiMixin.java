package io.hamlet.projs.suit.api;

import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.IdEntity;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public abstract class ApiMixin<E extends IdEntity, R extends JpaRepository<E, Long>> {

	final static String DEL_SUCCEED_TEXT = "删除成功";
	final static String UPDATE_SUCCEED_TEXT = "更新成功";
	final static String CREATE_SUCCEED_TEXT = "创建成功";
	
	final static String FIND_BY_TEMP = "FROM entity e WHERE e.attr=:param";
	final static String FIND_BY_2_PARAM_TEMP = "FROM entity e WHERE e.attr=:param and e.attr2=:param2";
	
	
	protected List<E> findAll(){
		List<E> rst =  repo().findAll();
		return rst;
	}
	
	protected E findOne(long id) {
		return repo().findById(id).get();
	}

	protected Result remove(E entity) {
		repo().delete(entity);

		return ResultUtils.success(DEL_SUCCEED_TEXT, entity.getId());
	}

	protected Result update(E entity) {
		repo().saveAndFlush(entity);
		return ResultUtils.success(entity, UPDATE_SUCCEED_TEXT);
	}

	protected Result create(E entity) {
		repo().save(entity);

		return ResultUtils.success(entity, CREATE_SUCCEED_TEXT);
	}



	R repo() {
		if(this.getRepo() == null) {
			throw new IllegalStateException("getRepo() must return a non-null repository");
		}
		return this.getRepo();
	}
	
	abstract Class getEntityClass();
	abstract R getRepo();
	protected String getEntityName() {
		return getEntityClass().getName();
	}
	

}
