package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;

import java.util.Collection;

/**
 * Created by sgarlick on 11/23/14.
 */
public interface Network {

  public String getEnvironment();

  /**
   * Fetches all VPCs in the given network environment for the supplied cidrAddress
   * @param cidrAddress cidrAddress of the vpcs to find
   * @return collection of vpcs that match the networks environment and cidrAddress
   */
  public Collection<Vpc> fetchVpcs(final String cidrAddress);

  /**
   * Fetches all the internet gateways for a given vpcId and network environment.
   * @param vpcId vpcId of the vpc to find internet gateways for.
   * @return collection of internet gateways
   */
  public Collection<InternetGateway> fetchInternetGateways(final String vpcId);
}