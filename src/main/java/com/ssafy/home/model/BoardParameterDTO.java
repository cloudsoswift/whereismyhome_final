package com.ssafy.home.model;


public class BoardParameterDTO {

	//"현재 페이지 번호
	private int pg;
	//페이지당 글갯수
	private int spp;
	//페이지의 시작 글번호
	private int start;
	//검색 조건
	private String key;
	//검색어
	private String word;

	public int getPg() {
		return pg;
	}

	public void setPg(int pg) {
		pg = pg == 0 ? 1 : pg;
		this.pg = pg;
	}

	public int getSpp() {
		return spp;
	}

	public void setSpp(int spp) {
		this.spp = spp;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}

