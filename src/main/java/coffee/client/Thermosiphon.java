package coffee.client;

import javax.inject.Inject;

class Thermosiphon implements Pump {
    private final Heater heater;

    @Inject Thermosiphon(Heater heater) {
        this.heater = heater;
    }

    @Override public void pump() {
        if (heater.isHot()) {
            Util.log("=> => pumping => =>");
        }
    }
}
