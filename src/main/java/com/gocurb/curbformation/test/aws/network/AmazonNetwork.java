package com.gocurb.curbformation.test.aws.network;


import com.google.inject.assistedinject.Assisted;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Vpc;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Immutable object that identifies a network by its Environment.
 * Created by sgarlick on 11/22/14. Describes the state of a Network
 */
public class AmazonNetwork implements Network {

  private final VpcService vpcService;
  private final SubnetService subnetService;
  private final String environment;

  @Override
  public String getEnvironment() {
    return environment;
  }

  @Inject
  AmazonNetwork(@Assisted("environment") final String environment,
                final VpcService vpcService,
                final SubnetService subnetService) {
    this.environment = environment;
    this.vpcService = vpcService;
    this.subnetService = subnetService;
  }

  @Override
  public Collection<Vpc> getVpcs(final String cidrAddress) {
    return vpcService.fetchVpcs(environment, cidrAddress);
  }

  @Override
  public Collection<InternetGateway> getInternetGateways(final String vpcId) {
    return vpcService.fetchInternetGateways(environment, vpcId);
  }

  @Override
  public Collection<Subnet> getPublicSubnets(final String vpcId) {
    return subnetService.fetchPublicSubnets(environment, vpcId);
  }

  @Override
  public Collection<Subnet> getPrivateSubnets(final String vpcId) {
    return subnetService.fetchPrivateSubnets(environment, vpcId);
  }
}
