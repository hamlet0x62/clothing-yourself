package io.hamlet.projs.suit.form;

import io.hamlet.projs.suit.annotation.CheckSame;
import io.hamlet.projs.suit.annotation.Validator;
import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.utils.ResultUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class ValidateForm {
	private Map<String, String> validationRst;

	public Result sendInvalidResult() {
		Map<String, String> rst = getValidationRst();
		return ResultUtils.error("表单参数验证失败", rst);
	}

	protected Map<String, String> validate() {
		validationRst = new HashMap<>(4);

		Class cls = this.getClass();
		try {
			for (Field field : cls.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(Validator.class)) {
					Validator validator = field.getAnnotation(Validator.class);
					String fieldValue = (String) field.get(this);
//					System.out.println(fieldValue);
					if (validator.notNull() && fieldValue == null) {
						validationRst.put(field.getName(), "字段不能为空");
					} else if (fieldValue == null) {
						continue;
					} else if (validator.fixedLength() > 0 && fieldValue.length() != validator.fixedLength()) {
						validationRst.put(field.getName(), "字段的长度为固定值:" + validator.fixedLength());
					} else if (validator.minLength() > 0 || validator.maxLength() > 0) {
						if (fieldValue != null && !(fieldValue.length() <= validator.maxLength()
								&& fieldValue.length() >= validator.minLength())) {
							validationRst.put(field.getName(),
									"长度需要在" + validator.minLength() + "至" + validator.maxLength() + "之间.");
						}
					}

				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return validationRst;
	}

	public Map<String, String> getValidationRst() {
		if (validationRst == null) {
			this.validate();
		}
		return validationRst;
	}

	public boolean isValid() {
		Map<String, String> rst = getValidationRst();
		return rst.size() == 0;
	}

	public boolean isSame(Object entity) {
		Class cls = this.getClass();
		Class entityCls = entity.getClass();
		try {
			for (Field field : cls.getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(CheckSame.class)) {
					CheckSame check = field.getAnnotation(CheckSame.class);
					String fieldName = check.entityField();
					if (fieldName == "") {
						fieldName = field.getName();
					}
					Field entityField = entityCls.getDeclaredField(fieldName);
					entityField.setAccessible(true);
					Object entityFieldVal = entityField.get(entity);
					Object formVal = field.get(this);
					if(entityFieldVal != null && entityFieldVal.equals(formVal)) {
						return false;
					}else if(entityFieldVal == null && formVal != null) {
						return false;
					}

				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
