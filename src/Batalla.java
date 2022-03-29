import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta para las operaciones relacionadas con los temas de una competicion y tratamiento de estrofas.
 * @version 1.0
 * @author Christian Hasko y Albert Garangou
 */
abstract public class Batalla {

    /**
     * Lista de temas disponibles para una batalla.
     */
    protected List<Tema> temas;

    /**
     * Constructor.
     * @param temas ArrayList de temas disponibles para usar.
     */
    public Batalla(ArrayList<Tema> temas) {

        this.temas = temas;

    }

    /**
     * Getter del nombre de un tema.
     * @param whatTopicToGet indice del tema a obtener.
     * @return nombre del tema especificado.
     */
    public String getTopicName(int whatTopicToGet) {

        return temas.get(whatTopicToGet).getName();

    }

    /**
     * Getter de las estrofas de un tema y nivel.
     * @param whatTopicToGet indice del tema a consultar.
     * @param nivel nivel del tema a consultar.
     * @param cual estrofa a obtener dentro del nivel y del tema especificados
     * @return estrofa completa.
     */
    public String getEstrofas(int whatTopicToGet, int nivel, int cual) {

        return temas.get(whatTopicToGet).getEstrofas(nivel, cual);

    }

    /**
     * Calculadora de cantidad de temas.
     * @return cantidad de temas disponibles.
     */
    public int cuantosTopics() {

        return temas.size();

    }

    /**
     * Clase abstracta a implementar para calcular la puntuacion de una estrofa.
     * @param rimas cantidad de rimas en una estrofa.
     * @return puntuacion de la estrofa.
     */
    abstract public double calcularPuntuacion(int rimas);

    /**
     * Determina la cantidad de rimas de una estrofa, entre 0 y 4.
     * @param estrofa ArrayList con los versos de una estrofa.
     * @return cantidad de rimas en la estrofa.
     */
    public int encontrarRimas(ArrayList<String> estrofa){
        int cantidad = 0;
        ArrayList<String> tmp = new ArrayList<>();

        try {
            for (String s : estrofa) {  //puntero

                String one = s.substring(s.length() - 2);   //cortar para tener las dos ultimas letras
                tmp.add(one);

            }

            cantidad = detector(tmp);   //calcular las rimas


        }catch (StringIndexOutOfBoundsException e) {    //comprobar que no haya suficientes letras
            System.out.println("\n\033[1;91mYo' your score for this attempt is zero, we cannot rate you with less than a word in a sentence.\nPlease, try to do better next time.\033[0m");
            cantidad = 0;
        } catch (IndexOutOfBoundsException e) {
            cantidad = 0;
        }
        return(cantidad);
    }

    /**
     * Determina la repetitividad de letras.
     * @param estrofa ArrayList con los grupos de letras a comparar.
     * @return repeticiones de grupos de letras.
     */
    private int detector(ArrayList<String> estrofa) {

        int cantidad1 = 0;
        int cantidad2 = 0;
        int cantidad3 = 0;
        int cantidad4 = 0;
        int cantidad = 0;

        cantidad1 = Collections.frequency(estrofa, estrofa.get(0)); //comprobar la cantidad por cada verso
        cantidad2 = Collections.frequency(estrofa, estrofa.get(1));
        cantidad3 = Collections.frequency(estrofa, estrofa.get(2));
        cantidad4 = Collections.frequency(estrofa, estrofa.get(3));

        cantidad = cantidad1+cantidad2+cantidad3+cantidad4;

        if(cantidad == 16) {    //establecer la cantidad
            cantidad = cantidad/4;
        }else if(cantidad == 8) {
            cantidad = cantidad/2;
        }else if(cantidad == 6) {
            cantidad = cantidad/3;
        }else if(cantidad == 10) {
            cantidad = cantidad/(10/3);
        }else {
            cantidad = 0;
        }

        return cantidad;

    }

}//END