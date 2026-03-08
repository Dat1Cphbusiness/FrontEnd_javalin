import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

public class CarpoolApp {

    static final File FILE = new File("rides.json");
    static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        if (!FILE.exists()){
            mapper.writeValue(FILE, new ArrayList<>());
        }

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        });

        app.post("/rides", ctx -> {
            List<Map> rides = mapper.readValue(FILE, List.class);
            //Her bliver json data konverteret til en java datastruktur (Map)
            rides.add(ctx.bodyAsClass(Map.class));
            //som så sendes videre til mapper objektet, for at blive skrevet til serveres filssystem
            mapper.writeValue(FILE, rides);
            ctx.status(201);
        });

        app.get("/rides", ctx -> {
            List<Map> rides = mapper.readValue(FILE, List.class);
            StringBuilder html = new StringBuilder("<ul>");
            for (Map ride : rides) {
                html.append("<li>")
                        .append(ride.get("name")).append(": ")
                        .append(ride.get("from")).append(" -> ")
                        .append(ride.get("to")).append(" ")
                        .append(ride.get("date"))
                        .append("</li>");
            }
            html.append("</ul>");
            ctx.html(html.toString());
        });

        app.start(7001);
    }
}