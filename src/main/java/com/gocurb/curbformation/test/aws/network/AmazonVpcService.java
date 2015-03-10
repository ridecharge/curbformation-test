package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeVpcPeeringConnectionsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;
import com.amazonaws.services.ec2.model.VpcPeeringConnection;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by sgarlick on 11/23/14. Handles operations related to VPCs
 */
@Singleton
class AmazonVpcService implements VpcService {

  private final AmazonEC2 amazonEC2;

  @Inject
  AmazonVpcService(final AmazonEC2 amazonEC2) {
    this.amazonEC2 = amazonEC2;
  }

  public Collection<Vpc> fetchVpcs(final String environment, final String cidrAddress) {
    final DescribeVpcsRequest
        request =
        new DescribeVpcsRequest()
            .withFilters(new Filter("tag:Environment").withValues(environment),
                         new Filter("cidr").withValues(cidrAddress));
    return amazonEC2.describeVpcs(request).getVpcs();
  }


  @Override
  public Collection<VpcPeeringConnection>
  fetchVpcPeeringConnections(final String environment,
                            final String accepterCidrBlock,
                            final String requesterCidrBlock) {
    final DescribeVpcPeeringConnectionsRequest request =
        new DescribeVpcPeeringConnectionsRequest()
            .withFilters(new Filter("tag:Environment").withValues(environment),
                         new Filter("accepter-vpc-info.cidr-block").withValues(accepterCidrBlock),
                         new Filter("requester-vpc-info.cidr-block")
                             .withValues(requesterCidrBlock));
    return amazonEC2.describeVpcPeeringConnections(request).getVpcPeeringConnections();
  }

  @Override
  public Collection<InternetGateway> fetchInternetGateways(final String environment,
                                                           final String vpcId) {
    final DescribeInternetGatewaysRequest
        request =
        new DescribeInternetGatewaysRequest()
            .withFilters(new Filter("attachment.vpc-id").withValues(vpcId),
                         new Filter("tag:Environment").withValues(environment));
    return amazonEC2.describeInternetGateways(request).getInternetGateways();
  }
}
