package spring.qlbh.QUANLYBANHANG.controller.Admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import spring.qlbh.QUANLYBANHANG.dao.HangDAO;
import spring.qlbh.QUANLYBANHANG.dao.LoaiHangDAO;
import spring.qlbh.QUANLYBANHANG.model.HangInfo;
import spring.qlbh.QUANLYBANHANG.model.LoaiHangInfo;

@Controller(value = "QuanLyHangController")
@RequestMapping(value = "/admin/hang")
public class QuanLyHangController {
	@Autowired
	private HangDAO hangDAO;
	@Autowired
	private LoaiHangDAO loaiHangDAO;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == HangInfo.class) {

			// Ä�Äƒng kÃ½ Ä‘á»ƒ chuyá»ƒn Ä‘á»•i giá»¯a cÃ¡c Ä‘á»‘i tÆ°á»£ng multipart thÃ nh byte[]
			dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		}
	}
//load rang sua hang
	@RequestMapping("/suahang/{maHang}")
	public String suaHang(@PathVariable("maHang") int maHang, Model model, HttpServletRequest request,
			@ModelAttribute("suaHangInfo") HangInfo suaHangInfo) {
		List<LoaiHangInfo> loai = loaiHangDAO.loadMenu();
		model.addAttribute("loai", loai);
		HangInfo hang = hangDAO.loadHangTheoId(maHang);
		model.addAttribute("hangtheoid", hang);
		return "admin/SuaHang";
	}
// sua hang thanh cong
	@RequestMapping("/suahang/hoanthanh/{maHang}")
	public String hoanThanh(@PathVariable("maHang") int maHang, Model model, HttpServletRequest request,
			@ModelAttribute("suaHangInfo") HangInfo suaHangInfo) {
		String tenHang = suaHangInfo.getTenHang();
//		String imageLike = suaHangInfo.getImageLink();
		String ngayNhapHang= suaHangInfo.getNgayNhapHang();
		int donGia = suaHangInfo.getDonGia();
		int maLoai =suaHangInfo.getMaLoai();
		float soLuong = suaHangInfo.getSoLuong();
		String donVi= suaHangInfo.getDonVi();
		String noiSX = suaHangInfo.getNoiSX();
		String tTThem = suaHangInfo.gettTThem();
		String trangThai = suaHangInfo.getTrangThai();		
		CommonsMultipartFile fileDatas = suaHangInfo.getAnh();
		String imageLink = fileDatas.getOriginalFilename();
		HangInfo suahang = new HangInfo(maHang, tenHang, imageLink, ngayNhapHang, donGia, maLoai,
				soLuong, donVi, noiSX, tTThem, trangThai);		
		// call goi ham sua
		hangDAO.uploadHang(suahang);
		// call up file.
		doUpload(request, suaHangInfo);
		return "redirect:/admin/hang";
	}
//xoa hang theo ma
	@RequestMapping("/xoahang")
	public String deleteHang(Model model, HttpServletRequest request, HttpSession session) {
		int maHang = Integer.parseInt(request.getParameter("maHang"));
		hangDAO.xoaHang(maHang);
		return "redirect:/admin/hang";
	}
// load trang them hang
	@RequestMapping(value = "/addhang")
	public String themhang(Model model) {
		List<LoaiHangInfo> loaiHang = loaiHangDAO.loadMenu();
		HangInfo hangInfo = new HangInfo();
		model.addAttribute("hangInfo", hangInfo);
		model.addAttribute("loaiHang", loaiHang);
		return "admin/addHang";
	}
//them hang thanh cong
	@RequestMapping(value = "/addhang/them", method = RequestMethod.POST)
	public String addHang(Model model, HttpServletRequest request, @ModelAttribute("hangInfo") HangInfo hangInfo) {
		Random rand = new Random();
		int maHang = rand.nextInt(1000);
		String tenHang = hangInfo.getTenHang();
		String ngayNhapHang= hangInfo.getNgayNhapHang();
		int donGia = hangInfo.getDonGia();
		int maLoai =hangInfo.getMaLoai();
		float soLuong = hangInfo.getSoLuong();
		String donVi= hangInfo.getDonVi();
		String noiSX = hangInfo.getNoiSX();
		String tTThem = hangInfo.gettTThem();
		String trangThai = hangInfo.getTrangThai();
		CommonsMultipartFile fileDatas = hangInfo.getAnh();
		String imageLink = fileDatas.getOriginalFilename();		
		HangInfo hang = new HangInfo(maHang, tenHang, imageLink, ngayNhapHang, donGia, maLoai,
					soLuong, donVi, noiSX, tTThem, trangThai);
			hangDAO.insertHang(hang);
			doUpload(request, hangInfo);
		return "redirect:/admin/hang";
	}
	// trang tim kiếm
		@RequestMapping(value = "/timkiemhang", method = RequestMethod.GET)
		public String timKiem(Model model, HttpServletRequest request, HttpSession session) {
			String tukhoa = request.getParameter("tukhoa");
			List<HangInfo> tkh = hangDAO.timKiemHangTheoTen(tukhoa);
			model.addAttribute("loadHang", tkh);
			return "admin/Hang";
		}
		//upload anh
	private void doUpload(HttpServletRequest request, //
			HangInfo hangInfo) {
		String uploadRootPath = request.getServletContext().getRealPath("/") + "template/client/img";
		System.out.println("uploadRootPath=" + uploadRootPath);
		File uploadRootDir = new File(uploadRootPath);
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		CommonsMultipartFile fileDatas = hangInfo.getAnh();
		List<File> uploadedFiles = new ArrayList<File>();
		String name = fileDatas.getOriginalFilename();
		System.out.println("Client File Name = " + name);
		if (name != null && name.length() > 0) {
			try {
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(fileDatas.getBytes());
				stream.close();
				uploadedFiles.add(serverFile);
				System.out.println("Write file: " + serverFile);
			} catch (Exception e) {
				System.out.println("Error Write file: " + name);
			}
		}

	}

}
