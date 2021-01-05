package com.minsoo.co.tireerpserver;

import com.minsoo.co.tireerpserver.model.entity.Admin;
import com.minsoo.co.tireerpserver.model.entity.QAdmin;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@SpringBootTest
class TireErpServerApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		List<Admin> admins = queryFactory.selectFrom(QAdmin.admin)
				.fetch();

		admins.forEach(admin -> System.out.println(admin.getName()));
	}

}
