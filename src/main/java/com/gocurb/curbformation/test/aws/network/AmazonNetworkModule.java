package com.gocurb.curbformation.test.aws.network;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Created by sgarlick on 11/25/14.
 */
public class AmazonNetworkModule extends AbstractModule {
  @Override
  public void configure() {
    install(new FactoryModuleBuilder().implement(Network.class, AmazonNetwork.class)
                .build(NetworkFactory.class));
    bind(VpcService.class).to(AmazonVpcService.class);
    bind(SubnetService.class).to(AmazonSubnetService.class);
  }
}
