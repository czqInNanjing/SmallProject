package factory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * @author Qiang
 * @since 06/01/2017
 */
public class EJBFactory {
    public static Object getEJB(String jndipath) {
        try {
            final Hashtable<String, Object> jndiProps = new Hashtable<String, Object>();
            jndiProps.put("jboss.naming.client.ejb.context", true);

            jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");



            final Context context = new InitialContext(jndiProps);
            return context.lookup(jndipath);

        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
