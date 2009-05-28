package com.san.my.web.action.account;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.san.my.common.global.IConstants;
import com.san.my.common.util.springs.ServiceLocator;
import com.san.my.service.IUsersService;
import com.san.tmts.dataobjects.UsersDO;


/**
 * <p> Validate a user login. </p>
 */
public  class Login  extends ActionSupport{
	
    public String execute() throws Exception {       
        IUsersService service = ServiceLocator.getUserService();
        UsersDO user = service.loadUser(username, password);
		if(user==null){
            addActionError("user not exists");            
            return ERROR;
		}else if(!user.getPassword().equals(password)){
			addActionError("Invalid password");
			return ERROR;
		}else {
			Map<String,Object> session = ActionContext.getContext().getSession();
			session.put(IConstants.KEY_USER_IN_SESSION,user);
			return SUCCESS;
		}
	}
   
    // ---- Username property ----

    /**
     * <p>Field to store User username.</p>
     * <p/>
     */
    private String username = null;


    /**
     * <p>Provide User username.</p>
     *
     * @return Returns the User username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Store new User username</p>
     *
     * @param value The username to set.
     */
    public void setUsername(String value) {
        username = value;
    }

    // ---- Username property ----

    /**
     * <p>Field to store User password.</p>
     * <p/>
     */
    private String password = null;


    /**
     * <p>Provide User password.</p>
     *
     * @return Returns the User password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Store new User password</p>
     *
     * @param value The password to set.
     */
    public void setPassword(String value) {
        password = value;
    }
		
}
