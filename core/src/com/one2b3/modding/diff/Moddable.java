package com.one2b3.modding.diff;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.ToolboxIgnore;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.IdEntity;

@KeepClass
public class Moddable implements IdEntity {

	@ToolboxIgnore
	public ID id = new ID();

	@Override
	public final ID getId() {
		return id;
	}

	@Override
	public final boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && id.equals(((Moddable) obj).id);
	}

	@Override
	public final int hashCode() {
		return id.hashCode();
	}

	public static ID[] toIDArray(Array<? extends Moddable> array) {
		ID[] ids = new ID[array.size];
		for (int i = 0; i < array.size; i++) {
			ids[i] = (array.get(i) == null ? null : array.get(i).id);
		}
		return ids;
	}

}
