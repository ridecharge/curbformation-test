package com.gocurb.curbformation.test.config;

import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.network.Network;
import com.gocurb.curbformation.test.aws.network.NetworkFactory;

import org.testng.annotations.DataProvider;

/**
 * Created by sgarlick on 11/26/14.
 */
public abstract class AbstractNetworkConfigurationTest {

  protected final Network network;

  public AbstractNetworkConfigurationTest(final NetworkFactory networkFactory) {
    network = networkFactory.create(System.getProperty("environment", "test"));
  }

  /**
   * List of cidrAddresses that should be associated with a single vpc within our network
   * environment
   */
  @DataProvider(name = "vpcCidrAddresses", parallel = true)
  public Object[][] vpcCidrAddresses() {
    return new Object[][]{{"10.250.0.0/16"}, {"10.251.0.0/16"}};
  }

  @DataProvider(name = "peeredVpcs", parallel = true)
  public Object[][] peeredVpcCidrAddresses() {
    return new Object[][]{{"10.251.0.0/16", "10.250.0.0/16"}};
  }

  protected Vpc getVpc(final String cidrAddress) {
    return network.getVpcs(cidrAddress).iterator().next();
  }
}
