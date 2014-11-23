package com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc;


import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

/**
 * Created by sgarlick on 11/22/14.
 */
public class AmazonVpc implements Vpc {

  private final String environment;

  @Override
  public String getEnvironment() {
    return environment;
  }

  private final String cidrAddress;

  @Override
  public String getCidrAddress() {
    return cidrAddress;
  }

  private final VpcService vpcService;

  @Inject
  AmazonVpc(@Assisted("environment") final String environment, @Assisted("cidrAddress") final String cidrAddress,
            final VpcService vpcService) {
    this.environment = environment;
    this.cidrAddress = cidrAddress;
    this.vpcService = vpcService;
  }

  @Override
  public boolean exists() {
    return vpcService.exists(this);
  }
}
