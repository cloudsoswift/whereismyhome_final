package com.ssafy.home.user.model;

public class UserInterestDTO {
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String apartmentName;
	private String roadName;
	private String buildYear;
	
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	@Override
	public String toString() {
		return "UserInterestDTO [sidoName=" + sidoName + ", gugunName=" + gugunName + ", apartmentName=" + apartmentName
				+ ", roadName=" + roadName  + ", buildYear=" + buildYear + "]";
	}
	
}
