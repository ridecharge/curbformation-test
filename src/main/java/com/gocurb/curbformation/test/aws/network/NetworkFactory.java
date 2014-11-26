package com.gocurb.curbformation.test.aws.network;

import com.google.inject.assistedinject.Assisted;

/**
 * Created by sgarlick on 11/23/14.
 */
public interface NetworkFactory {
  public Network create(@Assisted("environment") final String environment);
}
