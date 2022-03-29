import java.time.*;
import java.util.*;

import java.util.ArrayList;


/**
 * Clase para tratar los datos y caracteristicas de la competicion y distribucion de estos mismos.
 * @version 1.0
 * @author Christian Hasko y Albert Garangou
 */
public class Competicion {

    /**
     * Nombre de la competicion
     */
    private String              nombreCompeticion;

    /**
     * fecha inicial
     */
    private LocalDate           fechaInicio;

    /**
     * fecha final
     */
    private LocalDate           fechaFinal;

    /**
     * lista de fases
     */
    private List<Fase>          fases;

    /**
     * lista de paises permitidos
     */
    private List<Paises>        paises;

    /**
     * lista de raperos concursando
     */
    private List<Rapero>        raperos;

    /**
     *
     */
    private List<Rapero>        auxiliarRaperos;

    /**
     * Constructor.
     */
    public Competicion() {

    }

    /**
     *Constructor.
     * @param nombreCompeticion nombre de la competicion.
     * @param fechaInicio fecha en formato yyyy-MM-dd de inicio.
     * @param fechaFinal fecha en formato yyyy-MM-dd de finalizacion.
     * @param fases cantidad de fases de la competicion. Puede ser 2 o 3.
     * @param paises ArrayList de paises permitidos para los concursantes.
     * @param raperos ArrayList de raperos concursantes.
     */
    public Competicion(String nombreCompeticion, LocalDate fechaInicio, LocalDate fechaFinal, ArrayList<Fase> fases, ArrayList<Paises> paises ,ArrayList<Rapero> raperos) {

        this.nombreCompeticion =    nombreCompeticion;
        this.fechaInicio =          fechaInicio;
        this.fechaFinal =           fechaFinal;
        this.fases =                fases;
        this.paises =               paises;
        this.raperos =              raperos;
    }

    /**
     * Devuelve la cantidad de rimas en una estrofa introducida manualmente.
     * @param estrofas ArrayList de las estrofas introducidas.
     * @param whatToSetup tipo de batalla, necesario para saber que formula se utilizara para determinar la puntuacion.
     * @param whatFaseWeAre fase actual a consultarle los datos.
     * @return  numero de rimas.
     */
    public int encontrarRimas(ArrayList<String> estrofas, int whatToSetup, int whatFaseWeAre){
        if(whatToSetup == 1) {  //dependiendo del tipo de batalla, calcular las rimas
            return fases.get(whatFaseWeAre).encontrarRimas(estrofas, whatToSetup);

        }else if(whatToSetup == 2) {
            return fases.get(whatFaseWeAre).encontrarRimas(estrofas, whatToSetup);

        }else{
            return fases.get(whatFaseWeAre).encontrarRimas(estrofas, whatToSetup);

        }

    }

    /**
     * Getter del nombre de la competicion.
     * @return nombre.
     */
    public String getNombreCompeticion() {
        return nombreCompeticion;
    }

    /**
     * Getter de la fecha de inicio
     * @return fecha en formato yyy-MM-dd.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Getter de la fecha de finalizacion
     * @return fecha en formato yyy-MM-dd.
     */
    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    /**
     * Getter de la cantidad de fases de la competicion.
     * @return cantidad.
     */
    public int cuantasFases() {
        return fases.size();
    }

    /**
     * Getter de la cantidad de raperos participantes en el momento de la llamada.
     * @return cantidad.
     */
    public int cuantosRaperos() {
        return raperos.size();
    }

    /**
     * Getter de la cantidad de paises de los raperos permitidos.
     * @return cantidad.
     */
    public int cuantosPaises() {
        return(paises.size());
    }

    /**
     * Getter del nombre de un pais
     * @param whatToShow indice del pais dentro de la lista de paises.
     * @return nombre del pais.
     */
    public String quePais(int whatToShow) {
        return(paises.get(whatToShow).getPais());
    }

    /**
     * Añade un rapero al fichero pertinente y lo habilita para competir.
     * @param rapprName nombre real del rapero.
     * @param rapprStageName nombre artistico del rapero.
     * @param rapprBirthday fecha de nacimiento.
     * @param rapprNationality nacionalidad del rapero.
     * @param rapprLevel nivel del rapero. Puede ser 1 o 2.
     * @param rapprPhoto foto del rapero en formato compatible.
     */
    public void registrarRapero(String rapprName, String rapprStageName, LocalDate rapprBirthday, String rapprNationality, int rapprLevel, String rapprPhoto) {
        //crear el nuevo rapero
        Rapero rapero = new Rapero(rapprName, rapprStageName, rapprBirthday, rapprNationality, rapprLevel, rapprPhoto);

        //añadir el rapero
        raperos.add(rapero);
    }

    /**
     * Getter de un objeto Rapero
     * @param whatToGet indice del rapero a obtener dentro de la lista de participantes.
     * @return Objeto Rapero.
     */
    public Rapero obtenerRapero(int whatToGet) {
        return(raperos.get(whatToGet));
    }

    /**
     * Getter de la lista completa de raperos en el momento de la llamada.
     * @return lista con los raperos.
     */
    public List<Rapero> getRaperos() {
        return raperos;
    }

    /**
     * Getter de un objeto fase.
     * @param whatToGet indice de la fase a obtener. Puede haber hasta 3.
     * @return Objeto Fase.
     */
    public Fase obtenerFase(int whatToGet) {
        return(fases.get(whatToGet));
    }

    /**
     * Getter de la puntuacion de un rapero.
     * @param indiceRapero indice del rapero a consultar.
     * @return puntuacion.
     */
    public Double obtenerPuntuacion(int indiceRapero) {
        return raperos.get(indiceRapero).getScore();
    }

    /**
     * Elimina un rapero mediante su indice.
     * @param whatToRemove indice del rapero a eliminar.
     */
    public void eliminarRapero(int whatToRemove) {
        raperos.remove(whatToRemove);
    }

    /**
     * Elimina un rapero si la cantidad es impar, los mezcla y devuelve el indice del rapero "logeado" en caso de estar dentro. No elimina el rapero "logeado".
     * @param indiceRapero indice del rapero "logeado".
     * @param nombreRapero nombre del rapero "logeado".
     * @return indice nuevo del rapero "logeado".
     */
    public int escogerRaperos(int indiceRapero, String nombreRapero){
        if(cuantosRaperos()%2 != 0) {   //comprobar si la cantidad de raperos es impar

            int tmp;
            Random r = new Random();

            do {    //escoger un numero aleatorio mientras no sea el nuestro. No nos eliminamos

                tmp = r.nextInt((cuantosRaperos()-3) + 1) + 2;

            }while (tmp == indiceRapero);

            eliminarRapero(tmp);    //eliminar uno al azar

        }
        Collections.shuffle(raperos);   //mezclar


        int i = 0;
        for(i = 0; i < cuantosRaperos(); i++ ){ //encontrar donde esta nuestro rapero
            if(nombreRapero.equals(raperos.get(i).getNombreArtistico())){
                break;
            }
        }
        return i;
    }

    /**
     * Crea una batalla de un tipo especificado.
     * @param temas lista de temas disponibles.
     * @param whatToSetup indice de la fase.
     * @param typeOfBattle tipo de batalla a crear.
     */
    public void start(ArrayList<Tema> temas, int whatToSetup, int typeOfBattle) {

        fases.get(whatToSetup).setBatalla(temas, typeOfBattle); //empezar una batalla

    }

    /**
     * Obtiene la cantidad de temas de una fase.
     * @param whatFaseWeAre indice de la fase a consultar. Puede haber 2 o 3.
     * @param whatBattleToGet indice de la batalla a consultar. Hay 2.
     * @return cantidad.
     */
    public int getTopics(int whatFaseWeAre, int whatBattleToGet) {

        return fases.get(whatFaseWeAre).getTopics(whatBattleToGet);

    }

    /**
     * Obtiene el nombre de un tema de una batalla de una fase.
     * @param whatFaseWeAre indice de la fase. Puede haber 2 o 3.
     * @param whatBattleToGet indice de la batalla dentro de la fase. Puede haber 1 o 2.
     * @param topicIndex inidece del tema.
     * @return nombre del tema elegido.
     */
    public String getNameTopic(int whatFaseWeAre, int whatBattleToGet, int topicIndex) {

        return fases.get(whatFaseWeAre).getTopicName(whatBattleToGet, topicIndex);

    }

    /**
     * Obtiene una estrofa mediante los parametros especificados.
     * @param whatFaseWeAre indice de la fase. Puede haber 2 o 3.
     * @param whatBattleToGet indice de la batalla. Puede haber 1 o 2.
     * @param topicIndex indice del tema.
     * @param nivel nivel de la estrofa. Puede ser 1 o 2.
     * @param cual indice de la estrofa. Puede ser que haya mas de una estrofa por nivel y tema.
     * @return estrofa.
     */
    public String getEstrofas(int whatFaseWeAre, int whatBattleToGet, int topicIndex, int nivel, int cual) {

        return fases.get(whatFaseWeAre).getEstrofas(whatBattleToGet, topicIndex, nivel, cual);

    }

    /**
     * Calcula la puntuacion con decimales
     * @param rimas cantidad de rimas en la estrofa operada.
     * @param whatBattleToGet indice de la batalla a consultar (ya que depende del tipo de batalla creada la operacion matematica de la puntuacion).
     * @param whatFaseWeAre fase a la que consultar la batalla.
     * @return puntuacion con decimales.
     */
    public Double calcularPuntuacion(int rimas, int whatBattleToGet, int whatFaseWeAre) {

        if(whatBattleToGet == 1) {  //dependiendo de la batalla, calcular la puntuacion
            //tipo capela
            return fases.get(whatFaseWeAre).calcularPuntuacion(rimas, whatBattleToGet);

        }else if(whatBattleToGet == 2) {
            //tipo escrita
            return fases.get(whatFaseWeAre).calcularPuntuacion(rimas, whatBattleToGet);

        }else {
            //tipo sangre
            return fases.get(whatFaseWeAre).calcularPuntuacion(rimas, whatBattleToGet);

        }

    }

    /**
     * Establecer la puntuacion de un rapero.
     * @param indiceRapero indice del rapero a tratar.
     * @param puntuacion valor a guardar.
     */
    public void setPuntuacion(int indiceRapero, Double puntuacion) {

        raperos.get(indiceRapero).setScore(puntuacion);

    }

    /**
     * Ordena los objetos raperos mediante su puntuacion en orden decreciente.
     */
    public void sortRaperos() {

        raperos.sort(new Comparator<Rapero>() {
            /**
             * Compara dos objetos de tipo Rapero para deducir cual es mayor, menor o si son iguales.
             * @param o1 objeto A
             * @param o2 objeto B
             * @return menor a 0, mayor a 0 o igual a 0 si el primero es menor, mayor o igual al segundo.
             */
            @Override
            public int compare(Rapero o1, Rapero o2) {  //crear el metodo de ordenacion, de mayor a menor pts.

                Double puntuacion1 = o1.getScore();
                Double puntuacion2 = o2.getScore();

                return puntuacion2.compareTo(puntuacion1);
            }
        });

    }

    /**
     * Ordena los objetos raperos mediante su puntuacion en orden decreciente y devuelve la lista.
     * Similar a {@link #sortRaperos()}  sortRaperos} pero con return de la lista resultante de raperos.
     * @return lista de raperos.
     */
    public List<Rapero> getSortedRaperos() {

        List<Rapero> raperos1 = new ArrayList<>(raperos);

        raperos1.sort(new Comparator<Rapero>() {
            /**
             * Compara dos objetos de tipo Rapero para deducir cual es mayor, menor o si son iguales.
             * @param o1 objeto A
             * @param o2 objeto B
             * @return menor a 0, mayor a 0 o igual a 0 si el primero es menor, mayor o igual al segundo.
             */
            @Override
            public int compare(Rapero o1, Rapero o2) {  //crear el metodo de ordenacion de mayor a menor

                Double puntuacion1 = o1.getScore();
                Double puntuacion2 = o2.getScore();

                return puntuacion2.compareTo(puntuacion1);
            }
        });

        return raperos1;

    }

    /**
     * Elimina la mitad de los raperos en la lista de raperos.
     * @param nombreRapero nombre del rapero "logeado" para conocer si se ha eliminado.
     * @return  indice nuevo del rapero "logeado" o -1 en caso de ser eliminado.
     */
    public int filtrarRaperos(String nombreRapero) {

        int hastaQue = cuantosRaperos()/2;  //encontrar 1/2 de raperos

        for(int i = cuantosRaperos(); i != hastaQue; i--) { //eliminarlos hasta tener la mitad

            raperos.remove(i-1);

        }

        for (int i = 0; i < cuantosRaperos(); i++) {    //obtener la posicion del rapero logeado

            if(raperos.get(i).getNombreArtistico().equals(nombreRapero)) {
                return i;
            }

        }

        return -1;  //devolver -1 si no esta: se ha descalificado

    }

    /**
     * Elimina todos los raperos exepto los dos con mayor puntuacion en la lista de raperos.
     * @param nombreRapero nombre del rapero "logeado" para conocer si se ha eliminado.
     * @return indice del rapero "logeado" o -1 en caso de ser eliminado.
     */
    public int eliminacionFinal(String nombreRapero){

        for(int i = cuantosRaperos(); i > 2; i--){  //eliminar todos exepto los dos primeros
            raperos.remove(i-1);

        }

        for (int i = 0; i < cuantosRaperos(); i++) {    //encontrar la posicion del rapero logeado

            if(raperos.get(i).getNombreArtistico().equals(nombreRapero)) {
                return i;
            }

        }
        return -1;  //devolver -1 si se ha eliminado

    }

    /**
     * Devuelve el indice del nombre artistico del rapero introducido o -1 en caso de estar ausente.
     * @param nombreRapero nombre artistico del rapero a buscar.
     * @return indice del rapero con nombre artistico introducido o -1 en caso de estar ausente.
     */
    public int buscarRapero(String nombreRapero){
        for (int i = 0; i < cuantosRaperos(); i++) {    //por cada rapero

            if(raperos.get(i).getNombreArtistico().equals(nombreRapero)) {  //comprobar si somos el señalado
                return i;   //return posicion
            }

        }
        return -1;  //return -1 eliminado
    }


    /**
     * Devuelve una nueva lista de raperos copiada para no usar la referencia.
     * @param rapprs lista de raperos
     * @param isCreated boolean para saber si ya existe la lista
     */
    public void auxiliarRapprs(List<Rapero> rapprs, boolean isCreated) {

        if(!isCreated) {

            auxiliarRaperos = new ArrayList<Rapero>();
            auxiliarRaperos.addAll(new ArrayList<Rapero>(rapprs));

        }

    }

    /**
     * Funcion que busca si el rapero existe tanto por nombre como por nickname
     * @param nombreRapero nombre real del rapero a buscar
     * @param artisticName nombre artistico a buscar
     * @return posicion del rapero o "-1" si no se encuentra en la lista
     */
    public int buscarRaperoByName(String nombreRapero, boolean artisticName){

        if(artisticName) {


            for (int i = 0; i < auxiliarRaperos.size(); i++) {    //por cada rapero

                if(auxiliarRaperos.get(i).getNombreArtistico().equals(nombreRapero)) {  //comprobar si somos el señalado
                    return i;   //return posicion
                }

            }

        }else {

            for (int i = 0; i < auxiliarRaperos.size(); i++) {    //por cada rapero

                if(auxiliarRaperos.get(i).getNombre().equals(nombreRapero)) {  //comprobar si somos el señalado
                    return i;   //return posicion
                }

            }

        }


        return -1;  //return -1 eliminado o inexistente

    }

    /**
     * Devuelve un rapero de la lista auxiliar.
     * @param whatToGet indice del rapero a obtener
     * @return rapero esogido por indice
     */
    public Rapero obtenerRaperoAuxiliar(int whatToGet) {
        return(auxiliarRaperos.get(whatToGet));
    }


    /**
     * Funcion que ordena la lista auxiliar de raperos (copiada) segun la puntuacion.
     */
    public void sortAuxiliarRaperos() {

        auxiliarRaperos.sort(new Comparator<Rapero>() {
            /**
             * Compara dos objetos de tipo Rapero para deducir cual es mayor, menor o si son iguales.
             * @param o1 objeto A
             * @param o2 objeto B
             * @return menor a 0, mayor a 0 o igual a 0 si el primero es menor, mayor o igual al segundo.
             */
            @Override
            public int compare(Rapero o1, Rapero o2) {  //crear el metodo de ordenacion, de mayor a menor pts.

                Double puntuacion1 = o1.getScore();
                Double puntuacion2 = o2.getScore();

                return puntuacion2.compareTo(puntuacion1);
            }
        });

    }

}//END