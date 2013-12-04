package info.alenkov.tutorial.spring_hibernate_events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

import javax.sql.DataSource;

@ContextConfiguration(locations = {"classpath*:spring/*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
abstract public class AbstractTest extends AbstractTransactionalTestNGSpringContextTests {
	@Autowired(required = true)
	@Qualifier("dataSource")
	private DataSource ds;

	@BeforeMethod
	public void beforeMethod() throws Exception {
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		rdp.addScript(new ClassPathResource("import.sql"));
		rdp.populate(ds.getConnection());
	}
}
