package com.tools.sys;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import com.yida.core.base.entity.Account;

public enum OnlineAccountSet {

	INSTANCE;

	private final Set<Account> accounts = new ConcurrentSkipListSet<Account>();

	OnlineAccountSet() {

	}

	public boolean add(Account user) {
		return accounts.add(user);
	}

	public boolean remove(Account user) {
		return accounts.remove(user);
	}

	public Iterator<Account> iterator() {
		return accounts.iterator();
	}

	@Override
	public String toString() {
		return Arrays.toString(accounts.toArray());
	}
}
