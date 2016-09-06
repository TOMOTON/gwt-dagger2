package com.example.gwt.dagger2.client.di;

import com.example.gwt.dagger2.client.Console;

class ElectricHeater implements Heater {

	boolean heating;

	@Override
	public void on() {
		Console.log("~ ~ ~ heating ~ ~ ~");
		this.heating = true;
	}

	@Override
	public void off() {
		this.heating = false;
	}

	@Override
	public boolean isHot() {
		return heating;
	}

}