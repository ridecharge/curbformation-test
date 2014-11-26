package com.gocurb.curbformation.test.aws.network.subnet;

import com.amazonaws.services.ec2.model.Subnet;

import java.util.Collection;

/**
 * Created by sgarlick on 11/26/14.
 */
public interface SubnetService {
  public Collection<Subnet> fetchPublicSubnets(String environment, String vpcId);

  public Collection<Subnet> fetchPrivateSubnets(String environment, String vpcId);
}
