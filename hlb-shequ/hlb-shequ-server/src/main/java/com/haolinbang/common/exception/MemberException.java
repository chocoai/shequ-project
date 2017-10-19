package com.haolinbang.common.exception;

public class MemberException extends RuntimeException {

	private static final long serialVersionUID = -186923788944512458L;

	public MemberException() {

	}

	public MemberException(String msg) {
		super(msg);
	}

	public MemberException(Exception e) {
		super(e);
	}

}
