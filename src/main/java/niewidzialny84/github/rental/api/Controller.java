package niewidzialny84.github.rental.api;

import static spark.Spark.*;

public class Controller {

    public Controller(int port) {
        port(port);

        path("/api", () -> {
            get("/", (req,res) -> "Homepage");
            path("/car", () -> {
                //get("/:id", );
                //post("/", CaaddCarrAPI.);
            });
        });
    }
}
