package com.yozdemir.capital.provider.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yozdemir.capital.provider.EndProvider;

public class EndProviderImpl implements EndProvider {
	private final BufferedReader bufferedReader;

	public EndProviderImpl() {
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	public String getSearchTerm() {
		try {
			return bufferedReader.readLine();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return getSearchTerm();
	}

}
