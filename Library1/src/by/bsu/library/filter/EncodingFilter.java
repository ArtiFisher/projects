package by.bsu.library.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
    private String code;
    private static final String ENCODING = "encoding";
    public void init(FilterConfig fConfig){
        code = fConfig.getInitParameter(ENCODING);
    }

    public void destroy(){
        code = null;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();       
        if(code!=null && !code.equalsIgnoreCase(codeRequest)){
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

}
