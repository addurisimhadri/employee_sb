package com.sim.cms.vo;

import java.sql.Timestamp;

import com.sim.cms.entities.ImageModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostMsgDTO {
	private long id;
	private String msg;
	private long userId;
	private long likeCount;
	private Timestamp createdAt;
	private String username="";
	private ImageModel imageModel=new ImageModel();
	private boolean liked;

}
