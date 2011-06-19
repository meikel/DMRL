package at.meikel.mgr.httpclient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public class DataRetriever {

	// see: http://hc.apache.org/httpcomponents-client/tutorial/html/

	private HttpClient httpclient;

	public DataRetriever() {
		this(null, 0);
	}

	public DataRetriever(String proxyHostname, int proxyPort) {
		httpclient = new DefaultHttpClient();

		if (proxyHostname != null) {
			HttpHost proxy = new HttpHost(proxyHostname, proxyPort);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
	}

	public void retrieveFile(String url, File file) {
		InputStream is = retrieveInputStream(url, file);
		if (is != null) {
			try {
				saveFile2(is, file);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
	}

	public InputStream retrieveInputStream(String url, File file) {
		InputStream result = null;

		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = entity.getContent();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	private void saveFile(InputStream instream, File outputfile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outputfile);
			int totalNumberOfBytesRead;
			byte[] tmp = new byte[2048];
			do {
				totalNumberOfBytesRead = instream.read(tmp);
				if (totalNumberOfBytesRead != -1) {
					fos.write(tmp);
				}
			} while (totalNumberOfBytesRead != -1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void saveFile2(InputStream instream, File outputfile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outputfile);
			int totalNumberOfBytesRead;
			int value;
			do {
				value = instream.read();
				if (value != -1) {
					fos.write(value);
				}
			} while (value != -1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
