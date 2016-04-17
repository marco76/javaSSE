package ch.javaee.example.sse.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
public class HelloWorld {

	@GET
	public String getHello(){
		return "hello2";
	}
}
