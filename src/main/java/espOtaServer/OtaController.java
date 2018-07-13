package espOtaServer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Controller
//@RequestMapping("/ota")
public class OtaController {

    @RequestMapping(value="/ota", method=RequestMethod.GET)
    public String otaHeaders(Model model) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        Enumeration<String> headers = request.getHeaderNames();
        List<String> headerListEnum = Collections.list(headers);
        List<HttpHeader> headerList = new ArrayList<>();
        for (String headerName :headerListEnum) {
            String headerValue = request.getHeader(headerName);
            final HttpHeader httpHeader = new HttpHeader(headerName,headerValue);
            headerList.add(httpHeader);
        }
        model.addAttribute("httpHeaders", headerList);
        //
        model.addAttribute("RemoteAddr", request.getRemoteAddr());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("timeStamp",
                LocalDateTime.ofInstant(Instant.ofEpochMilli(request.getSession().getLastAccessedTime()),
                        ZoneId.systemDefault()).format(dtf));

        return "httpHeader";
    }
}
