<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <title>TrackMyTask Login</title>

	    <link href="<s:url value="/css/main.css"/>" rel="stylesheet"
          type="text/css"/>
		  
	</head>
    <body>


    <s:form action="doLogin" method="POST">
			
 <tr>
       <td colspan="2">
           Login
       </td>

   </tr>

 <tr>
       <td colspan="2">			
			<!--<s:fielderror /> -->
       </td>

 </tr>
			

			<s:textfield name="username" label="Login name"/>
			<s:password name="password" label="Password"/>
    		<s:submit value="Login" align="center"/>
				
	</s:form>

    </body>

</html>

