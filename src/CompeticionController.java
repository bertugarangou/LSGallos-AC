import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 * Clase con las acciones de la competicion. Simula, comprueva, calcula...
 * @version 1.0
 * @author  Christian Hasko y Albert Garangou
 */
public class CompeticionController {

    /**
     * lector de ficheros
     */
    private JSONReader reader = new JSONReader();
    /**
     * competicion general para guardar los datos y ejecuciones
     */
    private Competicion competicion;
    /**
     * Menu para mostrar todos los datos y recogerlos
     */
    private Menu menu = new Menu();

    /**
     * Crear el generador del perfil
     */
    private GeneradorPerfil generadorPerfil = new GeneradorPerfil();


    /**
     * Constructor.
     */
    public CompeticionController() {
    }


    /**
     * Abrir el fichero de la competicion.
     * @throws FileNotFoundException si no se encuentra o contiene errores.
     */
    public void readJsonCompetition() throws FileNotFoundException {
        competicion = reader.openCompetition();

    }

    /**
     * Ejecutar e iniciar la competicion.
     * @throws FileNotFoundException si no se encuentra el fichero solicitado o contiene errores.
     */
    public void executeCompetition() throws FileNotFoundException {
        boolean exit = false;   //flag salir
        String option;          //opcion introducida
        int indiceRapero;       //indice rapero logeado
        String nuestroRapero;   //nombre rapero logeado

        menu.mostrarInfoMenu(competicion);  //mostrar info basica

        do {    //comprobar fechas
            if (compareDates(competicion.getFechaInicio(), competicion.getFechaFinal()) == -1) {  //no empezada

                menu.basicMenu(1);
                option = menu.askWhatToDo();

                //leer menu
                while (!option.equals("1") && !option.equals("2")) {

                    menu.basicMenu(-1);
                    option = menu.askWhatToDo();

                }
                if (!option.equals("2")) {

                    registrarRapero(competicion.getFechaInicio(), competicion.getFechaFinal());

                    reader.writeCompetition(competicion);

                } else {
                    exit = true;
                }
            } else if (compareDates(competicion.getFechaInicio(), competicion.getFechaFinal()) == 0) { //en curso

                //leer menu
                menu.basicMenu(2);
                option = menu.askWhatToDo();
                while (!option.equals("1") && !option.equals("2")) {

                    menu.basicMenu(-2);
                    option = menu.askWhatToDo();

                }
                if (!option.equals("2")) {  //sim batalla

                    indiceRapero = loginRapero();

                    if (indiceRapero == -1) {   //no existe el rapero

                    }else {

                        if(competicion.cuantasFases() == 3) {   //si hay que hacer 3 fases

                            competicion.auxiliarRapprs(competicion.getRaperos(), false);

                            nuestroRapero = competicion.obtenerRapero(indiceRapero).getNombreArtistico();

                            indiceRapero = competicion.escogerRaperos(indiceRapero, nuestroRapero); //pairings y eliminacion del rapero impar

                            Random oponente = new Random();
                            int indiceOponente;
                            do {    //escoger un oponente
                                indiceOponente = oponente.nextInt((competicion.cuantosRaperos()) - 2) + 2;
                            } while (indiceOponente == indiceRapero);
                            //simular
                            simularOponentes(0, indiceRapero, indiceOponente, false);
                            //leer menu
                            exit = startCompetition(0, indiceRapero, indiceOponente);

                            Double puntuacionHastaElMomento = competicion.obtenerRapero(indiceRapero).getScore();

                            competicion.sortRaperos();  //ordenar para ranking

                            int indiceTemporal = competicion.buscarRapero(nuestroRapero);

                            //----------------------------------

                            //fase 2
                            indiceRapero = competicion.filtrarRaperos(nuestroRapero);

                            if (exit) { //si se sale

                                if(indiceRapero == -1) {    //si ya no jugamos
                                    //simular fases restantes sin nostros
                                    indiceRapero = competicion.escogerRaperos(indiceRapero, nuestroRapero);

                                    simularOponentes(1, -1, -1, true);

                                    competicion.sortRaperos();

                                    indiceRapero = competicion.eliminacionFinal(nuestroRapero);

                                    simularOponentes(2, -1, -1, true);

                                }else { //si aun jugamos
                                    //simular normal
                                    simularOponentes(1, indiceRapero, indiceOponente, false);

                                    competicion.sortRaperos();

                                    indiceRapero = competicion.eliminacionFinal(nuestroRapero);

                                    simularOponentes(2, -1, -1, true);


                                }

                                break;
                            }
                            //si se sige jugando
                            if(indiceRapero == -1) {    //eliminados
                                //terminar de simular
                                indiceRapero = competicion.escogerRaperos(indiceRapero, nuestroRapero);

                                simularOponentes(1, -1, -1, true);

                                competicion.sortRaperos();

                                indiceRapero = competicion.eliminacionFinal(nuestroRapero);

                                simularOponentes(2, -1, -1, true);

                                exit = menuLobbyDesactivado(puntuacionHastaElMomento, 1, 0, nuestroRapero, indiceTemporal+1, false);


                            }else {
                                //seguir jugando
                                indiceRapero = competicion.escogerRaperos(indiceRapero, nuestroRapero);

                                do {    //encontrar oponente
                                    indiceOponente = oponente.nextInt((competicion.cuantosRaperos()) - 2) + 2;
                                } while (indiceOponente == indiceRapero);

                                //simular
                                simularOponentes(1, indiceRapero, indiceOponente, false);

                                exit = startCompetition(1, indiceRapero, indiceOponente);

                                //----------------------

                                puntuacionHastaElMomento = competicion.obtenerRapero(indiceRapero).getScore();

                                competicion.sortRaperos();

                                indiceTemporal = competicion.buscarRapero(nuestroRapero);

                                //------------------------------

                                indiceRapero = competicion.eliminacionFinal(nuestroRapero);

                                if (exit) { //si se sale

                                    if(indiceRapero == -1) {    //si no jugamos

                                        simularOponentes(2, -1, -1, true);

                                    }else { //terminar fase 3

                                        simularOponentes(2, indiceRapero, -1, true);

                                    }

                                    break;
                                }

                                if(indiceRapero == -1){ //si eliminados pero seguimos

                                    simularOponentes(1, -1, -1, true);

                                    exit = menuLobbyDesactivado(puntuacionHastaElMomento, 2, 0, nuestroRapero, indiceTemporal+1, false);
                                }else { //no eliminados y no salimos


                                    do {    //fase 3
                                        indiceOponente = oponente.nextInt((competicion.cuantosRaperos()) - 1) + 1;
                                    } while (indiceOponente == indiceRapero);

                                    exit = startCompetition(2, indiceRapero, indiceOponente);

                                    if (exit) { //esperar a que se salga
                                        simularOponentes(2, indiceRapero, -1, true);
                                        break;
                                    }
                                    //leer si se sale (general bucle)
                                    exit = menuLobbyDesactivado(competicion.obtenerRapero(indiceRapero).getScore(), 3, 1, nuestroRapero, -1, true);

                                }



                            }

                            //------


                        }else { //2 fases

                            competicion.auxiliarRapprs(competicion.getRaperos(), false);

                            //fase 1
                            nuestroRapero = competicion.obtenerRapero(indiceRapero).getNombreArtistico();

                            indiceRapero = competicion.escogerRaperos(indiceRapero, nuestroRapero); //pairings y eliminacion del rapero impar

                            Random oponente = new Random();
                            int indiceOponente;
                            do {    //oponente
                                indiceOponente = oponente.nextInt((competicion.cuantosRaperos()) - 2) + 2;
                            } while (indiceOponente == indiceRapero);
                            //simular
                            simularOponentes(0, indiceRapero, indiceOponente, false);

                            exit = startCompetition(0, indiceRapero, indiceOponente);

                            Double puntuacionHastaElMomento = competicion.obtenerRapero(indiceRapero).getScore();

                            competicion.sortRaperos();

                            int indiceTemporal = competicion.buscarRapero(nuestroRapero);
                            //fase 3

                            indiceRapero = competicion.eliminacionFinal(nuestroRapero);

                            if (exit) { //fase 2 si se ha salido
                                if(indiceRapero == -1) {    //sin jugar
                                    simularOponentes(1, -1, -1, true);
                                }else { //aun jugamos
                                    simularOponentes(1, indiceRapero, -1, true);
                                }
                                break;
                            }

                            if(indiceRapero == -1){ //fase 2 eliminados

                                simularOponentes(1, -1, -1, true);

                                exit = menuLobbyDesactivado(puntuacionHastaElMomento, 2, 0, nuestroRapero, indiceTemporal+1, false);


                            }else { //fase dos y jugamos


                                do {    //encontrar oponente
                                    indiceOponente = oponente.nextInt((competicion.cuantosRaperos()) - 1) + 1;
                                } while (indiceOponente == indiceRapero);

                                exit = startCompetition(1, indiceRapero, indiceOponente);

                                if (exit) { //esperar a salir
                                    simularOponentes(1, indiceRapero, -1, true);
                                    break;
                                }
                                //comporbacion general bucle
                                exit = menuLobbyDesactivado(competicion.obtenerRapero(indiceRapero).getScore(), 3, 1, nuestroRapero, -1, true);

                            }

                        }

                    }

                }else{  //flag salir
                    exit = true;
                }
            } else {  //competicion termianda, simular todos los raperos para ver quin deberia haber ganado

                menu.basicMenu(3);

                if(competicion.cuantasFases() == 3) {       //3 fases

                    simularOponentes(0, -1, -1, true);  //f1
                    competicion.sortRaperos();
                    competicion.filtrarRaperos(null);
                    simularOponentes(1, -1, -1, true);//f2
                    competicion.sortRaperos();
                    competicion.eliminacionFinal(null);
                    simularOponentes(2, -1, -1, true);//f3

                }else { // 2 fases

                    simularOponentes(0, -1, -1, true);//f1
                    competicion.sortRaperos();
                    competicion.eliminacionFinal(null);
                    simularOponentes(1, -1, -1, true);//f2

                }
                //mostrar ganador
                menu.showWinner(competicion.obtenerRapero(0).getNombreArtistico(), competicion.obtenerRapero(0).getScore());
                //esperar para salir
                menu.askWhatToDo();
                exit = true;    //flag salir

            }
        }while(!exit);

        menu.basicMenu(4);  //despedida
    }

    /**
     * Comprueva el orden de las fechas actual, inicial y final.
     * @param start fecha inicial.
     * @param end fecha final.
     * @return -1 si la fecha actual es mas atiga a la inicial, 1 si es posterior a la final o 0 si esta contenida entre las dos.
     */
    private int compareDates(LocalDate start, LocalDate end) {
        LocalDate current = LocalDate.now();

        if(current.isBefore(start)){ //check si no empezo
            return(-1);
        } else if(current.isAfter(end)){    //check si ya termino
            return(1);
        }
        return(0);//check estamos en el periodo
    }

    /**
     * Añade un rapero al fichero con todos los raperos proporcionados.
     * @param startDate fecha de inicio.
     * @param endDate fecha final.
     */
    private void registrarRapero(LocalDate startDate, LocalDate endDate) {
        ArrayList<String> registrarRapero;
        LocalDate nacimientoCorrecto;
        int error = 1;
        int levelCorrecto;

        registrarRapero = menu.registrarRapero();   //pedir datos

        for(int i = 0; i < competicion.cuantosRaperos(); i++) { //comprobar que no sea repetido

            if(registrarRapero.get(0).equals(competicion.obtenerRapero(i).getNombre())) {   //nombre
                error = 8;
            }else if(registrarRapero.get(1).equals(competicion.obtenerRapero(i).getNombreArtistico())) {    //mote
                error = 8;
            }

        }
        if(error == 8) {    //error dato incorrecto del login

            menu.errorAlRegistrar(8);
        }else {

            try {   //fecha mal
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
                nacimientoCorrecto = LocalDate.parse(registrarRapero.get(2), dateTimeFormatter);

                if (nacimientoCorrecto.isAfter(endDate)) {  //fecha mal tipo de error
                    menu.errorAlRegistrar(2);
                } else if (nacimientoCorrecto.isAfter(startDate)) {
                    menu.errorAlRegistrar(1);
                } else if (nacimientoCorrecto.isBefore(LocalDate.now().minusYears(120))) {
                    menu.errorAlRegistrar(3);
                } else {

                    for (int i = 0; i < competicion.cuantosPaises()/*paises.cuantosPaises()*/; i++) {

                        if (registrarRapero.get(3).equals(competicion.quePais(i)/*paises.quePais(i)*/)) {
                            error = 0;
                        } else if (registrarRapero.get(3).equalsIgnoreCase(competicion.quePais(i)/*paises.quePais(i)*/)) {
                            error = 2;
                        }

                    }
                    if (error == 1) {   //errores paises
                        menu.errorAlRegistrar(4);
                    } else if (error == 2) {
                        menu.errorAlRegistrar(5);
                    } else {

                        try {
                            levelCorrecto = Integer.parseInt(registrarRapero.get(4));
                            competicion.registrarRapero(registrarRapero.get(0), registrarRapero.get(1), nacimientoCorrecto, registrarRapero.get(3), levelCorrecto, registrarRapero.get(5));
                            menu.errorAlRegistrar(6);
                        } catch (NumberFormatException e) {
                            menu.errorAlRegistrar(7);
                        }

                    }

                }

            } catch (DateTimeParseException e) {
                menu.errorAlRegistrar(-1);
            }

        }



    }

    /**
     * Establece un rapero disponible como usuario "logeado".
     * @return indice del rapero "logeado" o -1 en su ausencia.
     */
    private int loginRapero() {

        String nombreIntroducido;

        nombreIntroducido = menu.loginRapero();

        for (int i = 0; i < competicion.cuantosRaperos(); i++) {    //encontrar que rapero hico el login

            if(nombreIntroducido.equals(competicion.obtenerRapero(i).getNombreArtistico())) {   //comporbar nombre login

                return i;

            }

        }

        menu.errorAlLogin(nombreIntroducido);   //no existe

        return -1;

    }

    /**
     * Inicializa la competicion.
     * @param faseToStart indice de la fase a inicializar.
     * @param indiceRapero indice del rapero "logeado"
     * @param indiceOponente indice del rapero oponente al "logeado"
     * @return boolean true en caso de la ejecucion recurrente o false si se exige salir de la competicion.
     * @throws FileNotFoundException si no se ha encontrado el fichero solicitado competicion o contiene errores.
     */
    private boolean startCompetition(int faseToStart, int indiceRapero, int indiceOponente) throws FileNotFoundException {

        int option = 0;
        int battleCounter = 0;

        do {    //por cada batalla

            option = 0;

            Random r = new Random();
            int batallaRandom = r.nextInt(4-1) + 1;

            competicion.start(reader.leerBatallas(), faseToStart, batallaRandom);   //empezar compe



            do {    //comprobar opcion menu lobby

                menu.lobbyInfo(faseToStart + 1, competicion.cuantasFases(), competicion.obtenerPuntuacion(indiceRapero), battleCounter+1, batallaRandom, competicion.obtenerRapero(indiceOponente).getNombreArtistico());

                try {
                    option = Integer.parseInt(menu.askWhatToDo());
                    if (option < 1 || option > 4) {
                        menu.errorLobby(-2);
                    }
                } catch (NumberFormatException e) {
                    menu.errorLobby(-1);
                }

            } while (option < 1 || option > 4);


            if(option == 1) {   //op1

                battleCounter++;

                Random topic = new Random();
                int topicRandom = topic.nextInt((competicion.getTopics(faseToStart, batallaRandom) + 1) - 1) + 1;

                Random rapperStart = new Random();
                int randomRapperStart = rapperStart.nextInt(3-1)+1;

                zonaBarras(randomRapperStart, faseToStart, batallaRandom, topicRandom, indiceOponente, indiceRapero);

            }else if(option == 2) { //ranking

                menu.mostrarRanking(competicion.obtenerRapero(indiceRapero).getNombreArtistico(), competicion.getSortedRaperos(), competicion.obtenerPuntuacion(indiceRapero), false, -1, false);

            }else if(option == 3) { //op3

                competicion.sortAuxiliarRaperos();
                creadorPerfiles();


            }else {
                return true;
            }

        }while (battleCounter != 2);

        return false;

    }

    /**
     * Proceso de competicion del usuario "logeado" contra su oponente, eligiendo cual empieza, escogiendo estrofas y exigiendo su introduccion en caso de ser el turno del usuario "logeado".
     * @param randomRapperStart indice del usuario inicial.
     * @param faseToStart fase a tratar.
     * @param batallaRandom tipo de batalla. Puede ser escogida entre 3.
     * @param topicRandom tema de la batalla.
     * @param indiceOponente indice del contrincante.
     * @param indiceRapero indice del usuario "logeado".
     */
    private void zonaBarras(int randomRapperStart, int faseToStart, int batallaRandom, int topicRandom, int indiceOponente, int indiceRapero) {

        ArrayList<String> estrofastmp = new ArrayList<>();


        if (randomRapperStart == 1/*oponente*/) {

            menu.battleInfo(1, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceOponente).getNombreArtistico());

            for (int i = 0; i < 2; i++) {

                menu.battleInfo(4, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceOponente).getNombreArtistico());

                if (competicion.obtenerRapero(indiceOponente).getNivel() == 1) {

                    try {
                        menu.pintorEstrofas(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i));
                    } catch (IndexOutOfBoundsException e) {
                        menu.errorLobby(-3);
                    }

                } else if (competicion.obtenerRapero(indiceOponente).getNivel() == 2) {

                    try {
                        menu.pintorEstrofas(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i));
                    } catch (IndexOutOfBoundsException e) {
                        menu.errorLobby(-3);
                    }
                } else {
                    menu.errorLobby(-3);
                }

                ArrayList<String> estrofasoponentetmp2 = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp) {
                        estrofasoponentetmp2.add(s.replaceAll("[^A-Za-z0-9]", ""));
                    }
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp2, batallaRandom, faseToStart), batallaRandom, faseToStart));
                    //System.out.println(competicion.obtenerRapero(indiceOponente).getScore());
                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp2, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }



                menu.battleInfo(3, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceRapero).getNombreArtistico());

                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));

                competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofastmp, batallaRandom, faseToStart), batallaRandom, faseToStart));
                estrofastmp.removeAll(estrofastmp);


            }


        } else /*nuestro rapero*/ {

            menu.battleInfo(2, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceRapero).getNombreArtistico());

            for (int i = 0; i < 2; i++) {

                if (i == 0) {
                    menu.battleInfo(5, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceRapero).getNombreArtistico());
                } else {
                    menu.battleInfo(3, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceRapero).getNombreArtistico());

                }

                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));
                estrofastmp.add(menu.askWhatToDo().replaceAll("[^A-Za-z0-9]",""));

                competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofastmp, batallaRandom, faseToStart), batallaRandom, faseToStart));
                estrofastmp.removeAll(estrofastmp);

                menu.battleInfo(4, competicion.getNameTopic(faseToStart, batallaRandom, topicRandom), competicion.obtenerRapero(indiceOponente).getNombreArtistico());

                if (competicion.obtenerRapero(indiceOponente).getNivel() == 1) {

                    try {
                        menu.pintorEstrofas(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i));
                    } catch (IndexOutOfBoundsException e) {
                        menu.errorLobby(-3);
                    }

                } else if (competicion.obtenerRapero(indiceOponente).getNivel() == 2) {

                    try {
                        menu.pintorEstrofas(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i));
                    } catch (IndexOutOfBoundsException e) {
                        menu.errorLobby(-3);
                    }
                } else {
                    menu.errorLobby(-3);
                }

                ArrayList<String> estrofasoponentetmp2 = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp) {
                        estrofasoponentetmp2.add(s.replaceAll("[^A-Za-z0-9]", ""));
                    }
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp2, batallaRandom, faseToStart), batallaRandom, faseToStart));
                    //System.out.println(competicion.obtenerRapero(indiceOponente).getScore());
                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp2, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }

            }


        }
    }

    /**
     * Simulacion de las batallas de los usuarios CPU (no son y/o no compiten contra el rapero "logeado"):
     * @param faseToStart fase a tratar.
     * @param indiceRapero indice del rapero "logeado" para evitarlo.
     * @param indiceOponente indice del rapero contrincante para evitarlo.
     * @param desactivated estado del usuario "logeado": true si no puede participar mas o false si aun esta clasificado.
     * @throws FileNotFoundException si no se encuentra o presenta errores el fichero solicitado.
     */
    private void simularOponentes(int faseToStart, int indiceRapero, int indiceOponente, boolean desactivated) throws FileNotFoundException {

        if(desactivated) {  //comprobar si el rapero logeado puede jugar para simular todas las batallas o solo la siguiente

            for (int i = 0; i < (competicion.cuantosRaperos() - 1); i++) {  //simular todos los CPU

                Random r = new Random();
                int batallaRandom = r.nextInt(4 - 1) + 1;

                competicion.start(reader.leerBatallas(), faseToStart, batallaRandom);

                Random topic = new Random();
                int topicRandom = topic.nextInt((competicion.getTopics(faseToStart, batallaRandom) + 1) - 1) + 1;

                Random rapperStart = new Random();
                int randomRapperStart = rapperStart.nextInt(3 - 1) + 1;

                zonaSimulacionBarras(randomRapperStart, faseToStart, batallaRandom, topicRandom, i + 1, i);

            }


        }else { //simular todos menos el nuestro que perdio

            for (int i = 0; i < (competicion.cuantosRaperos() - 1); i++) {  //cada uno


                if (competicion.obtenerRapero(i).getNombreArtistico().equals(competicion.obtenerRapero(indiceRapero).getNombreArtistico()) || competicion.obtenerRapero(i).getNombreArtistico().equals(competicion.obtenerRapero(indiceOponente).getNombreArtistico())) {
                    //empty, evitar simular el nuestro
                } else {

                    Random r = new Random();
                    int batallaRandom = r.nextInt(4 - 1) + 1;

                    competicion.start(reader.leerBatallas(), faseToStart, batallaRandom);

                    Random topic = new Random();
                    int topicRandom = topic.nextInt((competicion.getTopics(faseToStart, batallaRandom) + 1) - 1) + 1;

                    Random rapperStart = new Random();
                    int randomRapperStart = rapperStart.nextInt(3 - 1) + 1;

                    try {
                        if (competicion.obtenerRapero(i + 1).getNombreArtistico().equals(competicion.obtenerRapero(indiceRapero).getNombreArtistico()) || competicion.obtenerRapero(i + 1).getNombreArtistico().equals(competicion.obtenerRapero(indiceOponente).getNombreArtistico())) {
                            zonaSimulacionBarras(randomRapperStart, faseToStart, batallaRandom, topicRandom, i + 2, i);
                        } else {
                            zonaSimulacionBarras(randomRapperStart, faseToStart, batallaRandom, topicRandom, i + 1, i);
                        }
                    } catch (IndexOutOfBoundsException e) {
                        //empty
                    }


                }


            }

        }

    }

    /**
     * Proceso de simulacion de la competicion para los usuarios que no son el "logeado". Operaciones con las estrofas y exigir calcular la puntuacion.
     * @param randomRapperStart indice del usuario inicial.
     * @param faseToStart fase a tratar.
     * @param batallaRandom tipo de batalla.
     * @param topicRandom tema elegido.
     * @param indiceOponente indice del oponente del usuario "logeado".
     * @param indiceRapero indice del usuario "logeado".
     */
    private void zonaSimulacionBarras(int randomRapperStart, int faseToStart, int batallaRandom, int topicRandom, int indiceOponente, int indiceRapero) {

        if (randomRapperStart == 1/*oponente*/) {

            for (int i = 0; i < 2; i++) {

                ArrayList<String> estrofasoponentetmp = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp2 = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp2) {
                        estrofasoponentetmp.add(s.replaceAll("[^A-Za-z0-9]", ""));
                        /*
                        rima asonante: solo letras
                        rima consonante: letras y entonacion
                         */
                    }
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp, batallaRandom, faseToStart), batallaRandom, faseToStart));

                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }

                //-------------------------------


                ArrayList<String> estrofasoponentetmp3 = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp4 = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceRapero).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp4) {
                        estrofasoponentetmp3.add(s.replaceAll("[^A-Za-z0-9]", "")); //limpiar caracteres para tener solo letras
                        /*
                        rima asonante: solo letras
                        rima consonante: letras y entonacion
                         */
                    }
                    competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp3, batallaRandom, faseToStart), batallaRandom, faseToStart));

                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp3, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }



            }


        } else /*nuestro rapero oponente*/ {


            for (int i = 0; i < 2; i++) {

                ArrayList<String> estrofasoponentetmp3 = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp4 = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceRapero).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp4) {
                        estrofasoponentetmp3.add(s.replaceAll("[^A-Za-z0-9]", ""));
                        /*
                        rima asonante: solo letras
                        rima consonante: letras y entonacion
                         */
                    }
                    competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp3, batallaRandom, faseToStart), batallaRandom, faseToStart));

                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceRapero, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp3, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }


                //---------------------


                ArrayList<String> estrofasoponentetmp = new ArrayList<>();
                try {
                    ArrayList<String> estrofasoponentetmp2 = new ArrayList<>(Arrays.asList(competicion.getEstrofas(faseToStart, batallaRandom, topicRandom - 1, competicion.obtenerRapero(indiceOponente).getNivel() - 1, i).split("\n")));
                    for (String s : estrofasoponentetmp2) {
                        estrofasoponentetmp.add(s.replaceAll("[^A-Za-z0-9]", ""));
                        /*
                        rima asonante: solo letras
                        rima consonante: letras y entonacion
                         */
                    }
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp, batallaRandom, faseToStart), batallaRandom, faseToStart));

                }catch (IndexOutOfBoundsException e) {
                    competicion.setPuntuacion(indiceOponente, competicion.calcularPuntuacion(competicion.encontrarRimas(estrofasoponentetmp, batallaRandom, faseToStart), batallaRandom, faseToStart));
                }



            }



        }



    }

    /**
     * Control del menu en casos donde las batallas estan desactivadas.
     * @param puntuacionHastaElMomento cantidad de puntuacion del usuario hasta el momento.
     * @param fase fase actual
     * @param lastBatle ultima batalla (donde ya no se puede avanzar).
     * @param nuestroRapero nombre del rapero "logeado".
     * @param indiceTemporal indice del rapero "logeado".
     * @param isFinal true si se ha terminado la competicion o false si el usuario ha sido descalificado.
     * @return : true en caso de exigir la finalizacion del programa.
     */
    private boolean menuLobbyDesactivado(Double puntuacionHastaElMomento, int fase, int lastBatle, String nuestroRapero, int indiceTemporal, boolean isFinal){
        int option = 0;

        do {

            do {
                //menu con opciones desactivadas
                option = 0;
                menu.lobbyInfoDesactivado(fase, competicion.cuantasFases(), puntuacionHastaElMomento, lastBatle);

                try {   //encontrar opcion
                    option = Integer.parseInt(menu.askWhatToDo());
                    if (option < 1 || option > 4) {
                        menu.errorLobby(-2);
                    }
                } catch (NumberFormatException e) {
                    menu.errorLobby(-1);
                }

            } while (option < 1 || option > 4);

            if (option == 1) {

                menu.errorLobby(-4);

            } else if (option == 2) {

                competicion.sortRaperos();
                menu.mostrarRanking(nuestroRapero, competicion.getRaperos(), puntuacionHastaElMomento, true, indiceTemporal, isFinal);


            } else if (option == 3) {   //codigo fase 4

                competicion.sortAuxiliarRaperos();
                creadorPerfiles();

            } else {
                return true;
            }

        }while (option != 4);   //se sale

        return false;   //salir
    }

    /**
     * Metodo para crear el perfil. Engloba todos los procedimientos necesarios.
     */
    private void creadorPerfiles() {
        String name = null;
        int num;

        name = menu.askRaperoPerfil();
        num = competicion.buscarRaperoByName(name, true);

        if (competicion.buscarRaperoByName(name, true) != -1) {
            generadorPerfil.generarPerfil(competicion.obtenerRaperoAuxiliar(num), num);
        }else{
            num = competicion.buscarRaperoByName(name, false);
            if (competicion.buscarRaperoByName(name, false) != -1) {
                generadorPerfil.generarPerfil(competicion.obtenerRaperoAuxiliar(num), num);

            }else{
                menu.errorAtProfile(-1);
            }

        }
    }




}//END