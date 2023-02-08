package com.one2b3.utils;

import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.features.models.NullOnEmpty;
import com.one2b3.endcycle.features.models.Shrink;
import com.one2b3.endcycle.utils.objects.DataName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Shrink
public class CheckedInteger {

	@NullOnEmpty
	@Name("")
	CheckType type;
	@Name("")
	int number;

	public boolean check(int number) {
		switch (type) {
		case BIGGER:
			return this.number > number;
		case EQUAL:
			return this.number == number;
		case NOT_EQUAL:
			return this.number != number;
		case SMALLER:
			return this.number < number;
		default:
			return false;
		}
	}

	@RequiredArgsConstructor
	public enum CheckType implements DataName {

		EQUAL("equal to"), //
		NOT_EQUAL("not equal to"), //
		SMALLER("smaller than"), //
		BIGGER("bigger than"), //
		;

		@Getter
		final String dataName;
	}

}
