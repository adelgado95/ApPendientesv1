package com.example.e547551knes.register;

public class ModelCliente {
    public static final String TABLE_NAME = "clientes";
    public static final String COLUMN_POS = "pos";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_CLASS = "clase";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_POS + " TEXT PRIMARY KEY,"
                    + COLUMN_NOMBRES + " TEXT,"
                    + COLUMN_ESTADO + " TEXT,"
                    + COLUMN_CLASS+ " TEXT"
                    + ");";
    public ModelCliente(String n, String p, String s,String c){
        name=n;
        pos=p;
        estado=s;
        setCclass(c);
    }
    public ModelCliente()
    {

    }
    private String name;
    private String pos;
    private String estado ;
    private String cclass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String selected) {
        estado = selected;
    }

    public String getCclass() {
        return cclass;
    }

    public void setCclass(String cclass) {
        this.cclass = cclass;
    }
}
