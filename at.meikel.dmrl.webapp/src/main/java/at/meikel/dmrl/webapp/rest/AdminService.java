package at.meikel.dmrl.webapp.rest;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.meikel.mgr.server.Server;

@Controller
public class AdminService {

	private final static String ADMIN_PREFIX = "/admin";

	@Autowired
	Server server = null;

	@RequestMapping(value = ADMIN_PREFIX + "/getCurrentDataFileName", method = RequestMethod.GET)
	@ResponseBody
	public String getCurrentDataFileName() {
		String result = null;

		if (server != null) {
			File currentDataFile = server.getCurrentDataFile();
			if (currentDataFile != null) {
				result = currentDataFile.getAbsolutePath();
			}
		}

		return result;
	}

	@RequestMapping(value = ADMIN_PREFIX + "/listAllDataFileNames", method = RequestMethod.GET)
	@ResponseBody
	public List<String> listAllDataFileNames() {
		Vector<String> result = new Vector<String>();
		if (server != null) {
			Collection<File> allDataFiles = server.listAllDataFiles();
			for (File file : allDataFiles) {
				result.add(file.getAbsolutePath());
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
