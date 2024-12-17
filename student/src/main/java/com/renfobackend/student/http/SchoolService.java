package com.renfobackend.student.http;

import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.renfobackend.student.dtos.SchoolDto;
import com.netflix.discovery.EurekaClient;



public class SchoolService {
	final private RestTemplate restTemplate = new RestTemplate();

	public SchoolDto getSchoolDetails(Long schoolId, EurekaClient discoveryClient) {
		InstanceInfo service = discoveryClient
      .getApplication("school")
      .getInstances()
			.get(0);
			
		int port = service.getPort();
		String host = service.getHostName();

		String url = "http://" + host + ":" + port + "/schools/" + schoolId;
		return restTemplate.getForObject(url, SchoolDto.class);
	}
}
