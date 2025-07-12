// Paquete donde se ubicarán tus configuraciones
package com.example.demo.configuracion;

import com.example.demo.modelo.Usuario;
import com.example.demo.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collections;
import java.util.Optional;

@Configuration
@EnableWebSecurity 
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService; 

    /**
     * Define un Bean para BCryptPasswordEncoder.
     * Este codificador se utilizará para hashear y verificar contraseñas.
     * @return Una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param http El objeto HttpSecurity para configurar la seguridad.
     * @return La cadena de filtros de seguridad configurada.
     * @throws Exception Si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize

                .requestMatchers("/", "/login", "/registro", "/productos-publicos", "/images/**", "/css/**", "/js/**").permitAll()
                
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") 
                        .successHandler(authenticationSuccessHandler()) 
                        .failureUrl("/login?error=true") 
                        .permitAll() 
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para cerrar sesión
                        .logoutSuccessUrl("/") // URL a la que redirigir después de cerrar sesión
                        .invalidateHttpSession(true) // Invalidar la sesión HTTP
                        .deleteCookies("JSESSIONID") 
                        .permitAll() // Permitir acceso a la URL de logout para todos
                )
                .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    /**
     * Define un UserDetailsService personalizado para cargar los detalles del usuario
     * desde nuestra base de datos.
     * @return Una instancia de UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return correo -> {
            Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(correo); // Asume que tienes un findByCorreo en UsuarioService
            if (usuarioOptional.isEmpty()) {
                throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
            }
            Usuario usuario = usuarioOptional.get();
            // Mapear el tipo de usuario a un rol de Spring Security
            String role = "ROLE_" + usuario.getTipo().name().toUpperCase(); // Ej: ROLE_USUARIO, ROLE_ADMIN
            return new User(usuario.getCorreo(), usuario.getContrasena(), Collections.singletonList(new SimpleGrantedAuthority(role)));
        };
    }

    /**
     * Manejador de éxito de autenticación para redirigir según el rol del usuario.
     * @return Una instancia de AuthenticationSuccessHandler.
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            // Obtener el usuario autenticado de Spring Security
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String correo = userDetails.getUsername();

            Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(correo);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                request.getSession().setAttribute("usuarioAutenticado", usuario);

                if (usuario.getTipo() == Usuario.TipoUsuario.admin) {
                    response.sendRedirect("/admin/bienvenida");
                } else {
                    response.sendRedirect("/bienvenida");
                }
            } else {
                response.sendRedirect("/login?error=true");
            }
        };
    }
}