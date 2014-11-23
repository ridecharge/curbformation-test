package com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Created by sgarlick on 11/23/14.
 */
public class AmazonVpcModule implements Module {

  @Override
  public void configure(Binder binder) {
    binder.bind(VpcService.class).to(AmazonVpcService.class);
    binder.install(new FactoryModuleBuilder().implement(Vpc.class, AmazonVpc.class).build(VpcFactory.class));
  }
}
