package com.cyld.lfcircle.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginBean implements Serializable {
	public String responseCode;
	public String message;

	public data data = new data();

	public class data implements Serializable{

		public user user = new user();

		public class user implements Serializable{
			public String Userid;
			public String Useremail;
			public String Usercellphone;
			public String Useridcard;
			public String Usersex;
			public String Userbirthday;
			public String Usernickname;
			public String Userhead;
			public String Useraddress;
			public String Userabstract;
		}
	}

}
