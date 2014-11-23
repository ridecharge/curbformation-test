package com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc;

/**
 * Created by sgarlick on 11/23/14.
 */
public interface Vpc {

  public String getEnvironment();

  public String getCidrAddress();

  boolean exists();
}
