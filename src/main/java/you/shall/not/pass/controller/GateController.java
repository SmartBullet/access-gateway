package you.shall.not.pass.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import you.shall.not.pass.dto.StaticResources;
import you.shall.not.pass.filter.staticresource.StaticResourceService;
import you.shall.not.pass.service.CsrfCookieService;
import you.shall.not.pass.dto.Access;
import you.shall.not.pass.service.SessionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class GateController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CsrfCookieService csrfCookieService;

    @Autowired
    private StaticResourceService resourceService;

    @Autowired
    private Gson gson;

    @GetMapping({"/access"})
    public ResponseEntity<String> access(HttpServletResponse response) {
        Access.AccessBuilder builder = Access.builder();
        Optional<Cookie> sessionCookie = sessionService.grantSessionCookie();
        sessionCookie.ifPresent(cookie -> {
            response.addCookie(cookie);
            builder.authenticated(true);
            csrfCookieService.addCsrfCookie(response);
        });
        return ResponseEntity.ok(gson.toJson(builder.build()));
    }

    @GetMapping({"/resources"})
    public ResponseEntity<String> resources() {
        StaticResources resources = StaticResources.builder()
                .resources(resourceService.getAllStaticResources()).build();
        return ResponseEntity.ok(gson.toJson(resources));
    }

    @GetMapping({"/home"})
    public String hello() {
        return "angular-app";
    }

}
