package at.meikel.mgr.server;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import at.meikel.mgr.httpclient.DataRetriever;
import at.meikel.mgr.model.Player;
import at.meikel.mgr.model.Rangliste;
import at.meikel.mgr.persistence.ExcelSheet;

public class ServerMain {

	private final static Logger LOGGER = Logger.getLogger(ServerMain.class);

	public static void main(String[] args) {
		DOMConfigurator.configure(new File("./sample-server/config",
				"log4j.xml").getAbsolutePath());
		
    // DataRetriever dr = new DataRetriever();
    // dr.doSomething();
		// System.exit(0);
		
		Server server = new Server("pu.hsqldb.mem");
		LOGGER.info("Retrieve data");
		server.retrieveData();
		LOGGER.info("List all excel sheets available");
		List<ExcelSheet> allData = server.listAllData();
		if ((allData == null) || (allData.isEmpty())) {
			LOGGER.warn("No data available.");
		} else {
			for (ExcelSheet sheet : allData) {
				LOGGER.info(sheet.getId() + ", " + sheet.getUrl() + ", "
						+ sheet.getTimestamp());
			}
		}
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
		for (int i = 1; i <= 40; i++) {
			LOGGER.info("Waiting loop #" + i);
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

}
