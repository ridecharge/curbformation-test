package com.gocurb.curbformation.test.config;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.AwsClientModule;
import com.gocurb.curbformation.test.aws.network.AmazonNetworkModule;
import com.gocurb.curbformation.test.aws.network.NetworkFactory;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Collection;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sgarlick on 11/26/14.
 * Asserts the configuration of our VPCs
 */
@Guice(modules = {AwsClientModule.class, AmazonNetworkModule.class})
@Test(groups = {"config-tests", "vpc-config-tests"})
public class VpcConfigurationTest extends AbstractNetworkConfigurationTest {

  @Inject
  VpcConfigurationTest(final NetworkFactory networkFactory) {
    super(networkFactory);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "There should only be one VPC with a given cidr inside a network environment.")
  public void hasOneVpcPerCidrAddress(final String cidrAddress) {
    assertThat(network.getVpcs(cidrAddress)).hasSize(1);
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
    assertThat(tags).contains(new Tag("Environment", network.getEnvironment()));
  }


  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have a single Internet Gateway attached.",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcHasInternetGatewayAttached(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    final Collection<InternetGateway>
        internetGateways =
        network.getInternetGateways(vpc);
    assertThat(internetGateways).hasSize(1);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The Internet Gateway attached to the VPC should have standard tags.",
      dependsOnMethods = "vpcHasInternetGatewayAttached")
  public void vpcInternetGatewayHasTags(final String cidrAddress) {
    assertThat(getInternetGateway(cidrAddress).getTags())
        .contains(new Tag("Environment", network.getEnvironment()),
                  new Tag("Network", "Public"));
  }


  private InternetGateway getInternetGateway(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    final Collection<InternetGateway>
        internetGateways =
        network.getInternetGateways(vpc);
    return internetGateways.iterator().next();
  }

}
