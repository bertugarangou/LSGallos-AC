import java.util.ArrayList;

/**
 * Clase para la obtencion de puntos en la simulacion de batallas de tipo A Capela.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 *
 */
public class ACapela extends Batalla {

    /**
     * Constructor.
     *
     * @param temas ArrayList con todos los temas posibles de la competicion.
     */
    public ACapela(ArrayList<Tema> temas) {
        super(temas);
    }

    /**
     * Obtiene la puntuacion especifica para las Batallas A Capela.
     *
     * @param rimas cantidad de rimas en una estrofa.
     * @return puntuacion de la estrofa.
     */
    @Override
    public double calcularPuntuacion(int rimas) {
        if(rimas == 0) {    //comprobar que no haya ninguna rima antes
            return 0;
        } else {
            return (6 * Math.sqrt(rimas) + 3) / 2;
        }
    }
}