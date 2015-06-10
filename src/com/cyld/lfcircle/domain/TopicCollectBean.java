package com.cyld.lfcircle.domain;

import java.io.Serializable;
import java.util.List;

public class TopicCollectBean implements Serializable{
	public List<LB> list;

	public class LB {
		public String themeId;
		public String postsId;
		public String title;
		public String time;
		public String theme;
		public User user = new User();

		public class User {
			public String Usernickname;
			public String UserSex;
			public String Userhead;
			public String UserLeve;

		}

	}

}
