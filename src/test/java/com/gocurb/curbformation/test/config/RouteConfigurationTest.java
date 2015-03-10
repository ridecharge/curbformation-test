package com.gocurb.curbformation.test.config;

import com.gocurb.curbformation.test.aws.AwsClientModule;
import com.gocurb.curbformation.test.aws.network.AmazonNetworkModule;
import com.gocurb.curbformation.test.aws.network.NetworkFactory;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * Created by sgarlick on 3/10/15. Defines the configuration for our VPC Routes
 */
@Guice(modules = {AwsClientModule.class, AmazonNetworkModule.class})
@Test(groups = {"config-tests", "route-config-tests"}, dependsOnGroups = "vpc-config-tests")
public class RouteConfigurationTest extends AbstractNetworkConfigurationTest {

  @Inject
  RouteConfigurationTest(final NetworkFactory networkFactory) {
    super(networkFactory);
  }


}
