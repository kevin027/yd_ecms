package com.yida.core.common;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * classpath目录操作监听
 */
public enum ClassPathWatch implements Closeable {
	
	INSTANCE;
	
	private Path classPath;
	public Path getClassPath() {
		return classPath;
	}
	
	private Map<ClassPathEventKey, List<ClassPathWatchListener>> maps = new ConcurrentHashMap<ClassPathEventKey, List<ClassPathWatchListener>>();
	private DirectoryWatch dirWatch;
	
	private ClassPathWatch() {
		classPath = Paths.get(ClassPathWatch.class.getResource("/").getPath().replaceAll("^/", "").replaceAll("%20", " "));
		try {
			dirWatch = new DirectoryWatch(classPath, new DirectoryWatchListener() {
				@Override
				public void performance(WatchEvent<Path> event) {
					ClassPathEventKey key = new ClassPathEventKey(event.context(), event.kind());
					List<ClassPathWatchListener> list = maps.get(key);
					if (null != list && 0 < list.size()) {
						for (ClassPathWatchListener classPathWatchListener : list) {
							classPathWatchListener.performance(event);
						}
					}
				}
			}, StandardWatchEventKinds.ENTRY_MODIFY);
			dirWatch.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void register(Path path, Kind<Path> kind,
			ClassPathWatchListener classPathWatchListener) {
		ClassPathEventKey key = new ClassPathEventKey(path, kind);
		List<ClassPathWatchListener> ls = maps.get(key);
		if (null == ls) {
			ls = new ArrayList<ClassPathWatchListener>();
			maps.put(key, ls);
			ls.add(classPathWatchListener);
		} else {
			if (!ls.contains(classPathWatchListener)) {
				ls.add(classPathWatchListener);
			}
		}
	}
	
	public List<ClassPathWatchListener> remove(Path path, Kind<Path> kind) {
		ClassPathEventKey key = new ClassPathEventKey(path, kind);
		return maps.remove(key);
	}

	@Override
	public void close() throws IOException {
		if (null != dirWatch) {
			dirWatch.close();
		}
	}
}

class ClassPathEventKey {
	private Path path;
	private Kind<Path> kind;
	public ClassPathEventKey(Path path, Kind<Path> kind) {
		this.path = path;
		this.kind = kind;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	public Kind<Path> getKind() {
		return kind;
	}
	public void setKind(Kind<Path> kind) {
		this.kind = kind;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassPathEventKey other = (ClassPathEventKey) obj;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
}
