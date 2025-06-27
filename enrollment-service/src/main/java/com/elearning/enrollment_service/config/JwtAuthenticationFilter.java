package com.elearning.enrollment_service.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            
            System.out.println(jwt);
            final String userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // لا نتصل بقاعدة البيانات، بل نتحقق من صحة التوقيع وتاريخ الانتهاء فقط
                if (jwtService.isTokenSignatureValid(jwt)) { 
                    
                    // استخراج كل الـ claims من التوكن
                    Claims claims = jwtService.extractAllClaims(jwt);
            System.out.println(claims);
                    
                    // استخراج الصلاحيات (roles) من الـ claims
                    @SuppressWarnings("unchecked")
                    List<String> roles = claims.get("roles", List.class);
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    // استخراج ID المستخدم
                    Long userId = claims.get("userId", Long.class);

                    System.out.println(userId + " User Id from Claims");
                    // إنشاء كائن المصادقة
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userId.toString(), 
                            null,
                            authorities // تمرير الصلاحيات المستخرجة من التوكن
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // تحديث سياق الأمان
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            System.out.println("Errror JWT Auth Filter");
            // في حالة وجود أي خطأ في التوكن (منتهي الصلاحية، توقيع خاطئ)، يتم مسح سياق الأمان
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}