package es.tsb.ltba.nomhad.httpclient;

/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Implementation of the Apache HttpComponents httpClient.
 * 
 */
public class NomhadHttpClient {

	private DefaultHttpClient httpClient;

	/**
	 * Creates a HttpClient without authentication
	 */
	public NomhadHttpClient() {
		httpClient = new DefaultHttpClient();
		httpClient = wrapClient(httpClient);
	}

	/**
	 * Creates a HttpClient with authentication
	 * 
	 * @param usr
	 *            user
	 * @param pwd
	 *            password
	 */
	public NomhadHttpClient(String usr, String pwd) {
		this();
		this.setCredentials(usr, pwd);
	}

	/**
	 * Set the credentials, if the client was created without them.
	 * 
	 * @param usr
	 *            user
	 * @param pwd
	 *            password
	 */
	public void setCredentials(String usr, String pwd) {
		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials(usr, pwd));
	}

	/**
	 * Http POST request.
	 * 
	 * @param uri
	 *            URI
	 * @param body
	 *            body
	 * @return response from server
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String post(String uri, String body) throws ClientProtocolException,
			IOException {
		HttpPost post = new HttpPost(uri);
		if (body != null) {
			post.setEntity(new StringEntity(body));
		}
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		return httpClient.execute(post, responseHandler);
	}

	/**
	 * stop the http client
	 */
	public void stopClient() {
		httpClient.getConnectionManager().shutdown();
	}

	/**
	 * Authentication
	 * 
	 * @param base
	 *            the client to be configured
	 * @return the authentication-enabled client
	 */
	private static DefaultHttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
