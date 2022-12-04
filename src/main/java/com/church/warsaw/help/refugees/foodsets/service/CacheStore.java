package com.church.warsaw.help.refugees.foodsets.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CacheStore<T> {
  private final Cache<String, T> cache;

  public CacheStore(int expiryDuration, TimeUnit timeUnit) {
    cache = CacheBuilder.newBuilder()
        .expireAfterWrite(expiryDuration, timeUnit)
        .concurrencyLevel(Runtime.getRuntime().availableProcessors())
        .build();
  }

  public T get(String firstKey, String secondKey) {
    String key = keyGenerator(firstKey, secondKey);
    return cache.getIfPresent(key);
  }

  public void add(String keyFirst, String keySecond, T value) {
    if (isNotBlank(keyFirst)
        && isNotBlank(keySecond)
        && Objects.nonNull(value)) {

      String key = keyGenerator(keyFirst, keySecond);
      cache.put(key, value);
    }
  }

  public void add(String key, T value) {
    if (key != null && value != null) {
      cache.put(key, value);
    }
  }

  private String keyGenerator(String firstKey, String secondKey) {

    return firstKey.toLowerCase().replaceAll("[\uFEFF-\uFFFF]", "")
        + secondKey.toLowerCase().replaceAll("[\uFEFF-\uFFFF]", "");
  }
}
