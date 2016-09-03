package coffee.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Util {
    public static void log(String msg) {
        RootPanel.get().add(new Label(msg));
    }
}
