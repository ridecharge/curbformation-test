package com.gocurb.curbformation.test.aws.network;

import com.google.common.collect.Sets;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeRouteTablesRequest;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.RouteTable;

import java.util.Collection;

import javax.inject.Inject;

/**
 * Created by sgarlick on 3/10/15. Retrieves information about AWS VPC Routes
 */
class AmazonRouteService implements RouteService {

  private final AmazonEC2 amazonEC2;

  @Inject
  AmazonRouteService(final AmazonEC2 amazonEC2) {
    this.amazonEC2 = amazonEC2;
  }


  public Collection<RouteTable> fetchPublicRouteTables(final String environment,
                                                       final String vpcId) {
    final Collection<Filter>
        filters =
        Sets.newHashSet(new Filter("tag:Network").withValues("public"));
    return fetchRouteTables(environment, vpcId, filters);
  }

  public Collection<RouteTable> fetchPrivateRouteTables(final String environment,
                                                        final String vpcId) {
    final Collection<Filter>
        filters =
        Sets.newHashSet(new Filter("tag:Network").withValues("private"));
    return fetchRouteTables(environment, vpcId, filters);
  }


  @Override
  public Collection<RouteTable> fetchRouteTables(final String environment, final String vpcId,
                                                 final Collection<Filter> additionalFilters) {
    final Collection<Filter>
        filters =
        Sets.newHashSet(new Filter("tag:Environment").withValues(environment),
                        new Filter("vpc-id").withValues(vpcId),
                        new Filter("route.state").withValues("active"));
    filters.addAll(additionalFilters);

    final DescribeRouteTablesRequest request =
        new DescribeRouteTablesRequest().withFilters(filters);
    return amazonEC2.describeRouteTables(request).getRouteTables();
  }
}
