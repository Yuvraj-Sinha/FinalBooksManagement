package com.trueid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.trueid.repo.CustomerRepo;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
public class CustomerServiceApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(CustomerServiceApplication.class, args);
		CustomerRepo bean = context.getBean(CustomerRepo.class);
		/*
		 * Address address = new Address(); address.setAddId("221");
		 * address.setCity("hshsd"); address.setState("jhds");
		 * address.setLandMark("sadsd"); address.setState("ewiufew");
		 * 
		 * Customer c = new Customer(); c.setContactNo("222"); c.setCustId("211");
		 * c.setEmail("ekhds@bjds"); c.setName("yuvraj"); c.setAddress(address);
		 * address.setCustomer(c); bean.save(c);
		 */
	}

}
