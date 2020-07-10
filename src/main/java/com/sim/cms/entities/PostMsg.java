package com.sim.cms.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "post_msg")
public class PostMsg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String msg;
	private long userId;
	private long likeCount;
	@Column(name="created_at", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")	
	private Timestamp createdAt;
	@Transient
	private String username;

}
