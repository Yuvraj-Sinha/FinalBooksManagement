package com.trueid.service;

import java.util.List;

public interface OrderedItemService {
	public String placeOrder(String username, List<Long> bookIds);
}
