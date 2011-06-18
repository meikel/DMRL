package at.meikel.mgr.server;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import at.meikel.mgr.httpclient.DataRetriever;
import at.meikel.mgr.model.Player;
import at.meikel.mgr.model.Rangliste;
import at.meikel.mgr.xlsreader.Row;
import at.meikel.mgr.xlsreader.Table;

public class Server {

	private File baseDir;
	private File dataDir;
	private File corruptedDataDir;
	private File configDir;
	private Rangliste rangliste;

	private static final Logger LOGGER = Logger.getLogger(Server.class);
	private static final SimpleDateFormat DATA_FILE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd-HHmmss-'data.xls'");
	// private static final String URL =
	// "http://vcrossley.com/sporto/sample.xls";
	private static final String URL = "http://www.minigolfsport.de/download/rangliste23.xls";
	private static final String SHEET_NAME = "DRL";

	// private static final String PROXY_HOSTNAME = "iproxy";
	// private static final int PROXY_PORT = 8088;

	public Server() {
		rangliste = new Rangliste();
	}

	public Rangliste getRankingList() {
		return rangliste;
	}

	public void setBaseDir(String pathname) {
		File dir;

		dir = new File(pathname);
		checkDir(dir);
		baseDir = dir;

		dir = new File(baseDir, "data");
		checkDir(dir);
		dataDir = dir;

		dir = new File(baseDir, "config");
		checkDir(dir);
		configDir = dir;

		File log4jConfig = new File(configDir, "log4j.xml");
		if ((log4jConfig.exists()) && (log4jConfig.isFile())
				&& (log4jConfig.canRead())) {
			DOMConfigurator.configureAndWatch(log4jConfig.getPath(), 60 * 1000);
		} else {
			BasicConfigurator.configure();
			LOGGER
					.info("Log4j config file '"
							+ log4jConfig.getPath()
							+ "' doesn't exist (or isn't a file or hasn't read permission). Using basic log4j configuration.");
		}
	}

	public void retrieveData() {
		DataRetriever dataRetriever = new DataRetriever(
		// PROXY_HOSTNAME,
		// PROXY_PORT
		);
		dataRetriever.retrieveFile(URL, new File(dataDir, DATA_FILE_FORMAT
				.format(new Date())));
	}

	public void reloadData() {
		Collection<File> allDataFiles = listAllDataFiles();

		Table table = null;
		while ((table == null) && (!allDataFiles.isEmpty())) {
			File latestDataFile = Collections.max(allDataFiles);
			LOGGER.info("Detected '" + latestDataFile.getPath()
					+ "' as latest data file. Reloading data.");
			table = Table.read(latestDataFile.getPath(), SHEET_NAME);
			if (table == null) {
				allDataFiles.remove(latestDataFile);
				File dir = new File(dataDir, "corrupted");
				checkDir(dir);
				corruptedDataDir = dir;

				File newFile = new File(corruptedDataDir, "corrupt-"
						+ latestDataFile.getName());
				LOGGER.warn("Found corrupted data file '" + latestDataFile
						+ "'. Moving to '" + newFile.getPath() + ".");
				latestDataFile.renameTo(newFile);
			} else {
				rangliste = new Rangliste();
				for (int i = table.getMinRowIndex(); i < table.getMaxRowIndex(); i++) {
					Row row = table.getRow(i);
					int platz = (int) Double.parseDouble(row.getColumnValue(1)
							.toString());
					try {
						Player spieler = new Player(row.getColumnValue(2)
								.toString(), row.getColumnValue(3).toString(),
								row.getColumnValue(4).toString(), row
										.getColumnValue(5).toString(), row
										.getColumnValue(6).toString(), row
										.getColumnValue(7).toString());
						rangliste.addSpieler(platz, spieler);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Collection<File> listAllDataFiles() {
		return listAllFiles(true);
	}

	public Collection<File> listAllInvalidFiles() {
		return listAllFiles(false);
	}

	private Collection<File> listAllFiles(final boolean valid) {
		IOFileFilter fileFilter = new AbstractFileFilter() {
			@Override
			public boolean accept(File file) {
				try {
					DATA_FILE_FORMAT.parse(file.getName());
					return valid;
				} catch (ParseException e) {
					return !valid;
				}
			}
		};
		@SuppressWarnings("unchecked")
		Collection<File> files = FileUtils.listFiles(dataDir, fileFilter, null);

		if (files == null) {
			files = new Vector<File>();
		}

		return files;
	}

	private void checkDir(File dir) {
		if (!dir.exists()) {
			if (dir.mkdir()) {
				LOGGER.info("Base directory didn't exist. Created '"
						+ dir.getPath() + "'.");
			} else {
				String msg = "Base directory '" + dir.getPath()
						+ "' doesn't exist. Unable to create it.";
				LOGGER.warn(msg);
				throw new RuntimeException(msg);
			}
		}

		if (!dir.isDirectory()) {
			String msg = "File '" + dir.getPath()
					+ "' exists but is not a directory.";
			LOGGER.warn(msg);
			throw new RuntimeException(msg);
		}

		if (!dir.canWrite()) {
			String msg = "No permission to write to directory '"
					+ dir.getPath() + "'.";
			LOGGER.warn(msg);
			throw new RuntimeException(msg);
		}
	}

}
