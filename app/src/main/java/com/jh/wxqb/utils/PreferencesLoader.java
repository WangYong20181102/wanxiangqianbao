package com.jh.wxqb.utils;


import android.content.Context;
import android.util.Base64;

import net.grandcentrix.tray.AppPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class PreferencesLoader {
    private static AppPreferences appPreferences;

    public PreferencesLoader() {
    }

    public static void init(Context context) {
        appPreferences = new AppPreferences(context);
    }

    public static AppPreferences getAppPreferences() {
        return appPreferences;
    }

    public static void setObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String e = new String(Base64.encode(baos.toByteArray(), 0));
            appPreferences.put(key, e);
        } catch (IOException var13) {
            var13.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }

                if(out != null) {
                    out.close();
                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }

        }

    }

    public static <T> T getObject(String key, Class<T> clazz) {
        String objectVal = appPreferences.getString(key, (String)null);
        if(objectVal == null) {
            return null;
        } else {
            byte[] buffer = Base64.decode(objectVal, 0);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;

            try {
                ois = new ObjectInputStream(bais);
                Object e = ois.readObject();
                Object var7 = e;
                return (T) var7;
            } catch (StreamCorruptedException var21) {
                var21.printStackTrace();
            } catch (IOException var22) {
                var22.printStackTrace();
            } catch (ClassNotFoundException var23) {
                var23.printStackTrace();
            } finally {
                try {
                    if(bais != null) {
                        bais.close();
                    }

                    if(ois != null) {
                        ois.close();
                    }
                } catch (IOException var20) {
                    var20.printStackTrace();
                }

            }

            return null;
        }
    }



}
