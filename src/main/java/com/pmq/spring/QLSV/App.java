package com.pmq.spring.QLSV;

import com.pmq.spring.QLSV.repository.DiemRepository;
import com.pmq.spring.QLSV.repository.SinhVienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootApplication
@RequiredArgsConstructor
public class App {

	public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(App.class, args);


    }

}
