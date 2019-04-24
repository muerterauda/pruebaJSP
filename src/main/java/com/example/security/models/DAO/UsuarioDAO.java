package com.example.security.models.DAO;

import com.example.security.models.entity.Usuario;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class UsuarioDAO extends BaseDAO<Usuario> {
    private static final String ROLES = "roles";
    private static final String NOMBRE_COMPLETO = "nombreCompleto";
    private static final String EMAIL = "email";

    public UsuarioDAO() {
        super(Usuario.class);
    }

    public Usuario findByDniEmail(String dni, String email) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        if (dni != null && email != null) {
            q.or(q.criteria(EMAIL).equal(email), q.criteria("dni").equal(dni));
        } else if (dni == null && email != null) {
            q.criteria(EMAIL).equal(email);
        } else if (dni != null) {
            q.criteria("dni").equal(dni);
        } else {
            return null;
        }
        return q.get();
    }

    public Usuario findByDni(String dni) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        q.and(q.criteria("dni").equal(dni));
        return q.get();
    }

    public List<Usuario> findByDniEmailNombre(String filtros) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        q.or(q.criteria("dni").equal(Pattern.compile(filtros, Pattern.CASE_INSENSITIVE)),
                q.criteria(EMAIL).equal(Pattern.compile(filtros, Pattern.CASE_INSENSITIVE)),
                q.criteria(NOMBRE_COMPLETO).equal(Pattern.compile(filtros, Pattern.CASE_INSENSITIVE)));
        return q.asList();
    }

    public List<Usuario> findByRol(Usuario.UserRole rol) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        q.criteria(ROLES).hasThisOne(rol);
        return q.asList();
    }

    public List<Usuario> findByNombreEmail(String nombre, String email, int offset, int limit,
                                           String orden, boolean ascendente, Usuario.UserRole rol) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        ArrayList<Criteria> criterios = new ArrayList<>();
        criterios.add(q.criteria(ROLES).hasThisOne(rol));
        if (nombre != null) {
            criterios.add(
                    q.criteria(NOMBRE_COMPLETO).equal(Pattern.compile(nombre, Pattern.CASE_INSENSITIVE)));
        }
        if (email != null) {
            criterios.add(q.criteria(EMAIL).equal(Pattern.compile(email, Pattern.CASE_INSENSITIVE)));
        }
        Criteria[] crriterias = new Criteria[criterios.size()];
        q.and(criterios.toArray(crriterias));
        String ordenq = (ascendente ? "" : "-") + orden;
        return q.offset(offset).limit(limit).order(ordenq).asList();
    }

    public long findTotalByNombreEmail(String nombre, String email, Usuario.UserRole rol) {
        Query<Usuario> q = getDs().createQuery(Usuario.class);
        ArrayList<Criteria> criterios = new ArrayList<>();
        criterios.add(q.criteria(ROLES).hasThisOne(rol));
        if (nombre != null) {
            criterios.add(
                    q.criteria(NOMBRE_COMPLETO).equal(Pattern.compile(nombre, Pattern.CASE_INSENSITIVE)));
        }
        if (email != null) {
            criterios.add(q.criteria(EMAIL).equal(Pattern.compile(email, Pattern.CASE_INSENSITIVE)));
        }
        Criteria[] crriterias = new Criteria[criterios.size()];
        q.and(criterios.toArray(crriterias));
        return q.countAll();
    }
}
