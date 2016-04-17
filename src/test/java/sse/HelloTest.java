package sse;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class HelloTest {
	
	WebTarget target;
	
	@Before
	@Ignore
	public void setUp(){
		Client client = ClientBuilder.newClient();
		target = client.target("http://localhost:7001/sse");
	}
	
	@Test
	public void helloTest(){
		String response = target.path("api/hello").request().get(String.class);
		assertEquals("hello2", response);
	}

}
