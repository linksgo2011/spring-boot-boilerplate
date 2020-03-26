package springbootboilerplate.application.auth;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * 用于不涉及 API 的测试
 */
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@DataJpaTest
@Transactional
public abstract class BusinessBaseTest {

}
