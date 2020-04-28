package spring.qlbh.QUANLYBANHANG.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import spring.qlbh.QUANLYBANHANG.dao.HangDAO;
import spring.qlbh.QUANLYBANHANG.entity.Hang;
import spring.qlbh.QUANLYBANHANG.entity.KhuyenMai;
import spring.qlbh.QUANLYBANHANG.entity.LoaiHang;
import spring.qlbh.QUANLYBANHANG.model.HangInfo;
import spring.qlbh.QUANLYBANHANG.model.HangKhuyenMaiInfo;

public class HangDAOImpl implements HangDAO {
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	public List<HangInfo> loadHang() {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai) " // cÃ¡c																																			// HangInfo
				+ " from " + Hang.class.getName() + " h "; // Ä‘á»ƒ Ã½ chá»— nÃ y, chá»— nÃ y
															// pháº£i cÃ¡ch ra, chá»© k nÃ³ lá»—i
		Query query = session.createQuery(sql);
		return query.list();
	}
	@Override
	public List<HangKhuyenMaiInfo> loadHangKM(){
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangKhuyenMaiInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai,km.phanTram) " // cÃ¡c																																			// HangInfo
				+ " from " + Hang.class.getName() + " h "+"LEFT JOIN "+ LoaiHang.class.getName() + " lh "+"ON h.maLoai=lh.maLoai LEFT JOIN "+ KhuyenMai.class.getName() + " km "+"ON lh.maKM=km.maKM"; // Ä‘á»ƒ Ã½ chá»— nÃ y, chá»— nÃ y											// pháº£i cÃ¡ch ra, chá»© k nÃ³ lá»—i
		Query query = session.createQuery(sql);
		return query.list();
	}
	// load hÃ ng theo loáº¡i
	@Override
	public List<HangKhuyenMaiInfo> loadHangTheoLoai(int maLoai) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangKhuyenMaiInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai,km.phanTram) " 
				+ " from " + Hang.class.getName() + " h " + "LEFT JOIN "+ LoaiHang.class.getName() + " lh "+"ON h.maLoai=lh.maLoai LEFT JOIN "+ KhuyenMai.class.getName() + " km "+"ON lh.maKM=km.maKM"+" where h.maLoai=: maLoai";
		Query query = session.createQuery(sql);
		query.setParameter("maLoai", maLoai);
		return query.list();
	}
	public HangKhuyenMaiInfo loadHangKMTheoId(int maHang) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " +HangKhuyenMaiInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai,km.phanTram) "
				+ " from " + Hang.class.getName() + " h " + "LEFT JOIN "+ LoaiHang.class.getName() + " lh "+"ON h.maLoai=lh.maLoai LEFT JOIN "+ KhuyenMai.class.getName() + " km "+"ON lh.maKM=km.maKM"+" where h.maHang=: maHang  ";
		Query query = session.createQuery(sql);
		query.setParameter("maHang", maHang);
		return (HangKhuyenMaiInfo) query.uniqueResult();
	}
	@Override
	public HangInfo loadHangTheoId(int maHang) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai) "
				+ " from " + Hang.class.getName() + " h " + " where h.maHang=: maHang  ";
		Query query = session.createQuery(sql);
		query.setParameter("maHang", maHang);
		return (HangInfo) query.uniqueResult();
	}

	@Override
	public HangInfo loadHangTheoTen(String tenHang) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangInfo.class.getName()
				+ " ( h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai) "
				+ " from " + Hang.class.getName() + " h " + " where h.tenHang=: tenHang";
		Query query = session.createQuery(sql);
		query.setParameter("tenHang", tenHang);
		return (HangInfo) query.uniqueResult();
	}

	@Override
	public void insertHang(HangInfo hangInfo) {
		Session session = sessionfactory.getCurrentSession();
		Hang hangentity = new Hang();
		hangentity.setMaHang(hangInfo.getMaHang());
		hangentity.setTenHang(hangInfo.getTenHang());
		hangentity.setImage(hangInfo.getImageLink());
		hangentity.setNgayNhapHang(hangInfo.getNgayNhapHang());
		hangentity.setDonGia(hangInfo.getDonGia());
		hangentity.setMaLoai(hangInfo.getMaLoai());
		hangentity.setSoLuong(hangInfo.getSoLuong());
		hangentity.setDonVi(hangInfo.getDonVi());
		hangentity.setNoiSX(hangInfo.getNoiSX());	
		hangentity.settTThem(hangInfo.gettTThem());	
		hangentity.setTrangThai(hangInfo.getTrangThai());
		session.persist(hangentity);

	}
	@Override
	public void uploadHang(HangInfo hangInfo) {
		Session session = sessionfactory.getCurrentSession();
		Hang hangentity = new Hang();
		hangentity.setMaHang(hangInfo.getMaHang());
		hangentity.setTenHang(hangInfo.getTenHang());
		hangentity.setImage(hangInfo.getImageLink());
		hangentity.setNgayNhapHang(hangInfo.getNgayNhapHang());
		hangentity.setDonGia(hangInfo.getDonGia());
		hangentity.setMaLoai(hangInfo.getMaLoai());
		hangentity.setSoLuong(hangInfo.getSoLuong());
		hangentity.setDonVi(hangInfo.getDonVi());
		hangentity.setNoiSX(hangInfo.getNoiSX());	
		hangentity.settTThem(hangInfo.gettTThem());	
		hangentity.setTrangThai(hangInfo.getTrangThai());

		session.update(hangentity);

	}

//	@Override
//	public HangInfo loadHangTheoTen(String tenHang) {
//		Session session = sessionfactory.getCurrentSession();
//		String sql = " select new  " + HangInfo.class.getName()
//				+ " ( h.maHang, h.tenHang, h.donGia, h.imageLink, h.vAT, h.maLoai, h.nhaSX, h.ngaySX, h.tGBaoHanh, h.tTThem, h.soLuongHang, h.trangThaiHang) "
//				+ " from " + Hang.class.getName() + " h " + " where h.tenHang=: tenHang ";
//		Query query = session.createQuery(sql);
//		query.setParameter("tenHang", tenHang);
//		return (HangInfo) query.uniqueResult();
//	}

	@Override
	public void xoaHang(int maHang) {
		Hang hang = this.findHang(maHang);
		if (hang != null) {
			this.sessionfactory.getCurrentSession().delete(hang);
		}
	}

	@Override
	public Hang findHang(int maHang) {
		Session session = sessionfactory.getCurrentSession();
		Criteria crit = session.createCriteria(Hang.class);
		crit.add(Restrictions.eq("maHang", maHang));
		return (Hang) crit.uniqueResult();
	}

	@Override
	public void suaHang(int maHang) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<HangInfo> timKiemHangTheoTen(String tenHang) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangInfo.class.getName()
				+ " ( h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai) "
				+ " from " + Hang.class.getName() + " h " + " where h.tenHang like : tenHang";
		Query query = session.createQuery(sql);
		query.setParameter("tenHang", "%"+tenHang+"%");
		return query.list();
	}
	@Override
	public List<HangInfo> timKiemHangTheoNXS(String tenNSX) {
		Session session = sessionfactory.getCurrentSession();
		String sql = " select new  " + HangInfo.class.getName()
				+ " (h.maHang,h.tenHang,h.image,h.ngayNhapHang,h.donGia,h.maLoai,h.soLuong,h.donVi,h.noiSX,h.tTThem,h.trangThai) "
				+ " from " + Hang.class.getName() + " h " + " where h.nhaSX like : tenNSX ";
		Query query = session.createQuery(sql);
		query.setParameter("tenNSX", "%"+tenNSX+"%");
		return query.list();

	}
}
