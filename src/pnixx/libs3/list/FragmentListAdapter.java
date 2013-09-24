package pnixx.libs3.list;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONException;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 22.03.13
 * Time: 16:31
 */
public abstract class FragmentListAdapter<A extends Activity, Row> extends Fragment implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected A activity;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected AbstractAdapter adapter;
	protected View progress;
	protected View footer_loader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.list, container, false);

		//Получаем объекты
		list = (ListView) view.findViewById(R.id.list);
		progress = view.findViewById(R.id.progress);

		//Биндим клик
		list.setOnItemClickListener(this);

		//Устанавливаем параметры для списка
		list.setDividerHeight(0);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isActive = true;
		activity = (A) getActivity();

		//Получаем шаблон для загрузчика
		View view_footer = activity.getLayoutInflater().inflate(R.layout.footer_loader, list, false);
		footer_loader = view_footer.findViewById(R.id.footer_layout);

		//Биндим скроллинг
		pageScrolling = new PageScrolling(activity);
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
	protected abstract void getPage() throws JSONException;

	//Скрыть прогресс бар
	protected void progressHide() {
		progress.setVisibility(View.GONE);
	}

	//Установка адаптера
	protected void setAdapter(AbstractAdapter adapter) {
		list.addFooterView(footer_loader);
		pageScrolling.setAdapter(adapter);
		list.setAdapter(adapter);
		list.removeFooterView(footer_loader);
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}

	//Добавление футера
	protected void addFooterLoader() {
		list.addFooterView(footer_loader);
	}

	//Удаление футера
	protected void removeFooterLoader() {
		list.removeFooterView(footer_loader);
	}
}
