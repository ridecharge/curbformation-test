package com.gocurb.curbformation.test.aws.network;


import com.google.inject.assistedinject.Assisted;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;
import com.gocurb.curbformation.test.aws.network.vpc.VpcService;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by sgarlick on 11/22/14. Describes the state of a Network
 */
public class AmazonNetwork implements Network {

  private final VpcService vpcService;
  private final String environment;

  @Override
  public String getEnvironment() {
    return environment;
  }

  @Inject
  public AmazonNetwork(@Assisted("environment") final String environment,
                final VpcService vpcService) {
    this.environment = environment;
    this.vpcService = vpcService;
  }

  @Override
  public Collection<Vpc> fetchVpcs(final String cidrAddress) {
    return vpcService.fetchVpcs(environment, cidrAddress);
  }

  @Override
  public Collection<InternetGateway> fetchInternetGateways(final String vpcId) {
    return vpcService.fetchInternetGateways(environment, vpcId);
  }
}
