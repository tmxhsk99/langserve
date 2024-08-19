package com.kjh.ollama.langserve.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
public class QueryDslConfig {
    @PersistenceContext
    public EntityManager em;

    @Bean
    public JPAQueryFactory JpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
