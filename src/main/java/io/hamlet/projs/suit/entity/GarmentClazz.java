package io.hamlet.projs.suit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static io.hamlet.projs.suit.constant.Constant.*;

@Table(name="T_GarmentClazz")
@Entity
public class GarmentClazz extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4765089896942396242L;
	@Column(length=TEXT_LEN_SHORT, columnDefinition = "nvarchar(26)")
	private String clazzName;
	
	@Column(length=TEXT_LEN_LONG)
	private String no;
	
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	

}
