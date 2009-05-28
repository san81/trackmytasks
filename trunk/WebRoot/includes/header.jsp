
<jsp:directive.page import="com.san.my.common.global.IConstants"/>
<jsp:directive.page import="com.san.tmts.dataobjects.UsersDO"/>
<% 
	UsersDO user = (UsersDO)session.getAttribute(IConstants.KEY_USER_IN_SESSION);
%>
<table width="100%">
 	<tr>
 	   <td>
 	   		Welcome <%=user.getUserName() %>
 	   </td>
 	   <td align="right">
 	   		<a href="logout.action">logout</a>
 	   </td>
 	 </tr>
 </table>
