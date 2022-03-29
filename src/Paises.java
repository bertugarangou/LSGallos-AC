/**
 * Clase con la informacion necesaria de un pais.
 */
public class Paises {

    /**
     * nombre
     */
    private String pais;
    /**
     * numero de habitantes
     */
    private long habitantes;
    /**
     * region perteneciente
     */
    private String region;
    /**
     * bandera
     */
    private String urlBandera;

    /**
     * Constructor.
     * @param pais nombre del pais.
     */
    public Paises(String pais) {

        this.pais = pais;

    }

    /**
     * Getter del nombre del pais.
     * @return nombre del pais.
     */
    public String getPais() {
        return pais;
    }
}//END