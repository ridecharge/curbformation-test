package com.gocurb.curbformation.test.aws;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Async;
import com.amazonaws.services.ec2.AmazonEC2AsyncClient;
import com.amazonaws.services.ec2.AmazonEC2Client;

/**
 * Created by sgarlick on 11/21/14.
 */
public class AwsClientModule implements Module {
  @Override
  public void configure(Binder binder) {
  }

  @Provides @Singleton
  private AWSCredentialsProvider providesAWSCredentialsProvider() {
    return new PropertiesFileCredentialsProvider("/Users/sgarlick/.aws/config");
  }

  @Provides @Singleton
  AmazonEC2Async providesAmazonEC2Async() {
    return  new AmazonEC2AsyncClient(providesAWSCredentialsProvider());
  }

  @Provides @Singleton
  AmazonEC2 providesAmazonEC2() {
    return new AmazonEC2Client(providesAWSCredentialsProvider());
  }

}
