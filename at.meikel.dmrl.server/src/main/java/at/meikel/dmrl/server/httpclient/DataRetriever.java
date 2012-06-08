package at.meikel.dmrl.server.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import at.meikel.dmrl.server.server.IDataRetriever;

public class DataRetriever implements IDataRetriever {

    private HttpClient httpclient;
    private String currentName = null;
    private Date currentDate = null;

    private static final String URL = "http://www.minigolfsport.de/download/";
    private final static Logger LOGGER = Logger.getLogger(DataRetriever.class);

    public DataRetriever() {
        this(null, 0);
    }

    public DataRetriever(String proxyHostname, int proxyPort) {
        // See: http://hc.apache.org/httpcomponents-client/tutorial/html/

        httpclient = new DefaultHttpClient();

        if (proxyHostname != null) {
            HttpHost proxy = new HttpHost(proxyHostname, proxyPort);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                    proxy);
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
                    proxy);
        }
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public InputStream retrieveInputStream() {
        // See: http://hc.apache.org/httpcomponents-client/tutorial/html/

        assert currentName != null;

        InputStream result = null;

        // System.setProperty("proxySet", "true");
        // System.setProperty("http.proxyHost", "193.96.234.157");
        // System.setProperty("http.proxyPort", "81");

        HttpGet httpget = new HttpGet(URL + currentName);
        // HttpHost proxy = new HttpHost("193.96.234.157", 81);
        // httpget.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
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

    @Override
    public boolean findCurrentFile() {
        // See: http://code.google.com/p/selenium/wiki/GettingStarted

        currentName = null;
        currentDate = null;

        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
        // WebDriver driver = new HtmlUnitDriver();
        WebDriver driver = new HtmlUnitDriver();
        // ((HtmlUnitDriver) driver).setProxy("193.96.234.157", 81);
        LOGGER.debug("Driver.get ...");
        driver.get(URL);
        LOGGER.debug("Page source=" + driver.getPageSource());
        LOGGER.debug("Driver.find ...");
        String xpath = "/html/body/table/tbody/tr";
        List<WebElement> elements = driver.findElements(By
                .xpath(xpath));
        LOGGER.debug("Found " + elements.size() + " elements for xpath '" + xpath +
                "' on resource '" + URL + "'.");
        Pattern pattern = Pattern.compile("^rangliste(\\d+)\\.xls$");
        for (WebElement element : elements) {
            try {
                WebElement nameElement = element.findElements(
                        By.xpath("./td[2]/a")).get(0);
                WebElement dateElement = element.findElements(
                        By.xpath("./td[3]")).get(0);
                String name = nameElement.getAttribute("href");
                Matcher matcher = pattern.matcher(name);
                if (matcher.matches()) {
                    if (matcher.groupCount() == 1) {
                        try {
                            // int n = Integer.parseInt(matcher.group(1));
                            String dateAsString = dateElement.getText();
                            Date date = df.parse(dateAsString);
                            if ((currentDate == null)
                                    || (currentDate.before(date))) {
                                if (LOGGER.isDebugEnabled()) {
                                    LOGGER
                                            .debug("Found a younger entry. Replacing '"
                                                    + currentName
                                                    + "' ("
                                                    + currentDate
                                                    + ") by '"
                                                    + name
                                                    + "' ("
                                                    + date
                                                    + ").");
                                }

                                currentDate = date;
                                currentName = name;
                            }
                        } catch (NumberFormatException e) {
                            LOGGER.debug("Ignoring '" + name
                                    + "' due to NumberFormatException.");
                        } catch (ParseException e) {
                            LOGGER.debug("Ignoring '" + name
                                    + "' due to ParseException.");
                        }
                    } else {
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("Ignoring '" + name
                                    + "' because group count doesn't match.");
                        }
                    }
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Ignoring '" + name
                                + "' because regexp doesn't match.");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                // ignore
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Found file '" + currentName + "' (" + currentDate
                    + ")");
        }
        return currentName != null;
    }

    @Override
    public String getCurrentFileName() {
        return currentName;
    }

    @Override
    public Date getCurrentFileDate() {
        return currentDate;
    }
}
