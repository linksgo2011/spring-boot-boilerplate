package springbootboilerplate.application.common;


import cn.printf.springbootboilerplate.common.QueryProcessor;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserCriteria;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserRepository;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import springbootboilerplate.application.APIBaseTest;
import springbootboilerplate.application.fixture.UserFixture;

import javax.persistence.criteria.Predicate;

import static org.junit.Assert.assertEquals;

public class QueryProcessorTest extends APIBaseTest {

    @Autowired
    UserFixture userFixture;

    @Autowired
    UserRepository userRepository;

    @Test
    public void should_build_criteria_by_query() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            // 输入 root,输出 predicate
            UserCriteria userCriteria = new UserCriteria();
            userCriteria.setUsername("admin");
            Predicate predicate = QueryProcessor.getPredicate(root, userCriteria, criteriaBuilder);

            assertEquals(predicate.getOperator().toString(), "AND");
            assertEquals(predicate.getExpressions().size(), 1);

            LikePredicate likePredicate = (LikePredicate) predicate.getExpressions().get(0);
            LiteralExpression pattern = (LiteralExpression) likePredicate.getPattern();
            assertEquals(pattern.getLiteral(), "%admin%");

            return predicate;
        }, pageRequest);
    }
}
