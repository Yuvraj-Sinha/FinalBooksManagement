package com.trueid.dto;

import java.io.Serializable;

import com.trueid.entity.Address;
import com.trueid.entity.User;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class CustomerDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "Cust Id")
	private String custId;
	@ApiModelProperty(value = "Name")
	private String name;
	@ApiModelProperty(value = "Email")
	private String email;
	@ApiModelProperty(value = "Contact No")
	private String contactNo;
	@ApiModelProperty(value = "Address")

	private Address address;
	@ApiModelProperty(value = "user")

	private User user;

}
