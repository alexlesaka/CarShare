package com.alexlesaka.carshare.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by aabuin on 21/08/2017.
 */

public class ImageController
{
    //Imagen de perfil
    private RequestQueue colaPeticiones;
    private ImageLoader lectorImagenes;


    public ImageController(Context context)
    {
        //Imagen de perfil
        colaPeticiones = Volley.newRequestQueue(context);
        lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache()
                {
                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });
    }


    //Imagen de Perfil
    public RequestQueue getColaPeticiones()
    {
        return colaPeticiones;
    }

    public void setColaPeticiones(RequestQueue colaPeticiones)
    {
        this.colaPeticiones = colaPeticiones;
    }

    public void setUserPhotoLoader(ImageLoader lectorImagenes) {
        this.lectorImagenes = lectorImagenes;
    }

    public ImageLoader getUserPhotoLoader() {
        return lectorImagenes;
    }
}
