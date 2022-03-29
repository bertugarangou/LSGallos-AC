import java.util.ArrayList;
import java.util.List;

/**
 * Clase para guardar un tema. Los temas guardan niveles y cada nivel distintas estrofas para que los objetos {@link Rapero} las puedan versar.
 * Es posible que una estro
 */
public class Tema {
    /**
     * titulo del tema
     */
    private String                      name;
    /**
     * lista de niveles con estrofas
     */
    private List<ArrayList<String>>     rimas;

    /**
     * Constructor.
     * @param name nombre del tema.
     * @param rimas Lista de niveles con ArrayList de las estrofas.
     */
    public Tema(String name, List<ArrayList<String>> rimas){
        this.name = name;
        this.rimas = rimas;
    }

    /**
     * Getter del nombre del tema.
     * @return nombre.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter de estrofas de un nivel y de un tema.
     * @param nivel nivel
     * @param aux indice de la estrofa.
     * @return estrofa compelta.
     */
    public String getEstrofas(int nivel, int aux) {

        return rimas.get(nivel).get(aux);

    }
}
