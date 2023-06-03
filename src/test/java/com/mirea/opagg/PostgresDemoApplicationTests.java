package com.mirea.opagg;

import com.mirea.opagg.controller.PlaceController;
import com.mirea.opagg.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PostgresDemoApplicationTests {
	@Autowired
	private PlaceController plCon;

	@Autowired
	private PlaceRepository plRep;

	@Test
	public void contextLoads() {
		assertThat(plCon).isNotNull();
		assertThat(plRep).isNotNull();
	}
}
