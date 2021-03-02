package io.hamlet.projs.suit.api;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import io.hamlet.projs.suit.repository.ModelRepository;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.Model;
import io.hamlet.projs.suit.entity.User;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/model")
public class ModelApi extends ApiMixin<Model, ModelRepository>{

	@Autowired
	ModelRepository modelRepo;

	@Path("/findByGender/{gender}")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public List<Model> findByGender(@PathParam(value = "gender") String gender){
		if (gender == null) {
			return Collections.emptyList();
		}


		return modelRepo.findAllByGender(gender.charAt(0));
	}
	
	@Path("/all")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public List<Model> findAllModel(){
		return findAll();
	}
	
	
	@Path("/create")
	@Produces(value="application/json;charset=UTF-8")
	@POST
	public Result addModel(Model model){
		return ResultUtils.success(create(model), "创建成功");
	}
	
	@Path("/{id}")
	@Produces(value="application/json;charset=UTF-8")
	@GET
	public Result getModelById(@PathParam("id") Long modelId) {
		return ResultUtils.success(findOne(modelId));
	}
	
	@Override
	Class getEntityClass() {
		// TODO Auto-generated method stub
		return Model.class;
	}

	@Override
	ModelRepository getRepo() {
		return modelRepo;
	}

}
