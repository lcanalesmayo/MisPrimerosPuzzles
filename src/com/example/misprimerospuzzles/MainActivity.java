package com.example.misprimerospuzzles;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Actividad principal de la aplicación
 * 
 * @author Luis
 * 
 */
public class MainActivity extends Activity {
	private static MainActivity instance = null;

	/**
	 * Punto de entrada a la aplicación
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setInstance(this);

		// Layout principal
		setContentView(R.layout.activity_main);

		// Eliminamos las barras de notificaciÃ³n
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Seleccionamos los items del puzzle
		String[] piezasString = PropertyUtils.getProperty("burro").split(",");
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.table);
		for (int i = 0; i < piezasString.length; i++) {
			String pieza = piezasString[i];
			int resID = getResources().getIdentifier(pieza, "drawable",
					getPackageName());
			((ImageView) linearLayout.getChildAt(i))
					.setImageDrawable(getResources().getDrawable(resID));

			switch (i) {
			case 0:
				((LinearLayout) findViewById(R.id.topleftsolution))
						.setBackgroundDrawable(getResources()
								.getDrawable(resID));
				break;
			case 1:
				((LinearLayout) findViewById(R.id.toprightsolution))
						.setBackgroundDrawable(getResources()
								.getDrawable(resID));

				break;
			case 2:
				((LinearLayout) findViewById(R.id.bottomleftsolution))
						.setBackgroundDrawable(getResources()
								.getDrawable(resID));

				break;
			case 3:
				((LinearLayout) findViewById(R.id.bottomrightsolution))
						.setBackgroundDrawable(getResources()
								.getDrawable(resID));

				break;
			default:
				break;
			}
		}

		// Modo ayuda desactivado inicialmente
		Commons.helpMode = false;

		// Drag listeners
		PuzzlePartDragListener puzzlePartDragListener = new PuzzlePartDragListener();

		findViewById(R.id.puzzlepart1).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart2).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart3).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart4).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.mainlayout).setOnDragListener(
				new DefaultDragListener());
		findViewById(R.id.topleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.topright).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomright)
				.setOnDragListener(puzzlePartDragListener);

		// Fondos de piezas de puzzle
		Commons.enterShape = getResources().getDrawable(
				R.drawable.shape_droptarget);
		Commons.normalShape = getResources().getDrawable(R.drawable.shape);
		Commons.enterShapeTransparent = getResources().getDrawable(
				R.drawable.shape_droptarget_transparent);
		Commons.normalShapeTransparent = getResources().getDrawable(
				R.drawable.shape_transparent);

		// Para el modo ayuda, la parte que puede quedarse transparente debe ir
		// al frente
		findViewById(R.id.puzzle).bringToFront();

		// Creamos los sonidos
		Commons.mPlayerFinal = MediaPlayer.create(this, R.raw.burro);
		Commons.mPlayerFail = MediaPlayer.create(this, R.raw.fail);
		Commons.mPlayerNice = MediaPlayer.create(this, R.raw.nice);
		Commons.mPlayerPista = MediaPlayer.create(this, R.raw.pista);
	}

	/**
	 * Método ejecutado al pulsar el botón "atras" (deshabilitado)
	 */
	@Override
	public void onBackPressed() {
		return;
	}

	/**
	 * Implementación del menú de la aplicación
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Implementación de los manejadores de las opciones del menú de la
	 * aplicación
	 * 
	 * @param item
	 *            opción de menú seleccionada
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.exit:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Manejadores de botones
	public void darPista(View v) {
		Commons.mPlayerPista.start();

		Commons.helpMode = !Commons.helpMode;

		View topleft = findViewById(R.id.topleft);
		if (topleft.getTag() != null) {
			if (Commons.helpMode) {
				topleft.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				topleft.setBackgroundDrawable(Commons.normalShape);
			}
		}

		View topright = findViewById(R.id.topright);
		if (topright.getTag() != null) {
			if (Commons.helpMode) {
				topright.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				topright.setBackgroundDrawable(Commons.normalShape);
			}
		}

		View bottomleft = findViewById(R.id.bottomleft);
		if (bottomleft.getTag() != null) {
			if (Commons.helpMode) {
				bottomleft
						.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				bottomleft.setBackgroundDrawable(Commons.normalShape);
			}
		}

		View bottomright = findViewById(R.id.bottomright);
		if (bottomright.getTag() != null) {
			if (Commons.helpMode) {
				bottomright
						.setBackgroundDrawable(Commons.normalShapeTransparent);
			} else {
				bottomright.setBackgroundDrawable(Commons.normalShape);
			}
		}
	}

	public void gotoHome(View v) {
	}

	public static MainActivity getInstance() {
		return instance;
	}

	public static void setInstance(MainActivity instance) {
		MainActivity.instance = instance;
	}
}
