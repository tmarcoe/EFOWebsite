package com.efo.restController;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efo.entity.User;
import com.efo.service.UserService;

@RestController
@RequestMapping("/rest/")
public class GetInfoRestController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("getuserid")
	public String getUserId(@RequestParam(value = "username") String username) throws JSONException {
		JSONObject json = new JSONObject();
		
		User user = userService.retrieve(username);
		
		json.put("name", user.getUser_id());
		
		return json.toString();
	}

}
