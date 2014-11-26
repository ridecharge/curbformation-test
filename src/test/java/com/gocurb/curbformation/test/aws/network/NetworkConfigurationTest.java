package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.AwsClientModule;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Collection;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Network configuration for a AWS Environment. Created by sgarlick on 11/19/14.
 */
@Guice(modules = {AwsClientModule.class, AmazonNetworkModule.class})
public class NetworkConfigurationTest {

  private final Network network;

  /**
   * List of cidrAddresses that should be associated with a single vpc within our network
   * environment
   */
  @DataProvider(name = "vpcCidrAddresses")
  public Object[][] vpcCidrAddresses() {
    return new Object[][]{{"10.0.0.0/16"}};
  }

  @Inject
  NetworkConfigurationTest(final NetworkFactory networkFactory) {
    network = networkFactory.create(System.getProperty("environment", "test"));
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "There should only be one VPC with a given cidr inside a network environment.")
  public void hasOneVpcPerCidrAddress(final String cidrAddress) {
    assertThat(network.fetchVpcs(cidrAddress)).hasSize(1);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should be available.",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcIsAvailable(final String cidrAddress) {
    assertThat(getVpc(cidrAddress).getState()).isEqualTo("available");
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have default instance tenancy",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcIsDefaultTenancy(final String cidrAddress) {
    assertThat(getVpc(cidrAddress).getInstanceTenancy()).isEqualTo(
        "default");
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have standard tags",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcHasTags(final String cidrAddress) {
    final Collection<Tag> tags = getVpc(cidrAddress).getTags();
    assertThat(tags).contains(new Tag("Environment", network.getEnvironment()),
                              new Tag("Name", network.getEnvironment() + "-VPC"));
  }


  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have a single Internet Gateway attached.",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcHasInternetGatewayAttached(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    final Collection<InternetGateway>
        internetGateways =
        network.fetchInternetGateways(vpc.getVpcId());
    assertThat(internetGateways).hasSize(1);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The Internet Gateway attached to the VPC should have standard tags.",
      dependsOnMethods = "vpcHasInternetGatewayAttached")
  public void vpcInternetGatewayHasTags(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    final Collection<InternetGateway>
        internetGateways =
        network.fetchInternetGateways(vpc.getVpcId());
    assertThat(getInternetGateway(internetGateways).getTags())
        .contains(new Tag("Environment", network.getEnvironment()),
                  new Tag("Network", "Public"));
  }

  private InternetGateway getInternetGateway(Collection<InternetGateway> internetGateways) {
    return internetGateways.iterator().next();
  }


  private Vpc getVpc(String cidrAddress) {
    return network.fetchVpcs(cidrAddress).iterator().next();
  }
}
