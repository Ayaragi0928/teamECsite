package com.internousdev.laravel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.laravel.dto.ProductInfoDTO;
import com.internousdev.laravel.util.DBConnector;

public class ProductInfoDAO {

	// 商品一覧画面で画像を選択し、その商品詳細画面を表示する用
	public ProductInfoDTO getProductInfoImg(int productId) throws SQLException {
		ProductInfoDTO dto = new ProductInfoDTO();
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "SELECT * FROM product_info WHERE product_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImagineFilePath(rs.getString("image_file_path"));
				dto.setImagineFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return dto;
	}

	// 商品詳細画面に関連商品を表示する
	public List<ProductInfoDTO> getRelation(int categoryId, int productId) throws SQLException {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();

		String sql = "SELECT * FROM product_info WHERE category_id = ? && product_id NOT IN(?) ORDER BY RAND() LIMIT 3";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImagineFilePath(rs.getString("image_file_path"));
				dto.setImagineFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con.close();
		}
		return productInfoDTOList;
	}

	// 文字入力があり、カテゴリー選択がありorなしのとき
	public List<ProductInfoDTO> getSearchItemCategory(String[] productName, String[] productNameKana, int categoryId) {
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		String sql = "SELECT * FROM product_info";

		// カテゴリーが選択されていた場合
		if (categoryId != 0) {
			for (int i = 0; i < productName.length; i++) {
				if (i == 0) {
					sql += " WHERE product_name LIKE '%" + productName[0] + "%' AND category_id=" + categoryId
							+ " OR product_name_kana LIKE '%" + productNameKana[0] + "%' AND category_id=" + categoryId;
				} else {
					sql += " OR product_name LIKE '%" + productName[i] + "%'AND category_id=" + categoryId
							+ " OR product_name_kana LIKE '%" + productNameKana[i] + "%' AND category_id=" + categoryId;
				}
			}

		// カテゴリーが選択されていない場合
		} else {
			for (int i = 0; i < productName.length; i++) {
				if (i == 0) {
					sql += " WHERE product_name LIKE '%" + productName[0] + "%' OR product_name_kana LIKE '%"
							+ productNameKana[0] + "%'";
				} else {
					sql += " OR product_name LIKE '%" + productName[i] + "%' OR product_name_kana LIKE '%"
							+ productNameKana[i] + "%'";
				}
			}
		}

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setImagineFilePath(rs.getString("image_file_path"));
				dto.setImagineFileName(rs.getString("image_file_name"));
				dto.setPrice(rs.getInt("price"));
				dto.setReleaseCompany(rs.getString("release_company"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setCategoryId(rs.getInt("category_id"));
				productInfoDTOList.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	// 文字入力がなく、カテゴリー選択がありorなしのとき (ヘッダーから直接商品一覧をクリックした場合も含む）
	public List<ProductInfoDTO> getSearchProduct(int categoryId) {
		List<ProductInfoDTO> productInfoDTO = new ArrayList<ProductInfoDTO>();
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "";

		// カテゴリー選択がある場合
		if (categoryId != 0) {
			sql = "SELECT * FROM product_info WHERE category_id=" + categoryId;

		// カテゴリー選択がない場合、もしくはヘッダーから直接商品一覧をクリックした場合
		} else if (categoryId == 0) {
			sql = "SELECT * FROM product_info";
		}

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImagineFilePath(rs.getString("image_file_path"));
				dto.setImagineFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTO.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

	public List<ProductInfoDTO> getSearchProductTest() throws SQLException {

		List<ProductInfoDTO> productInfoDTO = new ArrayList<ProductInfoDTO>();
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		String sql = "SELECT * FROM product_info";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductInfoDTO dto = new ProductInfoDTO();
				dto.setProductId(rs.getInt("product_id"));
				dto.setProductName(rs.getString("product_name"));
				dto.setProductNameKana(rs.getString("product_name_kana"));
				dto.setProductDescription(rs.getString("product_description"));
				dto.setCategoryId(rs.getInt("category_id"));
				dto.setPrice(rs.getInt("price"));
				dto.setImagineFilePath(rs.getString("image_file_path"));
				dto.setImagineFileName(rs.getString("image_file_name"));
				dto.setReleaseDate(rs.getDate("release_date"));
				dto.setReleaseCompany(rs.getString("release_company"));
				productInfoDTO.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}
}