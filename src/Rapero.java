import edu.salleurl.profile.Profileable;

import java.time.*;

/**
 * Clase con la inforamcion de un rapero.
 */
public class Rapero implements Profileable {
    /**
     * nombre y apellidos
     */
    private String      nombre;
    /**
     * mote o nombre artistico
     */
    private String      nombreArtistico;
    /**
     * fecha de nacimiento
     */
    private LocalDate   nacimiento;
    /**
     * pais de origen, nacionalidad
     */
    private String      pais;
    /**
     * nivel del rapero
     */
    private int         nivel;
    /**
     * foto del rapero
     */
    private String      url;
    /**
     * puntuacion del rapero para las batallas y fases
     */
    private Double      score;

    /**
     * Constructor.
     * @param nombre nombre completo del rapero.
     * @param nombreArtistico nombre artistico: mote.
     * @param nacimiento fecha de nacimiento en formato yyyy-MM-dd.
     * @param pais nombre del pais de nacimiento/residencia.
     * @param nivel nivel del rapero. Puede ser 1 o 2.
     * @param url URL en formato compatible de la foto del rapero.
     */
    public Rapero(String nombre, String nombreArtistico, LocalDate nacimiento, String pais, int nivel, String url) {
        this.nombre             = nombre;
        this.nombreArtistico    = nombreArtistico;
        this.nacimiento         = nacimiento;
        this.pais               = pais;
        this.nivel              = nivel;
        this.url                = url;
        this.score              = 0.00; //evitar que se quede en NULL para suprimir errores en el ranking
    }

    /**
     * Getter del nombre
     * @return nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del nombre artistico.
     * @return nombre artistico.
     */
    public String getNombreArtistico() {
        return nombreArtistico;
    }

    /**
     * Getter de la fecha de nacimiento.
     * @return fecha en formato yyyy-MM-dd LocalDate.
     */
    public LocalDate getNacimiento() {
        return nacimiento;
    }

    /**
     * Getter del nombre del pais.
     * @return nombre del pais.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Getter del nivel del rapero.
     * @return nivel.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Getter del url de la foto del rapero.
     * @return url de la foto.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Getter de la puntuacion del rapero.
     * @return puntuacion con decimales.
     */
    public Double getScore() {
        return score;
    }

    /**
     * Setter de la puntuacion.
     * @param score puntuacion con decimales.
     */
    public void setScore(Double score) {

        if(this.score == null) {
            this.score = score;
        }else {
            this.score = this.score + score;    //incrementar la puntuacion
        }

    }


    /**
     * Metodo implementado de Profileable para devolver el nombre.
     * @return nombre rapero.
     */
    @Override
    public String getName(){
        return(this.nombre);
    }

    /**
     * Metodo implementado de Profileable para devolver el nombre artistico.
     * @return nombre artistico rapero.
     */
    @Override
    public String getNickname(){
        return(this.nombreArtistico);
    }

    /**
     * Metodo implementado de Profileable para devolver la fecha de nacimiento.
     * @return fecha nacimiento.
     */
    @Override
    public String getBirthdate(){
        return(this.nacimiento.toString());
    }

    /**
     * Metodo implementado de Profileable para devolver la url de la foto.
     * @return url foto.
     */
    @Override
    public String getPictureUrl(){
        return(this.url);
    }

}//END