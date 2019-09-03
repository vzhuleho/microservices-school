/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 30.6.19         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.configuration;

import com.kyriba.schoolclassservice.service.externalservices.UserServiceRealClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * @author M-VBE
 * @since 19.2
 */
@Configuration
@Profile("container")
@EnableFeignClients(clients = UserServiceRealClient.UserServiceFeignClient.class)
public class EnableFeignClientsConfiguration
{
}