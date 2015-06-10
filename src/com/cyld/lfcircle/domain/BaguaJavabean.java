package com.cyld.lfcircle.domain;

import java.util.List;

public class BaguaJavabean {
     public Top top;   
	 public List<SmailList> smallList;
	 public topicList  imageList;
	  class Top{
		  
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
	  class SmailList{
		  
		  public String id ;
			public String title ;
			public String type ;
		  
	  }
	  
	  class topicList{
		  
		  public PageResult  pageresult;
		  public List<DataList> dataList;
		  
	  }
	  
	  class PageResult {
		  public String totalPageNum ;
			public String nowPagenum ;
			public String pageNum ;
		  
	  } 
	  
	 class DataList {
		    public User  user;
		    public ImageList imageList;
		    public ContentList contentList;
		    public String id ;
			public String title ;
			public String content ;
			public String time ;
			public String good ;
			public String bad ;
			public String num ;
			public String type ;
			public String collect ;
			public String totalPage ;
			
			
			public class User{
				public String Usernickname ;
				public String UserSex ;
				public String UserId ;
				public String Userhead ;
				public String UserLeve ;
				
			}
			
			
			public class ImageList{
				public String smailImage ;
				public String bigImage ;
			}
			
			public class ContentList{
			
			}
		 
	 }
	
	
	
}
