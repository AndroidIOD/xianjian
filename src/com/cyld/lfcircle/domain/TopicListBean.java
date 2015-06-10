package com.cyld.lfcircle.domain;

import java.util.List;

public class TopicListBean {
	
	public List<SmallList> smallList;
	
	public top top = new top();
	public class top{
		public String T_ID;
		public String T_Name;
		public String T_Details;
		public String T_Num;
		public String T_ImgSrc;
		public String T_ITTable;
		public String T_CTTable;
		public String T_DateTime;
		public String T_Isad;
		public String T_Type;
	}
	
	public SmallList SmallList = new SmallList();
	public class SmallList{
		public String id;
		public String title;
		public String type;
	}
	
	public topicList topicList = new topicList();
	public class topicList{
		
		public List<DataList> dataList;
		public List<ImageList> imageList;
		public List<ContentList> content;
		
		public ContentList ContentList = new ContentList();
		public class ContentList{
			
		}
		
		public ImageList ImageList = new ImageList();
		public class ImageList{
			public String smaillmage;
			public String bigImage;
		}
		
		public DataList DataList = new DataList();
		public class DataList{
			public String id;
			public String title;
			public String content;
			public String time;
			public String good;
			public String bad;
			public String num;
			public String type;
			public String collect;
			public String totalPage;
			
			public user user = new user();
			public class user{
				public String Usernickname;
				public String UserSex;
				public String UserId;
				public String Userhead;
				public String UserLeve;
			}
			
		}
		
		public pageResult pageResult = new pageResult();
		public class pageResult{
			public String totalPageNum;
			public String nowPagenum;
			public String pageNum;
		}
		
	}
	
}
