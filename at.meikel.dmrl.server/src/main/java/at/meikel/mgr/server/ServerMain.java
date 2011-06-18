package at.meikel.mgr.server;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import at.meikel.mgr.model.Player;
import at.meikel.mgr.model.Rangliste;

public class ServerMain {

	private final static Logger LOGGER = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) {
		Server server = new Server();
		server.setBaseDir("./sample-server");
		LOGGER.info("List all available data files");
		Collection<File> allDataFiles = server.listAllDataFiles();
		if (allDataFiles.isEmpty()) {
			LOGGER.warn("No data file available.");
		} else {
			for (File file : allDataFiles) {
				LOGGER.info(file.getAbsolutePath());
			}
		}
		LOGGER.info("Retrieve data");
		server.retrieveData();
		LOGGER.info("Load data");
		server.reloadData();
		LOGGER.info("Retrieve ranking list");
		Rangliste rankingList = server.getRankingList();
		LOGGER.info("Size = " + rankingList.size());
		String team = "SG Weiterstadt 1886";
		LOGGER.info("List all players from " + team);
		List<Player> sgw = rankingList.find(team);
		for (Player s : sgw) {
			LOGGER.info(s);
		}
	}

}
