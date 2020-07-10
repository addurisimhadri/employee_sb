package com.sim.cms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "image_table")
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ImageModel {
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "userId")
	private long userId;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Lob
    @Column(name = "picByte", columnDefinition="BLOB")
    private byte[] picByte;
	public ImageModel(long userId, String name, String type, byte[] picByte) {
		super();
		this.userId = userId;
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}
	public ImageModel() {
		super();
	}
    
    

}
