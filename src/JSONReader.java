import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import org.junit.Assert;
import java.io.*;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.*;
import java.util.List;

/**
 * Clase que comprueva, lee y escribe los ficheros JSON necesarios.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 */
public class JSONReader {

    /**
     * Constructor.
     */
    public JSONReader() {

    }

    /**
     * Lee el fichero "competicio.json" con los datos de la competicion.
     * @return objeto Competicion con todos los datos obtenidos.
     * @throws FileNotFoundException si el fichero esta ausente o contiene errores.
     */
    public Competicion openCompetition() throws FileNotFoundException {

        //cargar la comepticion
        Competicion competicion;


        FileInputStream jsonCompetition = new FileInputStream("resources/competició.json");

        Assert.assertNotNull(jsonCompetition);

        Reader reader = new InputStreamReader(jsonCompetition);

        JsonElement rootElement = JsonParser.parseReader(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();

        //----------------

        JsonObject competi = rootObject.getAsJsonObject("competition"); //datos iniciales
        JsonArray phases = competi.getAsJsonArray("phases");
        Assert.assertNotNull(competi);
        Assert.assertNotNull(phases);

        ArrayList<Fase> listaFases = new ArrayList<>(); //fases
        for (JsonElement jsonElement : phases) {

            JsonObject jsonObjectLocation = jsonElement.getAsJsonObject();
            Double budget = jsonObjectLocation.get("budget").getAsDouble();
            String country = jsonObjectLocation.get("country").getAsString();

            Fase fase = new Fase(budget, country);
            listaFases.add(fase);

        }

        LocalDate startDate = LocalDate.parse(competi.get("startDate").getAsString());  //fechas
        LocalDate endDate = LocalDate.parse(competi.get("endDate").getAsString());


        //--------------------------------------

        ArrayList<Paises> paises = new ArrayList<>();

        JsonArray country = rootObject.getAsJsonArray("countries"); //paises

        Type foundListType = new TypeToken<ArrayList<String>>(){}.getType();
        ArrayList<String> countries = new Gson().fromJson(country, foundListType);

        for (String s : countries) {
            Paises pais = new Paises(s);
            paises.add(pais);
        }

        //--------------------------------------

        JsonArray rapprs = rootObject.getAsJsonArray("rappers");    //raperos

        ArrayList<Rapero> listRaperos = new ArrayList<>();

        for(JsonElement jsonElement : rapprs){  //leer cada rapero

            JsonObject jsonObjectLocation = jsonElement.getAsJsonObject();      //datos de los raperos
            String rapprName = jsonObjectLocation.get("realName").getAsString();
            String rapprStageName = jsonObjectLocation.get("stageName").getAsString();
            LocalDate rapprBirthday = LocalDate.parse(jsonObjectLocation.get("birth").getAsString());
            String rapprNationality = jsonObjectLocation.get("nationality").getAsString();
            int rapprLevel = jsonObjectLocation.get("level").getAsInt();
            String rapprPhoto = jsonObjectLocation.get("photo").getAsString();

            Rapero rapero = new Rapero(rapprName, rapprStageName, rapprBirthday, rapprNationality, rapprLevel, rapprPhoto);

            for (Paises paise : paises) {   //paises del rapero
                if (rapprNationality.equals(paise.getPais())) listRaperos.add(rapero);
            }

        }

        //---------------------------------------

        competicion = new Competicion(competi.get("name").getAsString(), startDate, endDate, listaFases, paises,listRaperos);   //crear la compe

        return (competicion);//enviarla

    }

    /**
     * Escribe en el fichero "competicio.json" (nuevos registros de raperos).
     * @param competicion objeto competicion de donde copiar los datos.
     */
    public void writeCompetition(Competicion competicion) {

        int i = 0;

        try {
            JsonWriter writer = new JsonWriter(new FileWriter("resources/competició.json"));    //escoger fichero
            writer.setIndent("  "); //establecer espacio entre lineas
            writer.beginObject();
            writer.name("competition"); //guardar datos generales
            writer.beginObject();
            writer.name("name").value(competicion.getNombreCompeticion());
            writer.name("startDate").value(competicion.getFechaInicio().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
            writer.name("endDate").value(competicion.getFechaFinal().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
            writer.name("phases");
            writer.beginArray();
            for(int j = 0; j < competicion.cuantasFases(); j++) {   //guardar fases
                writer.beginObject();
                writer.name("budget").value(competicion.obtenerFase(i).getPresupuesto());
                writer.name("country").value(competicion.obtenerFase(i).getPais());
                writer.endObject();
                i++;
            }
            writer.endArray();
            writer.endObject();


            i = 0;

            writer.name("countries");   //guardar paises
            writer.beginArray();
            for(int j = 0; j < competicion.cuantosPaises(); j++) {
                writer.value(competicion.quePais(i));
                i++;
            }
            writer.endArray();

            i = 0;

            writer.name("rappers"); //guardar raperos + registrados
            writer.beginArray();
            for (int j = 0; j < competicion.cuantosRaperos(); j++) {
                writer.beginObject();
                writer.name("realName").value(competicion.obtenerRapero(i).getNombre());
                writer.name("stageName").value(competicion.obtenerRapero(i).getNombreArtistico());
                writer.name("birth").value(competicion.obtenerRapero(i).getNacimiento().format(DateTimeFormatter.ofPattern("uuuu-MM-dd")));
                writer.name("nationality").value(competicion.obtenerRapero(i).getPais());
                writer.name("level").value(competicion.obtenerRapero(i).getNivel());
                writer.name("photo").value(competicion.obtenerRapero(i).getUrl());
                writer.endObject();
                i++;
            }
            writer.endArray();
            writer.endObject();

            writer.close();

        } catch (IOException e) {   //interrupcion de error general (NO DEVE OCURRIR NUNCA)
            System.out.println("error general de la lectura de ficheros (soy debug. patito)");
        }

    }

    /**
     * Lee el fichero "batalles.json".
     * @return Array de temas leidos.
     * @throws FileNotFoundException si no se encuentra el fichero o contiene errores.
     */
    public ArrayList<Tema> leerBatallas() throws FileNotFoundException {

        Tema tema;

        FileInputStream jsonBatallas = new FileInputStream("resources/batalles.json");  //escoger fichero batalles

        Reader reader = new InputStreamReader(jsonBatallas);

        JsonElement rootElement = JsonParser.parseReader(reader);
        JsonObject rootObject = rootElement.getAsJsonObject();

        JsonArray temas = rootObject.getAsJsonArray("themes");

        ArrayList<Tema> listaTemas = new ArrayList<>();

        int i = 0, j = 0, k = 0;
        for(JsonElement jsonElement : temas) {  //leer temas

            JsonObject jsontema = jsonElement.getAsJsonObject();
            String name = jsontema.get("name").getAsString();
            JsonArray rimasAux = jsontema.getAsJsonArray("rhymes");

            i = 0;
            for(JsonElement jsonElement1 : rimasAux) {  //leer rimas

                JsonObject obtener = jsonElement1.getAsJsonObject();

                List<ArrayList<String>> temas2 = new ArrayList<>();

                for (j = 0; j < obtener.size(); j++) {  //leer niveles

                    JsonArray arr1 = obtener.getAsJsonArray(Integer.toString(i+1));
                    i++;

                    ArrayList<String> rimas = new ArrayList<>();

                    for(k = 0; k < arr1.size(); k++) {  //leer estrofas
                        rimas.add(arr1.get(k).getAsString());

                    }
                    temas2.add(rimas);

                }
                tema = new Tema(name, temas2);
                listaTemas.add(tema);
            }

        }

        return listaTemas;

    }

}//END