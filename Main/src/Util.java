import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {

    public static double roundDecimal(double value, int places) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }
}
