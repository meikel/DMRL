package at.meikel.dmrl.server.model;

import java.io.Serializable;

public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	private int position;
	private String passnummer;
	private String vorname;
	private String nachname;
	private String kategorie;
	private String verein;
	private String landesverband;
	private double ranglistenwert;

	public Player(int position, String passnummer, String name,
			String kategorie, String verein, String landesverband,
			String ranglistenwert) {
		this.position = position;
		this.passnummer = passnummer;
		if (name.contains(",")) {
			this.vorname = name.substring(name.indexOf(", ") + 2);
			this.nachname = name.substring(0, name.indexOf(", "));
		} else {
			this.vorname = "";
			this.nachname = name;
		}
		this.kategorie = kategorie;
		this.verein = verein;
		this.landesverband = landesverband;
		this.ranglistenwert = Double.parseDouble(ranglistenwert);
	}

	public int getPosition() {
		return position;
	}

	public String getPassnummer() {
		return passnummer;
	}

	public String getVorname() {
		return vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public String getKategorie() {
		return kategorie;
	}

	public String getVerein() {
		return verein;
	}

	public String getLandesverband() {
		return landesverband;
	}

	public double getRanglistenwert() {
		return ranglistenwert;
	}

	@Override
	public String toString() {
		return "[Spieler (" + getPosition() + ";" + getPassnummer() + ";"
				+ getVorname() + ";" + getNachname() + ";" + getKategorie()
				+ ";" + getVerein() + ";" + getLandesverband() + ";"
				+ getRanglistenwert() + ")]";
	}
}
