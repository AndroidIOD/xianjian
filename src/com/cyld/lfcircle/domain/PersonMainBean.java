package com.cyld.lfcircle.domain;

import java.io.Serializable;
import java.util.List;

//用于个人主页
@SuppressWarnings("serial")
public class PersonMainBean implements Serializable{
	public String Usr_bgimg;
	public String Usr_head;
	public String Usr_nickname;
	public String Usr_sex;
	public String Usr_address;
	public String Usr_abstract;
	public String Usr_age;
	public String Usr_level;
	public String Usr_group;
	
	public List<List<LB>> Usr_Proped;
	public List<List<RY>> Usr_Glory;
	
	public List<LB> LBB;
	public List<RY> RYY;
	public LB LB = new LB();
	public RY RY = new RY();
	public class LB{
		public String P_name;
		public String P_img;
		public String Up_count;
	}
	
	public class RY{
		public String G_name;
		public String G_img;
	}
	
}

