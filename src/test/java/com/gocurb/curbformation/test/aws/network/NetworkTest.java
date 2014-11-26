package com.gocurb.curbformation.test.aws.network;

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
public class NetworkTest {

  private Network network;

  @Mock
  private VpcService vpcService;

  private static final String ENVIRONMENT = "test";
  private static final String CIDRADDRESS = "10.0.0.0/16";
  private static final String VPCID = "vpc-123";

  @BeforeTest
  public void setup() {
    initMocks(this);
    network = new AmazonNetwork(ENVIRONMENT, vpcService);
  }

  @Test(description = "fetchVpcs should delegate to vpcServce.fetchVpcs")
  public void fetchVpcsDelegatesToService() {
    when(vpcService.fetchVpcs(ENVIRONMENT, CIDRADDRESS)).thenReturn(Collections.EMPTY_LIST);
    network.fetchVpcs(CIDRADDRESS);
    verify(vpcService).fetchVpcs(ENVIRONMENT, CIDRADDRESS);
  }

  @Test(description = "fetchVpcs should delegate to vpcServce.fetchInternetGateways")
  public void fetchInternetGatewaysDelegatesToService() {
    when(vpcService.fetchInternetGateways(ENVIRONMENT, VPCID)).thenReturn(Collections.EMPTY_LIST);
    network.fetchInternetGateways(VPCID);
    verify(vpcService).fetchInternetGateways(ENVIRONMENT, VPCID);
  }
}
