package com.yozdemir.capital.provider.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yozdemir.capital.provider.ScriptProvider;

public class ScriptProviderImpl implements ScriptProvider {

	@Override
	public List<String> getScripts(List<String> links) {
		final List<String> scripts = new Vector<>();
		final List<ScriptThread> threads = new ArrayList<>();

		for (final String link : links) {
			threads.add(new ScriptThread(link, scripts));
		}

		for (final ScriptThread scriptThread : threads) {
			while (scriptThread.isAlive()) {
				try {
					scriptThread.join();
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		return scripts;
	}

}

class ScriptThread extends Thread {
	private static final String SCRIPT_REGEX = "\\?.*";
	private static final int TIMEOUT = 10000;
	private final String link;
	private final List<String> scripts;

	public ScriptThread(final String link, final List<String> scripts) {
		this.link = link;
		this.scripts = scripts;

		start();
	}

	@Override
	public void run() {
		try {
			final Document document = Jsoup.connect(link).timeout(TIMEOUT).ignoreContentType(true).validateTLSCertificates(false).ignoreHttpErrors(true).get();

			final Elements elements = document.select("script");

			for (final Element element : elements) {
				final String src = element.attr("src");

				if (src != null && src.length() > 0 && src.contains(".js")) {
					final String sanitizedScript = src.substring(src.lastIndexOf("/") + 1).replaceFirst(SCRIPT_REGEX, "");

					scripts.add(sanitizedScript);
				}
			}
		} catch (final IOException e) {
			// Handle any exceptions.
		}
	}
}
