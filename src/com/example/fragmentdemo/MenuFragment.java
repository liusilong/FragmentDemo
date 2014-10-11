package com.example.fragmentdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MenuFragment extends Fragment implements OnItemClickListener {
	/**
	 * 菜单界面中只包含了一个ListView
	 */
	private ListView menuList;
	Display display;
	/**
	 * ListView的适配器
	 */
	private ArrayAdapter<String> adapter;
	// 用于填充ListView的数据
	private String[] menuItems = { "Sound", "Display" };

	// 当Activity和Fragment建立关联时，初始化适配器中的数据
	@SuppressLint("NewApi")
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// 实例化Adapter
		adapter = new ArrayAdapter<String>(activity,
				android.R.layout.simple_list_item_1, menuItems);
	}

	// 加载menu_fragment布局文件，为ListView绑定了适配器，并是设置了监听事件
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		menuList = (ListView) view.findViewById(R.id.menu_list);
		menuList.setAdapter(adapter);
		menuList.setOnItemClickListener(this);
		display = getActivity().getWindowManager().getDefaultDisplay();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (display.getWidth() > display.getHeight()) {
			getActivity().findViewById(R.id.details_layout).setVisibility(
					View.VISIBLE);
		} else if (display.getWidth() < display.getHeight()) {
			getActivity().findViewById(R.id.details_layout).setVisibility(
					View.GONE);
		}
	}

	/**
	 * 处理ListView的点击事件
	 * 
	 */
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		if (display.getWidth() > display.getHeight()) {
			// 横屏
			Fragment fragment = null;
			if (index == 0) {
				fragment = new SoundFragment();
			} else if (index == 1) {
				fragment = new DisplayFragment();
			}
			getFragmentManager().beginTransaction()
					.replace(R.id.details_layout, fragment).commit();
		} else if (display.getWidth() < display.getHeight()) {
			// 竖屏
			Intent intent = null;
			if (index == 0) {
				intent = new Intent(getActivity(), SoundActivity.class);
			} else if (index == 1) {
				intent = new Intent(getActivity(), DisplayActivity.class);
			}
			startActivity(intent);
		}
	}

}
