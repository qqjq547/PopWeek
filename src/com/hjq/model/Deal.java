package com.hjq.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Deal {
	String deal_id	;// 团购单ID
    String	title;// 团购标题
    String description;	//团购描述
    String city;	 	// 城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
    float list_price;	 //	 团购包含商品原价值
    float current_price;	// 	 团购价格
    String regions;	 // 团购适用商户所在商区
	String categories;	 	// 团购所属分类
	int purchase_count;	 	 //团购当前已购买数
	String publish_date;	 	// 团购发布上线日期
	String details;	 	// 团购详情
	String purchase_deadline;	 	// 团购单的截止购买日期
	String image_url;	 	// 团购图片链接，最大图片尺寸450×280
	String s_image_url;	 	// 小尺寸团购图片链接，最大图片尺寸160×100
	String more_image_urls;	 	 //更多大尺寸图片
	String more_s_image_urls;	 	// 更多小尺寸图片
	int is_popular;	 	 //是否为热门团购，0：不是，1：是
    String notice;	 	 //重要通知(一般为团购信息的临时变更)
    String deal_url;	 	// 团购Web页面链接，适用于网页应用
    String deal_h5_url;	 	// 团购HTML5页面链接，适用于移动应用和联网车载应用
    float commission_ratio;	 	 //当前团单的佣金比例
    int businesses;	 	// 商户数量
    String businesses_name;	 	// 商户名
    int businesses_id;	 	// 商户ID
    String businesses_address;	 	 //商户地址
    float businesses_latitude;	 	 //商户纬度
    float businesses_longitude;	 	 //商户经度
	String businesses_url;	 	 //商户页链接
	
   
public Deal(String deal_id, String title, String description, String city,
			float list_price, float current_price, String regions,
			String categories, int purchase_count, String publish_date,
			String details, String purchase_deadline, String image_url,
			String s_image_url, String more_image_urls,
			String more_s_image_urls, int is_popular, String notice,
			String deal_url, String deal_h5_url, float commission_ratio,
			int businesses, String businesses_name, int businesses_id,
			String businesses_address, float businesses_latitude,
			float businesses_longitude, String businesses_url) {
		super();
		this.deal_id = deal_id;
		this.title = title;
		this.description = description;
		this.city = city;
		this.list_price = list_price;
		this.current_price = current_price;
		this.regions = regions;
		this.categories = categories;
		this.purchase_count = purchase_count;
		this.publish_date = publish_date;
		this.details = details;
		this.purchase_deadline = purchase_deadline;
		this.image_url = image_url;
		this.s_image_url = s_image_url;
		this.more_image_urls = more_image_urls;
		this.more_s_image_urls = more_s_image_urls;
		this.is_popular = is_popular;
		this.notice = notice;
		this.deal_url = deal_url;
		this.deal_h5_url = deal_h5_url;
		this.commission_ratio = commission_ratio;
		this.businesses = businesses;
		this.businesses_name = businesses_name;
		this.businesses_id = businesses_id;
		this.businesses_address = businesses_address;
		this.businesses_latitude = businesses_latitude;
		this.businesses_longitude = businesses_longitude;
		this.businesses_url = businesses_url;
	}

public String getDeal_id() {
	return deal_id;
}

public void setDeal_id(String deal_id) {
	this.deal_id = deal_id;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public float getList_price() {
	return list_price;
}

public void setList_price(float list_price) {
	this.list_price = list_price;
}

public float getCurrent_price() {
	return current_price;
}

public void setCurrent_price(float current_price) {
	this.current_price = current_price;
}

public String getRegions() {
	return regions;
}

public void setRegions(String regions) {
	this.regions = regions;
}

public String getCategories() {
	return categories;
}

public void setCategories(String categories) {
	this.categories = categories;
}

public int getPurchase_count() {
	return purchase_count;
}

public void setPurchase_count(int purchase_count) {
	this.purchase_count = purchase_count;
}

public String getPublish_date() {
	return publish_date;
}

public void setPublish_date(String publish_date) {
	this.publish_date = publish_date;
}

public String getDetails() {
	return details;
}

public void setDetails(String details) {
	this.details = details;
}

public String getPurchase_deadline() {
	return purchase_deadline;
}

public void setPurchase_deadline(String purchase_deadline) {
	this.purchase_deadline = purchase_deadline;
}

public String getImage_url() {
	return image_url;
}

public void setImage_url(String image_url) {
	this.image_url = image_url;
}

public String getS_image_url() {
	return s_image_url;
}

public void setS_image_url(String s_image_url) {
	this.s_image_url = s_image_url;
}

public String getMore_image_urls() {
	return more_image_urls;
}

public void setMore_image_urls(String more_image_urls) {
	this.more_image_urls = more_image_urls;
}

public String getMore_s_image_urls() {
	return more_s_image_urls;
}

public void setMore_s_image_urls(String more_s_image_urls) {
	this.more_s_image_urls = more_s_image_urls;
}

public int getIs_popular() {
	return is_popular;
}

public void setIs_popular(int is_popular) {
	this.is_popular = is_popular;
}

public String getNotice() {
	return notice;
}

public void setNotice(String notice) {
	this.notice = notice;
}

public String getDeal_url() {
	return deal_url;
}

public void setDeal_url(String deal_url) {
	this.deal_url = deal_url;
}

public String getDeal_h5_url() {
	return deal_h5_url;
}

public void setDeal_h5_url(String deal_h5_url) {
	this.deal_h5_url = deal_h5_url;
}

public float getCommission_ratio() {
	return commission_ratio;
}

public void setCommission_ratio(float commission_ratio) {
	this.commission_ratio = commission_ratio;
}

public int getBusinesses() {
	return businesses;
}

public void setBusinesses(int businesses) {
	this.businesses = businesses;
}

public String getBusinesses_name() {
	return businesses_name;
}

public void setBusinesses_name(String businesses_name) {
	this.businesses_name = businesses_name;
}

public int getBusinesses_id() {
	return businesses_id;
}

public void setBusinesses_id(int businesses_id) {
	this.businesses_id = businesses_id;
}

public String getBusinesses_address() {
	return businesses_address;
}

public void setBusinesses_address(String businesses_address) {
	this.businesses_address = businesses_address;
}

public float getBusinesses_latitude() {
	return businesses_latitude;
}

public void setBusinesses_latitude(float businesses_latitude) {
	this.businesses_latitude = businesses_latitude;
}

public float getBusinesses_longitude() {
	return businesses_longitude;
}

public void setBusinesses_longitude(float businesses_longitude) {
	this.businesses_longitude = businesses_longitude;
}

public String getBusinesses_url() {
	return businesses_url;
}

public void setBusinesses_url(String businesses_url) {
	this.businesses_url = businesses_url;
}

public static ArrayList<Deal> JsonArrayToDeal(JSONArray array) throws JSONException{
	   ArrayList<Deal> deals=new ArrayList<Deal>();
	   for (int i = 0; i < array.length(); i++) {
		   JSONObject json=(JSONObject)array.get(i);
		   String deal_id=json.getString("deal_id");
		    String	title=json.getString("title");// 团购标题
		    String description=json.getString("description");	//团购描述
		    String city=json.getString("city");	 	// 城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
		    float list_price=(float)json.getDouble("list_price");	 //	 团购包含商品原价值
		    float current_price=(float)json.getDouble("current_price");	// 	 团购价格
		    String regions=json.getString("regions");	 // 团购适用商户所在商区
			String categories=json.getString("categories");	 	// 团购所属分类
			int purchase_count=json.getInt("purchase_count");	 	 //团购当前已购买数
			String publish_date=json.getString("publish_date");	 	// 团购发布上线日期
			String details=json.getString("details");	 	// 团购详情
			String purchase_deadline=json.getString("purchase_deadline");	 	// 团购单的截止购买日期
			String image_url=json.getString("image_url");	 	// 团购图片链接，最大图片尺寸450×280
			String s_image_url=json.getString("s_image_url");	 	// 小尺寸团购图片链接，最大图片尺寸160×100
			String more_image_urls=json.getString("more_image_urls");	 	 //更多大尺寸图片
			String more_s_image_urls=json.getString("more_s_image_urls");	 	// 更多小尺寸图片
			int is_popular=json.getInt("is_popular");	 	 //是否为热门团购，0：不是，1：是
		    String notice=json.getString("notice");	 	 //重要通知(一般为团购信息的临时变更)
		    String deal_url=json.getString("deal_url");	 	// 团购Web页面链接，适用于网页应用
		    String deal_h5_url=json.getString("deal_h5_url");	 	// 团购HTML5页面链接，适用于移动应用和联网车载应用
		    float commission_ratio=(float)json.getDouble("commission_ratio");	 	 //当前团单的佣金比例
		    int bus_num=json.getJSONArray("businesses").length();
		    String businesses_name="";	 	// 商户名
		    int businesses_id=0; 	// 商户ID
		    String businesses_address="";	 	 //商户地址
		    float businesses_latitude=0;	 	 //商户纬度
		    float businesses_longitude=0;	 	 //商户经度
			String businesses_url="";	
		    if (bus_num>0) {
		    	JSONObject businesses=json.getJSONArray("businesses").getJSONObject(0);	 	// 团购所适用的商户列表
		  	     businesses_name=businesses.getString("name");	 	// 商户名
		  	     businesses_id=businesses.getInt("id");	 	// 商户ID
		  	     businesses_address=businesses.getString("address");	 	 //商户地址
		  	     businesses_latitude=(float)businesses.getDouble("latitude");	 	 //商户纬度
		  	     businesses_longitude=(float)businesses.getDouble("longitude");	 	 //商户经度
		  		 businesses_url=businesses.getString("url");	
		   }
		   deals.add(new Deal(deal_id, title, description, city, list_price,
					current_price, regions, categories, purchase_count,
					publish_date, details, purchase_deadline, image_url,
					s_image_url, more_image_urls, more_s_image_urls,
					is_popular, notice, deal_url, deal_h5_url, commission_ratio, 
					bus_num,
					businesses_name, businesses_id, businesses_address,
					businesses_latitude, businesses_longitude, businesses_url));
	}
	   return deals;
   }
   public static ArrayList<Deal> JsonArrayToCoupon(JSONArray array) throws JSONException{
	   ArrayList<Deal> deals=new ArrayList<Deal>();
	   for (int i = 0; i < array.length(); i++) {
		   JSONObject json=(JSONObject)array.get(i);
		   String deal_id=json.getString("coupon_id");
		    String	title=json.getString("title");// 团购标题
		    String description=json.getString("description");	//团购描述
		    String city="";	 	// 城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
		    float list_price=0;	 //	 团购包含商品原价值
		    float current_price=0;	// 	 团购价格
		    String regions=json.getString("regions");	 // 团购适用商户所在商区
			String categories=json.getString("categories");	 	// 团购所属分类
			int purchase_count=json.getInt("download_count");	 	 //团购当前已购买数
			String publish_date=json.getString("publish_date");	 	// 团购发布上线日期
			String details="";	 	// 团购详情
			String purchase_deadline=json.getString("expiration_date");	 	// 团购单的截止购买日期
			String image_url=json.getString("logo_img_url");	 	// 团购图片链接，最大图片尺寸450×280
			String s_image_url=json.getString("logo_img_url");	 	// 小尺寸团购图片链接，最大图片尺寸160×100
			String more_image_urls=json.getString("logo_img_url");	 	 //更多大尺寸图片
			String more_s_image_urls=json.getString("logo_img_url");	 	// 更多小尺寸图片
			int is_popular=0;	 	 //是否为热门团购，0：不是，1：是
		    String notice="";	 	 //重要通知(一般为团购信息的临时变更)
		    String deal_url=json.getString("coupon_url");	 	// 团购Web页面链接，适用于网页应用
		    String deal_h5_url=json.getString("coupon_h5_url");	 	// 团购HTML5页面链接，适用于移动应用和联网车载应用
		    float commission_ratio=0;	 	 //当前团单的佣金比例
		    int bus_num=json.getJSONArray("businesses").length();
		    String businesses_name="";	 	// 商户名
		    int businesses_id=0; 	// 商户ID
		    String businesses_address="";	 	 //商户地址
		    float businesses_latitude=0;	 	 //商户纬度
		    float businesses_longitude=0;	 	 //商户经度
			String businesses_url="";	
		    if (bus_num>0) {
		    	JSONObject businesses=json.getJSONArray("businesses").getJSONObject(0);	 	// 团购所适用的商户列表
		  	     businesses_name=businesses.getString("name");	 	// 商户名
		  	     businesses_id=businesses.getInt("id");	 	// 商户ID	 //商户经度
		  		 businesses_url=businesses.getString("url");	
		   }
		    deals.add(new Deal(deal_id, title, description, city, list_price,
					current_price, regions, categories, purchase_count,
					publish_date, details, purchase_deadline, image_url,
					s_image_url, more_image_urls, more_s_image_urls,
					is_popular, notice, deal_url, deal_h5_url, commission_ratio, 
					bus_num,
					businesses_name, businesses_id, businesses_address,
					businesses_latitude, businesses_longitude, businesses_url)) ;
	}
	   return deals;
   }
   public static ArrayList<Deal> JsonArrayToReservation(JSONArray array) throws JSONException{
	   ArrayList<Deal> deals=new ArrayList<Deal>();
	   for (int i = 0; i < array.length(); i++) {
		   JSONObject json=(JSONObject)array.get(i);
		   String deal_id=json.getString("business_id");
		    String	title=json.getString("name");// 团购标题
		    String description=json.getString("address");	//团购描述
		    String city=json.getString("city");	 	// 城市名称，city为＂全国＂表示全国单，其他为本地单，城市范围见相关API返回结果
		    float list_price=0;	 //	 团购包含商品原价值
		    float current_price=0;	// 	 团购价格
		    String regions=json.getString("regions");	 // 团购适用商户所在商区
			String categories=json.getString("categories");	 	// 团购所属分类
			int purchase_count=0;	 	 //团购当前已购买数
			String publish_date="";	 	// 团购发布上线日期
			String details="";	 	// 团购详情
			String purchase_deadline="";	 	// 团购单的截止购买日期
			String image_url=json.getString("photo_url");	 	// 团购图片链接，最大图片尺寸450×280
			String s_image_url=json.getString("s_photo_url");	 	// 小尺寸团购图片链接，最大图片尺寸160×100
			String more_image_urls=json.getString("photo_url");	 	 //更多大尺寸图片
			String more_s_image_urls=json.getString("s_photo_url");	 	// 更多小尺寸图片
			int is_popular=0;	 	 //是否为热门团购，0：不是，1：是
		    String notice="";	 	 //重要通知(一般为团购信息的临时变更)
		    String deal_url=json.getString("online_reservation_url");	 	// 团购Web页面链接，适用于网页应用
		    String deal_h5_url=json.getString("online_reservation_h5_url");	 	// 团购HTML5页面链接，适用于移动应用和联网车载应用
		    float commission_ratio=0;	 	 //当前团单的佣金比例
		    int bus_num=0;
		    String businesses_name="";	 	// 商户名
		    int businesses_id=0; 	// 商户ID
		    String businesses_address="";	 	 //商户地址
		    float businesses_latitude=0;	 	 //商户纬度
		    float businesses_longitude=0;	 	 //商户经度
			String businesses_url="";	
		    deals.add(new Deal(deal_id, title, description, city, list_price,
					current_price, regions, categories, purchase_count,
					publish_date, details, purchase_deadline, image_url,
					s_image_url, more_image_urls, more_s_image_urls,
					is_popular, notice, deal_url, deal_h5_url, commission_ratio, 
					bus_num,
					businesses_name, businesses_id, businesses_address,
					businesses_latitude, businesses_longitude, businesses_url)) ;
	}
	   return deals;
   }
  
}
