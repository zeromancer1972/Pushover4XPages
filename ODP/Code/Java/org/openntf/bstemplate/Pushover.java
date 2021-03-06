package org.openntf.bstemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.openntf.domino.Document;
import org.openntf.domino.utils.XSPUtil;

import com.ibm.commons.util.io.json.JsonException;
import com.ibm.commons.util.io.json.JsonJavaFactory;
import com.ibm.commons.util.io.json.JsonObject;
import com.ibm.commons.util.io.json.JsonParser;

/**
 * Refer to https://pushover.net/api for more information
 * 
 * @author Oliver
 * 
 */

public class Pushover implements Serializable {

	private static final long serialVersionUID = 3300880974871816318L;
	private String userToken;
	private String appToken;
	private String message;
	private String url;
	private String url_title;
	private String device;
	private final String pushoverUrl = "https://api.pushover.net/1/messages.json";
	private PushoverResponse response;

	public Pushover() {

	}

	public Pushover(final String userToken, final String appToken, final String message,
			final String url) {
		this.userToken = userToken;
		this.appToken = appToken;
		this.message = message;
		this.url = url;

	}

	public void send() throws ClientProtocolException, IOException, IllegalStateException,
			JsonException {

		PushoverResponse res = new PushoverResponse();

		if (this.userToken.equals("") || this.appToken.equals("") || this.message.equals("")) {
			res.setMessage("");
			res
					.addError("Please check the fields \"User Token\", \"App Token\" and \"Message\" for valid values");
			res.setStatus(0);
			this.setResponse(res);
			return;
		}

		// create an HTTP POST request to the Pushover service URL

		// create a post request

		// set timeout
		RequestConfig config = RequestConfig.custom().setSocketTimeout(5000)
				.setConnectTimeout(5000).build();
		HttpClients.custom().setDefaultRequestConfig(config);
		CloseableHttpClient httpclient = HttpClients.custom().build();

		// where to POST?
		HttpPost post = new HttpPost(this.pushoverUrl);

		// content
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("user", this.userToken));
		nvps.add(new BasicNameValuePair("token", this.appToken));
		nvps.add(new BasicNameValuePair("message", this.message));
		if (this.url != null)
			nvps.add(new BasicNameValuePair("url", this.url));
		if (this.url_title != null)
			nvps.add(new BasicNameValuePair("url_title", this.url_title));
		if (this.device != null)
			nvps.add(new BasicNameValuePair("device", this.device));

		post.setEntity(new UrlEncodedFormEntity(nvps));

		// execute POST
		CloseableHttpResponse response = httpclient.execute(post);
		String responseText = "";
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity()
				.getContent(), "UTF-8"));
		String line;
		while ((line = rd.readLine()) != null) {
			responseText += line;
		}

		this.log(responseText);

		// parse JSON

		JsonJavaFactory factory = JsonJavaFactory.instanceEx;
		JsonObject json = (JsonObject) JsonParser.fromJson(factory, responseText);

		res.setMessage(json.getJsonProperty("request").toString());
		res.setStatus(Double.valueOf(json.getJsonProperty("status").toString()).intValue());
		if (res.getStatus() == 0) {
			Object arrErrors = factory.getProperty(json, "errors");
			for (Iterator<Object> itError = factory.iterateArrayValues(arrErrors); itError
					.hasNext();) {
				String errmsg = (String) itError.next();
				res.addError(errmsg);
			}
		}

		this.setResponse(res);

		response.close();
		httpclient.close();
	}

	private void log(final String message) {
		Document log = XSPUtil.getCurrentDatabase().createDocument();
		log.replaceItemValue("Form", "pushlog");
		log.replaceItemValue("$PublicAccess", "1");
		log.replaceItemValue("poMessage", message);
		if (this.device != null)
			log.replaceItemValue("poDevice", this.device);
		log.save();
	}

	public String getUserToken() {
		return userToken;
	}

	public String getAppToken() {
		return appToken;
	}

	public String getMessage() {
		return message;
	}

	public void setUserToken(final String userToken) {
		this.userToken = userToken;
	}

	public void setAppToken(final String appToken) {
		this.appToken = appToken;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlTitle() {
		return url_title;
	}

	public void setUrlTitle(final String url_title) {
		this.url_title = url_title;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(final String device) {
		this.device = device;
	}

	public PushoverResponse getResponse() {
		return response;
	}

	public void setResponse(final PushoverResponse response) {
		this.response = response;
	}

}
