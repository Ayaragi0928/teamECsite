package com.internousdev.laravel.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.laravel.dao.ProductInfoDAO;
import com.internousdev.laravel.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {

	private Map<String, Object> session;
	private int productId;
	private String message = "";
	private List<ProductInfoDTO> productInfoList = new ArrayList<ProductInfoDTO>();
	private ProductInfoDTO productInfoDTO = new ProductInfoDTO();

	public String execute() throws SQLException {
		String result = SUCCESS;

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		// 商品詳細情報がない場合
		productInfoDTO = productInfoDAO.getProductInfoImg(productId);

		if(productInfoDTO.getProductId() == 0) {
			setMessage("商品の詳細情報がありません。");
			return result;
		// 商品詳細情報がある場合
		} else {
			productInfoList = productInfoDAO.getRelation(productInfoDTO.getCategoryId(), productInfoDTO.getProductId());
		}
		return result;
	}

	public ProductInfoDTO getProductInfoDTO() {
		return productInfoDTO;
	}

	public void setProductInfoDTO(ProductInfoDTO productInfoDTO) {
		this.productInfoDTO = productInfoDTO;
	}

	public List<ProductInfoDTO> getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(ArrayList<ProductInfoDTO> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
}
