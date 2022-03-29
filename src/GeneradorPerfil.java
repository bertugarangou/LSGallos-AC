import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.salleurl.profile.Profile;
import edu.salleurl.profile.ProfileFactory;


import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;


/**
 * Clase para el control y generacion de perfiles mediante un usuario como parametro.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 *
 */
public class GeneradorPerfil {
    /**
     * Variable con el URL plantilla
     */
    private static final String defaultURL = "https://restcountries.eu/rest/v2/name/";

    /**
     * Constructor.
     */
    public GeneradorPerfil() {

    }

    /**
     * Metodo para generar un perfil
     * @param rapero raepro con sus datos
     * @param num posicion del rapero
     */
    public void generarPerfil(Rapero rapero, int num) {

        generateData(rapero.getPais(), rapero, num+1);

    }

    /**
     * Metodo para generar los datos y guardarlos como JsonArray temporalemtne.
     * @param aConsultar URL personalizada del rapero (sin default)
     * @param rapero Rapero con los datos
     * @param num posicion del rapero en la competicion
     */
    private void generateData(String aConsultar, Rapero rapero, int num) {

        try {

            System.out.println("\nGetting information about their country of origin ("+rapero.getPais()+")...");
            Thread.sleep(400);

            URL url = new URL(defaultURL+aConsultar);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            int respuesta = connection.getResponseCode();

            if(respuesta == HttpsURLConnection.HTTP_OK) {

                Reader reader = new InputStreamReader(connection.getInputStream());

                JsonElement rootElement = JsonParser.parseReader(reader);
                JsonArray jsonArray = rootElement.getAsJsonArray();

                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                String flag = jsonObject.get("flag").getAsString();

                JsonArray lenguas = jsonObject.get("languages").getAsJsonArray();

                ArrayList<String> lengua = new ArrayList<>();
                for (int j = 0; j < lenguas.size(); j++) {

                    JsonObject lenguas1 = lenguas.get(j).getAsJsonObject();

                    String leng = lenguas1.get("name").getAsString();

                    lengua.add(leng);

                }

                String direccion = "RappersHTMLProfiles/"+rapero.getNombreArtistico()+".html";

                Profile profile = ProfileFactory.createProfile(direccion, rapero);

                profile.setCountry(rapero.getPais());
                profile.setFlagUrl(flag);
                for (int i = 0; i < lengua.size(); i++) {

                    profile.addLanguage(lengua.get(i));

                }
                profile.addExtra("Points", String.valueOf(rapero.getScore()));

                if(num == 1) {
                    profile.addExtra("Position", (String.valueOf(num)+" Winner!"));
                }else {
                    profile.addExtra("Position", String.valueOf(num));
                }

                System.out.println("Generating HTML file...");
                Thread.sleep(600);
                System.out.println("\n\033[0;93mDone!\033[0m The profile will open in your default browser.");
                Thread.sleep(200);

                profile.writeAndOpen();

            }else {

                System.err.println("There's no internet connection or something failed.\nWe cannot access to restcountries API.");

            }


        } catch (IOException | InterruptedException e) {
            System.err.println("There's no internet connection or something failed.\nWe cannot access to restcountries API.");
        }

    }

}
