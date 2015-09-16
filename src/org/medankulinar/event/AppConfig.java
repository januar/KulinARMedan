/**
 * File ini digunakan untuk seting 
 * alamat webservice dailyme
 * 
 */
package org.medankulinar.event;

public class AppConfig {
 /* ganti alamat ini sesuai dengan server webservicenya 
  * jika mau dicoba di emulator, ip tetap 10.0.2.2
  */
	public static final String SERVER = "http://medankulinar-freepro.rhcloud.com/api/";
	public static final String GET_LIST = "index/getlistview";
	public static final String GET_DETAIL = "index/getdetail";
	public static final String GET_BY_KATEGORI = "index/getpoibykategori";
	public static final String GET_KATEGORI = "index/getkategori";
}
