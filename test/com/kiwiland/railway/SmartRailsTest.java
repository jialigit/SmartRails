package com.kiwiland.railway;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SmartRailsTest {

	private SmartRails sr;

	@Before
	public void setUp() throws Exception {
		sr = new SmartRails();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	@Test
	public void testDisplayGraph() {
		sr.displayGraph();
	}

	@Test
	public void testDisplayMenu() {
		sr.displayMenu();
	}

	@Test(expected = RouteFormatException.class)
	public void testCheckRouteFormatWithException() {
		String route = "aaa";
		sr.checkRouteFormat(route);
	}

	@Test
	public void testCheckRouteFormat() {
		String route = "A-B-C";
		sr.checkRouteFormat(route);
	}

	@Test
	public void testDistOfOneRoute() throws Exception {
		String route1 = "A-B-C";
		Integer dist1 = sr.distOfOneRoute(route1);
		assertThat(dist1, equalTo(9));

		String route2 = "A-D";
		Integer dist2 = sr.distOfOneRoute(route2);
		assertThat(dist2, equalTo(5));

		String route3 = "A-D-C";
		Integer dist3 = sr.distOfOneRoute(route3);
		assertThat(dist3, equalTo(13));

		String route4 = "A-E-B-C-D";
		Integer dist4 = sr.distOfOneRoute(route4);
		assertThat(dist4, equalTo(22));

	}

	@Test
	public void testRouteExist(){
		String route5 = "A-E-B-C-D";
		assertThat(sr.routeExist(route5),equalTo(true));
		
		String route6 = "A-E-D";
		assertThat(sr.routeExist(route6),equalTo(false));
	}
	
	@Test(expected=NoSuchRouteException.class)
	public void testNoSuchRoute() throws Exception{

		String route5 = "A-E-D";
		 
		Integer dist5 = sr.distOfOneRoute(route5);
		 
		System.out.println(dist5); 
	}

	@Test
	public void testCommandStringParse() {
		String command = " dist A     B";
		command = command.trim();
		int i = command.indexOf(" ");
		String first = command.substring(0, i);
		String last = command.substring(i);
		last = last.trim();
		System.out.println(first);

		System.out.println(last);

	}
}
