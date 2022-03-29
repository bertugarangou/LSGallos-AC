import java.util.ArrayList;

/**
 * Clase con las aciones de las fases de la competicion. Encamina las batallas con los datos de la competicion y sus raperos.
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 */
public class Fase {

    /**
     * presupuesto de la competicion
     */
    private Double  presupuesto;
    /**
     * pais donde se realiza
     */
    private String  pais;
    /**
     * posible fase A Capela
     */
    private ACapela aCapela;
    /**
     * posible fase Escrita
     */
    private Escrita escrita;
    /**
     * posible fase A Sangre
     */
    private ASangre aSangre;

    /**
     * Constructor.
     * @param presupuesto presupuesto de la fase.
     * @param pais pais donde se realizara la fase.
     */
    public Fase(Double presupuesto, String pais) {
        this.presupuesto =  presupuesto;
        this.pais =         pais;
    }

    /**
     * Getter de presupuesto de la fase.
     * @return presupuesto.
     */
    public Double getPresupuesto() {
        return presupuesto;
    }

    /**
     * Getter del pais de la fase.
     * @return nombre del pais.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Exige la cuenta de las rimas de una estrofa.
     * @param estrofas ArrayList con todas las estrofas posibles
     * @param whatToSetup indice de la estrofa a operar.
     * @return cantidad de rimas en la estrofa selecionada.
     */
    public int encontrarRimas(ArrayList<String> estrofas, int whatToSetup){
        if(whatToSetup == 1) {  //escoger que tipo de batalla encontrara las rimas
            return aCapela.encontrarRimas(estrofas);

        }else if(whatToSetup == 2) {
            return escrita.encontrarRimas(estrofas);

        }else{
            return aSangre.encontrarRimas(estrofas);

        }

    }

    /**
     * Crea una batalla del tipo selecionado.
     * @param temas ArrayList de temas posibles para la batalla.
     * @param whatToSetup tipo de batalla. Puede ser 1, 2 o 3 para A Capela, Escrita o A Sangre respectivamente.
     */
    public void setBatalla(ArrayList<Tema> temas, int whatToSetup) {
        if(whatToSetup == 1) { //crear una batalla nueva del tipo {whatToSetup} y llenarla de temas

            this.aCapela = new ACapela(temas);

        }else if(whatToSetup == 2) {

            this.escrita = new Escrita(temas);

        }else if (whatToSetup == 3) {

            this.aSangre = new ASangre(temas);

        }

    }

    /**
     * Obtiene la cantidad de temas de un tipo de batalla.
     * @param whatBattleToGet tipo de batalla. Puede ser 1, 2 o 3 para A Capela, Escrita o A Sangre respectivamente.
     * @return cantidad de temas de la batalla.
     */
    public int getTopics(int whatBattleToGet) {

        if(whatBattleToGet == 1) {  //encontrar cuantos temas tiene la batalla YA CREADA

            return aCapela.cuantosTopics();

        }else if(whatBattleToGet == 2) {

            return escrita.cuantosTopics();

        }else {

            return aSangre.cuantosTopics();

        }

    }

    /**
     * Obtiene el nombre del tema a elegir.
     * @param whatBattleToGet tipo de batalla. Puede ser 1, 2 o 3 para A Capela, Escrita o A Sangre respectivamente.
     * @param whatTopicIndex indice del tema.
     * @return nombre del tema selecionado.
     */
    public String getTopicName(int whatBattleToGet, int whatTopicIndex) {

        if(whatBattleToGet == 1) {//encontrar nombre del tema que tiene la batalla YA CREADA

            return aCapela.getTopicName(whatTopicIndex-1);

        }else if(whatBattleToGet == 2) {

            return escrita.getTopicName(whatTopicIndex-1);

        }else {

            return aSangre.getTopicName(whatTopicIndex-1);

        }

    }

    /**
     * Ontiene las estrofas de un tema selecionado.
     * @param whatBattleToGet tipo de batalla. Puede ser 1, 2 o 3 para A Capela, Escrita o A Sangre respectivamente.
     * @param whatTopicToGet indice del tema.
     * @param nivel nivel del rapero.
     * @param cual indice de la estrofa. Puede ser que para un nivel haya mas de una estrofa.
     * @return estrofa completa.
     */
    public String getEstrofas(int whatBattleToGet, int whatTopicToGet, int nivel, int cual) {

        if(whatBattleToGet == 1) {//encontrar estrofas tiene la batalla YA CREADA con un nivel

            return aCapela.getEstrofas(whatTopicToGet, nivel, cual);

        }else if(whatBattleToGet == 2) {

            return escrita.getEstrofas(whatTopicToGet, nivel, cual);

        }else {

            return aSangre.getEstrofas(whatTopicToGet, nivel, cual);

        }

    }

    /**
     * Exige el calculo de la puntuacion mediante una cantidad de rimas y un tipo de batalla para escoger la formula del calculo.
     * @param rimas cantidad de rimas.
     * @param whatBattleToGet tipo de batalla. Puede ser 1, 2 o 3 para A Capela, Escrita o A Sangre respectivamente. Por cada tipo se utiliza una formula distinta.
     * @return puntuacion resultante..
     */
    public Double calcularPuntuacion(int rimas, int whatBattleToGet) {

        if(whatBattleToGet == 1) { //enviar a una batalla u otra la cantidad de rimas para obtener puntuacion

            return aCapela.calcularPuntuacion(rimas);

        }else if(whatBattleToGet == 2) {

            return escrita.calcularPuntuacion(rimas);

        }else {

            return aSangre.calcularPuntuacion(rimas);

        }

    }

}//END