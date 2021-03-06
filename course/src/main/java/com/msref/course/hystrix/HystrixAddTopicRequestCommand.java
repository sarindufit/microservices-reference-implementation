package com.msref.course.hystrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.msref.course.model.Topic;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixAddTopicRequestCommand extends HystrixCommand<List<Topic>>{
	
	RestTemplate restTemplate;
	
	List<Topic> topics;
	
	Integer courseId;

	public HystrixAddTopicRequestCommand(List<Topic> topics, Integer courseId, RestTemplate restTemplate) {
		super(HystrixCommandGroupKey.Factory.asKey("TopicServiceGroup"));
		this.topics = topics;
		this.courseId = courseId;
		this.restTemplate = restTemplate;
	}

	@Override
	protected List<Topic> run() throws Exception {
		ResponseEntity<Topic[]> response = restTemplate.postForEntity("http://topic-service/api/courses/" + courseId +"/topics", topics, Topic[].class);
		return Arrays.asList(response.getBody());
	}
	
	@Override
	protected List<Topic> getFallback() {
		List<Topic> emptyTopicList = new ArrayList<>(); 
		return emptyTopicList;
	}	

}
