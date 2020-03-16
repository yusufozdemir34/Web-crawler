package com.yozdemir.capital.provider;

import java.util.List;
import java.util.Map.Entry;

public interface SortProvider {
	List<Entry<String, Long>> getTop(long n, List<String> elements);
}
