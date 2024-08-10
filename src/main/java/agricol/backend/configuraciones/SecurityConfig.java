package agricol.backend.configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import agricol.backend.utiles.UserRoles;
import lombok.AllArgsConstructor;


@Configuration
@EnableWebSecurity //1. Anotación configurar Spring Security
@AllArgsConstructor

public class SecurityConfig {

    // 2. Declarar rutas publicas
    private final String[] PUBLIC_RESOURCES = {
        "/auth/**"

    };
    // Declarar rutas de administrador
    // private final String[] ADMIN_RESOURCES = {};

    private final String[] VENDEDORES_RESOURCES = {};
    // private final String[] AllLogedIn_RESOURCES = {};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF por simplicidad
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/v2/api-docs",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui.html",
                                "/webjars/**",
                                // -- Swagger UI v3 (OpenAPI)
                                "/v3/api-docs/**",
                                "/swagger-ui/**")
                        .permitAll()
                        .requestMatchers(VENDEDORES_RESOURCES).hasAnyAuthority(UserRoles.VENDEDOR.name(), UserRoles.COMPRADORVENDEDOR.name())
                        .requestMatchers(PUBLIC_RESOURCES).permitAll()
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .cors(Customizer.withDefaults()); // Permitir todos los request sin autorización
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

   

}
