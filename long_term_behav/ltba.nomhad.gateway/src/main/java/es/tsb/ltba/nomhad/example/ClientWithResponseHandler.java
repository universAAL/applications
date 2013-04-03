package es.tsb.ltba.nomhad.example;

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

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated
 * resources.
 */
public class ClientWithResponseHandler {

	private static final String NOMHAD_URL_HEADER = "https://192.168.238.40:8443/nomhad/rest/2/cmr/patient/";
	private static final String OBSERVATIONS_REQUEST = "/observations";
	private static final String BODY_TEST = "{" +

	"\"mon1\": {" + "\"indicatorsGroup\": \"HEALTH_INDICATORS_GROUP_WEEK\","
			+ "  \"startDate\": \"20130127T121523+0100\"," + "\"devices\": ["
			+ "{" + "           \"id\": \"35-209900-176148-1\","
			+ "           \"specs\": [" + " \"MDC_DEV_SPEC_PROFILE_HYDRA\""
			+ "           ]," + "           \"observations\": ["
			+ "               {" + "                   \"type\": \"NO\","
			+ "                   \"indicator\": \"AAL_HEALTH_INDEX_WEEK\","
			+ "\"value\": 3.9" + "}" + "]" + "}" + "]" + "}" + "}            ";

	public final static void main(String[] args) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient = wrapClient(httpclient);
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
				new UsernamePasswordCredentials("A100", "123456"));

		try {
			HttpGet httpget = new HttpGet(NOMHAD_URL_HEADER + "A100"
					+ OBSERVATIONS_REQUEST);

			HttpPost httppost = new HttpPost(NOMHAD_URL_HEADER + "A100"
					+ OBSERVATIONS_REQUEST);
			httppost.setEntity(new StringEntity(BODY_TEST));
			System.out.println("executing request " + httpget.getURI());

			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httppost, responseHandler);
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
			System.out.println("----------------------------------------");

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

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
