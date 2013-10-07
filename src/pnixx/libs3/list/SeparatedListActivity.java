package pnixx.libs3.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import pnixx.libs3.R;

/**
 * User: nixx
 * Date: 07.10.13
 * Time: 23:06
 */
public abstract class SeparatedListActivity extends Activity implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected boolean isActive;
	protected PageScrolling pageScrolling;
	protected SeparatedListAdapter adapter;
	protected View progress;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.list);

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

	//Получаем данные страницы
	protected abstract void getPage();

	//Скрыть прогресс бар
	protected void progressHide() {
		progress.setVisibility(View.GONE);
	}

	//Установка адаптера
	protected void setAdapter(SeparatedListAdapter adapter) {
		list.setAdapter(adapter);
		pageScrolling.setAdapter(adapter);
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}
}
