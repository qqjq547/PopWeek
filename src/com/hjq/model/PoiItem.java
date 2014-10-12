package com.hjq.model;

import android.R.integer;

public class PoiItem {
private String uid; 
private String name;
private String address;
private String number;
private long lat;
private long lon;
private String business_url;
private int type;//0baidu,1dazhong
public PoiItem(String uid, String name, String address, String number,
		long lat, long lon, String business_url, int type) {
	super();
	this.uid = uid;
	this.name = name;
	this.address = address;
	this.number = number;
	this.lat = lat;
	this.lon = lon;
	this.business_url = business_url;
	this.type = type;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getNumber() {
	return number;
}
public void setNumber(String number) {
	this.number = number;
}
public long getLat() {
	return lat;
}
public void setLat(long lat) {
	this.lat = lat;
}
public long getLon() {
	return lon;
}
public void setLon(long lon) {
	this.lon = lon;
}
public String getBusiness_url() {
	return business_url;
}
public void setBusiness_url(String business_url) {
	this.business_url = business_url;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
@Override
public String toString() {
	return "PoiItem [uid=" + uid + ", name=" + name + ", address=" + address
			+ ", number=" + number + ", lat=" + lat + ", lon=" + lon
			+ ", business_url=" + business_url + ", type=" + type + "]";
}

}