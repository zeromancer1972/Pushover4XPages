package org.openntf.bstemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

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
import com.ibm.xsp.extlib.util.ExtLibUtil;

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

	public Pushover() {

	}

	public Pushover(final String userToken, final String appToken, final String message,
			final String url) {
		this.userToken = userToken;
		this.appToken = appToken;
		this.message = message;
		this.url = url;

	}

	@SuppressWarnings("unchecked")
	public void send() throws ClientProtocolException, IOException, JsonException,
			IllegalStateException {
		if (this.userToken.equals("") || this.appToken.equals("") || this.message.equals("")) {

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
		Map viewScope = (Map) ExtLibUtil.resolveVariable(FacesContext.getCurrentInstance(), "viewScope");
		viewScope.put("response", responseText);

		response.close();
		httpclient.close();
	}
	
	private void log(final String message){
		Document log = XSPUtil.getCurrentDatabase().createDocument();
		log.replaceItemValue("Form", "pushlog");
		log.replaceItemValue("$PublicAccess", "1");
		log.replaceItemValue("poMessage", message);
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

	public String getUrl_title() {
		return url_title;
	}

	public void setUrl_title(final String url_title) {
		this.url_title = url_title;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(final String device) {
		this.device = device;
	}

}
