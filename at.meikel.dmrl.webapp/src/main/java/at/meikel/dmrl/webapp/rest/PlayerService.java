package at.meikel.dmrl.webapp.rest;

import java.util.List;
import java.util.Vector;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import at.meikel.mgr.model.Player;

@Controller
public class PlayerService {

	@RequestMapping(value = "/players", method = RequestMethod.GET)
	@ResponseBody
	public List<Player> getAllPlayers() {
		Vector<Player> result = new Vector<Player>();

		result.add(new Player("4711", "Osterhase", "H", "SG Weiterstadt",
				"HBSV", "10.123"));
		result.add(new Player("47110815", "Weihnachtsmann", "SM",
				"SG Weiterstadt", "HBSV", "1.234"));

		return result;
	}

	@RequestMapping(value = "/players/byLicenseId/{licenseId}", method = RequestMethod.GET)
	@ResponseBody
	public Player getPlayerByLicenseId(@PathVariable Long licenseId) {
		Player result = new Player(Long.toString(licenseId), "Osterhase", "H",
				"SG Weiterstadt", "HBSV", "10.123");
		return result;
	}

}
