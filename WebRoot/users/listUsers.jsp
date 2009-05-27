<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="com.san.tmts.dataobjects.UsersDO"/>
<table>
<s:iterator value="users">
<tr>
	<td>
    	<a href=""><s:property value="userName"/></a>
    </td>
   <td>
    	<s:property value="email"/>
    </td>
 </tr>
</s:iterator>
</table>

<s:iterator value="myList">

    	<s:property/>
    
</s:iterator>