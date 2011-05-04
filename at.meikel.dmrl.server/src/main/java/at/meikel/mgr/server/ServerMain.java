package at.meikel.mgr.server;

public class ServerMain {

	public static void main(String[] args) {
		Server server = new Server();
		server.setBaseDir("./sample-server");
		server.retrieveData();
		server.reloadData();
	}

}
