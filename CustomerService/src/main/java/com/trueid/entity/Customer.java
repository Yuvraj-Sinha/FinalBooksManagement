package com.trueid.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.trueid.dto.Cart;
import com.trueid.dto.OrderItem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String custId;
	private String name;
	private String email;
	private String contactNo;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Address address;
	@OneToOne(mappedBy = "customer", cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	private User user;
	@Transient
	private Cart cart;
	@Transient
	private List<OrderItem> orderItem;
}
