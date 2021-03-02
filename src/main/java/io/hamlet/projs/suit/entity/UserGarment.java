package io.hamlet.projs.suit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "T_UserGarment")
@Entity
public class UserGarment extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1355280572888975808L;
	@Column
	private Long userId;
	@Column
	private Long garmentId;
	@Column
	private int zindex;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGarmentId() {
		return garmentId;
	}

	public void setGarmentId(Long garmentId) {
		this.garmentId = garmentId;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

}
