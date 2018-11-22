package com.example.e547551knes.register;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.ColorSpace;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.example.e547551knes.register.ModelCliente.*;


public class DataBaseHelperClient extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "clients_db";

    public DataBaseHelperClient(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void insert_configuration()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("insert into configuration VALUES('2018-10-03','RUTA-01-ROLANDOAREAS');");
    }
    public void update_fecha_configuration(String fecha)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update configuration set fecha='"+fecha+"' WHERE 1;");
    }
    public void update_ruta_configuration(String ruta)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update configuration set ruta='"+ruta+"' WHERE 1;");
    }
    public String getConfig_ruta()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT ruta from configuration;", null);
        String ruta="0";
        cursor.moveToFirst();// looping through all rows a
         ruta = cursor.getString(cursor.getColumnIndex("ruta"));
        return ruta;
    }
    public String getConfig_fecha()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT fecha from configuration;", null);
        String fecha="0";
        cursor.moveToFirst();
        fecha = cursor.getString(cursor.getColumnIndex("fecha"));
        return fecha;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ModelCliente.CREATE_TABLE);
        db.execSQL(ModelDetail.CREATES_TABLE);
        db.execSQL("CREATE TABLE configuration(fecha TEXT,ruta TEXT);");
        db.execSQL("INSERT INTO configuration values('0','0');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void update_clients_database(List<ModelCliente> lista_clientes)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM clientes where 1;");
        if(lista_clientes.size() > 0)
        {
            for (int i=0;i<lista_clientes.size();i++)
            {
                String cclase,pos,nombres,estado;
                cclase = lista_clientes.get(i).getCclass();
                pos = lista_clientes.get(i).getPos();
                nombres = lista_clientes.get(i).getName();
                estado = lista_clientes.get(i).getEstado();
                db.execSQL("INSERT INTO clientes (pos,nombres,estado,clase) VALUES ('"+pos+"','"+nombres+"','LIMPIO','"+cclase+"');");
            }
        }
    }

    public void borrar_detalle(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from detail_clients;");
        db.close();
    }
    public void borrar_clientes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE from clientes;");
        db.close();
    }
    public void CambiarEstado(String pos)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE clientes set estado='PENDIENTE' WHERE pos='"+pos+"'");
        db.close();
    }


    public void insert_client()
    {

    }
    public void update_detail(String id,String inicial,String creditos,String abonos,String pendiente)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query="UPDATE detail_clients set inicial="+inicial+" where id="+id+";";
        db.execSQL(query);
        String query2="UPDATE detail_clients set credito="+creditos+" where id="+id+";";
        db.execSQL(query2);
        String query3="UPDATE detail_clients set abono="+abonos+" where id="+id+";";
        db.execSQL(query3);
        String query4="UPDATE detail_clients set pendiente="+pendiente+" where id="+id+";";
        db.execSQL(query4);
        db.close();
    }
    public void insert_detail(ModelDetail detail)
    {
       SQLiteDatabase db = this.getWritableDatabase();
       String query="insert into detail_clients(fecha,nombres,pos,inicial,credito,abono,pendiente) values" +
               "('"+detail.getFecha()+"','"+detail.getNombres()+"','"+detail.getPos()+"',"+detail.getInicial()+","+detail.getCredito()+
               ","+detail.getAbono()+","+detail.getPendiente()+");";
       db.execSQL(query);
       db.close();

    }
    public void setAllClientsActive()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE clientes set estado='ACTIVO' WHERE 1");
        db.close();
    }
    public void getAllDetails(List<ModelDetail> detailList){
        detailList.clear();
        String selectQuery = "SELECT  * FROM " + ModelDetail.TABLA_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //honorio aprueba el codigo.
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelDetail detail = new ModelDetail();
                detail.setId(cursor.getString(cursor.getColumnIndex(ModelDetail.COLUM_ID)));
                detail.setNombres(cursor.getString(cursor.getColumnIndex(ModelDetail.COLUM_NOMBRES)));
                detail.setPos(cursor.getString(cursor.getColumnIndex(ModelDetail.COLUM_POS)));
                detail.setInicial(cursor.getInt(cursor.getColumnIndex(ModelDetail.COLUM_INICIAL)));
                detail.setCredito(cursor.getInt(cursor.getColumnIndex(ModelDetail.COLUM_CREDITO)));
                detail.setAbono(cursor.getInt(cursor.getColumnIndex(ModelDetail.COLUM_ABONOS)));
                detail.setPendiente(cursor.getInt(cursor.getColumnIndex(ModelDetail.COLUM_PENDIENTE)));
                detail.setFecha(cursor.getString(cursor.getColumnIndex(ModelDetail.COLUM_FECHA)));
                detailList.add(detail);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes l

    }
    public String getRuta()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT ruta FROM configuration;";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String ruta="";
        if (cursor.moveToFirst()) {
            do {
                ruta = cursor.getString(cursor.getColumnIndex("ruta"));
            } while (cursor.moveToNext());
        }
        return ruta;
    }
    public List<ModelCliente> getAllClients() {
        List<ModelCliente> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ModelCliente.TABLE_NAME +" WHERE estado != 'PENDIENTE';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
    //honorio aprueba el codigo.
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelCliente note = new ModelCliente();
                note.setPos(cursor.getString(cursor.getColumnIndex(ModelCliente.COLUMN_POS)));
                note.setName(cursor.getString(cursor.getColumnIndex(ModelCliente.COLUMN_NOMBRES)));
                note.setEstado(cursor.getString(cursor.getColumnIndex(ModelCliente.COLUMN_ESTADO)));
                note.setCclass(cursor.getString(cursor.getColumnIndex(ModelCliente.COLUMN_CLASS)));
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

}
