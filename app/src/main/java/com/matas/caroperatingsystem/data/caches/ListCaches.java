package com.matas.caroperatingsystem.data.caches;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ListCaches {

    public static final String CACHES_DIR = "caches";
    public static final String CACHES_NAME = "caches.data";

    public static void writeCaches(Context context, ArrayList<String> data) {
        if (data != null) {
            try {
                if (checkCaches(context)) {
                    deleteCaches(context);
                }
                String path = getDirCaches(context);
                File fileRoot = new File(path);
                if (!fileRoot.exists()) {
                    if (!fileRoot.mkdirs()) {
                        throw new Exception("Can not create dir");
                    }
                }
                FileWriter fileWriter = new FileWriter(new File(getFileCaches(context)));
                BufferedWriter bw = new BufferedWriter(fileWriter);

                Gson gson = new Gson();
                bw.write(gson.toJson(data));
                bw.close();
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<String> readCaches(Context context) {
        ArrayList<String> list = new ArrayList<>();
        if (checkCaches(context)) {
            File file = new File(getFileCaches(context));
            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader br = new BufferedReader(fileReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String data = sb.toString();
                Gson gson = new Gson();
                list = gson.fromJson(data,
                        new TypeToken<ArrayList<String>>() {}.getType());
            } catch (IOException ex) {
                ex.printStackTrace();
            }catch (JsonSyntaxException ex){
                ex.printStackTrace();
            }
        }
        return list;
    }

    private static boolean checkCaches(Context context) {
        String path = getFileCaches(context);
        File file = new File(path);
        return file.exists();
    }

    private static boolean deleteCaches(Context context) {
        String path = getFileCaches(context);
        File file = new File(path);
        return file.delete();
    }

    private static String getFileCaches(Context context) {
        return context.getCacheDir() + File.separator + CACHES_DIR + File.separator + CACHES_NAME;
    }

    private static String getDirCaches(Context context) {
        return context.getCacheDir() + File.separator + CACHES_DIR;
    }
}