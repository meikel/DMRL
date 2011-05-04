package at.meikel.mgr.model;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class Rangliste {

	private Hashtable<Integer, Player> map = new Hashtable<Integer, Player>();

	public void addSpieler(int platz, Player spieler) {
		map.put(new Integer(platz), spieler);
	}

	public List<Player> find(String verein) {
		Vector<Player> result = new Vector<Player>();
		Vector<Integer> keys = new Vector<Integer>(map.keySet());
		Collections.sort(keys);
		for (Integer platz : keys) {
			Player spieler = map.get(platz);
			if ((verein == null) || (verein.equals(spieler.getVerein()))) {
				result.add(spieler);
			}
		}
		return result;
	}
}
