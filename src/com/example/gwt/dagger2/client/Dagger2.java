/**
 * Licensed to TOMOTON GmbH under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TOMOTON GmbH licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.gwt.dagger2.client;

import com.example.gwt.dagger2.client.di.CoffeeApp.Coffee;
import com.example.gwt.dagger2.client.di.DaggerCoffeeApp_Coffee;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class Dagger2 implements EntryPoint {

	private Version version = GWT.create(Version.class);
	
	@Override
    public void onModuleLoad() {
	      GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
	        public void onUncaughtException(Throwable caught) {
	        	Console.log(caught);
	        }
	      });
	      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				Console.log("GWT+Dagger2 v" + version.getValue() + " starting...");
				onModuleReady();
			}
		});
	}
	
	public void onModuleReady() {
		Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();
		coffee.maker().brew();
	}

}
