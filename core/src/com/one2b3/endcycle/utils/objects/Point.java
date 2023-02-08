package com.one2b3.endcycle.utils.objects;

import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Shrink;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@Shrink
public final class Point {

	public static final Point Zero = new Point();

	public int x, y;

	public Point(Point point) {
		set(point);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void set(Point point) {
		if (point != null) {
			set(point.x, point.y);
		} else {
			set(0, 0);
		}
	}

	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void add(Point point) {
		if (point != null) {
			add(point.x, point.y);
		}
	}

	public void sub(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	public void sub(Point point) {
		if (point != null) {
			sub(point.x, point.y);
		}
	}

	public boolean is(int x, int y) {
		return this.x == x && this.y == y;
	}

	public boolean equals(Point point) {
		return point != null && this.x == point.x && this.y == point.y;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && equals((Point) obj);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	public boolean update(int x, int y) {
		if (!is(x, y)) {
			set(x, y);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return x + ":" + y;
	}

	public static Point fromString(String str) {
		if (str == null) {
			return null;
		}
		int idx = str.indexOf(':');
		if (idx == -1) {
			return null;
		}
		try {
			return new Point(Integer.parseInt(str.substring(0, idx)), Integer.parseInt(str.substring(idx + 1)));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
