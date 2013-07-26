package com.example.misprimerospuzzles;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.misprimerospuzzles.listeners.DefaultDragListener;
import com.example.misprimerospuzzles.listeners.PuzzlePartDragListener;
import com.example.misprimerospuzzles.listeners.PuzzlePartTouchListener;
import com.example.misprimerospuzzles.utils.Commons;
import com.example.misprimerospuzzles.utils.PropertyUtils;

/**
 * Puzzle individual de la aplicaci�n
 * 
 * @author Luis
 * 
 */
public class PuzzleActivity extends BaseActivity {
	private static PuzzleActivity instance = null;

	/**
	 * Punto de entrada a la actividad
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setInstance(this);
		
		// Layout principal
		setContentView(R.layout.puzzleactivity_layout);

		// Eliminamos las barras de notificación
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// Cargamos las propiedades
		propertyUtils = new PropertyUtils(instance);
		
		// Obtenemos los par�metros de invocaci�n
		Bundle bundle = getIntent().getExtras();

		// Seleccionamos los items del puzzle
		String puzzle = bundle.getString(Commons.NOMBRE_PUZZLE);
		String[] piezasString = propertyUtils.getProperty(puzzle).split(",");
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
		PuzzlePartDragListener puzzlePartDragListener = new PuzzlePartDragListener(this);

		findViewById(R.id.puzzlepart1).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart2).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart3).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlepart4).setOnTouchListener(
				new PuzzlePartTouchListener());
		findViewById(R.id.puzzlelayout).setOnDragListener(
				new DefaultDragListener());
		findViewById(R.id.topleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.topright).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomleft).setOnDragListener(puzzlePartDragListener);
		findViewById(R.id.bottomright)
				.setOnDragListener(puzzlePartDragListener);

		// Para el modo ayuda, la parte que puede quedarse transparente debe ir
		// al frente
		findViewById(R.id.puzzle).bringToFront();
		
		// La animaci�n en caso de pieza exitosa debe quedar m�s al frente a�n
		findViewById(R.id.okAnimation).bringToFront();
	}

	/**
	 * M�todo ejecutado al pulsar el bot�n "atras" (deshabilitado)
	 */
	@Override
	public void onBackPressed() {
		return;
	}

	/**
	 * Implementaci�n del men� de la aplicaci�n
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.puzzleactivity_menu, menu);
		return true;
	}

	/**
	 * Implementaci�n de los manejadores de las opciones del men� de la
	 * aplicaci�n
	 * 
	 * @param item
	 *            opci�n de men� seleccionada
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

	/**
	 * Vuelve al listado de puzzles disponibles
	 * @param v bot�n pulsado
	 */
	public void gotoHome(View v) {
		finish();
	}

	public static PuzzleActivity getInstance() {
		return instance;
	}

	public static void setInstance(PuzzleActivity instance) {
		PuzzleActivity.instance = instance;
	}
}
