package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Repository
@Transactional
public class UsuarioDaoImpl implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuarios() {
        String query ="FROM Usuario";
        return entityManager.createQuery(query).getResultList();


    }

    /**
     * @param id
     */
    @Override
    public void eliminar(Long id) {
        Usuario u = entityManager.find(Usuario.class,id);
        entityManager.remove(u);
    }

    /**
     * @param usuario
     */
    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    /**
     * @param usuario
     */
    @Override
    public boolean iniciarSesion(Usuario usuario) {
        String query ="FROM Usuario WHERE email = :email AND password = :pass";

        List<Usuario> list = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .setParameter("pass", usuario.getPassword())
                .getResultList();
        return !list.isEmpty();
    }
}
