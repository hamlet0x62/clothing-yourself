package io.hamlet.projs.suit.api;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.hamlet.projs.suit.repository.GarmentRepository;
import io.hamlet.projs.suit.repository.UserGarmentRepository;
import io.hamlet.projs.suit.repository.UserGarmentViewRepository;
import org.jboss.resteasy.annotations.Form;

import io.hamlet.projs.suit.annotation.RoleRequire;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.entity.Garment;
import io.hamlet.projs.suit.entity.Role;
import io.hamlet.projs.suit.entity.UserGarment;
import io.hamlet.projs.suit.entity.view.UserGarmentView;
import io.hamlet.projs.suit.form.UserGarmentForm;
import io.hamlet.projs.suit.utils.PassportUtil;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RoleRequire(Role.USER)
@Path("/game")
@Produces(value = "application/json;charset=utf-8")
public class SuitGameApi extends ApiMixin<UserGarment, UserGarmentRepository> {

	private static final String FIND_ONE_QUERY = "FROM UserGarment e WHERE e.garmentId=:garmentId and e.userId=:userId";

	@Context
	HttpServletRequest request;
	@Autowired
	UserGarmentRepository userGarmentRepo;
	@Autowired
	UserGarmentViewRepository userGarmentViewRepo;
	@Autowired
	GarmentRepository garmentRepo;

	@Path("/adjust/{userGarmentId}")
	@POST
	/**
	 * 调整模特已穿着的衣物
	 * 
	 * @param userGarmentId
	 * @param form
	 * @return
	 */
	public Result adjustUserGarment(@PathParam("userGarmentId") Long userGarmentId, @Form UserGarmentForm form) {

		Long userId = PassportUtil.getCurrentUserId(request);
		if (!form.isValid()) {
			return ResultUtils.formValidationError(form.getValidationRst());
		}
		UserGarment userGarment = findOne(userGarmentId);
		if (userGarment == null) {
			return ResultUtils.error("模特当前未穿着该衣服");
		} else if (!userGarment.getUserId().equals(userId)) {
			return ResultUtils.error("身份核对失败");
		} else {
			userGarment = form.adjustSuit(userGarment);
			return update(userGarment);
		}
	}

	@Path("/addSuit/{garmentId}")
	@POST
	/**
	 * 给用户当前的模特添加新的衣物
	 * 
	 * @param garmentId
	 * @param form
	 * @return
	 */
	public Result createUserGarment(@PathParam("garmentId") Long garmentId) {
		UserGarment userGarment = userGarmentRepo.findByUserIdAndGarmentId(garmentId, PassportUtil.getCurrentUserId(request));

		if (userGarment != null) {
			return ResultUtils.error("请勿重复添加同一件衣服");
		} else {
			Optional<Garment> opt = garmentRepo.findById(garmentId);
			if (!opt.isPresent()) {
				return ResultUtils.error("不存在此种衣物");
			} else {
				userGarment = new UserGarment();
				userGarment.setGarmentId(garmentId);
				userGarment.setZindex(0);
				userGarment.setUserId(PassportUtil.getCurrentPassport(request).getUserId());
				create(userGarment);
				UserGarmentView view = userGarmentViewRepo.findById(userGarment.getId()).get();
				return ResultUtils.success(view);
			}
		}
	}

	@Path("/garments")
	@GET
	public Result getUserGarmentView() {
		List<UserGarmentView> rv = userGarmentViewRepo.findAllByUserId(PassportUtil.getCurrentUserId(request));
		return ResultUtils.success(rv);
	}
	
	@Path("/removeSuit/{id}")
	@POST
	public Result removeUserGarment(@PathParam("id") Long userGarmentId) {
		
		UserGarment userGarment = findOne(userGarmentId);
		if(userGarment == null) {
			return ResultUtils.error("未找到记录");
		}else {
			remove(userGarment);
			return ResultUtils.success(userGarmentId, "删除成功");
			
		}
		
	}


	@Override
	Class getEntityClass() {
		// TODO Auto-generated method stub
		return UserGarment.class;
	}

	@Override
	UserGarmentRepository getRepo() {
		return userGarmentRepo;
	}

}
