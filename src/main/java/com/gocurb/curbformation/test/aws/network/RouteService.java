package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.RouteTable;

import java.util.Collection;

/**
 * Created by sgarlick on 3/10/15.
 */
interface RouteService {

  public Collection<RouteTable> fetchPrivateRouteTables(final String environment,
                                                        final String vpcId);

  public Collection<RouteTable> fetchPublicRouteTables(final String environment,
                                                       final String vpcId);

  public Collection<RouteTable> fetchRouteTables(final String environment,
                                                 final String vpcId,
                                                 final Collection<Filter> additionalFilters);
}
