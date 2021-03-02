package io.hamlet.projs.suit.api;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.hamlet.projs.suit.repository.GarmentClazzRepository;
import org.jboss.resteasy.annotations.Form;

import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.GarmentClazz;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.form.GarmentClsForm;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@RoleRequire(Role.ADMIN)
@Path("/garmentcls")
@Produces(value="application/json;charset=UTF-8")
public class GarmentClazzApi extends ApiMixin<GarmentClazz, GarmentClazzRepository> {

	@Autowired
	GarmentClazzRepository garmentClazzRepo;
	
	
	@Path("/create")
	@POST
	public Result createGarmentCls(@Form GarmentClsForm form) {
		if(form.isValid()) {
			GarmentClazz garmentCls = form.getGarmentCls();
			GarmentClazz existedOne = repo().findByNo(garmentCls.getNo());
			if(existedOne != null) {
				return ResultUtils.error("编号已存在");
			}
			return create(garmentCls);
		}else {
			return ResultUtils.error("表单数据验证失败", form.getValidationRst());
		}
		
	}
	
	@Path("/del/{id}")
	@GET
	public Result removeGarmentCls(@PathParam(value="id") Long id) {
		GarmentClazz garmentCls = findOne(id);
		if(garmentCls == null) {
			return ResultUtils.error("该记录不存在");
		}else {
			remove(garmentCls);
			return ResultUtils.success("删除成功", garmentCls);
		}
	}
	
	@Path("/update/{id}")
	@POST
	public Result updateGarmentCls(@PathParam(value="id") Long id, @Form GarmentClsForm form) {
		if(form.isValid()) {
			GarmentClazz garmentCls = form.getGarmentCls();
			GarmentClazz existedNoOne = garmentClazzRepo.findByNo(garmentCls.getNo());
			 GarmentClazz existedOne = findOne(id);
			if(existedOne == null) {
				return ResultUtils.error("不存在该条记录");
			}else {
				if (existedNoOne == null || existedNoOne.getId() == id) {
					existedOne = form.fillGarmentCls(existedOne);
				}else {
					return ResultUtils.error("编号已存在");
				}
				return update(existedOne);
			}
		}else {
			return ResultUtils.error("表单数据验证失败", form.getValidationRst());
		}
		
	}
	
	@RoleRequire(Role.USER)
	@Path("/{id}")
	@GET
	public GarmentClazz getOne(@PathParam(value="id") Long id){
		GarmentClazz garmentCls = findOne(id);
		return garmentCls;
	}
	@RoleRequire(Role.USER)
	@Path("/all")
	@GET
	public Result getAll(){
		return ResultUtils.success(findAll());
	}


	@Override
	Class getEntityClass() {
		// TODO Auto-generated method stub
		return GarmentClazz.class;
	}

	@Override
	GarmentClazzRepository getRepo() {
		return garmentClazzRepo;
	}

}
