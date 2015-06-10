package com.cyld.lfcircle.domain;

import java.util.ArrayList;

import android.widget.ImageView;

/**
 * 主题的列表
 * 
 * @author kevin
 * 
 */
public class TopicGroupList {

	public ArrayList<TopicHeaderData> header; // 上部的
	public ArrayList<TopicListData> list; // 下面的列表

	public class TopicHeaderData {
		private String name;
		private ImageView ImgSrc;

		@Override
		public String toString() {
			return "TopicHeaderData [name=" + name + ", ImgSrc=" + ImgSrc + "]";
		}

	}

	public class TopicListData {
		private String name;// //话题名称
		private ImageView ImgSrc;// 话题的图片
		private String details;// 话题的详情
		private long topicNumber;// 话题的数量

		@Override
		public String toString() {
			return "TopicListData [name=" + name + ", ImgSrc=" + ImgSrc
					+ ", details=" + details + ", topicNumber=" + topicNumber
					+ "]";
		}

	}

}
