package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.RouteTable;
import com.amazonaws.services.ec2.model.Subnet;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.ec2.model.VpcPeeringConnection;

import java.util.Collection;

/**
 * Interface to access the state of our network. Created by sgarlick on 11/23/14.
 */
public interface Network {

  public String getEnvironment();

  /**
   * Fetches all VPCs in the given network environment for the supplied cidrAddress
   *
   * @param cidrAddress cidrAddress of the vpcs to find
   * @return collection of vpcs that match the networks environment and cidrAddress
   */
  public Collection<Vpc> getVpcs(final String cidrAddress);

  public Collection<VpcPeeringConnection>
  getVpcPeeringConnections(final String accepterCidrBlock,
                           final String requesterCidrBlock);

  /**
   * Fetches all the internet gateways for a given vpcId and network environment.
   *
   * @param vpcId vpcId of the vpc to find internet gateways for.
   * @return collection of internet gateways
   */
  public Collection<InternetGateway> getInternetGateways(final String vpcId);

  public Collection<RouteTable> getPublicRouteTables(final String vpcId);

  public Collection<RouteTable> getPrivateRouteTables(final String vpcId);

  public Collection<InternetGateway> getInternetGateways(final Vpc vpc);

  public Collection<Subnet> getPublicSubnets(final String vpcId);

  public Collection<Subnet> getPublicSubnets(final Vpc vpc);

  public Collection<Subnet> getPrivateSubnets(final String vpcId);

  public Collection<Subnet> getPrivateSubnets(final Vpc vpc);
}
