package com.cursojava.curso.controlllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private  UsuarioDao usuarioDao;
    @Autowired
    private  JWTUtil jwt;
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String iniciarSesion(@RequestBody Usuario usuario) {
        Usuario ul = usuarioDao.iniciarSesion(usuario);
        if (ul != null){

            return jwt.create(ul.getId().toString(),ul.getEmail());
        }
        else {
            return "FAIL";
        }
    }
}
