package com.sulzer.bookstore.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for sell
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellDto {

	// book id
	@ApiModelProperty(value = "Id of the book to be sold")
	private long bookId;

	// book name
	@ApiModelProperty(value = "Number of copies of the book to be sold.")

	private int quantity;
}
