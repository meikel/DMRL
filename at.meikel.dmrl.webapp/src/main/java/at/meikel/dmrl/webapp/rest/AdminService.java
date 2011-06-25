package at.meikel.dmrl.webapp.rest;

import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.meikel.mgr.persistence.ExcelSheet;
import at.meikel.mgr.server.Server;

@Controller
public class AdminService {

	private final static String ADMIN_PREFIX = "/admin";

	@Autowired
	Server server = null;

	@RequestMapping(value = ADMIN_PREFIX + "/getCurrentData", method = RequestMethod.GET)
	@ResponseBody
	public String getCurrentDataFileName() {
		String result = null;

		if (server != null) {
			ExcelSheet sheet = server.getCurrentData();
			if (sheet != null) {
				result = "Id='" + sheet.getId() + "', URL='" + sheet.getUrl()
						+ "', State='" + sheet.getState() + "'";
			}
		}

		return result;
	}

	@RequestMapping(value = ADMIN_PREFIX + "/listAllData", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listAllDataFileNames() {
		Vector<String> result = new Vector<String>();
		if (server != null) {
			List<ExcelSheet> allData = server.listAllData();
			for (ExcelSheet sheet : allData) {
				result.add("Id='" + sheet.getId() + "', URL='" + sheet.getUrl()
						+ "', State='" + sheet.getState() + "'");
			}
		}
		return result;
	}

	@RequestMapping(value = ADMIN_PREFIX + "/retrieveData", method = RequestMethod.GET)
	@ResponseBody
	public String retrieveData() {
		StringBuilder result = new StringBuilder();
		if (server == null) {
			result.append("ERROR: server == null.");
		} else {
			long start = System.currentTimeMillis();
			server.retrieveData();
			long end = System.currentTimeMillis();
			long diff = end - start;
			result.append("OK: execution time = " + diff + "ms.");
		}
		result.append(server == null ? "null" : server.toString());
		return result.toString();
	}

	@RequestMapping(value = ADMIN_PREFIX + "/reloadData", method = RequestMethod.GET)
	@ResponseBody
	public String reloadData() {
		StringBuilder result = new StringBuilder();
		if (server == null) {
			result.append("ERROR: server == null.");
		} else {
			long start = System.currentTimeMillis();
			server.reloadData();
			long end = System.currentTimeMillis();
			long diff = end - start;
			result.append("OK: execution time = " + diff + "ms.");
		}
		return result.toString();
	}

}
