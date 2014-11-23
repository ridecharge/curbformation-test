package com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc;

import com.google.inject.assistedinject.Assisted;

/**
 * Created by sgarlick on 11/23/14.
 */
public interface VpcFactory {
  public AmazonVpc create(@Assisted("environment") final String environment, @Assisted("cidrAddress") final String cidrAddress);
}
