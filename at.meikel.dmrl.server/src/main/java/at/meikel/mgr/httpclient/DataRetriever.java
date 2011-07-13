package at.meikel.mgr.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

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

	public InputStream retrieveInputStream(String url) {
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

	public void doSomething() {
		WebDriver driver = new HtmlUnitDriver();
		driver.get("http://www.minigolfsport.de/download/");
		List<WebElement> elements = driver.findElements(By
				.xpath("//table[1]/tbody/tr"));
		System.out.println("size=" + elements.size());
		for (WebElement element : elements) {
			// System.out.println(element);
			System.out.println(element.findElement(By.xpath("child::td[2]/a")).getText());
		}
	}

}
