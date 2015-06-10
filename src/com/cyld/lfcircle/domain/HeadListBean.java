package com.cyld.lfcircle.domain;

import java.util.List;

public class HeadListBean {
	public String responseCode;
	public String message;

	public data data = new data();

	public class data {
		
		public List<Head> a;
		public List<Head> b;
		public List<Head> c;
		public List<Head> d;

		public Head Head = new Head();
		public class Head {
			public String img_id;
			public String imgurl;
			public String img_type;
		}

	}

}
