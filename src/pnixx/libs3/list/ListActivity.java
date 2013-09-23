package pnixx.libs3.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 23.09.13
 * Time: 17:41
 */
public abstract class ListActivity<Row> extends Activity implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		//Получаем объекты
		list = (ListView) findViewById(R.id.list);
		progress = findViewById(R.id.progress);

		//Биндим клик
		list.setOnItemClickListener(this);

		//Устанавливаем параметры для списка
		list.setDividerHeight(0);

		//Биндим скроллинг
		pageScrolling = new PageScrolling(this);
		list.setOnScrollListener(pageScrolling);
	}

	@Override
	public void onPause() {
		isActive = false;
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		isActive = true;
	}

	//Скрыть прогресс бар
	protected void progressHide() {
		progress.setVisibility(View.GONE);
	}

	//Установка адаптера
	protected void setAdapter(AbstractAdapter adapter) {
		pageScrolling.setAdapter(adapter);
		list.setAdapter(adapter);
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}
}
