package com.gocurb.curbformation.test.network;

import com.gocurb.curbformation.test.aws.AwsClientModule;
import com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc.AmazonVpcModule;
import com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc.VpcFactory;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the Network configuration for a AWS Environment.
 * Created by sgarlick on 11/19/14.
 */
@Guice(modules = { AwsClientModule.class, AmazonVpcModule.class })
public class VpcTest {

  @DataProvider(name="environments", parallel = true)
  public Object[][] environments() {
    return new Object[][] { new Object[] { "test", "10.0.0.0/16" } };
  }

  private final VpcFactory vpcFactory;

  @Inject
  VpcTest(final VpcFactory vpcFactory) {
    this.vpcFactory = vpcFactory;
  }

  @Test(dataProvider = "environments")
  public void singleVpcExistsForEnvironment(final String environment, final String cidr) {
    assertThat(vpcFactory.create(environment, cidr).exists()).isTrue();
  }
}
