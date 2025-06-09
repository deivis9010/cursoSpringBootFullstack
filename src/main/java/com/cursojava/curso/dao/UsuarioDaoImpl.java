package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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
     * @param id id para borrar
     */
    @Override
    public void eliminar(Long id) {
        Usuario u = entityManager.find(Usuario.class,id);
        entityManager.remove(u);
    }

    /**
     * @param usuario usuario
     */
    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    /**
     * @param usuario usuario
     */
    @Override
    public Usuario iniciarSesion(Usuario usuario) {
        String query ="FROM Usuario WHERE email = :email ";

        List<Usuario> list = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())

                .getResultList();
        if (list.isEmpty()) {
            return null;
        }
        Usuario u = list.get(0);
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if( argon2.verify(u.getPassword(), usuario.getPassword()))
           return u;

        return null;

    }
}
