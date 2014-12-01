package com.gocurb.curbformation.test.aws.network.config;

import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.AwsClientModule;
import com.gocurb.curbformation.test.aws.network.AmazonNetworkModule;
import com.gocurb.curbformation.test.aws.network.NetworkFactory;

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
@Test(groups = {"config-tests", "subnet-config-tests"}, dependsOnGroups = "vpc-config-tests")
public final class SubnetConfigurationTest extends AbstractNetworkConfigurationTest {

  @Inject
  SubnetConfigurationTest(final NetworkFactory networkFactory) {
    super(networkFactory);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The VPC should have two private subnets.")
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
      description = "The VPC should have two public subnets.")
  public void vpcHasTwoPublicSubnets(final String cidrAddress) {
    assertThat(getPublicSubnets(cidrAddress)).hasSize(2);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The subnets should have different availability zones.",
      dependsOnMethods = "vpcHasTwoPublicSubnets")
  public void vpcPublicSubnetsHaveDifferentAZ(final String cidrAddress) {
    subnetsAvailabilityZonesNotEqual(getPublicSubnets(cidrAddress));
  }


  private void subnetsAvailabilityZonesNotEqual(final Collection<Subnet> subnets) {
    final Iterator<Subnet> iterator = subnets.iterator();
    final Subnet subnetA = iterator.next();
    final Subnet subnetB = iterator.next();
    assertThat(subnetA.getAvailabilityZone())
        .isNotEqualTo(subnetB.getAvailabilityZone());
  }

  private Collection<Subnet> getPrivateSubnets(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    return network.getPrivateSubnets(vpc.getVpcId());
  }

  private Collection<Subnet> getPublicSubnets(final String cidrAddress) {
    final Vpc vpc = getVpc(cidrAddress);
    return network.getPublicSubnets(vpc.getVpcId());
  }
}
