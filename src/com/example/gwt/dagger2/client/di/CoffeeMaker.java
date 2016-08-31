package com.example.gwt.dagger2.client.di;

import javax.inject.Inject;

import com.example.gwt.dagger2.client.Console;

import dagger.Lazy;

public class CoffeeMaker {
	
	private final Lazy<Heater> heater; // Create a possibly costly heater only
										// when we use it.
	private final Pump pump;

	@Inject
	CoffeeMaker(Lazy<Heater> heater, Pump pump) {
		this.heater = heater;
		this.pump = pump;
	}

	public void brew() {
		heater.get().on();
		pump.pump();
		Console.log(" [_]P coffee! [_]P ");
		heater.get().off();
	}

}