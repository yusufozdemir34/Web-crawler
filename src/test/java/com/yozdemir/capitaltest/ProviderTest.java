package com.yozdemir.capitaltest;

import static org.apache.commons.lang3.reflect.FieldUtils.writeStaticField;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import com.yozdemir.capital.provider.LinkProvider;
import com.yozdemir.capital.provider.impl.LinkProviderImpl;

public class ProviderTest {
	private ClientAndServer mockServer;
	private static final String SEARCH_TERM = "searchTerm";
	private static final int SITE_COUNT = 5;
	private static final List<String> URLS = getUrlList();
	private final LinkProvider linkProvider = new LinkProviderImpl();

	private static List<String> getUrlList() {
		final List<String> urls = new ArrayList<>();
		for (int i = 0; i < SITE_COUNT; i++) {
			urls.add("http://website0" + i + "/url");
		}

		return urls;
	}

	@Before
	public void setUp() throws Exception {
		mockServer = startClientAndServer(1080);
		writeStaticField(LinkProviderImpl.class, "SEARCH_Q", "http://127.0.0.1:1080/search?q=", true);
	}

	@Test
	public void getLinks() throws IllegalAccessException {
		new MockServerClient("127.0.0.1", 1080).when(request().withMethod("GET").withPath("/search")).respond(response().withStatusCode(200).withBody(generateResponseBody()));

		final List<String> links = linkProvider.getLinks(SEARCH_TERM);
		assertThat(links.size(), is(SITE_COUNT));
		assertThat(links, is(URLS));
	}

	@After
	public void tearDown() throws Exception {
		mockServer.stop();
	}

	private String generateResponseBody() {
		final StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("<html>\n");
		stringBuilder.append("    <body>\n");
		for (final String url : URLS) {
			stringBuilder.append("        <div class=\"g\">\n");
			stringBuilder.append("            <a href=\"" + url + "\" />\n");
			stringBuilder.append("        </div>\n");
		}

		stringBuilder.append("    </body>\n");
		stringBuilder.append("</html>");

		return stringBuilder.toString();
	}
}