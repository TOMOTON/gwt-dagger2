package coffee.client;

class ElectricHeater implements Heater {
    boolean heating;

    @Override public void on() {
        Util.log(("~ ~ ~ heating ~ ~ ~"));
        this.heating = true;
    }

    @Override public void off() {
        this.heating = false;
    }

    @Override public boolean isHot() {
        return heating;
    }
}
