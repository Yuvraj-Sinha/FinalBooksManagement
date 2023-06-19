package com.sulzer.bookstore.service.dto;

import com.sulzer.bookstore.constants.Category;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	/**
	 * Unique Book Number given by company. Eg: ISBN number
	 */
	@ApiModelProperty(value = "Book Unique Id")
	private Long id;

	/**
	 * title of the book
	 */
	@ApiModelProperty(value = "Title of the book")
	private String title;

	/**
	 * author of the book
	 */
	@ApiModelProperty(value = "Author of the book")
	private String author;

	/**
	 * category of the book Eg: Novel, Fiction, etc
	 */
	@ApiModelProperty(value = "Category of the book")
	private Category category;

	/**
	 * price of the book
	 */
	@ApiModelProperty(value = "Price of the book")

	private float price;

	/**
	 * Amount of book available
	 */
	@ApiModelProperty(value = "Copies of book available on the store")

	private int totalCount;
	@ApiModelProperty(value = "quantity of solded books")
	private int sold;

}
