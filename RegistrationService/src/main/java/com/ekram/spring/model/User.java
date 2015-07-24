package com.ekram.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * User entity.
 * 
 * @author kazi_e
 *
 */
@XmlRootElement
public class User implements Serializable{

	private static final long serialVersionUID = -7788619177798333712L;

	@NotEmpty(message = "Please enter username")
	@Pattern(regexp = "[a-z][0-9a-z_\\-]*",message="Invalid username format")
	@Size(min = 6, max = 15, message = "username must between 6 and 15 characters")
	private String username;

	//Pattern is required, because original @Email validation doesn't work as expected
	@NotEmpty(message = "Please enter email")
	@Pattern(regexp="^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",message="Invalid Email format")
	@Size(min = 6, max = 30, message = "email must between 6 and 30 characters")
	private String email;

	private Date createdDate;

	public String getUsername() {
		return username;
	}

	@XmlElement
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	//@JsonSerialize(using=DateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(username).toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
		if(obj instanceof User){
			final User other = (User) obj;
			return new EqualsBuilder().append(username, other.username).isEquals();
		} else{
			return false;
		}
	}
}
