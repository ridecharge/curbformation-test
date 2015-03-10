package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.AmazonEC2;

import javax.inject.Inject;

/**
 * Created by sgarlick on 3/10/15.
 */
public class AmazonRouteService {

  private final AmazonEC2 amazonEC2;

  @Inject
  AmazonRouteService(final AmazonEC2 amazonEC2) {
    this.amazonEC2 = amazonEC2;
  }
}
