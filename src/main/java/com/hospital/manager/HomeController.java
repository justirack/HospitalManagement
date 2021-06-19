/*
  Copyright 2021, Justin Rackley, All rights reserved.
*/
package com.hospital.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * This controller re-directs any GET (i.e. web browser) requests to / to the
 * swagger page.
 * </p>
 */
@Slf4j
@ToString
@Controller
public final class HomeController
{
    /**
     * @return The re-direct string to the swagger page. This cannot be null.
     */
    @RequestMapping ("/")
    public String home()
    {
        log.info("Re-directing to {}.", SWAGGER_PAGE);

        return "redirect:/" + SWAGGER_PAGE;
    }

    // A string constant containing the exact name of the swagger html resource.
    // This cannot be null.
    private static final String SWAGGER_PAGE = "swagger-ui.html";
}