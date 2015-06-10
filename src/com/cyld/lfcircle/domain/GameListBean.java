package com.cyld.lfcircle.domain;

import java.util.List;

public class GameListBean {
	public List<LYJ> list1;
	public List<LYJ> list2;

	LYJ LYJ = new LYJ();
	public class LYJ {
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
}
