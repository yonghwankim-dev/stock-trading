package site.stocktrading.api.account.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Account {
	private final Long id;

	public Account(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return String.format("(id=%d)", id);
	}
}
