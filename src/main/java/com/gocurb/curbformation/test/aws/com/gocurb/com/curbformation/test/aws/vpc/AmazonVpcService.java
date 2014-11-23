package com.gocurb.curbformation.test.aws.com.gocurb.com.curbformation.test.aws.vpc;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Filter;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sgarlick on 11/23/14.
 */
@Singleton
public class AmazonVpcService implements VpcService {

  private final AmazonEC2 amazonEC2;

  @Inject
  AmazonVpcService(final AmazonEC2 amazonEC2) {
    this.amazonEC2 = amazonEC2;
  }

  public boolean exists(final AmazonVpc vpc) {
    final DescribeVpcsRequest
        request =
        new DescribeVpcsRequest()
            .withFilters(new Filter().withName("cidr").withValues(vpc.getCidrAddress()),
                         new Filter().withName("state").withValues("available"),
                         new Filter().withName("tag:Environment").withValues(vpc.getEnvironment()));
    final DescribeVpcsResult result = amazonEC2.describeVpcs(request);
    if(result.getVpcs().size() > 1) {
      throw new IllegalStateException("There can only be 1 vpc with a given Environment and Cidr Address");
    }
    return result.getVpcs().size() == 1;
  }
}
