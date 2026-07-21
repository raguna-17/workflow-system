package workflow.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;



@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {



    private final JwtProvider jwtProvider;

    private final CustomUserDetailsService userDetailsService;




    public JwtAuthenticationFilter(

            JwtProvider jwtProvider,

            CustomUserDetailsService userDetailsService

    ) {

        this.jwtProvider = jwtProvider;

        this.userDetailsService = userDetailsService;

    }





    /**
     * JWTチェック不要なURL
     */
    @Override
    protected boolean shouldNotFilter(

            HttpServletRequest request

    ) {


        String method =
                request.getMethod();



        String path =
                request.getRequestURI();




        // CORS preflight

        if ("OPTIONS".equalsIgnoreCase(method)) {

            return true;

        }




        // 公開API

        return path.equals("/users")

                || path.equals("/auth/login")

                || path.startsWith("/swagger-ui")

                || path.startsWith("/v3/api-docs");

    }





    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {



        String authorization =
                request.getHeader("Authorization");




        // JWTなし

        if (authorization == null

                || !authorization.startsWith("Bearer ")) {


            filterChain.doFilter(
                    request,
                    response
            );

            return;

        }




        String token =
                authorization.substring(7);




        // JWT不正

        if (!jwtProvider.validateToken(token)) {


            filterChain.doFilter(
                    request,
                    response
            );

            return;

        }





        String email =
                jwtProvider.getEmail(token);





        UserDetails userDetails =

                userDetailsService
                        .loadUserByUsername(email);






        UsernamePasswordAuthenticationToken authentication =

                new UsernamePasswordAuthenticationToken(

                        userDetails,

                        null,

                        userDetails.getAuthorities()

                );






        authentication.setDetails(

                new WebAuthenticationDetailsSource()

                        .buildDetails(request)

        );





        SecurityContextHolder

                .getContext()

                .setAuthentication(authentication);





        filterChain.doFilter(
                request,
                response
        );

    }

}