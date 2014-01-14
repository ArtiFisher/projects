package by.epam.library.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {

    public static final String ENCODING_UTF8 = "UTF-8";

    public void init(FilterConfig arg0) throws ServletException {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String encoding = request.getCharacterEncoding();
        if (!ENCODING_UTF8.equals(encoding)) {
            request.setCharacterEncoding(ENCODING_UTF8);
        }
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
