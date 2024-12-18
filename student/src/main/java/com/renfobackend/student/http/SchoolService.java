package com.renfobackend.student.http;

import java.util.List;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import com.netflix.appinfo.InstanceInfo;
import com.renfobackend.student.dtos.SchoolDto;
import com.netflix.discovery.EurekaClient;



public class SchoolService {
	@LoadBalanced
	final private RestTemplate restTemplate = new RestTemplate();

	public SchoolDto getSchoolDetails(Long schoolId, EurekaClient discoveryClient) {
		InstanceInfo service = discoveryClient
      .getApplication("SCHOOL")
      .getInstances()
			.get(0);
			
		// Using Eureka as discovery client, and knowing the RestTemplate is load balanced,
		// we can directly use the instance info to get the school details

		int port = service.getPort();		
		String host = service.getHostName();

		String url = "http://" + host + ":" + port + "/schools/" + schoolId;
		return restTemplate.getForObject(url, SchoolDto.class);
	}
}
