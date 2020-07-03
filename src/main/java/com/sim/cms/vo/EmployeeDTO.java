package com.sim.cms.vo;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmployeeDTO {
	
	private int id;  
	private String ename;  
	private int age;
	private double salary;  
	private String designation;
	private String email;
	private String add;
	
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
			 return true;
		 }
	     if (!(o instanceof EmployeeDTO)) {
	    	 return false;
	     }
	     EmployeeDTO user = (EmployeeDTO) o;
	     return id == user.id && Objects.equals(ename, user.ename) && Objects.equals(designation, user.designation);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, ename, designation);
	}

}
