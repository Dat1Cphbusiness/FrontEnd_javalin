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
            config.staticFiles.add(System.getProperty("user.dir") + "/src/main/resources/public", Location.EXTERNAL);

            //config.staticFiles.add("/public", Location.CLASSPATH);
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
            StringBuilder html = new StringBuilder("<table>");
            html.append("<tr><th>Navn</th><th>Dato</th><th>Fra</th><th>Til</th><th>Kommentarer</th><th>Rygning</th></tr>");

        for (Map ride : rides) {
               // html.append("<tr>")
            String color = "driver".equals(ride.get("role")) ? "steelblue" : "orange";
            html.append("<tr style='border-left: 6px solid ").append(color).append("'>")
                    .append("<td>").append(ride.get("name")).append("</td>")
                        .append("<td>").append(ride.get("date")).append("</td>")
                        .append("<td>").append(ride.get("from")).append("</td>")
                        .append("<td>").append(ride.get("to")).append("</td>")
                        .append("<td>").append(ride.get("comments")).append("</td>")
                        .append("<td>").append(ride.get("smoking")).append("</td>")
                        .append("</tr>");
            }

            html.append("</table>");
            ctx.html(html.toString());
        });

        app.start(7001);
    }
}