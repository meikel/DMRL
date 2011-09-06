package at.meikel.mgr.model;

import java.util.List;
import java.util.Vector;

public class Rangliste {

  private Vector<Player> eintraege = new Vector<Player>();

  public void addSpieler(Player spieler) {
    eintraege.add(spieler);
  }

  public int size() {
    return eintraege.size();
  }

  public List<Player> getAllPlayers() {
    return new Vector<Player>(eintraege);
  }

  public List<Player> find(String verein) {
    if (verein != null) {
      verein = verein.toLowerCase();
    }
    Vector<Player> result = new Vector<Player>();
    for (Player spieler : eintraege) {
      if ((verein == null) || (spieler.getVerein() == null) || (spieler.getVerein().toLowerCase().contains(verein))) {
        result.add(spieler);
      }
    }
    return result;
  }

  public Player findByLicenseId(String licenseId) {
    for (Player player : eintraege) {
      if (player.getPassnummer().equals(licenseId)) {
        return player;
      }
    }
    return null;
  }
}
