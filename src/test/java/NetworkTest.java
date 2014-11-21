import com.google.inject.Inject;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.gocurb.curbformation.test.aws.AwsClientModule;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by sgarlick on 11/19/14.
 */
@Guice(modules = AwsClientModule.class)
public class NetworkTest {

  @DataProvider(name="envs", parallel = true)
  public Object[][] envs() {
    return new Object[][] { new Object[] { "test" } };
  }

  @Inject
  private AmazonEC2 amazonEC2;

  @Test(dataProvider = "envs")
  public void vpcExists(final String env) {
    final DescribeVpcsRequest
        request =
        new DescribeVpcsRequest()
            .withFilters(new Filter().withName("cidr").withValues("10.0.0.0/16"),
                         new Filter().withName("state").withValues("available"),
                         new Filter().withName("tag:Environment").withValues(env));
    final DescribeVpcsResult result = amazonEC2.describeVpcs(request);
    assertThat(result.getVpcs().size()).isEqualTo(1);
  }
}
