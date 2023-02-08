package com.one2b3.endcycle.core.errors;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.revo.GameData;

public class SessionDumper {

	public static void dump() {
		Data.write(Data.getHandle("debug.txt"), true, getSession());
	}

	public static String getSession() {
		StringBuilder builder = new StringBuilder();
		builder.append("== Session: ");
		GameScreen screen = Cardinal.game.getScreen();
		builder.append(GameData.toString(screen));
		builder.append(" ==");
		if (screen != null) {
			printScreen(builder, screen);
		}
		if (Cardinal.getInput() != null) {
			print(builder, "Global Inputs", Cardinal.getInput().getListeners());
		}
		return builder.toString();
	}

	public static void printScreen(StringBuilder builder, GameScreen screen) {
		print(builder, "Objects", screen.getObjects());
		print(builder, "Events", screen.events.listeners);
		print(builder, "Inputs", screen.input.listeners);
	}

	private static void print(StringBuilder builder, String name, Array<?> array) {
		if (array.size > 0) {
			builder.append('\n');
			builder.append(name);
			builder.append(": ");
			for (int i = 0; i < array.size; i++) {
				if (i > 0) {
					builder.append(", ");
				}
				builder.append(GameData.toString(array.get(i)));
			}
		}
	}
}
