package com.sim.cms.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostMsg {	
	private long id;
	private String msg;
	private long userId;
	private long likeCount;
	private Timestamp createdAt;
	private String username;

}
