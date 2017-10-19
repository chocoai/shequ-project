package com.haolinbang.common.exception;

public class MemberNeedRegException extends RuntimeException {

	private static final long serialVersionUID = 1343990482088288158L;
	
	public MemberNeedRegException() {

	}

	public MemberNeedRegException(String msg) {
		super(msg);
	}

	public MemberNeedRegException(Exception e) {
		super(e);
	}

}
