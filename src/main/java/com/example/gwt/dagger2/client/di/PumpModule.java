package com.example.gwt.dagger2.client.di;

import dagger.Binds;
import dagger.Module;

@Module
abstract class PumpModule {

	@Binds
	abstract Pump providePump(Thermosiphon pump);

}