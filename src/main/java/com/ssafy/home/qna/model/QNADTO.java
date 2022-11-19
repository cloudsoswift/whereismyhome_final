package com.ssafy.home.qna.model;

public class QNADTO {
	private int qnaNo;
	private String userId;
	private String subject;
	private String content;
	private String registerTime;
	
	public int getQNANo() {
		return qnaNo;
	}
	public void setQNANo(int qnaNo) {
		this.qnaNo = qnaNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "NoticeDTO [qnaNo=" + qnaNo + ", userId=" + userId + ", subject=" + subject + ", content="
				+ content + ", registerTime=" + registerTime + "]";
	}
	
}
