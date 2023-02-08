package com.one2b3.endcycle.features.models.connect;

import java.lang.reflect.Type;
import java.util.*;

import com.one2b3.modding.diff.Moddable;
import com.one2b3.revo.Revo;

public class Connectors {

	private final static Map<Type, Connector<?, ?>> connectors = new HashMap<>();
	private final static List<Connector<?, ?>> list = new ArrayList<>();

	public static void put(Type type, Connector<?, ?> connector) {
		list.remove(connectors.put(type, connector));
		list.add(connector);
	}

	public static Connector<?, ?> get(Type type) {
		Connector<?, ?> connector = connectors.get(type);
		if (connector == null) {
			connector = Revo.create(type, Connector.class);
			put(type, connector);
		}
		return connector;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Moddable> ModdableConnector<T> getModdable(Class<? extends ModdableConnector<T>> type) {
		return (ModdableConnector<T>) get(type);
	}

	public static Collection<Connector<?, ?>> getConnectors() {
		return list;
	}

}
