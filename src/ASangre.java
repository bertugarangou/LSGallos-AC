import java.util.ArrayList;

/**
 * Clase para la obtencion de puntos en la simulacion de batallas de tipo A Sangre.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 *
 */
public class ASangre extends Batalla {
    /**
     * Nombre del productor
     */
    private String producerName;

    /**
     * Constructor.
     *
     * @param temas ArrayList con todos los temas posibles de la competicion.
     */
    public ASangre(ArrayList<Tema> temas) {
        super(temas);
    }

    /**
     * Obtiene la puntuacion especifica para las Batallas A Sangre.
     *
     * @param rimas cantidad de rimas en una estrofa.
     * @return puntuacion de la estrofa.
     */
    @Override
    public double calcularPuntuacion(int rimas) {

        if(rimas == 0) {    //comprobar que no haya ninguna rima antes
            return 0;
        } else {
            return (Math.PI * Math.pow(rimas, 2)) / 4;
        }
    }
}
