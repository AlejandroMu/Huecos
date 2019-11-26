package com.example.huecoscolombia.Model.driver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.huecoscolombia.Model.entity.User;

import java.lang.reflect.Field;

public class DBDriver extends SQLiteOpenHelper {

    public static final String DB_NAME = "Huecos";
    public static final int DB_VERSION = 2;
    private static DBDriver instance;

    public static synchronized DBDriver getInstance(Context con){
        return instance=instance==null?new DBDriver(con):instance;
    }

    private DBDriver(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      Class[] classes={User.class};
      for(Class ct:classes){
          try {
              processClass(ct,db);
          } catch (NoSuchFieldException e) {
              e.printStackTrace();
          }
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS "+ESTUD_PRODUCT_TABLE);

        onCreate(db);
    }

    private boolean processClass(Class clas,SQLiteDatabase db) throws NoSuchFieldException {

        //CREATE TABLE
        if(clas.isAnnotationPresent(Table.class)){
            String query="CREATE TABLE ";
            Table annotation=(Table) clas.getAnnotation(Table.class);
            String name=annotation.name();
            name=name.equals("")?clas.getSimpleName():name;
            query+=name+" (";
            Field[] fields=clas.getDeclaredFields();
            String fieldSen="";
            String foreignKeys="";
            String sep="";
            String sepF="";
            for (Field field:fields){
                if(field.isAnnotationPresent(Id.class)){
                    Id anF=(Id) clas.getAnnotation(Id.class);
                    fieldSen+=sep+anF.name()+" "+anF.type()+" PRIMARY KEY";
                    sep=",";
                }else if(field.isAnnotationPresent(Foreign.class)){
                    Class at=field.getType();
                    if(at.isAnnotationPresent(Table.class)){
                    Foreign anF=(Foreign) clas.getAnnotation(Foreign.class);
                    Table ta=(Table) at.getAnnotation(Table.class);
                    Id idF=(Id)at.getField(anF.reference()).getAnnotation(Id.class);
                    fieldSen+=sep+anF.name()+" "+idF.type();
                    foreignKeys+=sepF+"FOREIGN KEY ("+anF.name()+") REFERENCES "+ta.name()+" ("+idF.name()+")";
                    sepF=",";
                    }

                }else if(field.isAnnotationPresent(Colum.class)){
                    Colum anF=(Colum) clas.getAnnotation(Colum.class);
                    fieldSen+=sep+anF.name()+" "+anF.type();
                    sep=",";
                }
            }
            query+=fieldSen+foreignKeys+")";
            db.execSQL(query);

        }


        return false;
    }
}