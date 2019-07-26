package com.xiaomi.TestBulletin;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public  class FileUtil {
    public static final String FILENAME = "testBulletin20190726.dat";
    public static  void write(Context context,Object obj) {
        FileOutputStream fout = null;
        try {
            fout = context.openFileOutput(FILENAME, context.MODE_WORLD_WRITEABLE);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(obj);
            oos.close();
            Log.e("-------","write succuss");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static LinkedList<Object> read(Context context){
        LinkedList<Object> obj = new LinkedList<>();
        FileInputStream fis=null;
        ObjectInputStream ois=null;
        try {
            fis = context.openFileInput(FILENAME);
            ois=new ObjectInputStream(fis);
            obj=(LinkedList) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("FileNotFoundException",e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException",e.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("ClassNotFoundException",e.toString());
        }finally{
            try {
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
                return obj;
            }
            return obj;
        }
    };
}
