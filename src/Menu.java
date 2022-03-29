import java.util.*;

/**
 * Clase con las operaciones de mostrar datos por la consola y recibir datos por ella.
 */
public class Menu {

    /**
     * Constructor.
     */
    public Menu() {

    }

    /**
     * Mostrar informacion principal, menu de opciones.
     * @param competicion objeto Competicion con los datos a mostrar.
     */
    public void mostrarInfoMenu(Competicion competicion) {
        /*
        Para obtener una cadena de caracteres (string) concatenada se puede usar StringBuilder.append o hacer sumas de strings.
        En este caso se utiliza el metodo que permite ahorrar memoria pero incrementa considerablemente el numero de lineas.
        A partir de este uso, los demas seran con sumas de string ya que la cantidad de memoria utilizada no afecta el rendimiento.
        Si se utilizase para un codigo mucho mas mayor, si se deberia usar StringBuilder.apped u otra alternativa a "str1 + str2"
         */
        StringBuilder sb = new StringBuilder(); //crear

        //mostrar los datos iniciales de la competicion
        sb.append("Welcome to competition: "); sb.append(competicion.getNombreCompeticion()); sb.append(" \uD83E\uDD20");
        System.out.println(sb);
        sb.delete(0, sb.length());

        sb.append("Starts on "); sb.append(competicion.getFechaInicio());
        System.out.println(sb);
        sb.delete(0, sb.length());

        sb.append("Ends on "); sb.append(competicion.getFechaFinal());
        System.out.println(sb);
        sb.delete(0, sb.length());

        sb.append("Phases: "); sb.append(competicion.cuantasFases());
        System.out.println(sb);
        sb.delete(0, sb.length());

        sb.append("Currently: "); sb.append(competicion.cuantosRaperos()); sb.append(" participants");
        System.out.println(sb);
        sb.delete(0, sb.length());

    }

    /**
     * Metodo para recibir datos de la consola.
     * @return texto introducido.
     */
    public String askWhatToDo() {

        //declarar escaner y return del input de la consola
        Scanner scanner = new Scanner(System.in);

        return(scanner.nextLine());

    }

    /**
     * Mostrar informacion inicial sobre el estado de la competicion, errores de introducion de opciones y mensage de despedida..
     * @param whatToShow opcion para escoger que texto mostrar, pudiendo ser positivo para informacion y negativo para errores:
     *                   1: Competicion no iniciada.
     *                   2: Competicion en curso.
     *                   3: Competicion terminada.
     *                   4: Mensage de despedida.
     *                   -1: Opcion incorrecta (competicion no iniciada).
     *                   -2: Opcion incorrecta (competicion en curso).
     */
    public void basicMenu(int whatToShow) {

        //dependiendo de la peticion {whatToShow} mostrar un mensage u otro.
        /*
        Se podria haber utilizado clases de exceptions pero ya que solo se muestra un error por cada excepcion seria incrementar
        considerablemente el codigo para solo un mensage System.out.print.
         */
        if(whatToShow == 1) {
            System.out.print("\nCompetition has NOT started yet. Do you want to:\n\n1. Register\n2. Leave\n\nChoose an option: ");
        }else if(whatToShow == -1) {
            System.out.println("\nYou mama so fat u entered a macmenu and not a correct number.");
            System.out.print("Valid options: \n\n1. Register\n2. Leave\n\nChoose an option: ");
        }else if(whatToShow == -2) {
            System.out.println("\nYou mama so fat u entered a macmenu and not a correct number.");
            System.out.print("Valid options: \n\n1. Login\n2. Leave\n\nChoose an option: ");
        }else if(whatToShow == 2) {
            System.out.print("\nCompetition has already STARTED. Do you want to:\n\n1. Login\n2. Leave\n\nChoose an option: ");
        }else if(whatToShow == 3) {
            System.out.print("\nCompetition has already ENDED.");
        }else if(whatToShow == 4) {
            System.out.println("\nBA-BAY DUDE");
            System.out.println("\n\n\nChristian Hasko    &    Albert Garangou\n\n\n");
        }

    }

    /**
     * Metodo para pedir al usuario los datos de registro de un rapero.
     * @return ArrayList con los datos del usuario registrado.
     */
    public ArrayList<String> registrarRapero(){
        ArrayList<String> registered = new ArrayList<>();   //crear una lista con los datos a registrar

        System.out.println("\n--------------------------------------------------\nPlease, enter your personal information");

        System.out.print("- Full name: ");  //leer nombre
        registered.add(askWhatToDo());

        System.out.print("- Artistic name: ");  //leer mote
        registered.add(askWhatToDo());

        System.out.print("- Birth date (dd/MM/YYYY): ");    //leer fecha
        registered.add(askWhatToDo());

        System.out.print("- Country: ");    //leer pais
        registered.add(askWhatToDo());

        System.out.print("- Level: ");  //leer nivel
        registered.add(askWhatToDo());

        System.out.print("- Photo URL: ");  //leer foto
        registered.add(askWhatToDo());

        return(registered); //return con la lista de todos los datos introducidos

    }

    /**
     * Mostrar errores por fallos al registrar raperos.
     * @param whatToShow tipo de error:
     *                   -1: fecha incorrecta.
     *                   2: fecha demasiado reciente.
     *                   3: fecha demasiado mayor.
     *                   4: país no permitido.
     *                   5: país mal escrito.
     *                   7: nivel incorrecto.
     *
     *                   6: registro correcto.
     */
    public void errorAlRegistrar(int whatToShow) {
        //dependiendo de la peticion {whatToShow} mostrar un mensage u otro acerca del registro
        /*
        Se podria haber utilizado clases de exceptions pero ya que solo se muestra un error por cada excepcion seria incrementar
        considerablemente el codigo para solo un mensage System.out.print.
         */
        if(whatToShow == -1) {
            System.out.println("\nYo' IDK what type of calendar you know but this is not a valid date..");
        }else if(whatToShow == 1) {
            System.out.println("\nYo' you can't be born in the middle of the competition..");
        }else if(whatToShow == 2) {
            System.out.println("\nYo' you can't participate if you don't exist..");
        }else if(whatToShow == 3) {
            System.out.println("\nYo' how are you still alive today?\nUnfortunately our rapprs do not dare to compete with someone with your age.");
        }else if(whatToShow == 4) {
            System.out.println("\nYo' unfortunately your country is not in the list.. See ya next time!");
        }else if(whatToShow == 5) {
            System.out.println("\nYo' your country is in the list but.. learn to write!\nRemember that a country has to be written with UPPERCASE the first letter, it's case sensitive..");
        }else if(whatToShow == 6){
            System.out.println("\nRegistration completed! You are in da list \uD83E\uDD18");
        }else if(whatToShow == 7) {
            System.out.println("\nYo' bro, your level is not a level..");
        }else if(whatToShow == 8) {
            System.out.println("\nYo' bro, your're already in da list..");
        }

        System.out.print("--------------------------------------------------\n");
    }

    /**
     * Mostrar la peticion del nombre artístico.
     * @return nombre artístico.
     */
    public String loginRapero() {
        //mensage peticion login

        System.out.print("\nEnter your artistic name: ");

        return (askWhatToDo()); //devolver el nombre


    }

    /**
     * Mostrar mensage de error, nombre de "login" no encontrado.
     * @param nombre nombre incorrecto.
     */
    public void errorAlLogin(String nombre) {

        //mensage de error del login
        System.out.println("\nYo' bro, there's no '" + nombre + "' in ma' list.");

    }

    /**
     * Mostrar datos del "lobby".
     * @param faseActual fase actual.
     * @param cuantasFases cantidad maxima de fases.
     * @param puntuacion puntuacion actual.
     * @param battleCounter batalla actual.
     * @param batallaRandom tipo de batalla. Pueden ser 3.
     * @param nombreOponente nombre del contrincante.
     */
    public void lobbyInfo(int faseActual, int cuantasFases, Double puntuacion, int battleCounter, int batallaRandom, String nombreOponente) {
        //cabecera
        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Phase: " +faseActual+ " / " +cuantasFases);   //fase
        System.out.print(" | ");
        System.out.print("Score: ");
        if(puntuacion == 0) {   //obtener la puntuacion y en caso de ser 0 mostrar un mensage
            System.out.print("\033[0;96m" +"We are waiting for you to do something..." + "\u001B[0m");
        }
        else {
            System.out.format("\033[0;93m" + "%.2f" + "\033[0m", puntuacion);
        }
        System.out.print(" | ");
        System.out.print("Battle " +battleCounter+ " / 2: ");   //batalla
        if(batallaRandom == 1) {
            System.out.print("acapella");
        }else if(batallaRandom == 2) {
            System.out.print("escrita");
        }else if(batallaRandom == 3) {
            System.out.print("asangre");
        }
        System.out.print(" | ");    //rival
        System.out.println("Rival: " + "\033[1;95m" +nombreOponente + "\u001B[0m");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        //menu
        System.out.print("\n1. Start the battle\n2. Show ranking\n3. Create profile\n4. Leave competition\n\nChoose an option: ");

    }

    /**
     * Mostrar datos del lobby cuando la competicion esta desactivada.
     * @param faseActual índice de la fase actual.
     * @param cuantasFases maxima cantidad de fases.
     * @param puntuacion puntuacion actual.
     * @param lastBatle índice de la batalla donde se descalifico el usuario "logeado".
     */
    public void lobbyInfoDesactivado(int faseActual, int cuantasFases, Double puntuacion, int lastBatle){
        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.print("Phase: " +faseActual+ " / " +cuantasFases);   //fase
        System.out.print(" | ");
        System.out.print("Score: ");    //pts

        System.out.format("\033[0;93m" + "%.2f" + "\033[0m", puntuacion);
        System.out.print(" | ");

        if(lastBatle == 0) {    //mensage game over
            System.out.print("You've lost kid, I'm sure you'll do better next time...");
        }else{  //mensage win
            System.out.print("Congratulations! You are in the top tier!");
        }

        System.out.println("\n-------------------------------------------------------------------------------------------------------------------------------------------");

        //menu
        if(lastBatle == 0) {
            System.out.print("\n1. Start the battle (you are eliminated)\n2. Show ranking\n3. Create profile\n4. Leave competition\n\nChoose an option: ");
        }else{
            System.out.print("\n1. Start the battle (desacivated)\n2. Show ranking\n3. Create profile\n4. Leave competition\n\nChoose an option: ");
        }

    }

    /**
     * Mostrar errores del lobby.
     * @param whatToShow tipo de error:
     *                   -1: Numero incorrecto.
     *                   -2: opcion incorrecta.
     *                   -3: Sin estrofas disponibles.
     *                   -4: No se pueden hacer mas batallas.
     */
    public void errorLobby(int whatToShow) {
        //dependiendo de la peticion {whatToShow} mostrar un mensage u otro de error
        /*
        Se podria haber utilizado clases de exceptions pero ya que solo se muestra un error por cada excepcion seria incrementar
        considerablemente el codigo para solo un mensage System.out.print.
         */
        if(whatToShow == -1) {
            System.out.println("Yo' that's not a correct number..");
        }else if(whatToShow == -2) {
            System.out.println("Yo' that's not a valid option..");
        }else if(whatToShow == -3) {
            System.out.println("\nYo'.. idk what to say.");
        }else if(whatToShow == -4) {
            System.out.println("\nYou cannot battle anyone else!");
        }

    }

    /**
     * Mostrar informacion durante la batalla.
     * @param whatToShow tipo de mensage:
     *                   1: elecion del concursante inicial.
     *                   2: elecion del concursante inicial.
     *                   3: peticion escritura de verso + cambio de turno.
     *                   4: @deprecated.
     *                   5: peticion escritura de verso.
     *                   otros: nombre rapero inicial.
     * @param topicName tema de la batalla.
     * @param firstRapper nombre del rapero inicial.
     */
    public void battleInfo(int whatToShow, String topicName, String firstRapper) {
        //dependiendo de la peticion {whatToShow} mostrar un mensage u otro.
        /*
        Se podria haber utilizado clases de exceptions pero ya que solo se muestra un error por cada excepcion seria incrementar
        considerablemente el codigo para solo un mensage System.out.print.
         */
        if (whatToShow == 1) {  //empieza la fase y usuario A
            System.out.println("\n----------------------------------------------------");
            System.out.println("Topic: " + "\033[1;94m"+ topicName +"\u001B[0m");
            System.out.println("\nA coin is tossed in the air and...");
            System.out.println("\033[1;95m" + firstRapper + "\u001B[0m your turn! Drop it!");
        } else if (whatToShow == 2) {//empieza la fase y usuario B
            System.out.println("\n----------------------------------------------------");
            System.out.println("Topic: " + "\033[1;94m"+ topicName +"\u001B[0m");
            System.out.println("\nA coin is tossed in the air and...");
            System.out.println("\033[1;95m" + firstRapper + "\u001B[0m your turn! Drop it!");
        } else if (whatToShow == 3){    //siguiente ronda
            System.out.println("\n\033[1;95m" +firstRapper+ "\u001B[0m Your turn!");
            System.out.println("Enter your verse:\n");
        } else if (whatToShow == 5) {   //espera de input del verso
            System.out.println("\nEnter your verse:\n");
        } else {    //espera de input del contrincante (auto=
            System.out.println("\n\033[1;95m" +firstRapper+ "\u001B[0m:");
        }

    }

    /**
     * Mostrar estrofa.
     * @param estrofa estrofa a mostrar.
     */
    public void pintorEstrofas(String estrofa) {

        System.out.println("\n" + estrofa); //mostrar estrofa

    }

    /**
     * Mostrar ranking de concursantes.
     * @param nombreRapero nombre del rapero "logeado".
     * @param raperos lista de raperos a mostrar.
     * @param puntuacion puntuacion del rapero "logeado". Necesaria si el concursante no se encuentra en la lista de raperos.
     * @param desactivado estado de la competicion:
     *                    true: no se pueden hacer mas batallas.
     *                    false: estado predeterminado, se pueden hacer mas batallas.
     *                    Se utiliza para conocer si es un ranking final o temporal.
     * @param indiceTemporal índice del rapero "logeado".
     * @param isFinal estado de la competicion:
     *                false: no ha terminado la competicion.
     *                true: ranking final y competicion terminada.
     */
    public void mostrarRanking(String nombreRapero, List<Rapero> raperos, Double puntuacion, boolean desactivado, int indiceTemporal, boolean isFinal){

        //mostrar el ranking
        System.out.println("\n-------------------------------------\nPos. | Name | Score\n-------------------------------------\n");

        if(!desactivado || isFinal) {   //ranking final (win/game over)
            for(int i = 0; i < raperos.size(); i++){
                System.out.print(i+1 + "\t" + raperos.get(i).getNombreArtistico() + " <--- ");
                System.out.format("\033[0;93m" + "%.2f" + "\033[0m", raperos.get(i).getScore());
                if(raperos.get(i).getNombreArtistico().equals(nombreRapero)){   //si se trata del logeado
                    System.out.println("\033[1;95m \u26A1 YOU\u001B[0m");

                }else{
                    System.out.println("");
                }
            }
        }else { //ranking cuando aun se juega

            for(int i = 0; i < raperos.size(); i++){    //por cada rapero
                System.out.print(i+1 + "\t" + raperos.get(i).getNombreArtistico() + " <--- ");
                System.out.format("\033[0;93m" + "%.2f\n" + "\033[0m", raperos.get(i).getScore());
                if(raperos.get(i).getNombreArtistico().equals(nombreRapero)){
                    System.out.println("\033[1;95m \u26A1 YOU\u001B[0m");   //si se trata del logeado

                }
            }
            System.out.println("\n...\n");
            System.out.print(indiceTemporal + "\t" + nombreRapero + " <--- ");
            System.out.format("\033[0;93m" + "%.2f" + "\033[0m", puntuacion);
            System.out.println("\033[1;95m \u26A1 YOU\u001B[0m");

        }


    }

    /**
     * Mostrar mensage con el ganador.
     * @param nombreGanador nombre artístico del concursante ganador.
     * @param puntuacion puntuacion del ganador
     */
    public void showWinner(String nombreGanador, Double puntuacion) {

        System.out.print(" The winner was: " + "\033[1;95m" + nombreGanador + "\u001B[0m" + " with ");
        System.out.format("\033[0;93m" + "%.2f" + "\033[0m", puntuacion);
        System.out.println(" points!");
        System.out.print("Press any key to leave: ");

    }

    /**
     * Metodo para mostrar texto y recoger el nombre del rapero.
     * @return nombre del rapero .
     */
    public String askRaperoPerfil(){
        System.out.print("\nEnter the name of the rapper: ");
        return(askWhatToDo());
    }

    /**
     * Metodo para mostrar errores y mensages acerca del generador del rapero
     * @param whatToShow indice del error.
     */
    public void errorAtProfile(int whatToShow){
        if(whatToShow == -1){
            System.out.println("\nYo' bro there is no rapper with that name or nickname!\nGoing back to lobby.");
        }

    }

}//END