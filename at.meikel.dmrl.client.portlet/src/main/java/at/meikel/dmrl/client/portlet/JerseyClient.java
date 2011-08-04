package at.meikel.dmrl.client.portlet;

import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class JerseyClient {

	// See:
	// http://jersey.java.net/nonav/documentation/latest/user-guide.html
	// http://blogs.oracle.com/enterprisetechtips/entry/consuming_restful_web_services_with

	public static void main(String[] args) {
		// /players/byLicenseId/37225
		// /playersByTeam/SG%20Weiterstadt%201886
		// /players

		String[] services = new String[] { "/players/byLicenseId/37225",
				"/playersByTeam/SG%20Weiterstadt%201886"/* , "/players" */};

		Client c = Client.create();
		WebResource r = c.resource("http://dmrl.meikel.cloudbees.net:80/rest");

		for (String path : services) {
			String response = r.path(path).accept(
					MediaType.APPLICATION_JSON_TYPE).get(String.class);
			System.out
					.println("==================================================");
			System.out.println(path);
			System.out
					.println("==================================================");
			System.out.println(response);
			try {
				JSONObject json = JSONObject.fromObject(response);
				System.out.println(json);
			} catch (Exception e1) {
				try {
					JSONArray json = JSONArray.fromObject(response);
					System.out.println(json);
				} catch (Exception e2) {
					// ignore
				}
			}

			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}

		// String path = services[0];
		// String response =
		// r.path(path).accept(MediaType.APPLICATION_JSON_TYPE)
		// .get(String.class);
		// JSONObject json = JSONObject.fromObject(response);
		// System.out.println(json);
		// System.out.println(json.get("vorname"));
	}
}
