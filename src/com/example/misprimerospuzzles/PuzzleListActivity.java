package com.example.misprimerospuzzles;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.misprimerospuzzles.adapters.PuzzleListAdapter;
import com.example.misprimerospuzzles.utils.Commons;
import com.example.misprimerospuzzles.utils.PropertyUtils;

/**
 * Listado de puzzles de la aplicación
 * 
 * @author Luis
 * 
 */
public class PuzzleListActivity extends BaseActivity {
	private static PuzzleListActivity instance = null;

	/**
	 * Punto de entrada a la actividad
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setInstance(this);

		// Layout principal
		setContentView(R.layout.puzzlelistactivity_layout);

		// Eliminamos las barras de notificaciÃ³n
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Cargamos las propiedades
		propertyUtils = new PropertyUtils(instance);

		// Seleccionamos los items del puzzle
		GridView gridView = (GridView) findViewById(R.id.puzzlelistlayout);	
		gridView.setAdapter(new PuzzleListAdapter(instance));	
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent = new Intent(instance, PuzzleActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString(Commons.NOMBRE_PUZZLE, (String)v.getTag());
				intent.putExtras(bundle);
				
				startActivity(intent);
			}
		});	

		// Inicializamos elementos comunes
		// Fondos de piezas de puzzle
		Commons.enterShape = getResources().getDrawable(
				R.drawable.shape_droptarget);
		Commons.normalShape = getResources().getDrawable(R.drawable.shape);
		Commons.enterShapeTransparent = getResources().getDrawable(
				R.drawable.shape_droptarget_transparent);
		Commons.normalShapeTransparent = getResources().getDrawable(
				R.drawable.shape_transparent);
		
		// Creamos los sonidos
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
		inflater.inflate(R.menu.puzzlelistactivity_menu, menu);
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

	public static PuzzleListActivity getInstance() {
		return instance;
	}

	public static void setInstance(PuzzleListActivity instance) {
		PuzzleListActivity.instance = instance;
	}
}

