package at.meikel.mgr.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import at.meikel.mgr.server.IDataRetriever;

public class LocalFileDataRetriever implements IDataRetriever {

  private File file;
  private String path = "C:/michael.becker/_Privat/at.meikel.dmrl.root/at.meikel.dmrl.server/src/main/resources";

  public LocalFileDataRetriever(String filename) {
    file = new File(path, filename);
  }

  @Override
  public String getUrl() {
    return path;
  }

  @Override
  public InputStream retrieveInputStream() {
    InputStream result = null;

    try {
      result = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return result;
  }

  @Override
  public boolean findCurrentFile() {
    return true;
  }

  @Override
  public String getCurrentFileName() {
    return file.getAbsolutePath();
  }

  @Override
  public Date getCurrentFileDate() {
    return new Date(file.lastModified());
  }

}
