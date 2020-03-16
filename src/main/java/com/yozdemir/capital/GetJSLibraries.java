package com.yozdemir.capital;

import java.util.List;

import com.yozdemir.capital.constant.SearchConstants;
import com.yozdemir.capital.provider.EndProvider;
import com.yozdemir.capital.provider.LinkProvider;
import com.yozdemir.capital.provider.ScriptProvider;
import com.yozdemir.capital.provider.SortProvider;
import com.yozdemir.capital.provider.impl.EndProviderImpl;
import com.yozdemir.capital.provider.impl.LinkProviderImpl;
import com.yozdemir.capital.provider.impl.ScriptProviderImpl;
import com.yozdemir.capital.provider.impl.SortProviderImpl;

/**
 * Bonus steps - write tests or think about the approach for testing your code i
 * have written test class in test source - think about / implement Java
 * concurrency utilities to speed up certain tasks i have written ScriptThread
 * class for multithread - think about / implement deduplication algorithms for
 * the same Javascript libraries with different names i have used
 * stream().distinct() method
 */
public class GetJSLibraries {
	// 0) Read a string (search term) from standard input endproviderimpl
	// constructor
	private final EndProvider endProvider = new EndProviderImpl();
	private final LinkProvider linkProvider = new LinkProviderImpl();
	private final ScriptProvider scriptProvider = new ScriptProviderImpl();
	private final SortProvider sortProvider = new SortProviderImpl();

	public static void main(String[] args) {
		GetJSLibraries topLibraries = new GetJSLibraries();
		topLibraries.run();
	}

	void run() {

		// 1) Get a Google result page for the search term
		System.out.println(SearchConstants.WEB_CRAWLER);
		System.out.println(SearchConstants.INTRODUCE_SEARCH_TERM);
		final String searchTerm = endProvider.getSearchTerm();

		// 2) Extract main result links from the page
		System.out.println(SearchConstants.SEARCH);
		final List<String> links = linkProvider.getLinks(searchTerm);
		System.out.println("\t--------Found " + links.size() + " links----------");

		// 3) Download the respective pages and extract the names of Javascript libraries used in them
		System.out.println(SearchConstants.RETRIEVING_SCRIPTS);
		final List<String> scripts = scriptProvider.getScripts(links);
		System.out.println("\tFound " + scripts.size() + " scripts");

		// 4) Print top 5 most used libraries to standard output
		System.out.println(SearchConstants.GENERATING_METRICS);
		final List topFiveList = sortProvider.getTop(SearchConstants.TOP_N, scripts);

		// write results
		System.out.println(SearchConstants.TOP_FIVE);
		System.out.println(topFiveList);
		System.out.println(SearchConstants.EXECUTION_FINISHED);

	}

}
