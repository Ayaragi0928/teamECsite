<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="./css/productDetails.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
<title>商品詳細画面</title>
</head>
<body>
	<div id="header">
		<jsp:include page="header.jsp" />
	</div>
	<div id="main">
		<div id="top">
			<h1>商品詳細</h1>
		</div>
		<s:if test="message != ''">
			<div id="info">
				<s:property value="message" />
			</div>
		</s:if>
		<s:else>
			<div id="contents">
				<div id="left">
					<img
						src="<s:property value='productInfoDTO.imagineFilePath'/><s:property value='productInfoDTO.imagineFileName'/>" />
				</div>
				<br>
				<div id="right">
					<s:form action="AddCartAction">
						<table>
							<tr>
								<th>商品名</th>
								<td><s:property value="productInfoDTO.productName" /></td>
							</tr>
							<tr>
								<th>商品名ふりがな</th>
								<td><s:property value="productInfoDTO.productNameKana" /></td>
							</tr>
							<tr>
								<th>値段</th>
								<td><s:property value="productInfoDTO.price" />円</td>
							</tr>
							<tr>
								<th>購入個数</th>
								<td><select name="count">
										<option value="1" selected="selected">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
								</select>個</td>
							</tr>
							<tr>
								<th>発売会社名</th>
								<td><s:property value="productInfoDTO.releaseCompany" /></td>
							</tr>
							<tr>
								<th>発売年月日</th>
								<td><s:property value="productInfoDTO.releaseDate" /></td>
							</tr>
							<tr>
								<th>商品詳細情報</th>
								<td><s:property value="productInfoDTO.productDescription" /></td>
							</tr>
						</table>
						<s:hidden name="productId" value="%{productInfoDTO.productId}" />
						<div>
							<s:submit value="カートに追加" class="submit" />
						</div>
					</s:form>
				</div>
			</div>
		</s:else>
		<div id="relation">
			<s:if test="productInfoList.size()>0">
				<h2>【関連商品】</h2>
			</s:if>
				<div id="relation_img">
					<s:iterator value="productInfoList">
						<a
							href='<s:url action= "ProductDetailsAction">
								<s:param name="productId" value="%{productId}"/>
								</s:url>'>
							<img
							src="<s:property value='imagineFilePath'/><s:property value ='imagineFileName'/>" />
							<br> <s:property value="productName" /><br>
						</a>
					</s:iterator>
				</div>
		</div>
	</div>
</body>
</html>