package com.trueid.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.Book;
import com.trueid.dto.BookDto;

@Service
@FeignClient(name = "BOOK-SERVICE")
public interface BookStore {
	@GetMapping("/api/book/{id}")
	public ApiResponse<BookDto> getBook(@PathVariable(name = "id") Long id);

	@PutMapping("/api/sell-book/{id}")
	public ApiResponse<Book> purchaseBook(@PathVariable(name = "id") Long id);
}
