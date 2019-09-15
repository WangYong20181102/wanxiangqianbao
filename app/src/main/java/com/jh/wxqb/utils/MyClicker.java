package com.jh.wxqb.utils;

/**
 * 此为adapter类的接口回调，间接和activity通信*/

import android.view.View;

public interface MyClicker {
	void myClick(View view, int type);
}
