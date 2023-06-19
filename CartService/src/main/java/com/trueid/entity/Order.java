package com.trueid.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.trueid.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")

public class Order {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String orderId;
	private String title;
	private String author;
	private Category category;
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cart_id")
	@JsonBackReference
	private Cart cart;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", title=" + title + ", author=" + author + ", category=" + category
				+ ", id=" + id + "]";
	}

}
