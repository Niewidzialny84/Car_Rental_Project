package niewidzialny84.github.rental;

import niewidzialny84.github.rental.api.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RentalMain {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public static void main(String[] args) {
        logger.info("Starting API");
        Controller c = new Controller(5000);
    }
}
