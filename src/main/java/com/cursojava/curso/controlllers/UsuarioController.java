package com.cursojava.curso.controlllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtil jwt;

    //@GetMapping("/prueba")
    @RequestMapping(value = "getlistusuarios")
    public List<Usuario> GetListUsuarios(@RequestHeader(name = "Authorization") String token) {
        List<Usuario> list = new ArrayList<Usuario>();


       if (isValidToken(token)){
           list = usuarioDao.getUsuarios();
       }

        return list;

    }

    private boolean isValidToken(String token) {

        String idKey = jwt.getIdFromToken(token);

        if (idKey != null){
            return true;
        }
        return  false;

    }

    @RequestMapping(value = "addusuario",method = RequestMethod.POST)
    public void PostUsuario(@RequestBody Usuario usuario) {
        Argon2 argon2Hash = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String pass = argon2Hash.hash(1,1024,1,usuario.getPassword());
        usuario.setPassword(pass);
        usuarioDao.registrar(usuario);


    }

    @RequestMapping(value = "getusuario/{id}")
    public Usuario GetUsuario(@RequestHeader(name = "Authorization") String token, long id) {

        if (isValidToken(token)){
            return null;
        }
        Usuario u = new Usuario();
        u.setId(id);
        u.setApellidos("Leal");
        u.setNombre("Deivis");
        u.setEmail("deivis@de.com");
        u.setTelefono("55555");
        u.setPassword("pass123");
        return u;

    }

    @RequestMapping(value = "updusuario")
    public Usuario UpdateUsuario(@RequestHeader(name = "Authorization") String token) {

        if (isValidToken(token)){
            return null;
        }
        Usuario u = new Usuario();
        u.setId(Long.valueOf(25));
        u.setApellidos("Leal");
        u.setNombre("Deivis");
        u.setEmail("deivis@de.com");
        u.setTelefono("55555");
        u.setPassword("pass123");
        return u;

    }

    @RequestMapping(value = "delusuario/{id}",method = RequestMethod.DELETE)
    public void DeleteUsuario(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) {

        if (isValidToken(token)){
            return ;
        }

        usuarioDao.eliminar(id);
    }




}
