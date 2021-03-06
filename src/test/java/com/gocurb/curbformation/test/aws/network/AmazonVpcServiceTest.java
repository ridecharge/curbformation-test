package com.gocurb.curbformation.test.aws.network;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysRequest;
import com.amazonaws.services.ec2.model.DescribeInternetGatewaysResult;
import com.amazonaws.services.ec2.model.DescribeVpcsRequest;
import com.amazonaws.services.ec2.model.DescribeVpcsResult;
import com.amazonaws.services.ec2.model.Filter;
import com.amazonaws.services.ec2.model.InternetGateway;
import com.amazonaws.services.ec2.model.Vpc;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for the AmazonVpcService Created by sgarlick on 11/25/14.
 */
public final class AmazonVpcServiceTest {

  @Mock
  private AmazonEC2 amazonEC2;
  @InjectMocks
  private AmazonVpcService amazonVpcService;
  @Captor
  private ArgumentCaptor<DescribeVpcsRequest> describeVpcsRequestCaptor;
  @Captor
  private ArgumentCaptor<DescribeInternetGatewaysRequest> describeInternetGatewaysRequestCaptor;

  private static final String ENVIRONMENT = "test";
  private static final String CIDR_ADDRESS = "10.0.0.0/16";
  private static final String VPC_ID = "vpc-123";

  @BeforeTest
  public void setup() {
    initMocks(this);
  }

  @Test(description = "The getVpcs method should call describeVpcs with Environment and cidrAddress filters")
  public final void fetchVpcsCallsDescribeVpcs() {

    final DescribeVpcsResult result = new DescribeVpcsResult().withVpcs(new Vpc());
    when(amazonEC2.describeVpcs(any(DescribeVpcsRequest.class))).thenReturn(result);

    amazonVpcService.fetchVpcs(ENVIRONMENT, CIDR_ADDRESS);

    verify(amazonEC2).describeVpcs(describeVpcsRequestCaptor.capture());
    assertThat(describeVpcsRequestCaptor.getValue().getFilters())
        .contains(new Filter("tag:Environment").withValues(ENVIRONMENT),
                  new Filter("cidr").withValues(CIDR_ADDRESS));
  }

  @Test(description = "The getInternetGateways method should call describeInternetGateways with Environment and vpc-id filters")
  public final void fetchInternetGatewaysCallsDescribeInternetGateways() {
    final DescribeInternetGatewaysResult result = new DescribeInternetGatewaysResult().withInternetGateways(new InternetGateway());
    when(amazonEC2.describeInternetGateways(any(DescribeInternetGatewaysRequest.class))).thenReturn(result);

    amazonVpcService.fetchInternetGateways(ENVIRONMENT, VPC_ID);

    verify(amazonEC2).describeInternetGateways(describeInternetGatewaysRequestCaptor.capture());
    assertThat(describeInternetGatewaysRequestCaptor.getValue().getFilters())
        .contains(new Filter("tag:Environment").withValues(ENVIRONMENT),
                  new Filter("attachment.vpc-id").withValues(VPC_ID));
  }
}
