import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarpoolService {
    static File file = new File("rides.json");
    static ObjectMapper mapper = new ObjectMapper();


            public static void main(String [] args){
               Javalin app = Javalin.create(config ->{
                   config.staticFiles.add("/public", Location.CLASSPATH);
               });

               try{
                     if(!file.exists()){
                         mapper.writeValue(file, new ArrayList<>());
                    }
               } catch (IOException e) {
                    throw new RuntimeException(e);
               }

               //Get handler
                app.get("/rides", ctx->{
                    //todo: brug mapper til at læse data ind og lægge det over i en liste af key value pairs (se første linje i POST metoden)

                    /** todo: nu skal vi sammensætte en string af HTML, hvori vi bruger den loadede  data
                     *   - start med at instantiere en StringBuilder
                     *   - brug append metoden og tilføj html til en table - kun til slutningen af første række med tabellen header (research html for <table></table>)
                     **/



                    /* todo: Skrv en for-each hvor vi gennemløber rides
                     *  - For hver ride, append html for en række, hvor daten til hver celle trækkes ud med ride.get(). Fx. ride.get("name")
                     *  - append til sidst afslutningstagget for table
                     *  -  placer indholdet af StringBuilderen til ctx objektet
                     */






                });

                app.post("/rides", ctx->{
                    List<Map> rides = mapper.readValue(file, List.class);

                    //Her bliver json data konverteret til en java datastruktur (Map)
                    rides.add(ctx.bodyAsClass(Map.class));

                    mapper.writeValue(file, rides);
                    ctx.status(201);

                });


                app.start();
            }

}
/*

      app.get("/rides", ctx -> {

            //mapper læser JSON og laver det om til en Java data struktur vi kender
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

/
 */