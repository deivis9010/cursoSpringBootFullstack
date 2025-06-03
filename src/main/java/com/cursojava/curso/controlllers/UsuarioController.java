package com.cursojava.curso.controlllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    //@GetMapping("/prueba")
    @RequestMapping(value = "getlistusuarios")
    public List<Usuario> GetListUsuarios() {
        List<Usuario> list = new ArrayList<Usuario>();
        list = usuarioDao.getUsuarios();
        return list;

    }

    @RequestMapping(value = "addusuario",method = RequestMethod.POST)
    public void PostUsuario(@RequestBody Usuario usuario) {
        usuarioDao.registrar(usuario);


    }

    @RequestMapping(value = "getusuario/{id}")
    public Usuario GetUsuario(long id) {
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
    public Usuario UpdateUsuario() {
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
    public void DeleteUsuario(@PathVariable Long id) {
        usuarioDao.eliminar(id);
    }




}
