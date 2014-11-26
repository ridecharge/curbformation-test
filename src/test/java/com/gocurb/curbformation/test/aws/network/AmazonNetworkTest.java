package com.gocurb.curbformation.test.aws.network;

import com.gocurb.curbformation.test.aws.network.subnet.SubnetService;
import com.gocurb.curbformation.test.aws.network.vpc.VpcService;

import org.mockito.Mock;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by sgarlick on 11/26/14.
 */
public class AmazonNetworkTest {

  private Network network;

  @Mock
  private VpcService vpcService;
  @Mock
  private SubnetService subnetService;

  private static final String ENVIRONMENT = "test";
  private static final String CIDR_ADDRESS = "10.0.0.0/16";
  private static final String VPC_ID = "vpc-123";

  @BeforeTest
  public void setup() {
    initMocks(this);
    network = new AmazonNetwork(ENVIRONMENT, vpcService, subnetService);
  }

  @Test(description = "getVpcs should delegate to vpcServce.getVpcs")
  public void fetchVpcsDelegatesToService() {
    when(vpcService.fetchVpcs(ENVIRONMENT, CIDR_ADDRESS)).thenReturn(Collections.EMPTY_LIST);

    network.getVpcs(CIDR_ADDRESS);

    verify(vpcService).fetchVpcs(ENVIRONMENT, CIDR_ADDRESS);
  }

  @Test(description = "getVpcs should delegate to vpcServce.getInternetGateways")
  public void fetchInternetGatewaysDelegatesToService() {
    when(vpcService.fetchInternetGateways(ENVIRONMENT, VPC_ID)).thenReturn(Collections.EMPTY_LIST);

    network.getInternetGateways(VPC_ID);

    verify(vpcService).fetchInternetGateways(ENVIRONMENT, VPC_ID);
  }
}
