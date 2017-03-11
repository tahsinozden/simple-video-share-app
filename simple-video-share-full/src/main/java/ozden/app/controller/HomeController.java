package ozden.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "http://localhost:8081")
public class HomeController {
//	@RequestMapping("/")
	public String welcome(){
		return "redirect:index.html";
	}
}
