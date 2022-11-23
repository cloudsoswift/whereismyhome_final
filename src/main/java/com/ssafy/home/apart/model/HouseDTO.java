package com.ssafy.home.apart.model;

public class HouseDTO {
	private String aptCode;
	private String apartmentName;
	private String roadName;
	private String buildYear;
	private String lng;
	private String lat;
	private String like_id;
	
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
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLike_id() {
		return like_id;
	}
	public void setLike_id(String like_id) {
		this.like_id = like_id;
	}
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	@Override
	public String toString() {
		return "HouseDTO [aptCode=" + aptCode + ", apartmentName=" + apartmentName + ", roadName=" + roadName
				+ ", buildYear=" + buildYear + ", lng=" + lng + ", lat=" + lat + ", like_id=" + like_id + "]";
	}
	
}
