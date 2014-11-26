package com.gocurb.curbformation.test.aws.network.vpc;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;

import java.util.Collection;

/**
 * Created by sgarlick on 11/23/14.
 */
public interface VpcService {
  public Collection<Vpc> fetchVpcs(final String environment, final String cidrAddress);
  public Collection<InternetGateway> fetchInternetGateways(final String environment, final String vpcId);
}
