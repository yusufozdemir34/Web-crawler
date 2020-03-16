package com.yozdemir.capital.provider;

import java.util.List;

public interface LinkProvider {
    List<String> getLinks(String searchTerm);
}
