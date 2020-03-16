package com.yozdemir.capital.provider;

import java.util.List;

public interface ScriptProvider {
    List<String> getScripts(List<String> links);
}
