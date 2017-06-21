package org.spring.ajax.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.spring.ajax.jsonview.Views;
import org.spring.ajax.model.AjaxResponseBody;
import org.spring.ajax.model.SearchCriteria;
import org.spring.ajax.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class AjaxController {

	List<User> users;
	
	@PostConstruct
	private void initDataForTesting(){
		users = new ArrayList<User>();
		User user1 = new User("username1", "password1", "email1", "phone1", "address1");
		User user2 = new User("username2", "password2", "email2", "phone2", "address2");
		User user3 = new User("username3", "password3", "email3", "phone3", "address3");
		users.add(user1);
		users.add(user2);
		users.add(user3);
	}
	
	private boolean isValidSearchCriteria(SearchCriteria search){
		boolean valid = true;
		if(search == null)
			valid = false;
		if(StringUtils.isEmpty(search.getUsername()) 
				&& StringUtils.isEmpty(search.getEmail()))
			valid = false;
		return valid;
	}
	
	private List<User> findByUsernameOrEmail(SearchCriteria criteria){
		List<User> result = new ArrayList<User>();
		for(User user : users){
			if( (!StringUtils.isEmpty(criteria.getUsername())) 
					&& (!StringUtils.isEmpty(criteria.getEmail())) ){
				if( (criteria.getUsername().equals(user.getUsername())) 
						&& (criteria.getEmail().equals(user.getEmail()))){
					result.add(user);
					continue;
				}else {
					continue;
				}
			}
			
			if( (!StringUtils.isEmpty(criteria.getUsername())) 
					&& (StringUtils.isEmpty(criteria.getEmail())) ){
				if(criteria.getUsername().equals(user.getUsername() )) {
					result.add(user);
					continue;
				}else {
					continue;
				}
			}
			
			if( (StringUtils.isEmpty(criteria.getUsername())) 
					&& (!StringUtils.isEmpty(criteria.getEmail())) ){
				if(criteria.getEmail().equals(user.getEmail())){
					result.add(user);
					continue;
				}else {
					continue;
				}
			}
		}
		return result;
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/search/api/getSearchResult")
	public AjaxResponseBody getSearchResultViaAJAX(@RequestBody SearchCriteria criteria){
		AjaxResponseBody reply = new AjaxResponseBody();
		System.out.println(criteria.getUsername() + " "+ criteria.getEmail() );
		if(isValidSearchCriteria(criteria)){
			List<User> result = findByUsernameOrEmail(criteria);
			if(result.size() > 0){
				reply.setMsg("OK");
				reply.setCode("200");
				reply.setResult(result);
			}else {
				reply.setMsg("No Users !");
				reply.setCode("204");
			}
		}else {
			reply.setMsg("Search Criteria is Empty !");
			reply.setCode("400");
		}
		return reply;
	}
}
