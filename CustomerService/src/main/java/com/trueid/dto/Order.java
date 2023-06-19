package com.trueid.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String title;
	private String author;
	private Category category;
	private Long id;
	@JsonIgnore
	private Cart cart;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", title=" + title + ", author=" + author + ", category=" + category
				+ ", id=" + id + "]";
	}

}
