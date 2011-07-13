package at.meikel.dmrl.webapp.rest;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.meikel.mgr.model.Player;
import at.meikel.mgr.server.Server;

@Controller
public class PlayerService {

	@Autowired
	Server server = null;

	@RequestMapping(value = { "/players" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Player> getAllPlayers() {
		List<Player> result = null;
		if (server != null) {
			result = server.getRankingList().getAllPlayers();
		}

		if (result == null) {
			result = new Vector<Player>();
		}

		Collections.sort(result, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				if (p1 == null) {
					return p2 == null ? 0 : 1;
				} else {
					if (p2 == null) {
						return -1;
					} else {
						return (int) Math.signum(p1.getRanglistenwert()
								- p2.getRanglistenwert());
					}
				}
			}
		});

		return result;
	}

	@RequestMapping(value = { "/playersByTeam/{teamName}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Player> getPlayersByTeam(@PathVariable String teamName) {
		List<Player> result = null;
		if (server != null) {
			result = server.getRankingList().getAllPlayers();
		}

		if (result == null) {
			result = new Vector<Player>();
		}

		Iterator<Player> iterator = result.iterator();
		while (iterator.hasNext()) {
			Player player = iterator.next();
			if (!player.getVerein().equals(teamName)) {
				iterator.remove();
			}
		}

		Collections.sort(result, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				if (p1 == null) {
					return p2 == null ? 0 : 1;
				} else {
					if (p2 == null) {
						return -1;
					} else {
						return (int) Math.signum(p1.getRanglistenwert()
								- p2.getRanglistenwert());
					}
				}
			}
		});

		return result;
	}

	@RequestMapping(value = { "/players/byLicenseId/{licenseId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Player getPlayerByLicenseId(@PathVariable String licenseId) {
		if (server != null) {
			Player result = server.getRankingList().findByLicenseId(licenseId);
			return result;
		}

		return null;
	}

}
