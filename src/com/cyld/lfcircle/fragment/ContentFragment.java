package com.cyld.lfcircle.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cyld.lfcircle.R;
import com.cyld.lfcircle.base.BasePager;
import com.cyld.lfcircle.base.impl.FindPager;
import com.cyld.lfcircle.base.impl.CirclePager;
import com.cyld.lfcircle.base.impl.MessagePager;
import com.cyld.lfcircle.base.impl.MePager;
import com.cyld.lfcircle.base.impl.NewsPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 主页内容
 * 
 * @author Kevin
 * 
 */
public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;

	@ViewInject(R.id.vp_content) 
	private ViewPager mViewPager;

	private ArrayList<BasePager> mPagerList;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		// rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		ViewUtils.inject(this, view); // 注入view和事件
		return view;
	}

	@Override
	public void initData() {
		rgGroup.check(R.id.rb_circle);// 默认勾选首页

		// 初始化5个子页面
		mPagerList = new ArrayList<BasePager>();
		mPagerList.add(new CirclePager(mActivity));
		mPagerList.add(new MessagePager(mActivity));
		mPagerList.add(new NewsPager(mActivity));
		mPagerList.add(new FindPager(mActivity));
		mPagerList.add(new MePager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());

		// 监听RadioGroup的选择事件
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_circle:
					// mViewPager.setCurrentItem(0);// 设置当前页面
					mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
					break;
				case R.id.rb_message:
					mViewPager.setCurrentItem(1, false);// 设置当前页面
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(2, false);// 设置当前页面
					break;
				case R.id.rb_find:
					mViewPager.setCurrentItem(3, false);// 设置当前页面
					break;
				case R.id.rb_me:
					mViewPager.setCurrentItem(4, false);// 设置当前页面
					break;

				default:
					break;
				}
			}
		});

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mPagerList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		mPagerList.get(0).initData();// 初始化首页数据
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			// pager.initData();// 初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	/**
	 * 获取页面
	 * 
	 * @return
	 */
	public MessagePager getNewsCenterPager() {
		return (MessagePager) mPagerList.get(1);
	}
	
}
