package com.yida.core.common;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public interface ClassPathWatchListener {
	public void performance(WatchEvent<Path> event);
}
