package io.hamlet.projs.suit.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.hamlet.projs.suit.repository.GarmentRepository;
import org.jboss.resteasy.annotations.Form;

import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.Garment;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.form.GarmentForm;
import io.hamlet.projs.suit.form.GarmentQueryForm;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
@RoleRequire(Role.ADMIN)
@Produces("application/json;charset=utf-8")
@Path("/garment")
public class GarmentApi extends ApiMixin<Garment, GarmentRepository> {

	@Autowired
	GarmentRepository garmentRepo;
	
	@RoleRequire(Role.USER)
	@Path("/query")
	@POST
	public Result queryGarments(@Form GarmentQueryForm queryBean) {
		List<Garment> garmentList = Collections.emptyList();

		if(!queryBean.isEmpty()) {
			if(queryBean.isSingleParam()) {

				if(queryBean.getClazzId() == 0) {
					garmentList = repo().findAllByGender(queryBean.getGender());

				}else {
					garmentList = repo().findAllByClazzId(queryBean.getClazzId());
				}


			}else {
				garmentList = repo().findAllByGenderAndClazzId(queryBean.getGender(), queryBean.getClazzId());
			}
		}else {
			garmentList =  garmentRepo.findAll();
		}
		return ResultUtils.success(garmentList);
	
	}
	
	@Path("/create")
	@POST
	public Result createGarment(@Form GarmentForm form) {
		if(form.isValid()) {
			Garment Garment = form.getGarment();
			return create(Garment);
		}else {
			return ResultUtils.error("表单数据验证失败", form.getValidationRst());
		}
		
	}
	@RoleRequire(Role.USER)
	@Path("/all")
	@GET
	public Result getAllGarment() {
			return ResultUtils.success(findAll());
	}
	
	@Path("/del/{id}")
	@GET
	public Result removeGarment(@PathParam(value="id") Long id) {
		Garment Garment = findOne(id);
		if(Garment == null) {
			return ResultUtils.error("该记录不存在");
		}else {
			remove(Garment);
			return ResultUtils.success("删除成功", Garment);
		}
	}
	
	@Path("/update/{id}")
	@POST
	public Result updateGarment(@PathParam(value="id") Long id, @Form GarmentForm form) {
		Garment garment = findOne(id);
		if(!form.isValid()) {
			return ResultUtils.error("表格字段验证失败");
		}
		if(garment == null) {
			return ResultUtils.error("该记录不存在");
		}else {
			if(form.isSame(garment)){
				return ResultUtils.success("无修改");
			}else {
				form.fillGarment(garment);
				return update(garment);
			}
		}
	}
	
	@RoleRequire(Role.USER)
	@Path("/{id}")
	@GET
	public Garment getOne(@PathParam(value="id") Long id){
		Garment garment = findOne(id);
		return garment;
	}
	

	@Override
	Class getEntityClass() {
		// TODO Auto-generated method stub
		return Garment.class;
	}

	@Override
	GarmentRepository getRepo() {
		return garmentRepo;
	}
}
