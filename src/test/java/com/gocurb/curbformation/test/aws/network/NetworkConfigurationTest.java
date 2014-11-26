package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.AwsClientModule;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Iterator;

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
        network.getInternetGateways(vpc.getVpcId());
    assertThat(internetGateways).hasSize(1);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The Internet Gateway attached to the VPC should have standard tags.",
      dependsOnMethods = "vpcHasInternetGatewayAttached")
  public void vpcInternetGatewayHasTags(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    final Collection<InternetGateway>
        internetGateways =
        network.getInternetGateways(vpc.getVpcId());
    assertThat(getInternetGateway(internetGateways).getTags())
        .contains(new Tag("Environment", network.getEnvironment()),
                  new Tag("Network", "Public"));
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have two private subnets.",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcHasTwoPrivateSubnets(final String cidrAddress) {
    assertThat(getPrivateSubnets(cidrAddress)).hasSize(2);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The subnets should have different availability zones.",
      dependsOnMethods = "vpcHasTwoPrivateSubnets")
  public void vpcPrivateSubnetsHaveDifferentAZ(final String cidrAddress) {
    subnetsAvailabilityZonesNotEqual(getPrivateSubnets(cidrAddress));
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have two public subnets.",
      dependsOnMethods = "hasOneVpcPerCidrAddress")
  public void vpcHasTwoPublicSubnets(final String cidrAddress) {
    assertThat(getPublicSubnets(cidrAddress)).hasSize(2);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The subnets should have different availability zones.",
      dependsOnMethods = "vpcHasTwoPublicSubnets")
  public void vpcPublicSubnetsHaveDifferentAZ(final String cidrAddress) {
    subnetsAvailabilityZonesNotEqual(getPublicSubnets(cidrAddress));
  }


  private void subnetsAvailabilityZonesNotEqual(Collection<Subnet> subnets) {
    final Iterator<Subnet> iterator = subnets.iterator();
    final Subnet subnetA = iterator.next();
    final Subnet subnetB = iterator.next();
    assertThat(subnetA.getAvailabilityZone())
        .isNotEqualTo(subnetB.getAvailabilityZone());
  }

  private InternetGateway getInternetGateway(Collection<InternetGateway> internetGateways) {
    return internetGateways.iterator().next();
  }

  private Vpc getVpc(String cidrAddress) {
    return network.getVpcs(cidrAddress).iterator().next();
  }

  private Collection<Subnet> getPrivateSubnets(String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    return network.getPrivateSubnets(vpc.getVpcId());
  }

  private Collection<Subnet> getPublicSubnets(String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    return network.getPublicSubnets(vpc.getVpcId());
  }
}
