package com.gocurb.curbformation.test.config;

import com.amazonaws.services.ec2.model.RouteTable;
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
 * Created by sgarlick on 3/10/15. Defines the configuration for our VPC Routes
 */
@Guice(modules = {AwsClientModule.class, AmazonNetworkModule.class})
@Test(groups = {"config-tests", "route-config-tests"}, dependsOnGroups = "vpc-config-tests")
public class RouteConfigurationTest extends AbstractNetworkConfigurationTest {

  @Inject
  RouteConfigurationTest(final NetworkFactory networkFactory) {
    super(networkFactory);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "Each VPC should have two private route tables")
  public void vpcHasTwoPrivateRouteTables(final String cidrAddress) {
    final Vpc vpc = network.getVpcs(cidrAddress).iterator().next();
    final Collection<RouteTable> routeTables = network.getPrivateRouteTables(vpc.getVpcId());
    assertThat(routeTables).hasSize(2);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "Each VPC should have single public route table")
  public void vpcHasOnePublicRouteTable(final String cidrAddress) {
    final Vpc vpc = network.getVpcs(cidrAddress).iterator().next();
    final Collection<RouteTable> routeTables = network.getPublicRouteTables(vpc.getVpcId());
    assertThat(routeTables).hasSize(1);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The public route table should have a local, nat, and vpc peering route",
      dependsOnMethods = "vpcHasOnePublicRouteTable")
  public void publicRouteTableShouldHaveRoutes(final String cidrAddress) {
    final Vpc vpc = network.getVpcs(cidrAddress).iterator().next();
    final RouteTable routeTable = network.getPrivateRouteTables(vpc.getVpcId()).iterator().next();
    assertThat(routeTable.getRoutes()).hasSize(3);
  }

  @Test(dataProvider = "vpcCidrAddresses",
      description = "The private route tables should have a local, igw, and vpc peering route",
      dependsOnMethods = "vpcHasTwoPrivateRouteTables")
  public void privateRouteTableShouldHaveRoutes(final String cidrAddress) {
    final Vpc vpc = network.getVpcs(cidrAddress).iterator().next();
    final RouteTable routeTable = network.getPrivateRouteTables(vpc.getVpcId()).iterator().next();
    assertThat(routeTable.getRoutes()).hasSize(3);
  }
}
