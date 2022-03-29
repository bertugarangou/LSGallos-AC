import java.io.FileNotFoundException;

/**
 * Clase inicial y principal.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 */
public class Main {

    /**
     * Metodo inicial y principal.
     * @param args argumentos de la linea de comandos (N/A).
     */
    public static void main(String[] args) {

        CompeticionController controller = new CompeticionController(); //crear la competicion

        try {
            controller.readJsonCompetition();   //cargar la competicion

            try {
                controller.executeCompetition();    //inicializar
            } catch (FileNotFoundException e) { //error de fichero
                System.err.println("Yo' file batalles.json not found... It's needed to execute the program \uD83D\uDE05\nPlease add the json file to the resources directory.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Yo' file competició.json not found... It's needed to execute the program \uD83D\uDE05\nPlease add the json file to the resources directory.");
        }

    }

}//END