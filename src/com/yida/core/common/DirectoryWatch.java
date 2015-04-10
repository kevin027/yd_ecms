package com.yida.core.common;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Executors;

/**
 * classpath目录监听
 */
public class DirectoryWatch implements Closeable {
	
	private Path dir;
	private DirectoryWatchListener listener;
	private Kind<?>[] eventKinds;
	
	private WatchService watchService;
    private WatchKey watchKey;
    private boolean isClose = false;
    
	public DirectoryWatch(Path dir, DirectoryWatchListener listener, Kind<?>... eventKinds) {
        this.dir = dir;
        this.listener = listener;
        this.eventKinds = eventKinds;
	}
	
	public void open() throws IOException {
		watchService = FileSystems.getDefault().newWatchService();
	    watchKey = dir.register(watchService, eventKinds);
		Executors.newSingleThreadExecutor().execute(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				while (!isClose) {
		            WatchKey key;
		            try {
		                key = watchService.take();
		            } catch (InterruptedException x) {
		                return;
		            }
		            for (WatchEvent<?> event : key.pollEvents()) {
		                Kind<?> kind = event.kind();
		                if (kind == StandardWatchEventKinds.OVERFLOW) {
		                    continue;
		                }
		                if (null != listener) {
		                	listener.performance((WatchEvent<Path>)event);
		                }
		            }
		            watchKey.reset();
		        }
			}
		});
	}

	@Override
	public void close() throws IOException {
		isClose = true;
		if (null != watchService ) {
			watchService.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		try (DirectoryWatch dw = new DirectoryWatch(Paths.get("d:/"), null, StandardWatchEventKinds.ENTRY_MODIFY);) {
			dw.open();
		}
	}
}
