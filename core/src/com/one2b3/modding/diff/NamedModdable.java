package com.one2b3.modding.diff;

import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.endcycle.utils.objects.Named;

public abstract class NamedModdable extends Moddable implements Named, DataName {

	@Description("The name that will be shown in the toolbox")
	public String dataName = "";

	@Override
	public String getName() {
		return dataName;
	}

	@Override
	public String getDataName() {
		return ID.combine(id, dataName == null || dataName.length() == 0 ? getName() : dataName);
	}
}
