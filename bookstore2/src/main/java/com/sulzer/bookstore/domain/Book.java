package com.sulzer.bookstore.domain;

import javax.persistence.Entity;

import javax.persistence.Id;

import com.sulzer.bookstore.constants.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representation of Book Table
 **/
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	/**
	 * Unique Book Number given by company. Eg: ISBN number
	 */
	@Id
	private Long id;

	/**
	 * title of the book
	 */

	private String title;

	/**
	 * author of the book
	 */
	private String author;

	/**
	 * category of the book Eg: Novel, Fiction, etc
	 */
	private Category category;

	/**
	 * price of the book
	 */

	private float price;

	/**
	 * Amount of book available
	 */

	private int totalCount;

	/**
	 * Total copies of book sold
	 */

	private int sold;
}
