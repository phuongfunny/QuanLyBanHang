package spring.qlbh.QUANLYBANHANG.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hang")
public class Hang {
	private int maHang;
	private String tenHang;
	private String imageLink;
	private String ngayNhapHang;
	private int donGia;
	private int maLoai;
	private float soLuong;
	private String donVi;
	private String noiSX;
	private String tTThem;
	private String trangThai;
	@Id
	@Column(name="MaHang")
	public int getMaHang() {
		return maHang;
	}
	public void setMaHang(int maHang) {
		this.maHang = maHang;
	}
	@Column(name="TenHang")
	public String getTenHang() {
		return tenHang;
	}
	public void setTenHang(String tenHang) {
		this.tenHang = tenHang;
	}
	@Column(name="Image")
	public String getImage() {
		return imageLink;
	}
	public void setImage(String imageLink) {
		this.imageLink = imageLink;
	}
	@Column(name="NgayNhapHang")
	public String getNgayNhapHang() {
		return ngayNhapHang;
	}
	public void setNgayNhapHang(String ngayNhapHang) {
		this.ngayNhapHang = ngayNhapHang;
	}
	@Column(name="DonGia")
	public int getDonGia() {
		return donGia;
	}
	public void setDonGia(int donGia) {
		this.donGia = donGia;
	}
	@Column(name="MaLoai")
	public int getMaLoai() {
		return maLoai;
	}
	public void setMaLoai(int maLoai) {
		this.maLoai = maLoai;
	}
	@Column(name="SoLuong")
	public float getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(float soLuong) {
		this.soLuong = soLuong;
	}
	@Column(name="DonVi")
	public String getDonVi() {
		return donVi;
	}
	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}
	@Column(name="NoiSX")
	public String getNoiSX() {
		return noiSX;
	}
	public void setNoiSX(String noiSX) {
		this.noiSX = noiSX;
	}
	@Column(name="TTThem")
	public String gettTThem() {
		return tTThem;
	}
	public void settTThem(String tTThem) {
		this.tTThem = tTThem;
	}
	@Column(name="TrangThai")
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	
	

	
	
}
