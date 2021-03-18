package com.miniface.entities;

import javax.validation.constraints.NotNull;

public class ProracuniEntryEntity {
	
	@NotNull(message = "must enter type")
	private int type;
	@NotNull(message = "must enter local")
	private int local;
	@NotNull(message = "must enter returnTrip")
	private boolean returnTrip;
	@NotNull(message = "must enter message")
	private int weight;
	@NotNull(message = "must enter groupID")
	private int groupID;
	
	private int ID;
	
	public ProracuniEntryEntity(int type, int local, boolean returnTrip, int weight, int groupID) {
		this.type = type;
		this.local = local;
		this.returnTrip = returnTrip;
		this.weight = weight;
		this.groupID = groupID;
	}

	public ProracuniEntryEntity() {
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public boolean getReturnTrip() {
		return returnTrip;
	}

	public void setReturnTrip(boolean returnTrip) {
		this.returnTrip = returnTrip;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
