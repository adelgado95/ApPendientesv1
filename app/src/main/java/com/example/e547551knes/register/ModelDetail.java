package com.example.e547551knes.register;

public class ModelDetail {
    public static final String TABLA_NAME = "detail_clients";
    public static final String COLUM_ID = "id";
    public static final String COLUM_FECHA = "fecha";
    public static final String COLUM_NOMBRES = "nombres";
    public static final String COLUM_POS = "pos";
    public static final String COLUM_INICIAL = "inicial";
    public static final String COLUM_CREDITO = "credito";
    public static final String COLUM_ABONOS = "abono";
    public static final String COLUM_PENDIENTE = "pendiente";
    public static final String CREATES_TABLE =
            "CREATE TABLE " + TABLA_NAME + "("
                    + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUM_POS + " TEXT,"
                    + COLUM_NOMBRES + " TEXT,"
                    + COLUM_INICIAL + " INTEGER,"
                    + COLUM_CREDITO + " INTEGER,"
                    + COLUM_ABONOS + " INTEGER,"
                    + COLUM_PENDIENTE + " INTEGER,"
                    + COLUM_FECHA + " TEXT"
                    + ");";
    public ModelDetail(String id, String p, String n,Integer i, Integer c, Integer a, Integer pe,String fecha){
        this.setId(id);
        setPos(p);
        setNombres(n);
        setInicial(i);
        setCredito(c);
        setAbono(a);
        setPendiente(pe);
        setFecha(fecha);

    }
    public ModelDetail()
    {

    }

    private String id;
    private String pos;
    private String nombres;
    private int inicial;
    private int credito;
    private int abono;
    private int pendiente;
    private String fecha;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public int getInicial() {
        return inicial;
    }

    public void setInicial(int inicial) {
        this.inicial = inicial;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public int getAbono() {
        return abono;
    }

    public void setAbono(int abono) {
        this.abono = abono;
    }

    public int getPendiente() {
        return pendiente;
    }

    public void setPendiente(int pendiente) {
        this.pendiente = pendiente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
