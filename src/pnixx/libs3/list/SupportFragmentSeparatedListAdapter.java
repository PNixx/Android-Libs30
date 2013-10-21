package pnixx.libs3.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import pnixx.libs3.R;

import java.util.ArrayList;

/**
 * User: nixx
 * Date: 22.03.13
 * Time: 16:31
 */
public abstract class SupportFragmentSeparatedListAdapter<A extends FragmentActivity, Row> extends Fragment implements AdapterView.OnItemClickListener {

	protected ListView list;
	protected A activity;
	protected boolean isActive;
	protected ArrayList<Row> rows;
	protected PageScrolling pageScrolling;
	protected SeparatedListAdapter adapter;
	protected View progress;

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
	protected abstract void getPage();

	//Скрыть прогресс бар
	protected void progressHide() {
		progress.setVisibility(View.GONE);
	}

	//Установка адаптера
	protected void setAdapter(SeparatedListAdapter adapter) {
		if( isActive && isAdded() ) {
			pageScrolling.setAdapter(adapter);
			list.setAdapter(adapter);
		}
	}

	//Получение списка
	protected ListView getListView() {
		return list;
	}
}
