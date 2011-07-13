package at.meikel.mgr.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;

import at.meikel.mgr.httpclient.DataRetriever;
import at.meikel.mgr.model.Player;
import at.meikel.mgr.model.Rangliste;
import at.meikel.mgr.persistence.ExcelSheet;
import at.meikel.mgr.persistence.ExcelSheet.STATE;
import at.meikel.mgr.xlsreader.Row;
import at.meikel.mgr.xlsreader.Table;

public class Server {

	private EntityManagerFactory emf;
	private EntityManager em;

	private ExcelSheet currentData;
	private Rangliste rangliste;

	private static final Logger LOGGER = Logger.getLogger(Server.class);
	private static final String URL = "http://www.minigolfsport.de/download/rangliste22.xls";
	private static final String SHEET_NAME = "DRL";

	// private static final String PROXY_HOSTNAME = "iproxy";
	// private static final int PROXY_PORT = 8088;

	public Server(String persistenceUnit) {
		rangliste = new Rangliste();
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
		retrieveData();
		reloadData();
	}

	public ExcelSheet getCurrentData() {
		return currentData;
	}

	public Rangliste getRankingList() {
		return rangliste;
	}

	public void retrieveData() {
		DataRetriever dataRetriever = new DataRetriever(
		// PROXY_HOSTNAME,
		// PROXY_PORT
		);
		// dataRetriever.retrieveFile(URL, new File(dataDir, DATA_FILE_FORMAT
		// .format(new Date())));

		ExcelSheet sheet = new ExcelSheet();
		sheet.setTimestamp(new Date());
		sheet.setUrl(URL);
		sheet.setState(STATE.OK);

		InputStream is = dataRetriever.retrieveInputStream(URL);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int b;
			do {
				b = is.read();
				if (b >= 0) {
					baos.write(b);
				}
			} while (b >= 0);
			sheet.setData(baos.toByteArray());
			LOGGER.info("Retrieved sheet Id='" + sheet.getId() + "', URL='"
					+ sheet.getUrl() + "', Size='" + sheet.getData().length
					+ "'.");
		} catch (IOException e) {
			sheet.setState(STATE.CORRUPTED);
			LOGGER.error(e);
		}

		em.getTransaction().begin();
		em.persist(sheet);
		em.getTransaction().commit();
	}

	public void reloadData() {
		List<ExcelSheet> allData = listAllData();
		Collections.sort(allData, new Comparator<ExcelSheet>() {
			@Override
			public int compare(ExcelSheet sheet1, ExcelSheet sheet2) {
				// TODO: use some utility classes / libraries
				if (sheet1 == null) {
					if (sheet2 == null) {
						return 0;
					}
					return -1; // null is smaller than every non-null value
				} else {
					if (sheet2 == null) {
						return 1; // null is smaller than every non-null value
					}
					Date ts1 = sheet1.getTimestamp();
					Date ts2 = sheet2.getTimestamp();
					if (ts1 == null) {
						if (ts2 == null) {
							return 0;
						}
						return -1; // null is smaller than every non-null value
					} else {
						if (ts2 == null) {
							return 1; // null is smaller than every non-null
							// value
						}
						return (int) Math.abs(ts1.getTime() - ts2.getTime());
					}
				}
			}
		});

		for (ExcelSheet sheet : allData) {
			if (sheet.getState() == STATE.OK) {
				LOGGER.info("Processing sheet Id='" + sheet.getId()
						+ "', URL='" + sheet.getUrl() + "', Size='"
						+ sheet.getData().length + "'.");
				ByteArrayInputStream bais = new ByteArrayInputStream(sheet
						.getData());
				Table table = Table.read(bais, SHEET_NAME);
				if (table != null) {
					rangliste = new Rangliste();
					for (int i = table.getMinRowIndex(); i < table
							.getMaxRowIndex(); i++) {
						Row row = table.getRow(i);
						int platz = (int) Double.parseDouble(row
								.getColumnValue(1).toString());
						String passnummer = row.getColumnValue(2).toString();
						try {
							passnummer = Long.toString((long) Double
									.parseDouble(passnummer));
						} catch (Exception e) {
							// ignore
						}

						try {
							Player spieler = new Player(platz, passnummer, row
									.getColumnValue(3).toString(), row
									.getColumnValue(4).toString(), row
									.getColumnValue(5).toString(), row
									.getColumnValue(6).toString(), row
									.getColumnValue(7).toString());
							rangliste.addSpieler(platz, spieler);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					currentData = sheet;
					LOGGER.info("Successfully loaded data from sheet id ='"
							+ sheet.getId() + "'.");
					break;
				}
				LOGGER.warn("Found corrupted excel sheet with id '"
						+ sheet.getId() + "'. Setting state to CORRUPTED.");
				sheet.setState(STATE.CORRUPTED);
				em.getTransaction().begin();
				em.persist(sheet);
				em.getTransaction().commit();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<ExcelSheet> listAllData() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// Query query = em.createNativeQuery("select * from ExcelSheet");
		Query query = em.createQuery("select s from ExcelSheet as s");
		final List<ExcelSheet> result = query.getResultList();
		tx.commit();
		return result;
	}

}
