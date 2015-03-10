package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.Subnet;

import java.util.Collection;

/**
 * Created by sgarlick on 11/26/14.
 */
interface SubnetService {
  public Collection<Subnet> fetchPublicSubnets(final String environment, final String vpcId);

  public Collection<Subnet> fetchPrivateSubnets(final String environment, final String vpcId);
}
