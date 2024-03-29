<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"
	errorPage="/error/error.do" buffer="20kb"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>



<html>
<head>	
	<title><s:text name="label.tmts" /></title>		
</head>

<body>
	<table border=1 width="100%" height="100%">
		<tr>
			<td colspan="2">
				<tiles:insertAttribute name="banner"></tiles:insertAttribute>
			</td>
		</tr>
		<tr height="500">
			<td width="20%"> <tiles:insertAttribute name="sideMenu"></tiles:insertAttribute> </td>
			<td>
				<table>
					<tr>
					  <td valign="top">
						<tiles:insertAttribute name="innerMenu"></tiles:insertAttribute> 
					  </td>
					  </tr>
					  <tr>
					  <td>
						<tiles:insertAttribute name="body"></tiles:insertAttribute> 					  
					  </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</body>
</html>
