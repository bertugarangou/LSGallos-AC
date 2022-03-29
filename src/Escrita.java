import java.util.ArrayList;

/**
 * Clase para la obtencion de puntos en la simulacion de batallas de tipo Escrita.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 *
 */
public class Escrita extends Batalla {

    /**
     * segundos maximos de la batalla
     */
    private int segMax;

    /**
     * Constructor.
     *
     * @param temas ArrayList con todos los temas posibles de la competicion.
     */
    public Escrita(ArrayList<Tema> temas) {
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

        if(rimas == 0) {    //calcular la puntuacion mediante el numero de rimas
            return 0;
        } else {
            return 1 + 3 * rimas;
        }
    }
}