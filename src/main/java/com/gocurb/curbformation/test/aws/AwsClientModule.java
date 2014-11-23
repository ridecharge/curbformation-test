package com.gocurb.curbformation.test.aws;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Async;
import com.amazonaws.services.ec2.AmazonEC2AsyncClient;
import com.amazonaws.services.ec2.AmazonEC2Client;

import javax.inject.Singleton;

/**
 * Guice Providers for AWS Clients
 * Created by sgarlick on 11/21/14.
 */
public class AwsClientModule implements Module {
  @Override
  public void configure(Binder binder) {
  }

  @Provides @Singleton
  AWSCredentialsProvider providesAWSCredentialsProvider() {
    return new PropertiesFileCredentialsProvider("/Users/sgarlick/.aws/config");
  }

  @Provides @Singleton
  AmazonEC2Async providesAmazonEC2Async(final AWSCredentialsProvider awsCredentialsProvider) {
    return  new AmazonEC2AsyncClient(awsCredentialsProvider);
  }

  @Provides @Singleton
  AmazonEC2 providesAmazonEC2(final AWSCredentialsProvider awsCredentialsProvider) {
    return new AmazonEC2Client(awsCredentialsProvider);
  }

}
