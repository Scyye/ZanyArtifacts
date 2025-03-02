//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package dev.scyye.zanyArtifacts;

import java.util.ArrayList;

public class SuperList<T> extends ArrayList<T> {
	public SuperList() {
	}

	public void addIf(T obj, boolean condition) {
		if (condition) {
			this.add(obj);
		}
	}
}
