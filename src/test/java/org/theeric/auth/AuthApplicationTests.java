package org.theeric.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.theeric.auth.test.AbstractIntegrationTest;

@SpringBootTest
@ActiveProfiles("test")
class AuthApplicationTests extends AbstractIntegrationTest {

	@Test
	void migrate() {
	}

}
