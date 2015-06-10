package com.cyld.lfcircle.domain;

import java.io.Serializable;
import java.util.List;

public class MyTopicBean implements Serializable {
	public List<LB> list;

	public class LB {
		public String title;
		public String content;
		public String good;
		public String bad;
		public String num;
		public String id;
		public String themeid;
	}
}
