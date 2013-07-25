package com.example.misprimerospuzzles;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

/**
 * Variables globales en el transcurso de la aplicación
 * 
 * @author Luis
 * 
 */
public class Commons {
	// Fondos para piezas de puzzle
	public static Drawable enterShape;
	public static Drawable normalShape;
	public static Drawable enterShapeTransparent;
	public static Drawable normalShapeTransparent;

	// Modo ayuda
	public static boolean helpMode;

	// Sonidos
	public static MediaPlayer mPlayerFinal;
	public static MediaPlayer mPlayerFail;
	public static MediaPlayer mPlayerNice;
	public static MediaPlayer mPlayerPista;
}
