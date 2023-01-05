package com.peshkov.lab4.config;

import com.peshkov.lab4.dao.FileProvider;
import com.peshkov.lab4.dao.FileProviderImpl;
import com.peshkov.lab4.dao.Repository;
import com.peshkov.lab4.dao.RepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class MyConfiguration {

    @Bean
    @Scope("singleton")
    public Repository repository(FileProvider provider) {
        return new RepositoryImpl(provider);
    }

    @Bean
    @Scope("singleton")
    public MathFunction targetFunction() {
        return new MathFunction() {
            @Override
            public Double apply(Double argument) {
                return argument * argument;
            }

            @Override
            public String representation() {
                return "x*x";
            }
        };
    }

    @Bean
    @Scope("singleton")
    public FileProvider fileProvider() {
        return new FileProviderImpl();
    }
}



