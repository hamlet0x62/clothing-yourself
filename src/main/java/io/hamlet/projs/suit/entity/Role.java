package io.hamlet.projs.suit.entity;

public enum Role {
	ANONYMOUS(-1),  USER(1), ADMIN(2),;
	static Role[] roles = {ADMIN, USER};
	
	private int index;
	
	Role(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	public static Role getRole(int id) {
		for(Role r: roles) {
			if(r.getIndex() == id) {
				return r;
			}
		}
		return ANONYMOUS;
	}
	
}
