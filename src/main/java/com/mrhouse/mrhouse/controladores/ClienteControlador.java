/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thell
 */
@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
@Autowired
private ServicioCliente servicioCliente;
    @GetMapping("/registrar")
    public String registrar() {
        return "cliente_form.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam Integer dni, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            servicioCliente.registrar(archivo, nombre, dni, email, password, password2);
          modelo.put("exito", "Tu usuario de cliente fue guardado con exito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre",nombre);
            modelo.put("email",email);
            return  "cliente_form.html";
        }
        return "index.html";
    }
 @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "usuario o contraseña invalidos");
            
        }
return "login.html";
        
    }
    
}
