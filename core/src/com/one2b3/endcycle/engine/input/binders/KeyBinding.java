package com.one2b3.endcycle.engine.input.binders;

import java.util.List;

import com.one2b3.endcycle.engine.input.KeyCode;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "keyCode")
public class KeyBinding {

	KeyCode keyCode;
	List<Integer> codes;

}
