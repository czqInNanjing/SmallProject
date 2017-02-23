package cn.tags;

import cn.constants.Constants;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @author Qiang
 * @since 02/01/2017
 */
public class CheckSessionHandler extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        try {
            Object id = getJspContext().getAttribute(Constants.userIDStr , PageContext.SESSION_SCOPE);
            if (id == null) {
                PageContext pageContext = (PageContext) getJspContext();
                pageContext.getRequest().getRequestDispatcher("/html/login.html").forward(pageContext.getRequest() , pageContext.getResponse());

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new JspException(e.getMessage());
        }
    }
}
