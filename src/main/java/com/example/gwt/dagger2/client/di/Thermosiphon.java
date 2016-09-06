package com.example.gwt.dagger2.client.di;

import javax.inject.Inject;

import com.example.gwt.dagger2.client.Console;

class Thermosiphon implements Pump {

	private final Heater heater;

	@Inject
	Thermosiphon(Heater heater) {
		this.heater = heater;
	}

	@Override
	public void pump() {
		if (heater.isHot()) {
			Console.log("=> => pumping => =>");
		}
	}

}