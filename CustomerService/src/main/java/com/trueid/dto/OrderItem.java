package com.trueid.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderedItemId;
	// private String OrderId;
	private Long bookId;
	private String quantity;
	private float price;
	private String username;

}
