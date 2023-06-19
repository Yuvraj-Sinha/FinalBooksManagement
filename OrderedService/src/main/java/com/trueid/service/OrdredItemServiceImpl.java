package com.trueid.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trueid.custom.response.ApiResponse;
import com.trueid.dto.Book;
import com.trueid.entity.OrderItem;
import com.trueid.external.services.BookStore;
import com.trueid.repo.OrderedItemRepo;

@Service
public class OrdredItemServiceImpl implements OrderedItemService {
	@Autowired
	BookStore bookStore;
	@Autowired
	OrderedItemRepo orderRepo;

	@Transactional(rollbackOn = Exception.class)
	public String placeOrder(String username, List<Long> bookIds) {
		List<Long> val = bookIds;

		ArrayList<Long> al = new ArrayList<>();
		for (int k = 0; k < val.size(); k++) {
			Long j = val.get(k);
			int count = 0;
			boolean b = true;
			for (int l = 0; l < al.size(); l++) {
				if (al.get(l).equals(j)) {
					// System.out.println(j);
					b = false;
				}
			}
			if (b == true) {

				for (int i = 0; i < val.size(); i++) {
					if (j.equals(val.get(i))) {
						count++;
						// al.add(val[j]);
					}

					al.add(val.get(k));
				}
				System.out.println("Count of " + val.get(k) + ":" + count);
				ApiResponse<Book> purchaseBook = bookStore.purchaseBook(val.get(k));
				Book book = purchaseBook.getData();
				OrderItem order = new OrderItem();
				order.setBookId(book.getId());
				// order.setOrderId(book.g);
				Double price = Double.valueOf(book.getPrice());

				order.setPrice(price * count);
				order.setQuantity(count + "");
				order.setUsername(username);

				try {
					List<OrderItem> findByUsername = orderRepo.findByUsername(username);
					if (findByUsername.size() == 0) {
						OrderItem save = orderRepo.save(order);

					}
					System.out.println(findByUsername);

					for (OrderItem ord : findByUsername) {
						if (ord.getBookId().equals(book.getId())) {
							String quantity = ord.getQuantity();
							int qyn = Integer.parseInt(quantity);
							qyn = qyn + count;

							orderRepo.save(ord);
						} else {
							OrderItem save = orderRepo.save(order);
						}

					}

				} catch (Exception e) {

				}
				System.out.println(book);

			}

		}

		return "done";
	}

}
