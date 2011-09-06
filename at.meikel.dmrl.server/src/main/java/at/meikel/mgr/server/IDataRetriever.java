package at.meikel.mgr.server;

import java.io.InputStream;
import java.util.Date;

public interface IDataRetriever {

  public String getUrl();

  public InputStream retrieveInputStream();

  public boolean findCurrentFile();

  public String getCurrentFileName();

  public Date getCurrentFileDate();

}
