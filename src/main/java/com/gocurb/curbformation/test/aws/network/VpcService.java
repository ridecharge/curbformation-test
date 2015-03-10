package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.ec2.model.VpcPeeringConnection;

import java.util.Collection;

/**
 * Created by sgarlick on 11/23/14.
 */
interface VpcService {

  public Collection<Vpc> fetchVpcs(final String environment, final String cidrAddress);

  public Collection<VpcPeeringConnection>
  fetchVpcPeeringConnections(String environment,
                             String accepterCidrBlock,
                             String requesterCidrBlock);

  public Collection<InternetGateway> fetchInternetGateways(final String environment,
                                                           final String vpcId);
}
