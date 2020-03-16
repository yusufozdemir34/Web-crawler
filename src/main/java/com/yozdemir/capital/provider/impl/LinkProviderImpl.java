package com.yozdemir.capital.provider.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yozdemir.capital.provider.LinkProvider;

public class LinkProviderImpl implements LinkProvider {
	private static final int RESULT_SIZE = 13;
	private static final int TIMEOUT = 9000;
	private static String SEARCH_Q = "https://www.google.com/search?num=" + RESULT_SIZE + "&q=";

	public List<String> getLinks(String searchTerm) {
		final List<String> urls = new ArrayList<>();

		try {
			final Document document = Jsoup.connect(SEARCH_Q + searchTerm).timeout(TIMEOUT).get();
			final Elements divList = document.select("div.g");

			for (final Element div : divList) {
				final Elements elements = div.getElementsByTag("a");
				final String url = elements.attr("href");

				if (url.startsWith("http")) {
					urls.add(url);
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return urls.stream().distinct().collect(toList());
	}

}
