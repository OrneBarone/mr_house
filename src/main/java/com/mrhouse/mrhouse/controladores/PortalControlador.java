
package com.mrhouse.mrhouse.controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mrhouse.mrhouse.Entidades.*;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioUsuario;

@Controller
@RequestMapping("/")

public class PortalControlador {
    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioInmueble servicioInmueble;

    @GetMapping("/")
    public String index(ModelMap modelo) {

        List<Inmueble> inmuebles = servicioInmueble.listarInmuebles();

        modelo.addAttribute("inmuebles", inmuebles);

        return "index.html";
    }

    @GetMapping("/registro")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registrar")
    public String registro(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

        try {
            servicioUsuario.registrar(nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente!");
            return "redirect:/";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password", password2);
            return "registrar.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }

    // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        // if (logueado.getRol().toString().equalsIgnoreCase("ADMIN")) {
      //  return "index.html";
        // }

        return "index.html";
    }
}
