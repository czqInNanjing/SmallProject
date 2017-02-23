package cn.action;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * @author Qiang
 * @since 03/01/2017
 */
@Controller
public class LogoutAction extends BaseAction {

    @Override
    public String execute() throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null ){
            session.invalidate();
        }
        response.sendRedirect("/login");
        return super.execute();
    }


}
