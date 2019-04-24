/*
 * Copyright (C) 2014 The Climate Corporation and released under an Apache 2.0 license. You may not
 * use this library except in compliance with the License. You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0 See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership. Unless required by applicable law or agreed
 * to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.example.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.example.security.models.entity.Usuario;
import com.example.security.models.DAO.UsuarioDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;


/**
 * The SAMLUserDetailsService is similar to the Spring Security UserDetailsService interface.
 * Annoyingly though, it’s a separate interface, not a sub interface. This means that any
 * implementation of UserDetailsService that we already have will have to be re-implemented for
 * SAML. The SAMLUserDetailsService is optional. If it’s not provided, weill get an instance of
 * OpenSAML NameIDImpl as principal. This is a little cumbersome to work with and is likely to cause
 * cause issues if we're integrating with an existing Spring Security project. Spring Security
 * usually uses an implementation of UserDetails as the principal. Hence this class that maps
 * SAMLCredential to Spring Security UserDetails.
 */
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    // Logger
    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);
    // DAO usuarios
    private static UsuarioDAO udao = new UsuarioDAO();
//    private static InvestigadorDAO idao = new InvestigadorDAO();

    /**
     * Default constructor.
     */
    public SAMLUserDetailsServiceImpl() {
    }

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority;
        SAMLUserDetailsImpl userDetails;
        Usuario u = null;
//        Investigador inv = null;
        String inv = null;

        // Obtener todos los datos necesarios
        String email = "";
        if (credential.getAttribute("irisMailMainAddress") != null) {
            email = credential.getAttributeAsString("irisMailMainAddress");
        }
        String photo = "";
        if (credential.getAttribute("jpegPhoto") != null) {
            photo = credential.getAttributeAsString("jpegPhoto");
        }
        String nombreCompleto = "";
        if (credential.getAttribute("displayName") != null) {
            nombreCompleto = credential.getAttributeAsString("displayName");
        }
        String dni = "";
        if (credential.getAttribute("schacPersonalUniqueID") != null) {
            dni = parseNif(credential.getAttributeAsString("schacPersonalUniqueID"));
        }

//     if (dni.equals("79059063J")) {
//     dni = "24276979L";
//     email = "acurdiales@uma.es";
//     nombreCompleto = "Amalia Cristina Urdiales García";
//     }

        // usuario conectado
        u = udao.findByDniEmail(dni, email);
//        inv = idao.buscarInvestigadorPorDNI(dni, true);

        if (u == null && inv == null) {
            // si no es usuario se crea uno sin roles
            LOG.debug("Usuario sin permisos: " + nombreCompleto);
            u = new Usuario(nombreCompleto, dni, email);
        } else {
            // si el la primera vez que se conecta o es investigador sin usuario
            if ((u != null && u.isPrimeraConexion()) || (u == null && inv != null)) {
                // crea el usuario para el investigador
                if (u == null) {
                    u = new Usuario();
                }
                // rol de usuario si es investigador
                if (inv != null) {
                    if (u.getRoles() == null) {
                        u.setRoles(new HashSet<Usuario.UserRole>());
                    }
                    u.getRoles().add(Usuario.UserRole.ROLE_USER);
                }
                // añado datos oficiales al usuario
                u.setDNI(dni);
                u.setEmail(email);
                u.setNombreCompleto(nombreCompleto);
                u.setPrimeraConexion(false);
                // set foto usuario desconocido
                URI uri;
                try {
                    uri = new URI(credential.getLocalEntityID());
                    u.setFoto("https://" + uri.getHost() + "/img/no_photo.png");
                } catch (URISyntaxException e) {
                    LOG.error(e.getMessage());
                }
            }

            if (inv != null) {
                u.getRoles().add(Usuario.UserRole.ROLE_USER);
            } else {
                u.getRoles().remove(Usuario.UserRole.ROLE_USER);
            }

            // añade los roles que tenga el usuario
            if (u.getRoles() != null) {
                for (Usuario.UserRole rol : u.getRoles()) {
                    authority = new SimpleGrantedAuthority(rol.getValue());
                    authorities.add(authority);
                }
            }

            // set de la fecha actual
            u.setLastConnection(new Date());
            // si falla al guardar
            if (udao.save(u) == null) {
                LOG.error("Error al guardar el usuario: " + u.getEmail());
            }

        }
        // userDetails usado por SSS
        userDetails = new SAMLUserDetailsImpl(email, "password", true, true, true, true, authorities);
        userDetails.setEmail(email);
        userDetails.setNombreCompleto(nombreCompleto);
        userDetails.setPhoto(photo);
        userDetails.setUsuario(u);

        return userDetails;
    }

    private static String lpad(String valueToPad, char filler, int size) {
        char[] array = new char[size];

        int len = size - valueToPad.length();

        for (int i = 0; i < len; i++)
            array[i] = filler;

        valueToPad.getChars(0, valueToPad.length(), array, size - valueToPad.length());

        return String.valueOf(array);
    }

    private static String parseNif(String valor) {

        String nif = valor.substring(valor.lastIndexOf(":") + 1);
        if (!valor.contains(":UNK:")) {
            nif = lpad(nif, '0', 9);
            // Lo paso a mayusculas.
            nif = nif.toUpperCase();

        }
        return nif;
    }

}
