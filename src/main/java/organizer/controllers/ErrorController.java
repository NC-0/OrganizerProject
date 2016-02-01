package organizer.controllers;

import com.google.common.base.Throwables;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

	@RequestMapping("error")
	public String performError(HttpServletRequest request){
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if(statusCode!=null)
			request.setAttribute("code",String.valueOf(statusCode));
		if(throwable!=null)
			request.setAttribute("message",Throwables.getRootCause(throwable).getMessage());
		String backUrl = requestUri;
		if(requestUri.equals("/error"))
			backUrl = "/";
		if(statusCode==404)
			backUrl = "/";
		request.setAttribute("url",backUrl);
		return "error";
	}
}

