package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeSubnetsRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.Subnet;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sgarlick on 11/25/14.
 */
@Singleton
class AmazonSubnetService implements SubnetService {

  private final AmazonEC2 amazonEC2;

  @Inject
  AmazonSubnetService(final AmazonEC2 amazonEC2) {
    this.amazonEC2 = amazonEC2;
  }

  @Override
  public Collection<Subnet> fetchPublicSubnets(final String environment, final String vpcId) {
    return fetchSubnets(environment, vpcId, Visibility.PUBLIC);
  }

  @Override
  public Collection<Subnet> fetchPrivateSubnets(final String environment, final String vpcId) {
    return fetchSubnets(environment, vpcId, Visibility.PRIVATE);
  }

  private Collection<Subnet> fetchSubnets(final String environment, final String vpcId, final Visibility visibility) {
    final DescribeSubnetsRequest
        request =
        new DescribeSubnetsRequest()
            .withFilters(new Filter("tag:Environment").withValues(environment),
                         new Filter("tag:network").withValues(visibility.toString().toLowerCase()),
                         new Filter("vpc-id").withValues(vpcId));
    return amazonEC2.describeSubnets(request).getSubnets();
  }

  private enum Visibility {
    PUBLIC,
    PRIVATE;

    @Override
    public String toString() {
      return WordUtils.capitalizeFully(name());
    }
  }
}
