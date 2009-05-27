<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.san.tmts.dataobjects.UsersDO"/>
<% List users = (List)request.getAttribute("users"); %>
<s:iterator id="users">
	<s:property/>
</s:iterator>