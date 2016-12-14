package com.kiwiland.railway;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import thinkingreed.algorithms.graph.DirectedGraph;
import thinkingreed.algorithms.graph.Edge;
import thinkingreed.algorithms.graph.NoSuchRouteException;
import thinkingreed.algorithms.graph.Node;
import thinkingreed.algorithms.graph.RouteFormatException;

public class SmartRails {
	private DirectedGraph<String> diGraph;

	private static Set<String> cmds = new HashSet<String>();

	static {
		cmds.add("help");
		cmds.add("quit");

		cmds.add("dist");
		cmds.add("trips");
		cmds.add("lenosr");
		cmds.add("routes");
	}

	public SmartRails() {
		initGraph();
	}

	public void run() {

		System.out.println("SmartRails System started.");
		displayMenu();
		System.out.print(">>");
		Scanner reader = new Scanner(System.in);
		while (reader.hasNext()) {

			String input = reader.nextLine();
			Command command = new Command(input);
			String head = command.head();
			String options = command.options();
			if (cmds.contains(head)) {
				try {
					switch (head) {
					case "quit":
						System.out.println("app quit!");
						System.exit(0);
					case "help":
						displayMenu();
						break;
					case "dist":
						// System.out.println("calculate the distance for one
						// fixed route.");
						validateOptions(head, options);
						String route = options;
						if (routeExist(route)) {
							Integer dist = distOfOneRoute(route);
							System.out.println(">>the distance of route " + route + ":" + dist);
						} else {
							System.out.println("NO SUCH ROUTE");
						}

						break;
					case "trips":
						// System.out.println("trips info given src and dest and
						// condition.");
						validateOptions(head, options);
						String start = "";
						String end = "D";
						Integer maxStops = 3;
						Integer exacStop = 4;
						int condType = 1;// maxStop condition
						condType = 2;// exacStop condition

						if (condType == 1) {
							
							System.out.println("The number of trips starting at" + start + " and ending at" + end
									+ " with a maximum of " + maxStops + " stops");
						} else {
							System.out.println("The number of trips starting at" + start + " and ending at" + end
									+ " with a exactly " + exacStop + " stops");
						}

						break;
					case "lenosr":
						// System.out.println("length of the shortest route
						// given src and dest.");

						start = "C";
						end = "D";
						Integer length = 40;
						System.out.println("The length of the shortest route (in terms of distance to travel) "
								+ "from " + start + " to " + end + ":" + length);
						break;
					case "routes":
						// System.out.println("list all the routes given the src
						// and dest.");

						start = "C";
						end = "D";
						Integer maxDist = 30;
						Integer number = 30;
						System.out.println("The number of different routes " + "from " + start + " to " + end
								+ " with a distance " + "of less than " + maxDist + ":" + number);
						break;
					default:
						System.out.println("this command is not supported now.");
						break;
					}

				} catch (RouteFormatException e) {
					System.out.println("options '" + options + "', format is not valid!Reason:" + e.getMessage()
							+ "please try again!");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} else {
				System.out.println("'" + head + "' is not a valid command! Input 'help' to display the Menu.");
				System.out.print(">>");
				continue;//
			}
			System.out.print(">>");
		}
		System.out.println("END!");

	}

	/**
	 * smartRails system init environment data initing.
	 */
	private void initGraph() {

		// System.out.println("system initing...");
		diGraph = new DirectedGraph<String>();
		// init Nodes

		diGraph.addVertex("A");
		diGraph.addVertex("B");
		diGraph.addVertex("C");
		diGraph.addVertex("D");
		diGraph.addVertex("E");

		// init Edges

		diGraph.addEdge("A", "B", 5);
		diGraph.addEdge("B", "C", 4);
		diGraph.addEdge("C", "D", 8);
		diGraph.addEdge("D", "C", 8);
		diGraph.addEdge("D", "E", 6);
		diGraph.addEdge("A", "D", 5);
		diGraph.addEdge("C", "E", 2);
		diGraph.addEdge("E", "B", 3);
		diGraph.addEdge("A", "E", 7);
		System.out.println(diGraph);
	}

	public void displayGraph() {
		Map<String, Node<String>> adjList = diGraph.getAdjacencyList();

		for (String vertext : adjList.keySet()) {
			Node node = this.diGraph.getNode(vertext);
			List<Edge> edges = node.edges();
			for (Edge edge : edges) {
				System.out.println(edge.toString());
			}
		}
	}

	public void displayMenu() {
		System.out.println("commands you can input are as follows:");
		System.out.println("1.quit: to end the app.");
		System.out.println("2.help: to show the menu commands available.");
		System.out.println("3.dist <route>,where route is the route string like A-B-C.");
		System.out.println("4.trips <start> <end> -ms <maxStops>,where start is the start stop and end is the"
				+ "end stop.the result will output the number of the routes and list the routes.");
		System.out.println("5.trips <start> <end> -s <stops>,with exactly <stops> stops.");
		System.out.println("6.lenosr <start> <end>,the length of shortest route from start to end.");
		System.out.println("7.routes" + "s <start> <end> -md <minDist>: to show the number of different routes and"
				+ "list the routes.");

	}

	public Boolean routeExist(String route) {
		String[] vertexs = route.split("-");
		for (int i = 0; i < vertexs.length - 1; i++) {
			String start = vertexs[i];
			String end = vertexs[i + 1];
			if (!diGraph.containsEdge(start, end))
				return false;
		}

		return true;
	}

	public Integer distOfOneRoute(String route) throws Exception {
		if (routeExist(route)) {
			Integer dist = 0;
			String[] vertexs = route.split("-");

			for (int i = 0; i < vertexs.length - 1; i++) {
				dist = dist + diGraph.edgeWeightIfExist(vertexs[i], vertexs[i + 1]);
			}

			return dist;

		} else {
			throw new NoSuchRouteException("no such route." + route);
		}

	}

	public void checkRouteFormat(String route) {
		if (route.isEmpty()) {
			throw new RouteFormatException("route cannot be empty!");
		}
		String[] stops = route.split("-");

		if (route.lastIndexOf("-") == -1) {

			throw new RouteFormatException("route string format is invalid,at least one '-' is expected!");
		}
		if (stops.length < 2) {
			throw new RouteFormatException("count of stops is less than 2 but at least two stops is expected!");
		}

		/*
		 * for (String stop : stops) { System.out.println(stop); }
		 */

	}

	public void checkTripOptionsFormat(String options) {

	}

	public void checkLenosrOptionsFormat(String options) {

	}

	public void checkRoutesOptionsFormat(String options) {

	}

	public void validateOptions(String head, String options) {

		switch (head) {
		case "dist":
			checkRouteFormat(options);
			break;
		case "trips":
			checkTripOptionsFormat(options);
			break;
		case "lenosr":
			checkLenosrOptionsFormat(options);
			break;
		case "routes":
			checkRoutesOptionsFormat(options);
			break;
		default:
			break;
		}

	}
}
