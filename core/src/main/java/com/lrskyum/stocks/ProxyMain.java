/*
 * Copyright (c) 2016.
 * Lars Ruben Skyum
 */

package com.lrskyum.stocks;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 *
 */
public class ProxyMain {
    public static void main(String[] args) {
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withPort(8080)
                        .withFiltersSource(new HttpFiltersSourceAdapter() {
                            public HttpFilters filterRequest(final HttpRequest originalRequest, final ChannelHandlerContext ctx) {
                                return new HttpFiltersAdapter(originalRequest) {
                                    @Override
                                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                        // TODO: implement your filtering here
                                        return null;
                                    }

                                    @Override
                                    public HttpObject serverToProxyResponse(HttpObject httpObject) {
                                        // TODO: implement your filtering here
                                        return httpObject;
                                    }
                                };
                            }
                        }).start();
    }
}
